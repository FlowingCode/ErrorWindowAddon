/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2022 Flowing Code
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

/** A factory interface for creating error windows. */
public interface ErrorWindowFactory {

  /**
   * Shows the error details on a new window.
   *
   * @param details the details of the error to be shown
   */
  void showError(ErrorDetails details);

  /**
   * Checks whether the application is in production mode.
   *
   * @return true if the application is in production mode, false otherwise
   */
  default boolean isProductionMode() {
    return ("true".equals(System.getProperty("productionMode")));
  }
}
