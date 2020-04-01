package com.flowingcode.vaadin.addons.errorwindow;

public class DefaultErrorWindowFactory implements ErrorWindowFactory {

	@Override
	public void showError(ErrorDetails details) {
		ErrorWindow w = new ErrorWindow(details, isProductionMode());
		w.open();
	}

}
