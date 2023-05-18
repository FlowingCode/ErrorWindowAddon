/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2022 Flowing Code
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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.internal.DefaultErrorHandler;
import org.apache.http.HttpStatus;

/** View used to display an error message when navigation fails due to an exception. */
@SuppressWarnings("serial")
@DefaultErrorHandler
@javax.annotation.security.PermitAll
@jakarta.annotation.security.PermitAll
public class ErrorView extends VerticalLayout implements HasErrorParameter<Exception> {

  /**
   * This method sets the error parameter to display the error message and returns an HTTP status
   * code of 500 Internal Server Error.
   *
   * @param event a {@code BeforeEnterEvent} object
   * @param parameter an {@code ErrorParameter} object containing the caught exception
   * @return an int representing the HTTP status code 500 Internal Server Error
   */
  @Override
  public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<Exception> parameter) {
    setSizeFull();

    getElement().getThemeList().add("error-window");

    ErrorWindowI18n i18n = ErrorWindowI18n.createDefault();
    i18n.setClose("Back");
    new ErrorWindow(parameter.getCaughtException(), i18n) {
      @Override
      public void close() {
        UI.getCurrent().getPage().getHistory().back();
      }
    }.getChildren().forEach(this::add);

    return HttpStatus.SC_INTERNAL_SERVER_ERROR;
  }

}
