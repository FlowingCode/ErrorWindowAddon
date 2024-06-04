/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2024 Flowing Code
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

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.InternalServerError;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinContext;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.startup.ApplicationRouteRegistry;
import java.util.Collections;

/**
 * Implementation of {@code VaadinServiceInitListener} interface. This class adds a session init
 * listener to the Vaadin service, sets an error handler and registers an error view.
 */
public class VaadinServiceInitListenerImpl implements VaadinServiceInitListener {

  private static final long serialVersionUID = 1L;

  private static boolean productionMode;

  /**
   * Implements the {@code serviceInit} method from {@code VaadinServiceInitListener} interface.
   * This method sets {@code ErrorManager} as the error handler and registers {@link ErrorView} as
   * the default error view.
   *
   * @param event The ServiceInitEvent to be handled
   */
  @Override
  public void serviceInit(ServiceInitEvent event) {
    productionMode = event.getSource().getDeploymentConfiguration().isProductionMode();

    event
        .getSource()
        .addSessionInitListener(ev -> ev.getSession().setErrorHandler(this::handleError));
    registerErrorView(event.getSource());
  }

  /**
   * Registers an error view to the Vaadin service.
   *
   * @param service The Vaadin service to which the error view needs to be registered
   */
  private void registerErrorView(VaadinService source) {
    VaadinContext context = VaadinService.getCurrent().getContext();
    ApplicationRouteRegistry registry = ApplicationRouteRegistry.getInstance(context);
    Class<?> configuredExceptionHandler =
        registry.getConfiguration().getExceptionHandlerByClass(Exception.class);
    if (configuredExceptionHandler == InternalServerError.class) {
      registry.setErrorNavigationTargets(Collections.singleton(ErrorView.class));
    }
  }

  private void handleError(ErrorEvent event) {
    if (UI.getCurrent() != null) {
      ErrorManager.showError(event.getThrowable());
    } else {
      event.getThrowable().printStackTrace();
    }
  }

  static boolean getProductionMode() {
    return productionMode;
  }

}
