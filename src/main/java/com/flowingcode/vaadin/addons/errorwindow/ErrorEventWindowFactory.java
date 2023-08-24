/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2023 Flowing Code
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

import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.EventUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class ErrorEventWindowFactory implements ErrorWindowFactory {

  private final ErrorWindowFactory delegate;

  private ErrorEventWindowFactory(ErrorWindowFactory delegate) {
    this.delegate = Objects.requireNonNull(delegate);
  }

  public static ErrorWindowFactory newInstance(ErrorWindowFactory delegate) {
    return new ErrorEventWindowFactory(delegate);
  }

  @Override
  public void showError(ErrorDetails details) {
    UI ui = UI.getCurrent();
    ErrorEvent event = new ErrorEvent(ui, details.getThrowable());
    collectErrorEventObservers(ui.getElement()).forEach(observer -> observer.onError(event));
    if (!event.isPreventDefault()) {
      delegate.showError(details);
    }
  }

  @Override
  public boolean isProductionMode() {
    return delegate.isProductionMode();
  }

  public static List<ErrorEventObserver> collectErrorEventObservers(Element element) {
    return EventUtil
        .getImplementingComponents(flattenDescendants(element), ErrorEventObserver.class)
        .collect(Collectors.toList());
  }

  // see EventUtil.flattenDescendants
  private static Stream<Element> flattenDescendants(Element element) {
    Collection<Element> descendants = new ArrayList<>();
    EventUtil.inspectHierarchy(element, descendants, item -> true);
    return descendants.stream();
  }

}
