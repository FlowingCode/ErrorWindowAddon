package com.flowingcode.vaadin.addons.errorwindow;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class WindowErrorHandler implements ErrorHandler {

    private final String errorMessage;

    private final UI ui;

    public WindowErrorHandler(final UI ui) {
        this(ui, null);
    }

    public WindowErrorHandler(final UI ui, final String errorMessage) {
        this.ui = ui;
        this.errorMessage = errorMessage;
    }

    @Override
    public void error(final ErrorEvent event) {
        if (this.errorMessage == null) {
            new ErrorWindow(event.getThrowable()).open(ui);
        } else {
            new ErrorWindow(event.getThrowable(), errorMessage).open(ui);
        }
    }

}
