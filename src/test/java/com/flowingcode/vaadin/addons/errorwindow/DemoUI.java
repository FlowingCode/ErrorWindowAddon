/*-
 * #%L
 * Error Window Add-on Project
 * %%
 * Copyright (C) 2018 - 2020 Flowing Code
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
		
		add(errorButton, throwErrorWithoutErrorHandler, throwErrorWithCustomMessage, toggleProductionMode);
	
	}
	
}
