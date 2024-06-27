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

import com.vaadin.flow.component.html.Span;

public class Badge extends Span {

    public Badge(String text) {
        super(text);
        pending();
    }

    public Badge pending() {
        this.getElement().getThemeList().add("badge");
        return this;
    }

    public Badge confirmed() {
        this.getElement().getThemeList().add("badge success");
        return this;
    }

    public Badge denied() {
        this.getElement().getThemeList().add("badge error");
        return this;
    }

    public Badge onHold() {
        this.getElement().getThemeList().add("badge contrast");
        return this;
    }
}
