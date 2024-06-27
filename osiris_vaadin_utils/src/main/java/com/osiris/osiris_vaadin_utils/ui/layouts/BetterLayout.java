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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

/**
 * A DIV containing an HorizontalLayout, VerticalLayout and a WindowResizeEvent listener.
 * Shows the VerticalLayout when the windows height > width and the HorizontalLayout when height < width.
 */
@Tag("better-layout")
public class BetterLayout extends Div implements FlexComponent {
    private int maxWidthInPixel;
    private int gapSizeInPixel;
    private boolean stretch;

    public BetterLayout() {
        init(400, true, 10);
    }

    public BetterLayout(boolean stretch) {
        init(400, stretch, 10);
    }

    public BetterLayout(Component... components) {
        this.add(components);
        init(400, true, 10);
    }

    /**
     * @param maxWidthInPixel the maximum width one component inside this layout can have.
     * @param stretch         should the component get stretched.
     * @param gapSizeInPixel  gap between each component
     */
    public BetterLayout(int maxWidthInPixel, boolean stretch, int gapSizeInPixel) {
        init(maxWidthInPixel, stretch, gapSizeInPixel);
    }

    private void init(int maxWidthInPixel, boolean stretch, int gapSizeInPixel) {
        this.maxWidthInPixel = maxWidthInPixel;
        this.stretch = stretch;
        this.gapSizeInPixel = gapSizeInPixel;
        this.getStyle().set("display", "flex");
        this.getStyle().set("flex-wrap", "wrap");
        this.getStyle().set("justify-content", "center");
        this.getStyle().set("gap", gapSizeInPixel + "px");
        this.setWidthFull();
    }


    private void updateStyle(Component component) {
        if (stretch)
            component.getElement().getStyle().set("flex", "1 1 " + maxWidthInPixel + "px");
        else
            component.getElement().getStyle().set("flex", "0 1 " + maxWidthInPixel + "px");
    }


    @Override
    public void replace(Component oldComponent, Component newComponent) {
        super.replace(oldComponent, newComponent);
        updateStyle(newComponent);
    }

    @Override
    public void add(Component... components) {
        super.add(components);
        for (Component comp :
                components) {
            updateStyle(comp);
        }
    }

    @Override
    public void add(String text) {
        this.add(new Text(text));
    }

    @Override
    public void addComponentAtIndex(int index, Component component) {
        super.addComponentAtIndex(index, component);
    }

    @Override
    public void addComponentAsFirst(Component component) {
        super.addComponentAsFirst(component);
    }
}
