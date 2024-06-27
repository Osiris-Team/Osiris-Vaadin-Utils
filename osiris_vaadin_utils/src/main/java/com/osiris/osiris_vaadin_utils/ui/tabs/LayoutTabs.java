/*
 * Copyright Osiris Team
 * All rights reserved.
 *
 * This software is copyrighted work licensed under the terms of the
 * AutoPlug License.  Please consult the file "LICENSE" for details.
 */

package com.osiris.osiris_vaadin_utils.ui.tabs;

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

import com.osiris.jlib.logger.AL;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//@Tag("layout-tabs") // Do NOT rename. This will cause margin/padding issues for child layouts
public class LayoutTabs extends VerticalLayout {
    private Tabs containerTabs = new Tabs();
    private Map<Tab, VerticalLayout> tabsAndPages = new HashMap<>();

    public LayoutTabs() {
        init();
    }

    public LayoutTabs(Component... components) {
        super(components);
        init();
    }

    private void init() {
        this.setPadding(false);
        this.setMargin(false);
        this.setSpacing(true);
        this.add(containerTabs);
        containerTabs.setWidthFull();
        containerTabs.addSelectedChangeListener(event -> {
            Collection<VerticalLayout> values = tabsAndPages.values();
            if (!values.isEmpty()) {
                values.forEach(page -> page.setVisible(false));
                Component selectedPage = tabsAndPages.get(containerTabs.getSelectedTab());
                selectedPage.setVisible(true);
            }
        });
    }

    /**
     * See {@link #addTabAndPage(Tab, Component...)} for details.
     */
    public LayoutTabs addTabAndPage(String tabName, Component... components) {
        this.addTabAndPage(new Tab(tabName), components);
        return this;
    }

    /**
     * Adds components to the tab. No nulls allowed.
     * No duplicate tabs allowed. Adds the tabs/pages if they weren't added yet.
     *
     * @param tab        the tab to add components to.
     * @param components the components to add.
     */
    public LayoutTabs addTabAndPage(Tab tab, Component... components) {
        try {
            VerticalLayout page = new VerticalLayout();
            addTabAndSetPage(tab, page);

            for (Component component : components) {
                if (component == null) throw new Exception("Component to add cannot be null");
                page.add(component);
            }
        } catch (Exception e) {
            AL.warn(e);
        }
        return this;
    }

    public LayoutTabs addTabAndSetPage(String tabName, VerticalLayout page) {
        this.addTabAndSetPage(new Tab(tabName), page);
        return this;
    }

    /**
     * Adds components to the tab. No nulls allowed.
     * No duplicate tabs allowed. Adds the tabs/pages if they weren't added yet.
     *
     * @param tab        the tab to add components to.
     * @param page the page to add.
     */
    public LayoutTabs addTabAndSetPage(Tab tab, VerticalLayout page) {
        try {
            if (tab == null) throw new Exception("Tab shouldn't be null!");
            if (page == null) throw new Exception("Page should not be null!");

            // Add the tab only if it doesn't exist yet
            if (!tabsAndPages.containsKey(tab)) {
                page.setSizeFull();
                page.setPadding(false);
                //ChildLayout.getStyle().set("margin-top", "var(--lumo-space-m)");
                containerTabs.add(tab);
                this.add(page);
                tabsAndPages.put(tab, page);
            } else {
                page = tabsAndPages.get(tab);
            }
            page.setVisible(false);

            // If this is the first tab added, make its content visible.
            if (tabsAndPages.size() == 1) {
                for (VerticalLayout v :
                        tabsAndPages.values()) {
                    v.setVisible(true);
                    break;
                }
            }
        } catch (Exception e) {
            AL.warn(e);
        }
        return this;
    }

    public VerticalLayout getTabPage(Tab tab) {
        return tabsAndPages.get(tab);
    }

    /**
     * Searches the tab by comparing the tabs labels.
     * Note that the case is ignored.
     *
     * @param tabLabel
     * @return
     * @throws Exception
     */
    public LayoutTabs setSelectedTab(String tabLabel) throws Exception {
        for (Tab t :
                tabsAndPages.keySet()) {
            if (t.getLabel().equalsIgnoreCase(tabLabel)) {
                containerTabs.setSelectedTab(t);
                return this;
            }
        }
        throw new Exception("Tab label '" + tabLabel + "' couldn't be found!");
    }

    public LayoutTabs setSelectedTab(Tab tab) {
        containerTabs.setSelectedTab(tab);
        return this;
    }

    public LayoutTabs setAlignmentTabs(Alignment alignment) {
        containerTabs.getStyle().set("alignContent", alignment.name());
        return this;
    }

    public LayoutTabs setAlignmentTab(Tab tab, Alignment alignment) {
        tab.getStyle().set("alignSelf", alignment.name());
        return this;
    }

    public Map<Tab, VerticalLayout> getTabsAndPages() {
        return tabsAndPages;
    }

    public LayoutTabs setTabsAndPages(Map<Tab, VerticalLayout> tabsAndPages) {
        this.tabsAndPages = tabsAndPages;
        return this;
    }

    public Tabs getContainerTabs() {
        return containerTabs;
    }

    public LayoutTabs setContainerTabs(Tabs containerTabs) {
        this.containerTabs = containerTabs;
        return this;
    }

}
