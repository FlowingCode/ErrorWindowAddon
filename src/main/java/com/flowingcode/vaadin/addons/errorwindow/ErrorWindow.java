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

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

/**
 * Component to visualize an error, caused by an exception, as a modal sub-window. <br> 
 * When in production mode it shows a code to report. <br>
 * When in debug mode it allows to visualize the stack trace of the error.
 * 
 * @author pbartolo
 *
 */
@SuppressWarnings("serial")
public class ErrorWindow extends Dialog {

	private static final Logger logger = LoggerFactory.getLogger(ErrorWindow.class);

	private VerticalLayout exceptionTraceLayout;

	private final Throwable cause;

	private final String errorMessage;

	private final String uuid;

	private boolean productionMode;

	private ErrorWindowI18n i18n;

	public ErrorWindow(final Throwable cause) {
		this(cause, null, isProductionMode(), ErrorWindowI18n.createDefault());
	}

	public ErrorWindow(final Throwable cause, final ErrorWindowI18n i18n) {
		this(cause, null, isProductionMode(), i18n);
	}

	public ErrorWindow(final Throwable cause, final String errorMessage) {
		this(cause, errorMessage, isProductionMode(), ErrorWindowI18n.createDefault());
	}

	public ErrorWindow(final Throwable cause, final String errorMessage, final ErrorWindowI18n i18n) {
		this(cause, errorMessage, isProductionMode(), i18n);
	}

	public ErrorWindow(final Throwable cause, final String errorMessage, boolean productionMode) {
		this(cause, errorMessage, productionMode, ErrorWindowI18n.createDefault());
	}
	public ErrorWindow(final Throwable cause, final String errorMessage, boolean productionMode, final ErrorWindowI18n i18n) {
		super();

		uuid = UUID.randomUUID().toString();
		this.cause = cause;
		this.errorMessage = errorMessage;
		this.productionMode = productionMode;
		this.i18n = i18n;
		initWindow();
	}
	
	public ErrorWindow(ErrorDetails errorDetails) {
		this(errorDetails, ErrorWindowI18n.createDefault());
	}

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
		add(createMainLayout());
	}

	/**
	 * Creates the main layout of the ErrorWindow.
	 */
	private VerticalLayout createMainLayout() {
		final VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSpacing(false);
		mainLayout.setPadding(false);
		mainLayout.setMargin(false);

		final Html title = new Html(String.format("<h3 style='margin-top:0px;text-align:center'>%s</h3>", i18n.getCaption()));
		title.getElement().getStyle().set("width", "100%");
		mainLayout.add(title);
		
		final Html errorLabel = createErrorLabel();
		mainLayout.add(errorLabel);
		mainLayout.setHorizontalComponentAlignment(Alignment.START,errorLabel);

		HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setSpacing(true);
		buttonsLayout.setPadding(false);
		buttonsLayout.setMargin(false);
		
		if (!productionMode) {
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
		final Button errorDetailsButton = new Button(i18n.getDetails(), event -> {
			boolean visible = !exceptionTraceLayout.isVisible();
			exceptionTraceLayout.setVisible(visible);
			if(visible) {
				event.getSource().setIcon(VaadinIcon.MINUS.create());
			} else {
				event.getSource().setIcon(VaadinIcon.PLUS.create());
			}
		});
		errorDetailsButton.setIcon(VaadinIcon.PLUS.create());
		return errorDetailsButton;
	}

	
	private VerticalLayout createExceptionTraceLayout() {
		exceptionTraceLayout = new VerticalLayout();
		exceptionTraceLayout.setSpacing(false);
		exceptionTraceLayout.setMargin(false);
		exceptionTraceLayout.setPadding(false);
		exceptionTraceLayout.add(createStackTraceArea());
		exceptionTraceLayout.setVisible(false);
		return exceptionTraceLayout;
	}

	protected TextArea createStackTraceArea() {
		final TextArea area = new TextArea();
		area.setWidthFull();
		area.setHeight("15em");
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintWriter pw = new PrintWriter(baos);
		cause.printStackTrace(pw);
		pw.flush();
		area.setValue(baos.toString());
		return area;
	}

	protected Html createErrorLabel() {
		String label = errorMessage == null ? i18n.getDefaultErrorMessage() : errorMessage;
		if (productionMode) {
			label = label.concat(String.format("<br />%s<h4><p><center>%s<center/></p></h4>", i18n.getInstructions(), uuid));
		}
		final Html errorLabel = new Html("<span>" + label + "</span>");
		errorLabel.getElement().getStyle().set("width", "100%");
		return errorLabel;
	}

}
