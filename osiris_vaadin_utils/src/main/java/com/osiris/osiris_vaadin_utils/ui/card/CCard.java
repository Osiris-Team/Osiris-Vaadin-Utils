/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.card;

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

import com.osiris.osiris_vaadin_utils.ui.MyStyle;
import com.osiris.osiris_vaadin_utils.ui.layouts.VLayout;
import com.vaadin.flow.component.Component;

// Note that this uses styles from shared_styles.css
public class CCard extends VLayout implements MyStyle {
    public CCard() {
        initStyle();
    }

    public CCard(Theme theme) {
        initStyle();
        setTheme(theme);
    }

    public CCard(Component... children) {
        super(children);
        initStyle();
    }

    public CCard(Theme theme, Component... children) {
        super(children);
        initStyle();
        setTheme(theme);
    }

    public CCard clickable(boolean b){
        if(b) initStyleClickable();
        else initStyle();
        return this;
    }

    private void setTheme(Theme theme) {
        if (theme == Theme.CENTERED) {
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        } else if (theme == Theme.END) {
            setJustifyContentMode(JustifyContentMode.END);
            setDefaultHorizontalComponentAlignment(Alignment.END);
        }
    }

    public CCard add2(Component... c) {
        add(c);
        return this;
    }

    public CCard widthFull() {
        setWidthFull();
        return this;
    }

    public CCard widthMax(String s) {
        setMaxWidth(s);
        return this;
    }

    public CCard heightFull() {
        setHeightFull();
        return this;
    }

    public CCard padding(boolean b) {
        setPadding(b);
        return this;
    }

    public enum Theme {
        CENTERED, END
    }
}
