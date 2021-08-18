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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ErrorManager {

  private static final Map<Class<?>, ErrorWindowFactory> factories = new ConcurrentHashMap<>();

  static {
    factories.put(Object.class, new DefaultErrorWindowFactory());
  }

  private ErrorManager() {
    throw new IllegalStateException("Utility class not meant to be instantiated");
  }

  public static void showError(Throwable throwable) {
    showError(throwable, throwable.getLocalizedMessage());
  }

  public static void showError(Throwable throwable, String cause) {
    ErrorDetails details = new ErrorDetails(throwable, cause);
    getErrorWindowFactory(throwable.getClass()).showError(details);
  }

  public static void setErrorWindowFactory(Class<? extends Throwable> clazz,
      ErrorWindowFactory errorWindowFactory) {
    factories.put(clazz.asSubclass(Throwable.class), errorWindowFactory);
  }

  public static ErrorWindowFactory getErrorWindowFactory(Class<?> clazz) {
    return Optional.ofNullable(factories.get(clazz))
        .orElseGet(() -> getErrorWindowFactory(clazz.getSuperclass()));
  }

  public static void setErrorWindowFactory(ErrorWindowFactory errorWindowFactory) {
    factories.put(Throwable.class, errorWindowFactory);
  }

  public static ErrorWindowFactory getErrorWindowFactory() {
    return getErrorWindowFactory(Throwable.class);
  }

}
