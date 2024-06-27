/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.tooltips;

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
import com.vaadin.flow.component.HasStyle;
import dev.mett.vaadin.tooltip.Tooltips;
import dev.mett.vaadin.tooltip.config.TooltipConfiguration;

public class TooltipDefault {

    /**
     * This adds a Tooltip to the wanted component. With a custom Tooltip layout.
     *
     * @param on    The component on which the tooltip should be showed.
     * @param desc  The description.
     * @param <T>
     */
    public <T extends Component & HasStyle> TooltipDefault(final T on, final String desc) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div>");
        if (desc != null && !desc.isEmpty()) {
            htmlBuilder.append("<p style=\"color=white;\">" + desc + "</p>");
        }
        htmlBuilder.append("</div>");
        Tooltips.getCurrent().setTooltip(on, htmlBuilder.toString());
    }

    /**
     * This adds a Tooltip to the wanted component. With a custom Tooltip layout.
     *
     * @param on    The component on which the tooltip should be showed.
     * @param title The title.
     * @param desc  The description.
     * @param <T>
     */
    public <T extends Component & HasStyle> TooltipDefault(final T on, final String title, final String desc, TooltipConfiguration config) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<div>");
        if (title != null && !title.isEmpty()) {
            htmlBuilder.append("<b style=\"color=white;\">" + title + "</b>");
        }
        if (desc != null && !desc.isEmpty()) {
            htmlBuilder.append("<p style=\"color=white;\">" + desc + "</p>");
        }
        htmlBuilder.append("</div>");
        config.setContent(htmlBuilder.toString());
        Tooltips.getCurrent().setTooltip(on, config);
    }

}
