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

/** Encapsulates error details, including the throwable object and the cause of the error. */
public class ErrorDetails {

  private Throwable throwable;
  private String cause;

  /**
   * Constructs a new ErrorDetails object with the given throwable and cause.
   *
   * @param throwable the throwable object that caused the error
   * @param cause the cause of the error
   */
  public ErrorDetails(Throwable throwable, String cause) {
    super();
    this.throwable = throwable;
    this.cause = cause;
  }

  /**
   * Retrieves the throwable object that caused the error.
   *
   * @return the throwable object that caused the error
   */
  public Throwable getThrowable() {
    return throwable;
  }

  /**
   * Sets the throwable object that caused the error.
   *
   * @param throwable the throwable object that caused the error
   */
  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  /**
   * Retrieves the cause of the error.
   *
   * @return the cause of the error
   */
  public String getCause() {
    return cause;
  }

  /**
   * Sets the cause of the error.
   *
   * @param cause the cause of the error
   */
  public void setCause(String cause) {
    this.cause = cause;
  }
}
