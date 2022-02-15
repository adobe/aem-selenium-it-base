/*
 * Copyright 2022 Adobe Systems Incorporated
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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class CoralRadio extends AEMBaseComponent {

  private final ElementsCollection radioElements;

  /**
   * @param parent any parent element which would contains a coral-radio.
   * @param name filter on that name.
   */
  public CoralRadio(final SelenideElement parent, final String name) {
    super(parent);
    radioElements = element().$$(String.format("coral-radio[name=\"%s\"]", name));
  }

  /**
   * @param value to find the related radio button.
   * @return the first matching element or null if not found.
   */
  public SelenideElement elementByValue(final String value) {
    final ElementsCollection filtered = radioElements.filter(Condition.attribute("value", value));
    return filtered.isEmpty() ? null : filtered.first();
  }

  /**
   * @param value to find the related radio button.
   */
  public void activateByValue(final String value) {
    clickableClick(elementByValue(value));
  }

  /**
   * @param value to find the related radio button.
   * @return true if the radio group has such possible value
   */
  public boolean hasValue(final String value) {
    return !radioElements.filter(Condition.attribute("value", value)).isEmpty();
  }

}
