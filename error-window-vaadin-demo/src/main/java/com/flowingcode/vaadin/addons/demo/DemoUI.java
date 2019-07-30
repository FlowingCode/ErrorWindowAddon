package com.flowingcode.vaadin.addons.demo;

/*-
 * #%L
 * Error Window Add-on for Vaadin 8 Demo
 * %%
 * Copyright (C) 2017 - 2018 Flowing Code
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

import javax.servlet.annotation.WebServlet;

import com.flowingcode.vaadin.addons.errorwindow.ErrorWindow;
import com.flowingcode.vaadin.addons.errorwindow.WindowErrorHandler;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
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
        Button errorButton = new Button("Throw Error", event -> {
            Integer.parseInt("asdf");
        });
        errorButton.setErrorHandler(new WindowErrorHandler(this));

        Button errorMsgButton = new Button("Throw Error Custom Message", event -> {
            Integer.parseInt("asdf");
        });
        errorMsgButton.setErrorHandler(new WindowErrorHandler(this, "I'M A CUSTOM MESSAGE"));

        Button errorWithoutHandlerButton = new Button("Open Error Popup Without Handler");
        errorWithoutHandlerButton.addClickListener(event -> {
            try {
                throw new Exception("Button click failed");
            } catch (final Exception e) {
                new ErrorWindow(e).open(this);
            }
        });

       VerticalLayout layout = new VerticalLayout(errorButton, errorMsgButton, errorWithoutHandlerButton);
       setContent(layout);
    }
}
