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

import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public final class Breadcrumbs extends AEMBaseComponent {

    private final String buttonSelector;
    private final String popoverSelector;

    /**
     * Constructor for Breadcrumbs component.
     */
    public Breadcrumbs() {
        super("betty-breadcrumbs");

        buttonSelector = String.format("%s  button.betty-breadcrumbs-button", getCssSelector());
        popoverSelector = String.format("%s > coral-popover.betty-breadcrumbs-popover", getCssSelector());
    }

    /**
     * Creates the CSS selector used to locate the n-th item in this breadcrumbs.
     * @param index The 0 based index of the item for which to construct the CSS selector
     * @return String the CSS selector for the targeted item
     *
     */
    private String createBreadcrumbsItemSelector(final int index) {
        return popoverSelector + String.format(" coral-selectlist coral-selectlist-item:nth-child(%d)", index + 1);
    }

    /**
     * Gets the button of the Breadcrumbs, the one that opens the popover with all the items.
     *
     * @return the button.
     */
    public SelenideElement button() {
        return $(buttonSelector).shouldBe(Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Gets the popover of this breadcrumbs.
     *
     * @return the popover.
     */
    public SelenideElement popover() {
        Helpers.waitForElementAnimationFinished(popoverSelector);
        return $(popoverSelector);
    }

    /**
     * Clicks on the button of this breadcrumbs and returns the opened popover.
     *
     * @return the popover.
     */
    public SelenideElement openPopover() {
        button().click();
        return popover();
    }

    /**
     * Assuming the popover of this breadcrumbs is open, return the n-th item from this popover.
     * @param index the 0 based index of the item to return
     * @return the element corresponding to the item.
     *
     */
    public SelenideElement getPopoverItemAt(final int index) {
        final String itemSelector = createBreadcrumbsItemSelector(index);
        return $(itemSelector).shouldBe(Constants.EXISTS_ENABLED_VISIBLE);
    }
}
