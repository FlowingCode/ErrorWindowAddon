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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.Optional;

@DemoSource
@PageTitle("Error Window Demo")
@Route(value = "error-window/error-window", layout = ErrorwindowDemoView.class)
@SuppressWarnings("serial")
public class ErrorwindowDemo extends VerticalLayout {

  public ErrorwindowDemo() {
    ErrorWindowI18n errorWindowI18n = ErrorWindowI18n.create(this::getTranslation);
    ErrorManager.setErrorWindowFactory(new I18nErrorWindowFactory(errorWindowI18n));
    
    Button errorButton =
        new Button(
            "Throw Error",
            event -> {
              Integer.parseInt("asdf");
            });

    Button throwErrorWithoutErrorHandler =
        new Button(
            "Throw Error without an error handler",
            ev -> {
              try {
                Integer.parseInt("asdf");
              } catch (NumberFormatException e) {
                ErrorManager.showError(e);
              }
            });

    Button throwErrorWithCustomMessageAndCustomTexts =
        new Button(
            "Throw Error with custom message (custom labels)",
            ev -> {
              try {
                Integer.parseInt("asdf");
              } catch (NumberFormatException e) {
                ErrorWindowI18n i18n = ErrorWindowI18n.createDefault();
                i18n.setCaption("Uh oh... that's embarrassing");
                i18n.setInstructions(
                    "Please pass this unique error identifier to your administrator");
                i18n.setClose("Ok");
                i18n.setDetails("Show me technical details");
                i18n.setDefaultErrorMessage("Something really strange happened");
                ErrorWindow w = new ErrorWindow(e, "CUSTOM ERROR MESSAGE", i18n);
                w.open();
              }
            });

    Button throwErrori18nSupport =
        new Button(
            "Throw Error with i18n support",
            ev -> {
              try {
                Integer.parseInt("asdf");
              } catch (NumberFormatException e) {
                new ErrorWindow(e, "CUSTOM ERROR MESSAGE", errorWindowI18n).open();
              }
            });

    // #if vaadin eq 0
    Checkbox productionModeCb = new Checkbox("Production Mode");
    productionModeCb.setValue(getProductionMode());
    productionModeCb.addValueChangeListener(
        e -> {
          setProductionMode(e.getValue());
          Notification.show(
              "Currently production mode is: " + System.getProperty("productionMode"));
        });
    // #endif

    Upload upload = new Upload(new NullMemoryBuffer());
    upload.addSucceededListener(ev -> {
      throw new UnsupportedOperationException("File upload is not allowed");
    });

    setSizeFull();
    add(
        productionModeCb,
        errorButton,
        throwErrorWithoutErrorHandler,
        throwErrorWithCustomMessageAndCustomTexts,
        throwErrori18nSupport);

    add(new Button("Navigation error", ev -> {
      UI current = UI.getCurrent();
      try {
        UI.class.getMethod("navigate", Class.class).invoke(current, ThrowInConstructorView.class);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }));

    add(upload);

  }

  // #if vaadin eq 0
  private boolean getProductionMode() {
    return Optional.ofNullable(System.getProperty("errorWindowProductionMode"))
        .map(Boolean::valueOf).orElseGet(() -> {
          setProductionMode(true);
          return Boolean.TRUE;
        });
  }

  private void setProductionMode(boolean mode) {
    System.setProperty("errorWindowProductionMode", Boolean.toString(mode));
  }
  // #endif
}
