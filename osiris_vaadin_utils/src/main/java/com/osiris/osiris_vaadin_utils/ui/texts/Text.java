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

import com.osiris.osiris_vaadin_utils.ui.JS;
import com.osiris.osiris_vaadin_utils.ui.tooltips.TooltipDefault;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Text extends VerticalLayout {
    private String text = "";

    /**
     * A text element but with a url.
     *
     * @param text
     * @param url
     */
    public Text(String text, String url) {
        this(text);
        this.getElement().setAttribute("onclick", "location.href='" + url + "'");
    }

    /**
     * Create a new text with medium size.
     */
    public Text(String text) {
        set(text);
        m();
        setPadding(false);
        setMargin(false);
        setSpacing(false);
        setWidth(null);
    }

    /**
     * Text can also be html.
     */
    public Text set(String text) {
        this.text = text;
        removeAll();

        if (text != null) {
            Html html = null;
            try {
                html = new Html(text);
            } catch (Exception ignored) {
            }

            if (html != null)
                add(html);
            else
                add(text);
        } else {
            add("null");
        }
        return this;
    }

    public String get() {
        return text;
    }

    public Text appendComp(Component comp) {
        add(comp);
        return this;
    }

    public Text appendText(String text) {
        add(text);
        return this;
    }

    public Text appendLink(String url, String text) {
        add(new Html("<a href=\"" + url + "\">" + text + "</a>"));
        return this;
    }

    public Text setStyle(String name, String value) {
        this.getStyle().set(name, value);
        return this;
    }

    public void setMaxSize() {
        this.setClassName("max-text");
    }

    public Text xxs() {
        setStyle("font-size", "var(--lumo-font-size-xxs)");
        return this;
    }

    public Text xs() {
        setStyle("font-size", "var(--lumo-font-size-xs)");
        return this;
    }

    public Text s() {
        setStyle("font-size", "var(--lumo-font-size-s)");
        return this;
    }

    public Text m() {
        setStyle("font-size", "var(--lumo-font-size-m)");
        return this;
    }

    public Text l() {
        setStyle("font-size", "var(--lumo-font-size-l)");
        return this;
    }

    public Text xl() {
        setStyle("font-size", "var(--lumo-font-size-xl)");
        return this;
    }

    public Text xxl() {
        setStyle("font-size", "var(--lumo-font-size-xxl)");
        return this;
    }

    public Text xxxl() {
        setStyle("font-size", "var(--lumo-font-size-xxxl)");
        return this;
    }

    /**
     * Remove the spacing for this component.
     * Note that this destroys/ignores the padding.
     */
    public Text removeWhiteSpace() {

        this.getStyle()
                .set("margin", "0")
                .set("padding", "0")
                .set("spacing", "0");
        return this;
    }

    public Text padding(String val) {
        this.getStyle()
                .set("padding", val);
        return this;
    }

    public Text paddingTopBottom(String val) {
        this.getStyle()
                .set("padding-top", val)
                .set("padding-bottom", val);
        return this;
    }

    public Text margin(String val) {
        this.getStyle()
                .set("margin", val);
        return this;
    }

    public Text spacing(String val) {
        this.getStyle()
                .set("spacing", val);
        return this;
    }

    public Text alignStart() {
        this.getStyle().set("text-align", "start");
        return this;
    }

    public Text alignJustify() {
        this.getStyle().set("text-align", "justify");
        return this;
    }

    public Text alignCenter() {
        this.getStyle().set("text-align", "center");
        return this;
    }

    public Text alignCenterXY() {
        this.setAlignItems(FlexComponent.Alignment.CENTER);
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        alignCenter();
        return this;
    }

    public Text alignEnd() {
        this.getStyle().set("text-align", "end");
        return this;
    }

    public Text setColor(String color) {
        setStyle("color", color);
        return this;
    }

    public Text bold() {
        setStyle("font-weight", "bold");
        return this;
    }

    public Text tooltip(String s) {
        new TooltipDefault(this, s);
        return this;
    }

    public Text widthMax(String s){
        setMaxWidth(s);
        return this;
    }
    public Text heightMax(String s){
        setMaxHeight(s);
        return this;
    }

    public Text expandableText(String txt) {
        return expandableText(txt, 15);
    }
    public Text expandableText(String txt, int charsToShow) {
        String shortTxt = txt.substring(0, Math.min(charsToShow + 1, txt.length())) + "...";
        H3 comp = new H3(shortTxt);
        appendComp(comp);
        comp.addAttachListener(e -> {
            if (!e.isInitialAttach()) return;
            JS.executeJs("$0.addEventListener('mouseenter', (e) => {$0.innerHTML = `" + txt + "`;});\n" +
                    "$0.addEventListener('mouseleave', (e) => {$0.innerHTML = `" + shortTxt + "`;});\n", comp);
        });
        comp.getStyle().set("color", "#6e6e6e");
        return this;
    }

    public Text makeThisExpandable(String txt, int charsToShow) {
        String shortTxt = txt.substring(0, Math.min(charsToShow + 1, txt.length())) + "...";
        set(shortTxt);
        this.addAttachListener(e -> {
            if (!e.isInitialAttach()) return;
            JS.executeJs("$0.addEventListener('mouseenter', (e) => {$0.innerHTML = `" + txt + "`;});\n" +
                    "$0.addEventListener('mouseleave', (e) => {$0.innerHTML = `" + shortTxt + "`;});\n", this);
        });
        return this;
    }

    public Text widthFull() {
        setWidthFull();
        return this;
    }

    public Text padding(boolean b) {
        if(b) getStyle().set("padding", "var(--lumo-space-m)");
        else getStyle().set("padding", null);
        return this;
    }

    public Div newAutoScrollContainerWithThis(){
        Div scrollContainer = new Div();
        scrollContainer.setId("scroll-container");
        scrollContainer.setSizeFull();
        // Create the scroll text element
        Div scrollText = new Div();
        scrollText.setId("scroll-text");
        scrollText.add(this);

        // Add the scroll text to the scroll container
        scrollContainer.add(scrollText);

        // Add CSS to the component
        String styles =
                "#scroll-container {" +
                        //"  border: 3px solid black;" +
                        //"  border-radius: 5px;" +
                        "  overflow: hidden;" +
                        "}" +
                        "#scroll-text {" +
                        "  transform: translateX(0%);" +
                        "}" +
                        "#scroll-container:hover #scroll-text {" +
                        "  animation: my-animation 15s linear infinite;" +
                        "}" +
                        "@keyframes my-animation {" +
                        "  from { transform: translateX(50%); }" + // Support centered text, thus start further before
                        "  to { transform: translateX(-100%); }" +
                        "}";

        // Inject styles into the page
        scrollContainer.getElement().getNode().runWhenAttached(ui ->
                ui.getPage().executeJs(
                        "const style = document.createElement('style');" +
                                "style.textContent = $0;" +
                                "document.head.appendChild(style);",
                        styles
                )
        );
        return scrollContainer;
    }
}
