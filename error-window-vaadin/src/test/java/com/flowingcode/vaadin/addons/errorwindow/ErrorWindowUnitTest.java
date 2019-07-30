package com.flowingcode.vaadin.addons.errorwindow;

/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2018 Flowing Code
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

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import junit.framework.TestCase;

@RunWith(MockitoJUnitRunner.class)
public class ErrorWindowUnitTest extends TestCase {
	
	
	private static final String DEFAULT_CAPTION = "<b>An error has occurred</b>";
	 
	private static final String DEFAULT_ERROR_LABEL_MESSAGE = "Please contact the system administrator for more information.";
	
	@Mock
	private VaadinService vaadinService;	
	 
	@Mock
	private DeploymentConfiguration deploymentConfiguration;
	
	private ErrorWindow errorWindow;
	 
	@SuppressWarnings("serial")
	UI ui = new UI() {
		@Override
		protected void init(VaadinRequest vaadinRequest) { }
	};
	
	@Before
	public void init() {
		VaadinService.setCurrent(vaadinService);
		when(vaadinService.getDeploymentConfiguration()).thenReturn(deploymentConfiguration);
		when(deploymentConfiguration.isProductionMode()).thenReturn(false);
	}
		
	@Test
	public void testBasicErrorWindow() {	
		errorWindow = new ErrorWindow(new Throwable("error ocurred"));
		errorWindow.open(ui);	
				 
		assertNotNull(errorWindow.getCaption());
		assertTrue(errorWindow.getCaption().equals(DEFAULT_CAPTION));
		assertTrue(getErrorWindowLabelLayout().getValue().equals(DEFAULT_ERROR_LABEL_MESSAGE));
		assertTrue(getErrorWindowDetailsButton().getCaption().equals("Show error detail")); 
		assertTrue(!getErrorWindowTraceLayout().isVisible());
	}
	
	@Test
	public void testErrorWindowWithCustomMessage() {	
		String customMessage = "I'm a custom message";
		errorWindow = new ErrorWindow(new Throwable("error ocurred"), customMessage);
		errorWindow.open(ui);	
	
		assertEquals(getErrorWindowLabelLayout().getValue(), customMessage);
	}

	@Test
	public void testErrorWindowInProductionMode() {
		when(deploymentConfiguration.isProductionMode()).thenReturn(true);
		
		errorWindow = new ErrorWindow(new Throwable("error ocurred"));
		errorWindow.open(ui);	
		
		assertEquals(getErrorWindowMainLayout().getComponentCount(), 2);
		assertTrue(getErrorWindowLabelLayout().getValue().contains("Report the following code")); 
	}
	
	private VerticalLayout getErrorWindowMainLayout() {
		return (VerticalLayout)errorWindow.getContent();
	}
	 
	private Label getErrorWindowLabelLayout() {
		return (Label)getErrorWindowMainLayout().getComponent(0);
	}
	
	private HorizontalLayout getErrorWindowDetailsLayout() {
		return (HorizontalLayout)getErrorWindowMainLayout().getComponent(1);
	}
	
	private Button getErrorWindowDetailsButton() {
		return (Button)getErrorWindowDetailsLayout().getComponent(0);
	}
	 
	private VerticalLayout getErrorWindowTraceLayout() {
		return (VerticalLayout)getErrorWindowMainLayout().getComponent(2);
	}	
	
}
