/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2020 Flowing Code
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

import com.flowingcode.vaadin.addons.DemoLayout;
import com.flowingcode.vaadin.addons.GithubLink;
import com.flowingcode.vaadin.addons.demo.TabbedDemo;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@Route(value = "error-window", layout = DemoLayout.class)
@GithubLink("https://github.com/FlowingCode/ErrorWindowAddon")
public class ErrorwindowDemoView extends TabbedDemo {

	private static final String ERROR_DEMO = "Error Window Demo";
	private static final String ERROR_SOURCE =
        "https://github.com/FlowingCode/ErrorWindowAddon/blob/master/src/test/java/com/flowingcode/vaadin/addons/errorwindow/ErrorwindowDemo.java";

	public ErrorwindowDemoView() {
		addDemo(new ErrorwindowDemo(), ERROR_DEMO, ERROR_SOURCE);
		setSizeFull();
	}

}
