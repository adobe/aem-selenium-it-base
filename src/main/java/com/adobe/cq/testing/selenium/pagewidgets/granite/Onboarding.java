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

package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;

public class Onboarding extends BaseComponent {

    /**
     * Construct a OnboardingComponent.
     */
    public Onboarding() {
        super("coral-overlay.granite-shell-onboarding");
    }

    /**
     * Check to see if the popover is opened and visible.
     *
     * @return true if open.
     */
    public boolean isOpen() {
        return isExisting() && isVisibleWithinViewport() && element().is(Condition.cssClass("is-open"));
    }

    /**
     * Close the popover and wait for it to disappear.
     *
     * @return OnboardingComponent.
     */
    public Onboarding close() {
        final SelenideElement currentElement = element();
        currentElement.$("coral-panel[selected] button[coral-close]").should(EXISTS_ENABLED_VISIBLE).click();
        currentElement.shouldNotBe(Condition.visible);
        return this;
    }

    /**
     * Go to the next step.
     *
     * @return OnboardingComponent.
     */
    public Onboarding next() {
        element().$("coral-panel[selected] button[coral-wizardview-next]").should(EXISTS_ENABLED_VISIBLE).click();
        Helpers.waitForElementAnimationFinished("granite-shell-onboarding-popover");
        return this;
    }
}
