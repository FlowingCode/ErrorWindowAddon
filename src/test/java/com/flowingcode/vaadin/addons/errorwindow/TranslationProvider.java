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

import com.vaadin.flow.i18n.I18NProvider;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.slf4j.LoggerFactory;

public class TranslationProvider implements I18NProvider {

  public static final String BUNDLE_PREFIX = "messages";

  public final Locale LOCALE_ES = new Locale("es");
  public final Locale LOCALE_EN = new Locale("en");

  private List<Locale> locales = Collections.unmodifiableList(Arrays.asList(LOCALE_ES, LOCALE_EN));

  @Override
  public List<Locale> getProvidedLocales() {
    return locales;
  }

  @Override
  public String getTranslation(String key, Locale locale, Object... params) {
    if (key == null) {
      LoggerFactory.getLogger(TranslationProvider.class.getName())
          .warn("Got lang request for key with null value!");
      return "";
    }

    final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);

    String value;
    try {
      value = bundle.getString(key);
    } catch (final MissingResourceException e) {
      LoggerFactory.getLogger(TranslationProvider.class.getName()).warn("Missing resource", e);
      return "!" + locale.getLanguage() + ": " + key;
    }
    if (params.length > 0) {
      value = MessageFormat.format(value, params);
    }
    return value;
  }
}
