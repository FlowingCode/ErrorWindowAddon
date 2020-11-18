package com.flowingcode.vaadin.addons.errorwindow;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.flowingcode.vaadin.addons.GithubLink;
import com.flowingcode.vaadin.addons.demo.TabbedDemo;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route(value = "error-window", layout = DemoLayout.class)
@GithubLink("https://github.com/FlowingCode/ErrorWindowAddon")
public class ErrorwindowDemoView extends TabbedDemo {

	private static final String ERROR_DEMO = "Error Window Demo";
	private static final String ERROR_SOURCE = "https://github.com/FlowingCode/ErrorWindowAddon/blob/master/src/test/java/com/flowingcode/vaadin/addons/errorwindow/ErrorwindowDemoView.java";

	public ErrorwindowDemoView() {
		addDemo(new ErrorwindowDemo(), ERROR_DEMO, ERROR_SOURCE);		
		setSizeFull();
	}

}
