package com.flowingcode.vaadin.addons.errorwindow;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@Route("error-window/throw")
public class ThrowInConstructorView extends Div {

  public ThrowInConstructorView() {
    throw new RuntimeException();
  }

}
