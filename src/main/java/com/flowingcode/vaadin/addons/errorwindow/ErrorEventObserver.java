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

import java.io.Serializable;

/**
 * Any {@code com.vaadin.ui.Component} implementing this interface will be informed when
 * {@link ErrorManager} handles an exception.
 */
@FunctionalInterface
public interface ErrorEventObserver extends Serializable {

  /**
   * Notifies when {@link ErrorManager} handles an exception.
   *
   * @param event error event with exception details
   */
  void onError(ErrorEvent event);

}
