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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.PendingJavaScriptResult;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JS {

    public static ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * @see com.vaadin.flow.component.page.Page#executeJs(String, Serializable...)
     */
    public static PendingJavaScriptResult executeJs(String expression,
                                                    Serializable... parameters) {
        return UI.getCurrent().getPage().executeJs(tryCatch(expression), parameters);
    }

    private static String tryCatch(String expression) {
        return "try{" + expression + "}catch(e){console.error(e);}";
    }

    /**
     * @see com.vaadin.flow.component.page.Page#executeJs(String, Serializable...)
     */
    public static void executeJs(UI ui, String expression,
                                 Serializable... parameters) {
        ui.access(() -> {
            ui.getPage().executeJs(tryCatch(expression), parameters);
        });
    }

    /**
     * Executes the provided JavaScript code in the next request not the current one. <br>
     * Useful when needing to do JavaScript stuff on newly created vaadin components,
     * which are currently not available via JavaScript yet, but will be once the request finishes. <br>
     *
     * @see com.vaadin.flow.component.page.Page#executeJs(String, Serializable...)
     */
    public static void executeJsNext(String expression,
                                     Serializable... parameters) {
        UI ui = UI.getCurrent();
        executor.execute(() -> {
            ui.access(() -> {
                ui.getPage().executeJs(tryCatch(expression), parameters);
            });
        });
    }

    public static void executeJsNext(UI ui, String expression,
                                     Serializable... parameters) {
        executor.execute(() -> {
            ui.access(() -> {
                ui.getPage().executeJs(tryCatch(expression), parameters);
            });
        });
    }

    public static void scrollIntoView(Component parentLayout, Component comp) {
        JS.executeJsNext("$0.scrollTop = $1.offsetTop;", parentLayout, comp);
    }

    public static void openNewTab(String url) {
        JS.executeJs("window.open(`" + url + "`, '_blank').focus()");
    }
}
