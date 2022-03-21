/*
 * ************************************************************************
 *  ADOBE CONFIDENTIAL
 *  ___________________
 *
 *  Copyright 2020 Adobe
 *  All Rights Reserved.
 *
 *  NOTICE: All information contained herein is, and remains
 *  the property of Adobe and its suppliers, if any. The intellectual
 *  and technical concepts contained herein are proprietary to Adobe
 *  and its suppliers and are protected by all applicable intellectual
 *  property laws, including trade secret and copyright laws.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Adobe.
 * ************************************************************************
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
