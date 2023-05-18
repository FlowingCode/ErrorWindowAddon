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

import com.vaadin.flow.server.UploadException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages errors and displays error messages through different ErrorWindowFactory implementations.
 */
public final class ErrorManager {

  private static final Map<Class<?>, ErrorWindowFactory> factories = new ConcurrentHashMap<>();

  static {
    factories.put(Object.class, new DefaultErrorWindowFactory());
    factories.put(UploadException.class, details -> {
      Throwable cause = details.getThrowable().getCause();
      if (cause != null) {
        showError(cause);
      } else {
        getErrorWindowFactory(Exception.class).showError(details);
      }
    });
  }

  private ErrorManager() {
    throw new IllegalStateException("Utility class not meant to be instantiated");
  }

  /**
   * Displays an error message for the given Throwable.
   *
   * @param throwable the Throwable object for which the error message should be displayed
   */
  public static void showError(Throwable throwable) {
    showError(throwable, throwable.getLocalizedMessage());
  }

  /**
   * Displays an error message for the given Throwable with the specified cause message.
   *
   * @param throwable the Throwable object for which the error message should be displayed
   * @param cause the cause for the error
   */
  public static void showError(Throwable throwable, String cause) {
    ErrorDetails details = new ErrorDetails(throwable, cause);
    getErrorWindowFactory(throwable.getClass()).showError(details);
  }

  /**
   * Sets the ErrorWindowFactory for the specified Throwable class.
   *
   * @param clazz the class of Throwable for which the ErrorWindowFactory should be set
   * @param errorWindowFactory the ErrorWindowFactory implementation to be set for the specified
   *     class
   */
  public static void setErrorWindowFactory(Class<? extends Throwable> clazz,
      ErrorWindowFactory errorWindowFactory) {
    factories.put(clazz.asSubclass(Throwable.class), errorWindowFactory);
  }

  /**
   * Gets the ErrorWindowFactory implementation for the specified class or its super classes.
   *
   * @param clazz the class for which the ErrorWindowFactory implementation is needed
   * @return the ErrorWindowFactory implementation associated with the specified class or its super
   *     classes
   */
  public static ErrorWindowFactory getErrorWindowFactory(Class<?> clazz) {
    return Optional.ofNullable(factories.get(clazz))
        .orElseGet(() -> getErrorWindowFactory(clazz.getSuperclass()));
  }

  /**
   * Sets the default ErrorWindowFactory implementation.
   *
   * @param errorWindowFactory the default ErrorWindowFactory implementation to be set
   */
  public static void setErrorWindowFactory(ErrorWindowFactory errorWindowFactory) {
    factories.put(Throwable.class, errorWindowFactory);
  }

  /**
   * Gets the default ErrorWindowFactory implementation.
   *
   * @return the default ErrorWindowFactory implementation
   */
  public static ErrorWindowFactory getErrorWindowFactory() {
    return getErrorWindowFactory(Throwable.class);
  }

}
