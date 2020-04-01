package com.flowingcode.vaadin.addons.errorwindow;

public interface ErrorWindowFactory {

	void showError(ErrorDetails details);
	
	default boolean isProductionMode() {
		return ("true".equals(System.getProperty("productionMode")));
	}

}