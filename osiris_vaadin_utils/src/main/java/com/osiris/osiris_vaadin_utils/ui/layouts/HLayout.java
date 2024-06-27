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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class HLayout extends HorizontalLayout {
    public HLayout() {
        setPadding(false);
        setMargin(false);
    }

    public HLayout(Component... children) {
        super(children);
        setPadding(false);
        setMargin(false);
    }

    public HLayout(JustifyContentMode justifyContentMode, Component... children) {
        super(children);
        setPadding(false);
        setMargin(false);
        setJustifyContentMode(justifyContentMode);
    }

    public HLayout(Alignment alignment, Component... children) {
        super(children);
        setPadding(false);
        setMargin(false);
        setAlignItems(alignment);
    }

    public HLayout widthFull() {
        setWidthFull();
        return this;
    }

    public HLayout setStyle(String key, String val) {
        getStyle().set(key, val);
        return this;
    }

    public HLayout addAndExpand2(Component... comps) {
        addAndExpand(comps);
        return this;
    }

    public HLayout add2(Component... comps) {
        add(comps);
        return this;
    }

    public HLayout justifyStart(){
        setJustifyContentMode(JustifyContentMode.START);
        return this;
    }

    public HLayout justifyCenter(){
        setJustifyContentMode(JustifyContentMode.CENTER);
        return this;
    }

    public HLayout justifyEnd(){
        setJustifyContentMode(JustifyContentMode.END);
        return this;
    }

    public HLayout justifyEven(){
        setJustifyContentMode(JustifyContentMode.EVENLY);
        return this;
    }

    public HLayout justifyBetween(){
        setJustifyContentMode(JustifyContentMode.BETWEEN);
        return this;
    }

    public HLayout verticalStart(){
        setDefaultVerticalComponentAlignment(Alignment.START);
        return this;
    }

    public HLayout verticalCenter(){
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        return this;
    }

    public HLayout verticalEnd(){
        setDefaultVerticalComponentAlignment(Alignment.END);
        return this;
    }
}
