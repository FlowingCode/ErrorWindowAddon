package com.flowingcode.vaadin.addons.errorwindow;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

public class VaadinServiceInitListenerImpl implements VaadinServiceInitListener {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addSessionInitListener(ev -> 
			ev.getSession().setErrorHandler(ev2 -> {
				ErrorManager.showError(ev2.getThrowable());
			})
		);
	}

}
