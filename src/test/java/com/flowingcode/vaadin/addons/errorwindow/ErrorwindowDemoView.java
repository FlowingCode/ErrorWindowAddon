package com.flowingcode.vaadin.addons.errorwindow;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.awt.TextField;

@SuppressWarnings("serial")
@Route(value = "errorwindow", layout = DemoLayout.class)
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

		Button toggleProductionMode = new Button("Toggle production mode");
		toggleProductionMode.addClickListener(ev->{
			System.setProperty("productionMode",""+("false".equals(System.getProperty("productionMode"))));
			Notification.show("Currently production mode is: " + System.getProperty("productionMode"));
		});
		
		setSizeFull();
		add(errorButton, throwErrorWithoutErrorHandler, throwErrorWithCustomMessage, toggleProductionMode);
	}

}