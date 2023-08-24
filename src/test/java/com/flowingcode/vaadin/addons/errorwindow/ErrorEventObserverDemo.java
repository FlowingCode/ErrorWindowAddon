/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2023 Flowing Code
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
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@DemoSource
@PageTitle("Error Event")
@Route(value = "error-window/error-event", layout = ErrorwindowDemoView.class)
@SuppressWarnings("serial")
public class ErrorEventObserverDemo extends VerticalLayout implements ErrorEventObserver {

  private VerticalLayout target = new VerticalLayout();

  @Override
  public void onError(ErrorEvent event) {
    String str = "We handled a " + event.getThrowable().getClass().getName();
    target.add(new Div(new Text(str)));
    event.preventDefault();
  }

  public ErrorEventObserverDemo() {
    ErrorWindowI18n errorWindowI18n = ErrorWindowI18n.create(this::getTranslation);
    ErrorManager.setErrorWindowFactory(new I18nErrorWindowFactory(errorWindowI18n));

    Button errorButton =
        new Button(
            "Throw Error",
            event -> {
              throw new RuntimeException("Uh oh!");
            });

    Button throwErrorWithoutErrorHandler =
        new Button(
            "Throw Error without an error handler",
            ev -> {
              try {
                throw new RuntimeException("Uh oh!");
              } catch (RuntimeException e) {
                ErrorManager.showError(e);
              }
            });

    Upload upload = new Upload(new NullMemoryBuffer());
    upload.addSucceededListener(ev -> {
      throw new UnsupportedOperationException("File upload is not allowed");
    });

    setSizeFull();
    add(errorButton, throwErrorWithoutErrorHandler, upload);

    add(target);
    target.add(new Text("Errors will be appended here:"));
  }

}
