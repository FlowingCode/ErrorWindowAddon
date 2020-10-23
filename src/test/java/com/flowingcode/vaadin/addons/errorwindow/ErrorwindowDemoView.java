package com.flowingcode.vaadin.addons.errorwindow;

import com.flowingcode.vaadin.addons.DemoLayout;
import com.flowingcode.vaadin.addons.demo.impl.TabbedDemoImpl;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route(value = "error-window", layout = DemoLayout.class)
public class ErrorwindowDemoView extends VerticalLayout {

	private static final String ERROR_DEMO = "Error Window Demo";
	private static final String ERROR_SOURCE = "https://github.com/FlowingCode/ErrorWindowAddon/blob/master/src/test/java/com/flowingcode/vaadin/addons/errorwindow/ErrorwindowDemoView.java";

	public ErrorwindowDemoView() {
		TabbedDemoImpl<ErrorwindowDemo> errorDemo = new TabbedDemoImpl<>(new ErrorwindowDemo(), ERROR_DEMO,
				ERROR_SOURCE);
		add(errorDemo);
		setSizeFull();
	}

}
