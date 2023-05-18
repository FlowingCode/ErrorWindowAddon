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

/** A factory class to create an error window with internationalization support. */
public class I18nErrorWindowFactory implements ErrorWindowFactory {

  private final ErrorWindowI18n errorWindowI18n;
  
  /**
   * Constructs a new I18nErrorWindowFactory instance with a given ErrorWindowI18n instance.
   *
   * @param errorWindowI18n the ErrorWindowI18n instance to be used
   */
  I18nErrorWindowFactory(ErrorWindowI18n errorWindowI18n){
    this.errorWindowI18n = errorWindowI18n;
  }
  
  /**
   * Shows an error window with the given error details and internationalization support.
   *
   * @param details the ErrorDetails instance to be shown in the error window
   */
  @Override
  public void showError(ErrorDetails details) {
    new ErrorWindow(details, errorWindowI18n).open();
  }
}
