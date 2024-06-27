/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.texts;

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

public class RelativeFont {

    /**
     * Makes the font size responsive and relative to the screens width.
     * Default minimum: 50px.
     * Default max: 100px.
     *
     * @param component
     */
    public static void set(Component component) {
        int minSize = 50;
        int maxSize = 100;
        component.getElement().getStyle()
                .set("font-size", "calc(" + minSize + "px + (" + maxSize + " - " + minSize + ") * ((100vw - 300px) / (1600 - 300)))");
    }

    /**
     * Makes the font size responsive and relative to the screens width.
     * Enter the minimum font-size/height in px (At screen-width 300px).
     * Enter the maximum font-size/height in px (At screen-width 1600px).
     *
     * @param component
     * @param minSize
     * @param maxSize
     */
    public static void set(Component component, String minSize, String maxSize) {
        component.getElement().getStyle()
                .set("font-size", "calc(" + minSize + "px + (" + maxSize + " - " + minSize + ") * ((100vw - 300px) / (1600 - 300)))");
    }

    /**
     * Remove the spacing for this component.
     */
    public static void noSpacing(Component component) {
        component.getElement().getStyle()
                .set("margin", "0")
                .set("padding", "0")
                .set("spacing", "0");
    }

}
