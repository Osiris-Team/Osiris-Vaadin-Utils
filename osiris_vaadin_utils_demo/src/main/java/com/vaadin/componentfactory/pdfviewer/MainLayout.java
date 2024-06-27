package com.vaadin.componentfactory.pdfviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {

  public MainLayout() {
    final DrawerToggle drawerToggle = new DrawerToggle();

    final RouterLink basicExample = new RouterLink("Basic example", BasicExample.class);

    final VerticalLayout menuLayout = new VerticalLayout(basicExample);
    addToDrawer(menuLayout);
    addToNavbar(drawerToggle);
  }

  @Override
  public void setContent(Component content) {
    super.setContent(content);
    content.getElement().getStyle().set("height", "100%");
    content.getElement().getStyle().set("overflow", "hidden");
    content.getElement().getStyle().set("display", "flex");
    content.getElement().getStyle().set("flex-direction", "column");
  }
}
