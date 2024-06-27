/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.notifications;

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


import com.vaadin.flow.component.UI;

public class Notify {

    public static void success() {
        success("Success!");
    }

    public static void success(String text) {
        if (UI.getCurrent() == null) return;
        new SuccessNotification(text).open();
    }


    public static void error(String text) {
        if (UI.getCurrent() == null) return;
        new ErrorNotification(text).open();
    }

    public static void slowDown() {
        info("Please slow down...");
    }

    public static void info(String text) {
        if (UI.getCurrent() == null) return;
        new InfoNotification(text).open();
    }


    public static void accessDenied() {
        error("Access denied! Please login.");
    }
}
