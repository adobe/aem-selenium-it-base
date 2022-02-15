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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public final class CoralButtonList extends AEMBaseComponent {

    private static final String BUTTON_ITEM_ELEMENT = "button[is=\"coral-buttonlist-item\"]";

    /**
     * @param parent the parent element containing this select list.
     */
    public CoralButtonList(final SelenideElement parent) {
        super(parent.getSearchCriteria() + " coral-buttonlist");
    }

    /**
     * @param selector the selector to wrap this object on.
     */
    public CoralButtonList(final String selector) {
        super(selector);
    }

    /**
     * @return all the items in this list.
     */
    public ElementsCollection items() {
        return element().$$(BUTTON_ITEM_ELEMENT);
    }

    /**
     * @param value value of the targeted item in this list.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByValue(final String value) {
        return element().$(String.format("%s[value=\"%s\"]", BUTTON_ITEM_ELEMENT, value));
    }
    
    /**
     * @param text
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByText(final String text) {
        return element().$$(BUTTON_ITEM_ELEMENT).findBy(Condition.text(text));
    }

    /**
     * Clicks an item.
     *
     * @param value - the value of the item in the rail toggle.
     */
    public void selectByValue(final String value) {
        final SelenideElement item = getItemByValue(value);
        if (item != null) {
            clickableClick(item);
        }
    }

    /**
     * Determines if an option exists on the cyclebutton.
     *
     * @param value - the value of the item in the rail toggle.
     * @return true if an option exists or not on the cyclebutton.
     */
    public boolean hasItemByValue(final String value) {
        return getItemByValue(value) != null;
    }
}
