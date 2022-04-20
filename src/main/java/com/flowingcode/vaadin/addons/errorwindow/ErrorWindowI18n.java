/*-
 * #%L
 * Error Window Add-on
 * %%
 * Copyright (C) 2017 - 2021 Flowing Code
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
 * Internationalization object for customizing the component UI texts. An instance with the default
 * messages can be obtained using {@link ErrorWindowI18n#createDefault()}
 *
 * @author michael.knigge@gmx.de
 */
public class ErrorWindowI18n {

  private String caption;
  private String instructions;
  private String close;
  private String details;
  private String defaultErrorMessage;
  private String clipboard;

  private ErrorWindowI18n() {
    this.caption = "An error has occurred";
    this.instructions = "Please report the following code to your system administrator";
    this.close = "Close";
    this.details = "Show error details";
    this.defaultErrorMessage = "Please contact the system administrator for more information.";
    this.clipboard = "Copy details to clipboard";
  }

  /** @return a new instance with the default messages */
  public static ErrorWindowI18n createDefault() {
    return new ErrorWindowI18n();
  }

  /** Sets the caption of the error window. */
  public void setCaption(final String caption) {
    this.caption = caption;
  }

  /** @return returns the caption of the error window. */
  public String getCaption() {
    return this.caption;
  }

  /** Sets the instructions for the user if the UUID is displayed in production mode. */
  public void setInstructions(final String instructions) {
    this.instructions = instructions;
  }

  /** @return returns the instructions for the user if the UUID is displayed in production mode. */
  public String getInstructions() {
    return this.instructions;
  }

  /** Sets the text of the "Close"-Button. */
  public void setClose(final String close) {
    this.close = close;
  }

  /** @return returns the text of the "Close"-Button. */
  public String getClose() {
    return this.close;
  }

  /** Sets the text of the "Details"-Button. */
  public void setDetails(final String details) {
    this.details = details;
  }

  /** @return returns the text of the "Details"-Button. */
  public String getDetails() {
    return this.details;
  }

  /**
   * Sets the default error message shown if the message passed to the error window is <code>null
   * </code>.
   */
  public void setDefaultErrorMessage(final String defaultErrorMessage) {
    this.defaultErrorMessage = defaultErrorMessage;
  }

  /**
   * @return returns the default error message shown if the message passed to the error window is
   *     <code>null</code>.
   */
  public String getDefaultErrorMessage() {
    return this.defaultErrorMessage;
  }

  /** @return returns the text for the "Copy details to clipboard" button. */
  public String getClipboard() {
    return clipboard;
  }

  /** Sets the text for the "Copy details to clipboard" button. */
  public void setClipboard(String clipboard) {
    this.clipboard = clipboard;
  }
  
}
