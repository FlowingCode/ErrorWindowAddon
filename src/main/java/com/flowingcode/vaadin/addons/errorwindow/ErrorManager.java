package com.flowingcode.vaadin.addons.errorwindow;

public class ErrorManager {
	
	private static ErrorWindowFactory errorWindowFactory = new DefaultErrorWindowFactory();

	public static void showError(Throwable throwable) {
		showError(throwable, throwable.getLocalizedMessage());
	}
	
	public static void showError(Throwable throwable, String cause) {
		ErrorDetails details = new ErrorDetails(throwable, cause);
		errorWindowFactory.showError(details);
	}

	public static void setErrorWindowFactory(ErrorWindowFactory errorWindowFactory) {
		ErrorManager.errorWindowFactory = errorWindowFactory;
	}

}
