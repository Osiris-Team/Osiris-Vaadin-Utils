/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.popups;

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

import com.osiris.osiris_vaadin_utils.ui.layouts.HLayout;
import com.osiris.osiris_vaadin_utils.ui.layouts.VLayout;
import com.osiris.osiris_vaadin_utils.ui.layouts.VLayoutScroll;
import com.osiris.osiris_vaadin_utils.ui.texts.Text;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class Popup extends Dialog {
    public VLayout parentLayout;
    public VLayout headerLayout;
    public HLayout btnLayout;
    public String title;
    public Component customTitle;
    public VLayoutScroll contentWrapper;
    public Component content;
    public Component noBtn;
    public Component yesBtn;

    public Popup() {
        try {
            if (UI.getCurrent() == null)
                return;
        } catch (Exception exception) {
            return;
        }
        parentLayout = new VLayout();
        parentLayout.setSizeFull();
        headerLayout = new VLayout();
        btnLayout = new HLayout();
        btnLayout.setWidthFull();
        customTitle = null;
        contentWrapper = new VLayoutScroll();
        contentWrapper.setWidthFull();
        content = new Text("Missing content!");

        parentLayout.setMargin(false);
        parentLayout.setPadding(false);
        parentLayout.setSpacing(true);
        headerLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        headerLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        this.setWidth("400px");
        this.setMaxHeight("500px");
        this.add(parentLayout);
    }

    public Popup buildAndOpen() {
        build();
        setOpened(true);
        return this;
    }

    public Popup build() {
        parentLayout.removeAll();

        if (customTitle != null) {
            parentLayout.add(customTitle);
        } else if (title != null) {
            headerLayout.add(new Text(title));
            parentLayout.add(headerLayout);
        }

        if (content != null) {
            contentWrapper.addAndExpand(content);
            parentLayout.addAndExpand(contentWrapper);
        }

        if (noBtn != null || yesBtn != null || btnLayout.getChildren().toArray().length != 0) {
            parentLayout.add(btnLayout);
        }

        if (!this.getThemeNames().isEmpty()) {// There are only 2 theme variants, and both are no padding themes, thats why this check is enough.
            btnLayout.setPadding(true);
        }
        return this;
    }

    public Popup setComponentAsTitle(Component component) {
        customTitle = component;
        return this;
    }

    public Popup setTitle(String title) {
        this.title = title;
        return this;
    }

    public Popup setContent(Component content) {
        if (content != null) this.content = content;
        return this;
    }

    public Popup setNoBtn(Component btn) {
        if (btn != null) {
            this.noBtn = btn;
            btnLayout.add(noBtn);
            btnLayout.expand(noBtn);
        }
        return this;
    }

    /**
     * Creates a new button with the label 'Close', which closes this popup.
     */
    public Popup setNoBtn() {
        Button btn = new Button("Close");
        btn.addClickListener(e -> close());
        setNoBtn(btn);
        return this;
    }

    /**
     * Creates a new button which closes this popup.
     */
    public Popup setNoBtn(String name) {
        Button btn = new Button(name);
        btn.addClickListener(e -> close());
        setNoBtn(btn);
        return this;
    }

    public Popup setNoBtn(Button btn) {
        if (btn != null) {
            btn.addThemeVariants(ButtonVariant.LUMO_ERROR);
            this.noBtn = btn;
            btnLayout.add(noBtn);
            btnLayout.expand(noBtn);
        }
        return this;
    }

    /**
     * Creates a new button which closes this popup.
     */
    public Popup setYesBtn(String name) {
        Button btn = new Button(name);
        btn.addClickListener(e -> close());
        setYesBtn(btn);
        return this;
    }

    public Popup setYesBtn(Component btn) {
        if (btn != null) {
            this.yesBtn = btn;
            btnLayout.add(yesBtn);
            btnLayout.expand(yesBtn);
        }
        return this;
    }

    public Popup setYesBtn(Button btn) {
        if (btn != null) {
            btn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            this.yesBtn = btn;
            btnLayout.add(yesBtn);
            btnLayout.expand(yesBtn);
        }
        return this;
    }
}
