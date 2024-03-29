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

/**
 * Default implementation of {@link ErrorWindowFactory}. Displays error details using an {@link
 * ErrorWindow}.
 */
public class DefaultErrorWindowFactory implements ErrorWindowFactory {

  /**
   * Displays the error details creating a new instance of {@link ErrorWindow}. The error window is
   * displayed on the screen by invoking {@link ErrorWindow#open()}.
   *
   * @param details the error details to display
   */
  @Override
  public void showError(ErrorDetails details) {
    ErrorWindow w = new ErrorWindow(details);
    w.open();
  }
}
