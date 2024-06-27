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

import com.osiris.osiris_vaadin_utils.ui.texts.Text;
import com.osiris.osiris_vaadin_utils.ui.tooltips.TooltipDefault;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.ThemableLayout;
import com.vaadin.flow.dom.Style;

public interface MyStyle extends ThemableLayout,
        FlexComponent {
    String BORDER_STYLE = "1px solid #6e819238";

    static Checkbox checkbox(String label) {
        Checkbox comp = new Checkbox(label);
        comp.setWidthFull();
        comp.getStyle().set("border-bottom", BORDER_STYLE);
        return comp;
    }

    static Text minTitle(String txt) {
        Text comp = new Text(txt).m();
        comp.setWidthFull();
        comp.getStyle().set("margin-top", "2vh");
        comp.getStyle().set("border-bottom", BORDER_STYLE);
        comp.getStyle().set("padding-bottom", "1vh");
        comp.getStyle().set("margin-bottom", "1vh");
        return comp;
    }

    static Text medTitle(String txt) {
        Text comp = new Text(txt).l();
        comp.setWidthFull();
        comp.getStyle().set("margin-top", "2vh");
        comp.getStyle().set("border-bottom", BORDER_STYLE);
        comp.getStyle().set("padding-bottom", "1vh");
        comp.getStyle().set("margin-bottom", "1vh");
        return comp;
    }

    static Text maxTitle(String txt) {
        Text comp = new Text(txt).xl();
        comp.setWidthFull();
        comp.getStyle().set("margin-top", "2vh");
        comp.getStyle().set("border-bottom", BORDER_STYLE);
        comp.getStyle().set("padding-bottom", "1vh");
        comp.getStyle().set("margin-bottom", "1vh");
        return comp;
    }

    static Html divider(){
        Html hr = new Html("<hr></hr>");
        hr.getElement().getStyle().set("border-bottom", BORDER_STYLE);
        return hr;
    }

    static <T extends Component & HasStyle> T tooltip(T comp, String txt) {
        new TooltipDefault(comp, txt);
        return comp;
    }

    static Image img(String src) {
        Image img = new Image();
        img.setSrc(src);
        img.setHeight("100%");
        return img;
    }

    public static class WrappedComp<T extends Component & HasStyle>{
        public T comp;

        public WrappedComp(T comp) {
            this.comp = comp;
        }

        public WrappedComp s(String key, String val){
            comp.getStyle().set(key, val);
            return this;
        }
    }

    static <T extends Component & HasStyle> WrappedComp<T> wrap(T comp) {
        return new WrappedComp<>(comp);
    }

    default void initStyle() {
        setClassName("childCard");
        Style style = getStyle();
        style.set("border", BORDER_STYLE);
        style.set("border-radius", "5px");
    }

    /**
     * Relies on animations for ./shared-styles.
     */
    default void initStyleClickable() {
        setClassName("childCardClickable");
        Style style = getStyle();
        style.set("border", "1px solid #6e819238");
        style.set("border-radius", "5px");
        style.set("animation", "childCardFadeOut 1s");
    }

    default void removeBorders() {
        getStyle().set("border", null);
    }

}
