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

import com.osiris.osiris_vaadin_utils.ui.texts.Text;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.progressbar.ProgressBar;

public class SectionLoading extends VLayout {
    public static String RED_CROSS_EMOJI = "❌";
    public static String ROCKET_EMOJI = "\uD83D\uDE80";
    public static String MEME_FACE_EMOJI = "\uD83D\uDDFF";
    private final Text txtTitle = new Text("Loading...").xl();
    private final ProgressBar progressBar = new ProgressBar();
    private final Text txtDescription = new Text("Fetching data, please be patient.");

    public SectionLoading() {
        setPadding(true);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setSizeFull();
        add(txtTitle, progressBar, txtDescription);
        progressBar.setIndeterminate(true);
    }

    public void setLoadingView(){
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);
        txtTitle.set("Loading...");
        txtDescription.set("Fetching data, please be patient.");
    }

    public void setLoadingView(String title, String description){
        progressBar.setVisible(true);
        progressBar.setIndeterminate(true);
        txtTitle.set(title);
        txtDescription.set(description);
    }

    public SectionLoading setErrorView() {
        setErrorView(new Exception());
        return this;
    }

    public SectionLoading setErrorView(Exception e) {
        txtTitle.set(RED_CROSS_EMOJI);
        progressBar.setVisible(false);
        txtDescription.set("Something went really wrong.");
        add(e.getMessage());
        return this;
    }

    public SectionLoading setEmptyView() {
        txtTitle.set(MEME_FACE_EMOJI);
        progressBar.setVisible(false);
        txtDescription.set("Nothing to show here yet.");
        return this;
    }

    public SectionLoading setTitle(String txt) {
        txtTitle.set(txt);
        return this;
    }

    public SectionLoading setDescription(String txt) {
        txtDescription.set(txt);
        return this;
    }

    public Text getTxtTitle() {
        return txtTitle;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Text getTxtDescription() {
        return txtDescription;
    }
}
