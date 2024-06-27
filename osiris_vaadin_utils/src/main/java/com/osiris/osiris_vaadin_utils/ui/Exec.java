/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui;

/*-
 * #%L
 * Osiris-Vaadin-Utils
 * %%
 * Copyright (C) 2021 - 2024 Vaadin Ltd
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.osiris.events.BetterConsumer;
import com.osiris.events.Event;
import com.osiris.osiris_vaadin_utils.AL;
import com.osiris.osiris_vaadin_utils.misc.RunnableWithException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.shared.communication.PushMode;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// NOTE THIS CLASS REQUIRES FOLLOWING CODE IN UI INIT EVENT:
// if (ui.getPushConfiguration().getPushMode() != PushMode.AUTOMATIC)
//   ui.getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
// OR
// @Push on your AppShellConfigurator class.
public class Exec {
    public static final Event<ExecutionDetails> onExecution = new Event<ExecutionDetails>()
            .initCleaner(60, comp -> !((Component) comp).isAttached(), AL::warn);
    private static final Map<UI, List<RunnableWithException>> mapUiAndRunnables = new HashMap<>();
    private static final Thread uiCodeExecutor = new Thread(() -> {
        try {
            long ms = 0;
            int count;
            while (true) {
                Thread.sleep(1000);
                ms = System.currentTimeMillis();
                count = 0;
                synchronized (mapUiAndRunnables) {
                    for (Map.Entry<UI, List<RunnableWithException>> entry : mapUiAndRunnables.entrySet()) {
                        UI ui = entry.getKey();
                        List<RunnableWithException> runnables = entry.getValue();
                        // accessSynchronously causes issues // push() inside here with PushMode.MANUAL
                        // also causes issues (infinite loop, recursion aka stackoverflow)
                        if (!ui.isClosing()) {
                            count++;
                            ui.access(() -> {
                                // Execute all runnables for this UI within one access/push.
                                for (RunnableWithException runnable : runnables) {
                                    try {
                                        runnable.run();
                                    } catch (Exception e) {
                                        AL.warn(e);
                                    }
                                }
                            });
                        }
                    }
                    mapUiAndRunnables.clear();
                }
                onExecution.execute(new ExecutionDetails(System.currentTimeMillis() - ms, count));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    });
    /**
     * Single Runnable that executes runnables in their specified intervals. <br>
     * 1 interval == 1 second.
     */
    public static ExecutorService virtual = Executors.newVirtualThreadPerTaskExecutor();
    public static ExecutorService real = Executors.newCachedThreadPool();

    static {
        uiCodeExecutor.start();
    }

    /**
     * Executes the provided code now in current thread.
     */
    public static void now(Runnable code) {
        code.run();
    }

    /**
     * Executes the provided code later in another thread.
     */
    public static void later(Runnable code) {
        virtual.execute(code);
    }

    public static void laterReal(Runnable code) {
        real.execute(code);
    }

    /**
     * Executes the provided UI code now in current thread, but
     * with exclusive access to the UI. <br>
     * Changes done to the UI are pushed to the client. <br>
     */
    public static void now(Component comp, RunnableWithException uiCode) {
        UI ui = null;
        if (comp instanceof UI) ui = (UI) comp;
        else ui = comp.getUI().orElse(null); // Returns null when not attached to an UI yet
        if (ui == null) {
            ui = UI.getCurrent();
            Objects.requireNonNull(ui);
        }

        if (ui.isAttached())
            ui.access(() -> { // accessSynchronously causes issues // push() inside here with PushMode.MANUAL
                // also causes issues (infinite loop, recursion aka stackoverflow)
                try {
                    uiCode.run();
                } catch (Exception e) {
                    AL.warn(e);
                }
            });
    }

    /**
     * Executes the provided code later in another thread. <br>
     * The code consumer provides a UIHelper object as parameter that helps
     * to execute stuff in the UI context and pushing it.
     */
    public static void later(Component comp, BetterConsumer<UIHelper> code) {
        UI ui = null;
        if (comp instanceof UI) ui = (UI) comp;
        else ui = comp.getUI().orElse(null); // Returns null when not attached to an UI yet
        if (ui == null) {
            ui = UI.getCurrent();
            Objects.requireNonNull(ui);
        }
        ui.getPushConfiguration().setPushMode(PushMode.AUTOMATIC);

        UIHelper uiHelper = new UIHelper(ui);
        Runnable wrapperCode = () -> {
            try {
                code.accept(uiHelper);
            } catch (Exception e) {
                AL.warn(e);
            }
        };
        virtual.execute(wrapperCode);
    }

    /**
     * Executes the provided UI code later in another thread. <br>
     * The code will be added to {@link #mapUiAndRunnables}. All that code
     * will then be executed withing the UI context and pushed in one request
     * instead of one request per code. <br>
     * The list is checked periodically by {@link #uiCodeExecutor}.
     */
    public static void laterBatched(Component comp, RunnableWithException uiCode) {
        UI ui = null;
        if (comp instanceof UI) ui = (UI) comp;
        else ui = comp.getUI().orElse(null); // Returns null when not attached to an UI yet
        if (ui == null) {
            ui = UI.getCurrent();
            Objects.requireNonNull(ui);
        }
        ui.getPushConfiguration().setPushMode(PushMode.AUTOMATIC);

        synchronized (mapUiAndRunnables) {
            List<RunnableWithException> list = mapUiAndRunnables.get(ui);
            if (list == null) {
                list = new ArrayList<>();
                mapUiAndRunnables.put(ui, list);
            }
            list.add(uiCode);
        }
    }

    public static class ExecutionDetails {
        public final long msTook;
        public final int countExecuted;

        public ExecutionDetails(long msTook, int countExecuted) {
            this.msTook = msTook;
            this.countExecuted = countExecuted;
        }
    }

    public static class UIHelper {
        public UI ui;

        public UIHelper(UI ui) {
            this.ui = ui;
        }

        /**
         * Code is not executed right away in the UI context, but
         * later together with all the other code meant to be executed in that UI. <br>
         * Should increase performance.
         */
        public void pushBatched(RunnableWithException code) {
            Exec.laterBatched(ui, code);
        }

        public void push(RunnableWithException code) {
            Exec.now(ui, code);
        }
    }
}
