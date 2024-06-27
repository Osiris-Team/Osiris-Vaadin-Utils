package com.vaadin.componentfactory.pdfviewer;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class BasicExample extends Div {
  
  public BasicExample() {
    add("Hello World!");
  }
  
}
