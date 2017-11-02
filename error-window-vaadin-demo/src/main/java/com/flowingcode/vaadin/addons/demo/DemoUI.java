package com.flowingcode.vaadin.addons.demo;

import javax.servlet.annotation.WebServlet;

import com.flowingcode.vaadin.addons.errorwindow.ErrorWindow;
import com.flowingcode.vaadin.addons.errorwindow.WindowErrorHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("Error Window Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(final VaadinRequest request) {
        final Button errorButton = new Button("Throw Error", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                Integer.parseInt("asdf");
            }
        });
        errorButton.setErrorHandler(new WindowErrorHandler(this));

        final Button errorMsgButton = new Button("Throw Error Custom Message", new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                Integer.parseInt("asdf");
            }

        });
        errorMsgButton.setErrorHandler(new WindowErrorHandler(this, "I'M A CUSTOM MESSAGE"));

        final Button errorWithoutHandlerButton = new Button("Open Error Popup Without Handler");
        errorWithoutHandlerButton.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(final ClickEvent event) {
                try {
                    throw new Exception("Button click failed");
                } catch (final Exception e) {
                    new ErrorWindow(e).open(DemoUI.this);
                }
            }

        });

        final VerticalLayout layout = new VerticalLayout(errorButton, errorMsgButton, errorWithoutHandlerButton);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }
}
