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
import com.osiris.osiris_vaadin_utils.ui.layouts.HLayout;
import com.vaadin.flow.component.Component;

// Note that this uses styles from shared_styles.css
public class CCardHorizontal extends HLayout implements MyStyle {
    public CCardHorizontal() {
        initStyle();
    }

    public CCardHorizontal(Component... children) {
        super(children);
        initStyle();
    }

    public CCardHorizontal clickable(boolean b){
        if(b) initStyleClickable();
        else initStyle();
        return this;
    }

    public CCardHorizontal padding() {
        setPadding(true);
        return this;
    }

    public CCardHorizontal add_(Component... c) {
        add(c);
        return this;
    }

    public CCardHorizontal addAndExpand_(Component... c) {
        addAndExpand(c);
        return this;
    }

    public CCardHorizontal widthFull() {
        setWidthFull();
        return this;
    }

    public CCardHorizontal centerY() {
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return this;
    }

    public CCardHorizontal centerX() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        return this;
    }
}
