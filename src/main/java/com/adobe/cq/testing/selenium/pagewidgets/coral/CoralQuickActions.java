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
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.Assert.assertTrue;

public class CoralQuickActions extends BaseComponent {

  private ElementsCollection openedQuickActions;

  /**
   * Construct the quickactions for card.
   */
  public CoralQuickActions() {
    super("coral-quickactions.is-open");
    openedQuickActions = element().$$("coral-icon");
  }

  /**
   * Get the clickable element for the given action name.
   * @param action action name.
   * @return the clickable element for this action (coral-icon).
   */
  public SelenideElement getQuickAction(final String action) {
    ElementsCollection availableQuickActions = getOpenedQuickActions();
    availableQuickActions.shouldHave(CollectionCondition.sizeGreaterThan(0));
    ElementsCollection filteredActions = availableQuickActions
        .filter(Condition.attribute("icon", action))
        .filter(Condition.visible);
    assertTrue(filteredActions.size() <= 1);
    if (filteredActions.size() == 1) {
        return filteredActions.get(0);
           }
    // check for more button and popover
    SelenideElement moreButton = element().$("button[icon=\"more\"]");
    if (moreButton.isDisplayed()) {
        moreButton.hover();
        moreButton.click();
        CoralPopOver popover = new CoralPopOver("._coral-QuickActions-moreOverlay");
        filteredActions = popover.element().$$("coral-icon")
            .filter(Condition.attribute("icon", action))
            .filter(Condition.visible);
        filteredActions.shouldHaveSize(1);
        return filteredActions.get(0);
           }
    throw new NoSuchElementException("Couldn't find " + action + " icon");
  }

  /**
   * @return list of all the current opened quickactions available.
   */
  public ElementsCollection getOpenedQuickActions() {
    return openedQuickActions;
  }

}
