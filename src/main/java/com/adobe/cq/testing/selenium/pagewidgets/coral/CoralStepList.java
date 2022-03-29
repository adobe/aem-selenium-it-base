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

public class CoralStepList extends AEMBaseComponent {

  private static final String CORAL_STEPLIST_SELECTOR = "coral-steplist";
  private static final String CORAL_STEP_SELECTOR = "coral-step";
  private static final String CORAL_STEP_LABEL_SELECTOR = "coral-step-label";

  /**
   * Construct a coral-steplist wrapper.
   */
  public CoralStepList() {
    super(CORAL_STEPLIST_SELECTOR);
  }

  /**
   * @return find current selected step.
   */
  public SelenideElement getCurrentStep() {
    return element().$$(CORAL_STEP_SELECTOR).find(Condition.cssClass("is-selected"));
  }

  /**
   * @return the title of the current step (as displayed in the current language).
   */
  public String getCurrentStepLabel() {
    return getCurrentStep().$(CORAL_STEP_LABEL_SELECTOR).getText();
  }

  /**
   * @return all the steps elements
   */
  public ElementsCollection allSteps() {
    return element().$$(CORAL_STEP_SELECTOR);
  }

  /**
   * @return all the completed steps elements
   */
  public ElementsCollection allCompletedSteps() {
    return element().$$(CORAL_STEP_SELECTOR).filter(Condition.cssClass("is-complete"));
  }

}
