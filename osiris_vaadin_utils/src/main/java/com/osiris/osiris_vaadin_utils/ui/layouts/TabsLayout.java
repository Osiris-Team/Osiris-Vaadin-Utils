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

import com.osiris.osiris_vaadin_utils.AL;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Tag("layout-tabs") // Do NOT rename. This will cause margin/padding issues for child layouts
public class TabsLayout extends VLayout {
    private Tabs containerTabs = new Tabs();
    private Map<Tab, VLayout> tabsAndPages = new HashMap<>();

    public TabsLayout() {
        init();
    }

    public TabsLayout(Component... components) {
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
            Collection<VLayout> values = tabsAndPages.values();
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
    public TabsLayout addTabAndPage(String tabName, Component... components) {
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
    public TabsLayout addTabAndPage(Tab tab, Component... components) {
        try {
            if (tab == null) throw new Exception("Tab shouldn't be null!");
            if (components == null) throw new Exception("Components should not be null!");

            // Add the tab only if it doesn't exist yet
            VLayout vl = null;
            if (!tabsAndPages.containsKey(tab)) {
                vl = new VLayout();
                vl.setSizeFull();
                vl.setPadding(false);
                //ChildLayout.getStyle().set("margin-top", "var(--lumo-space-m)");
                containerTabs.add(tab);
                this.add(vl);
                tabsAndPages.put(tab, vl);
            } else {
                vl = tabsAndPages.get(tab);
            }
            vl.setVisible(false);

            // If this is the first tab added, make its content visible.
            if (tabsAndPages.size() == 1) {
                for (VLayout v :
                        tabsAndPages.values()) {
                    v.setVisible(true);
                    break;
                }
            }

            for (Component component : components) {
                if (component == null) throw new Exception("Component to add cannot be null");
                vl.add(component);
            }
        } catch (Exception e) {
            AL.warn(e);
        }
        return this;
    }

    public VLayout getTabPage(Tab tab) {
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
    public TabsLayout setSelectedTab(String tabLabel) throws Exception {
        for (Tab t :
                tabsAndPages.keySet()) {
            if (t.getLabel().equalsIgnoreCase(tabLabel)) {
                containerTabs.setSelectedTab(t);
                return this;
            }
        }
        throw new Exception("Tab label '" + tabLabel + "' couldn't be found!");
    }

    public TabsLayout setSelectedTab(Tab tab) {
        containerTabs.setSelectedTab(tab);
        return this;
    }

    public TabsLayout setAlignmentTabs(FlexComponent.Alignment alignment) {
        containerTabs.getStyle().set("alignContent", alignment.name());
        return this;
    }

    public TabsLayout setAlignmentTab(Tab tab, FlexComponent.Alignment alignment) {
        tab.getStyle().set("alignSelf", alignment.name());
        return this;
    }

    public Map<Tab, VLayout> getTabsAndPages() {
        return tabsAndPages;
    }

    public Map<Tab, List<Component>> getTabsAndContents() {
        Map<Tab, List<Component>> map = new HashMap<>();
        tabsAndPages.forEach((tab, page) -> {
            map.put(tab, page.getChildren().toList());
        });
        return map;
    }

    public Map<Tab, Component> getTabsAndFirstContents() {
        Map<Tab, Component> map = new HashMap<>();
        getTabsAndContents().forEach((tab, contents) -> {
            if(contents.isEmpty()) map.put(tab, null);
            else map.put(tab, contents.get(0));
        });
        return map;
    }

    public TabsLayout setTabsAndPages(Map<Tab, VLayout> tabsAndPages) {
        this.tabsAndPages = tabsAndPages;
        return this;
    }

    public Tabs getContainerTabs() {
        return containerTabs;
    }

    public TabsLayout setContainerTabs(Tabs containerTabs) {
        this.containerTabs = containerTabs;
        return this;
    }

}
