package com.flowingcode.vaadin.addons.errorwindow;

public class ErrorDetails {

	private Throwable throwable;
	private String cause;
	
	public ErrorDetails(Throwable throwable, String cause) {
		super();
		this.throwable = throwable;
		this.cause = cause;
	}
	
	public Throwable getThrowable() {
		return throwable;
	}
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}

}
