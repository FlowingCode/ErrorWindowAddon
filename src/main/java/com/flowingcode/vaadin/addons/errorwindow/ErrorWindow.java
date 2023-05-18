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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Component to visualize an error, caused by an exception, as a modal sub-window. 
 * When in production mode it shows a code to report. 
 * When in debug mode it allows to visualize the stack trace of the error.
 *
 * <p>This class has several constructors that allow specifying the cause of the error, an error message, whether the application is in production mode,
 * and the internationalization of the error message.</p>
 *
 * <p>This class also provides constructors that accept an {@link ErrorDetails} instance, which provides additional information about the error.</p>
 *
 * <p>A unique identifier is assigned to the dialog window, for use in reporting the error when in production mode.</p>
 * 
 * @see ErrorDetails
 * @author pbartolo
 */
@SuppressWarnings("serial")
@CssImport(value = "./flowingcode/error-window.css")
public class ErrorWindow extends Dialog {

  private static final Logger logger = LoggerFactory.getLogger(ErrorWindow.class);

  private VerticalLayout exceptionTraceLayout;

  private final Throwable cause;

  private final String errorMessage;

  private final String uuid;

  private boolean productionMode;

  private ErrorWindowI18n i18n;

  private Registration attachListenerRegistration;

  /**
   * Constructs and initializes an ErrorWindow object with the supplied cause and default production mode and i18n.
   *
   * @param cause: The cause of the error
   */
  public ErrorWindow(final Throwable cause) {
    this(cause, null, isProductionMode(), ErrorWindowI18n.createDefault());
  }

  /**
   * Constructs and initializes an ErrorWindow object with the supplied details and and default production mode.
   *
   * @param cause: The cause of the error
   * @param i18n: The internationalization of the ErrorWindow
   */
  public ErrorWindow(final Throwable cause, final ErrorWindowI18n i18n) {
    this(cause, null, isProductionMode(), i18n);
  }

  /**
   * Constructs and initializes an ErrorWindow object with the supplied details and default and default production mode and i18n.
   *
   * @param cause: The cause of the error
   * @param errorMessage: An optional error message
   */
  public ErrorWindow(final Throwable cause, final String errorMessage) {
    this(cause, errorMessage, isProductionMode(), ErrorWindowI18n.createDefault());
  }

  /**
   * Constructs and initializes an ErrorWindow object with the supplied details and default production mode.
   *
   * @param cause: The cause of the error
   * @param errorMessage: An optional error message
   * @param i18n: The internationalization of the ErrorWindow
   */
  public ErrorWindow(final Throwable cause, final String errorMessage, final ErrorWindowI18n i18n) {
    this(cause, errorMessage, isProductionMode(), i18n);
  }

  /**
   * Constructs and initializes an ErrorWindow object with the supplied details and default i18n.
   *
   * @param cause: The cause of the error
   * @param errorMessage: An optional error message
   * @param productionMode: The mode in which the Application is running. If true, a code is displayed with error details, else debug information is shown
   */
  public ErrorWindow(final Throwable cause, final String errorMessage, boolean productionMode) {
    this(cause, errorMessage, productionMode, ErrorWindowI18n.createDefault());
  }

  /**
   * Constructs and initializes an ErrorWindow object with the supplied details.
   *
   * @param cause: The cause of the error
   * @param errorMessage: An optional error message
   * @param productionMode: The mode in which the Application is running. If true, a code is displayed with error details, else debug information is shown
   * @param i18n: The internationalization of the ErrorWindow
   */
  public ErrorWindow(
      final Throwable cause,
      final String errorMessage,
      boolean productionMode,
      final ErrorWindowI18n i18n) {
    super();

    uuid = UUID.randomUUID().toString();
    this.cause = cause;
    this.errorMessage = errorMessage;
    this.productionMode = productionMode;
    this.i18n = i18n;

    attachListenerRegistration = addAttachListener(ev -> {
      attachListenerRegistration = null;
      ev.unregisterListener();
      initWindow();
    });
  }

  @Override
  public Stream<Component> getChildren() {
    if (attachListenerRegistration != null) {
      attachListenerRegistration.remove();
      attachListenerRegistration = null;
      initWindow();
    }
    return super.getChildren();
  }

  public ErrorWindow(ErrorDetails errorDetails) {
    this(errorDetails, ErrorWindowI18n.createDefault());
  }

  public ErrorWindow(ErrorDetails errorDetails, boolean productionMode) {
    this(errorDetails.getThrowable(), errorDetails.getCause(), productionMode);
  }

  /**
   * Constructs and initializes an ErrorWindow object with the supplied error details and internationalization.
   * This constructor allows for provision of additional information, which is an ErrorDetails object.
   *
   * @param errorDetails: The ErrorDetails object
   * @param i18n: The internationalization of the ErrorWindow
   */
  public ErrorWindow(ErrorDetails errorDetails, final ErrorWindowI18n i18n) {
    this(errorDetails.getThrowable(), errorDetails.getCause(), i18n);
  }

  private static boolean isProductionMode() {
    return ErrorManager.getErrorWindowFactory().isProductionMode();
  }

  private void initWindow() {
    if (logger.isErrorEnabled()) {
      logger.error(String.format("Error occurred %s", uuid), cause);
    }
    setWidth("800px");
    setCloseOnEsc(true);
    setDraggable(true);
    getElement().getThemeList().add("error-window");
    add(createMainLayout());
  }

  /** Creates the main layout of the ErrorWindow. */
  private VerticalLayout createMainLayout() {
    final VerticalLayout mainLayout = new VerticalLayout();
    mainLayout.setSpacing(false);
    mainLayout.setPadding(false);
    mainLayout.setMargin(false);
    mainLayout.setSizeFull();

    final Html title =
        new Html(
            String.format(
                "<h1 class='title'>%s</h1>",
                i18n.getCaption()));

    title.getElement().getStyle().set("width", "100%");
    mainLayout.add(title);

    final Html errorLabel = createErrorLabel();
    mainLayout.add(errorLabel);
    mainLayout.setHorizontalComponentAlignment(Alignment.START, errorLabel);

    HorizontalLayout buttonsLayout = new HorizontalLayout();    
    buttonsLayout.setSpacing(true);
    buttonsLayout.setPadding(false);
    buttonsLayout.setMargin(false);

    if (!productionMode) {
      buttonsLayout.setWidthFull();

      // copy details to clipboard button
      Button clipboarButton = new Button(i18n.getClipboard());
      clipboarButton.getThemeNames().add(ButtonVariant.LUMO_TERTIARY.getVariantName());
      clipboarButton.addClickListener(e -> UI.getCurrent().getPage()
          .executeJs("navigator.clipboard.writeText($0)", getStackTrace()));
      clipboarButton.getStyle().set("margin-right", "auto");
      buttonsLayout.add(clipboarButton);

      // show details button
      Button button = createDetailsButtonLayout();
      buttonsLayout.add(button);
      mainLayout.add(createExceptionTraceLayout());
    }

    final Button closeButton = new Button(i18n.getClose(), event -> close());
    buttonsLayout.add(closeButton);
    mainLayout.add(buttonsLayout);
    mainLayout.setHorizontalComponentAlignment(Alignment.END, buttonsLayout);

    return mainLayout;
  }

  private Button createDetailsButtonLayout() {
    final Button errorDetailsButton =
        new Button(
            i18n.getDetails(),
            event -> {
              boolean visible = !exceptionTraceLayout.isVisible();
              exceptionTraceLayout.setVisible(visible);
              if (visible) {
                event.getSource().setIcon(VaadinIcon.MINUS.create());
              } else {
                event.getSource().setIcon(VaadinIcon.PLUS.create());
              }
              setResizable(visible);
              event.getSource().getElement()
                  .executeJs(
                      "var overlay = this.closest('vaadin-dialog-overlay'); if (overlay) overlay.$.overlay.style.height=''");
            });
    errorDetailsButton.setIcon(VaadinIcon.PLUS.create());
    return errorDetailsButton;
  }

  private VerticalLayout createExceptionTraceLayout() {
    Component stackTraceArea = createStackTraceArea();
    exceptionTraceLayout = new VerticalLayout();
    exceptionTraceLayout.setSpacing(false);
    exceptionTraceLayout.setMargin(false);
    exceptionTraceLayout.setPadding(false);
    exceptionTraceLayout.add(stackTraceArea);
    exceptionTraceLayout.setFlexGrow(1, stackTraceArea);
    exceptionTraceLayout.setVisible(false);
    exceptionTraceLayout.getElement().getStyle().set("flex-grow", "1");
    return exceptionTraceLayout;
  }

  protected Component createStackTraceArea() {
    final Div area = new Div();
    area.setClassName("stacktrace");
    area.setWidthFull();
    area.setHeight("15em");
    area.add(getStackTrace());
    return area;
  }

  private String getStackTrace() {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final PrintWriter pw = new PrintWriter(baos);
    cause.printStackTrace(pw);
    pw.flush();
    return baos.toString();
  }
  
  protected Html createErrorLabel() {
    String label = errorMessage == null ? i18n.getDefaultErrorMessage() : errorMessage;
    if (productionMode) {
      label =
          label.concat(
              String.format(
                  "<br />%s<div class='uuid'>%s</div>",
                  i18n.getInstructions(), uuid));
    }
    final Html errorLabel = new Html("<span>" + label + "</span>");
    errorLabel.getElement().getStyle().set("width", "100%");
    return errorLabel;
  }
}
