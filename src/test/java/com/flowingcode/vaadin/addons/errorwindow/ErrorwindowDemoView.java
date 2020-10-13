package com.flowingcode.vaadin.addons.errorwindow;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.awt.TextField;

@SuppressWarnings("serial")
@Route(value = "error-window", layout = DemoLayout.class)
public class ErrorwindowDemoView extends VerticalLayout {

	public ErrorwindowDemoView() {
		Button errorButton = new Button("Throw Error", event -> {
			Integer.parseInt("asdf");
		});

		Button throwErrorWithoutErrorHandler = new Button("Throw Error without an error handler", ev->{
			try {
				Integer.parseInt("asdf");
			} catch (NumberFormatException e) {
				ErrorManager.showError(e);
			}
		});
		
		Button throwErrorWithCustomMessage = new Button("Throw Error with custom message", ev->{
			try {
				Integer.parseInt("asdf");
			} catch (NumberFormatException e) {
				ErrorWindow w = new ErrorWindow(e, "CUSTOM ERROR MESSAGE");
				w.open();
			}
		});
		
		Checkbox productionModeCb = new Checkbox("Production Mode");
		productionModeCb.addValueChangeListener(e -> {
			System.setProperty("productionMode", "" + productionModeCb.getValue().toString());
			Notification.show("Currently production mode is: " + System.getProperty("productionMode"));
		});

		setSizeFull();
		add(productionModeCb, errorButton, throwErrorWithoutErrorHandler, throwErrorWithCustomMessage);
	}

}
