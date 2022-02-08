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
import com.adobe.cq.testing.selenium.pageobject.granite.ViewType;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * The collection switcher component wrapper.
 */
public class CollectionSwitcher extends AEMBaseComponent {

    private static final String SWITCHER_ICON_PLACEHOLDER = "%icon%";
    private final String viewButtonSelector;
    private final String currentViewButtonSelector;
    private final String toggleButtonSelector;
    private final String popoverMenuSelector;

    /**
     * Construct a CollectionSwitcherComponent.
     */
    public CollectionSwitcher() {
        // Alternative: "coral-cyclebutton.granite-collection-switcher"
        super("#granite-collection-switcher-toggle");
        currentViewButtonSelector = getCssSelector() + " button[is='coral-button'] coral-icon[icon='%icon%']";
        popoverMenuSelector = getCssSelector() + "-menu";
        viewButtonSelector = popoverMenuSelector + " coral-selectlist-item coral-icon[icon='%icon%']";
        toggleButtonSelector = "#granite-collection-switcher-toggle-button";
    }

    private String createSwitchButtonSelector(final String viewIcon) {
        return viewButtonSelector.replace(SWITCHER_ICON_PLACEHOLDER, viewIcon);
    }

    private String createCurrentViewButtonSelector(final String viewIcon) {
        return currentViewButtonSelector.replace(SWITCHER_ICON_PLACEHOLDER, viewIcon);
    }

    /**
     * Tries to switch the attached collection to the specified view. It identifies the switcher and checks if the icon
     * for the current view is already the one desired. If not, clicks the switcher to show the overlay with the
     * possible views and chose from there.
     *
     * @param viewType - the icon corresponding to the view to switch to.
     * @return {Boolean} {@code true} if the view was indeed changed or {@code false} if the desired view was already
     * already the current one.
     */
    public boolean switchToView(final ViewType viewType) {
        element().should(Constants.EXISTS_ENABLED_VISIBLE);

        final String viewIcon = viewType.iconName();

        SelenideElement currentViewButton = $(createCurrentViewButtonSelector(viewIcon));
        if (currentViewButton.isDisplayed()) {
            // desired view already current
            return false;
        }

        element().click();

        // select corresponding view in popover in case we are not in the simple mode where the click above already changed the view
        if ($(toggleButtonSelector).has(Condition.attribute("aria-haspopup", "true"))) {
            $(popoverMenuSelector).should(Condition.cssClass("is-open")).shouldBe(
                    Constants.EXISTS_ENABLED_VISIBLE);

            $(createSwitchButtonSelector(viewIcon)).should(Constants.EXISTS_ENABLED_VISIBLE).click();

            $(popoverMenuSelector).shouldNotBe(Condition.visible);
        }

        return true;
    }
}
