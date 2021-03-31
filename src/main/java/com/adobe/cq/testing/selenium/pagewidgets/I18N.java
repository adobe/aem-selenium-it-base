/*
 * Copyright 2021 Adobe Systems Incorporated
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adobe.cq.testing.selenium.pagewidgets;

import com.codeborne.selenide.Selenide;

public final class I18N {

  private I18N() {

  }

  /**
   * @param string message to internationalize.
   * @return internationalized in the current context language.
   */
  public static String geti18nString(final String string) {
    return Selenide.executeJavaScript("return Granite.I18n.get(arguments[0]);", string);
  }
}
