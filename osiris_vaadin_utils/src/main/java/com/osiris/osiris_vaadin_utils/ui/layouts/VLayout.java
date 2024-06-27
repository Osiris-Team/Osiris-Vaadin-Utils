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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class VLayout extends VerticalLayout {
    public VLayout() {
        setPadding(false);
        setMargin(false);
    }

    public VLayout(Component... children) {
        super(children);
        setPadding(false);
        setMargin(false);
    }

    public VLayout(JustifyContentMode justifyContentMode, Component... children) {
        super(children);
        setJustifyContentMode(justifyContentMode);
        setPadding(false);
        setMargin(false);
    }

    public VLayout(Alignment alignment, Component... children) {
        super(children);
        setPadding(false);
        setMargin(false);
        setAlignItems(alignment);
    }

    public VLayout widthFull() {
        setWidthFull();
        return this;
    }

    public VLayout setStyle(String key, String val) {
        getStyle().set(key, val);
        return this;
    }

    public VLayout addAndExpand2(Component... comps) {
        addAndExpand(comps);
        return this;
    }

    public VLayout add2(Component... comps) {
        add(comps);
        return this;
    }

    public VLayout spacing(boolean b) {
        setSpacing(b);
        return this;
    }

    public VLayout justifyStart(){
        setJustifyContentMode(JustifyContentMode.START);
        return this;
    }

    public VLayout justifyCenter(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        return this;
    }

    public VLayout justifyEnd(){
        setJustifyContentMode(JustifyContentMode.END);
        return this;
    }

    public VLayout justifyEven(){
        setJustifyContentMode(JustifyContentMode.EVENLY);
        return this;
    }

    public VLayout justifyBetween(){
        setJustifyContentMode(JustifyContentMode.BETWEEN);
        return this;
    }

    public VLayout horizontalStart(){
        setDefaultHorizontalComponentAlignment(Alignment.START);
        return this;
    }

    public VLayout horizontalCenter(){
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        return this;
    }

    public VLayout horizontalEnd(){
        setDefaultHorizontalComponentAlignment(Alignment.END);
        return this;
    }
}
