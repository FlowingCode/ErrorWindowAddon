package com.flowingcode.vaadin.addons.errorwindow;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasErrorParameter;
import com.vaadin.flow.router.internal.DefaultErrorHandler;
import org.apache.http.HttpStatus;

@SuppressWarnings("serial")
@DefaultErrorHandler
public class ErrorView extends VerticalLayout implements HasErrorParameter<Exception> {

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
