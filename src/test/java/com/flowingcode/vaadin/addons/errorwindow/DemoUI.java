package com.flowingcode.vaadin.addons.errorwindow;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route()
public class DemoUI extends VerticalLayout {

	public DemoUI() {
		Button errorButton = new Button("Throw Error", event -> {
			Integer.parseInt("asdf");
		});
		Button toggleProductionMode = new Button("Toggle production mode");
		toggleProductionMode.addClickListener(ev->{
			System.setProperty("productionMode",""+("false".equals(System.getProperty("productionMode"))));
			Notification.show("Currently production mode is: " + System.getProperty("productionMode"));
		});
		add(errorButton, toggleProductionMode);
		
		
	}
	
}
