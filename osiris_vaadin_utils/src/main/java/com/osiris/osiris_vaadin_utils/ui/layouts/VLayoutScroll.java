/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.layouts;

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

import com.osiris.osiris_vaadin_utils.ui.JS;
import com.vaadin.flow.component.Component;

//@Tag("layout-scroll") // Do NOT rename. This will cause margin/padding issues for child layouts
public class VLayoutScroll extends VLayout { // Was Div before, not sure if it works as vertical layout

    public VLayoutScroll() {
        preparePanel();
    }

    public VLayoutScroll(Component... children) {
        super(children);
        preparePanel();
    }

    private void preparePanel() {
        setWidth("100%");
        setHeight("100%");
        this.getStyle().set("overflow", "auto");
        this.setPadding(false);
        this.setMargin(false);
        this.setSpacing(true);
    }

    public void scrollDown() {
        JS.executeJsNext("$0.scrollBy({top:1000000, left:0, behavior: \"smooth\"});", this);
    }

    public void scrollUp() {
        JS.executeJsNext("$0.scrollBy({top:0, left:0, behavior: \"smooth\"});", this);
    }

    public VLayoutScroll add2(Component... comp) {
        add(comp);
        return this;
    }
}
