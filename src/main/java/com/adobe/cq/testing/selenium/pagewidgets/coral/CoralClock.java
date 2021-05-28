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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;

public class CoralClock extends BaseComponent {

  private final SelenideElement clock;
  private final SelenideElement hours;
  private final SelenideElement minutes;

  /**
   * @param parent the parent element containing a coral-clock.
   */
  public CoralClock(final SelenideElement parent) {
    super(parent);
    clock = element().$("coral-clock");
    hours = clock.$("input[handle=\"hours\"]");
    minutes = clock.$("input[handle=\"minutes\"]");
  }

  /**
   * @return the hours field.
   */
  public SelenideElement hours() {
    return hours;
  }

  /**
   * @return the minutes field.
   */
  public SelenideElement minutes() {
    return minutes;
  }

  /**
   * @return the period field.
   */
  public CoralSelect period() {
    return new CoralSelect("handle=\"period\"");
  }
}
