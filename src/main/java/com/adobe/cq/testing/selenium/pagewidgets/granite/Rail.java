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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.cq.EnumRail;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.codeborne.selenide.Selenide.$$;

public class Rail extends AEMBaseComponent {

    private final String panelsSelector;
    private final String openClass;

    /**
     * Default constructor.
     */
    public Rail() {
        super(".foundation-layout-panel-rail");

        panelsSelector = getCssSelector() + " .foundation-layout-panel-rail-panel";
        openClass = "foundation-layout-panel-rail-active";
    }

    /**
     * Gets the panels inside the rail.
     *
     * @return panels inside the rail.
     */
    public ElementsCollection panels() {
        return $$(panelsSelector);
    }

    /**
     * Whether the rail is open or not.
     *
     * @return true if the rail is open, otherwise false.
     */
    public boolean isOpen() {
        waitForElementAnimationFinished(getCssSelector());
        return element().has(Condition.cssClass(openClass));
    }

    /**
     * Returns a panel based on the name.
     *
     * @param rail - the name of the panel.
     * @return the panel element.
     */
    public SelenideElement getPanelByName(final EnumRail rail) {
        final String selector = String.format("coral-panel[data-shell-collectionpage-rail-panel='%s']", rail.getPanelName());
        return element().$(selector);
    }

    /**
     * Returns a panel based on the selector.
     *
     * @param selector - selector used to identify the panel.
     * @return the panel element.
     */
    public SelenideElement getPanelBySelector(final String selector) {
        return element().$(selector);
    }
}
