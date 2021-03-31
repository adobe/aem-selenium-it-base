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
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public final class CoralSelectList extends BaseComponent {

    private static final String SELECTOR_ITEM_ELEMENT = "coral-selectlist-item";
    private static final String SELECTOR_ITEM_ELEMENT_CONTENT = "coral-selectlist-item-content";
    private static final String SELECTOR_GROUP_ELEMENT = "coral-selectlist-group";

    /**
     * @param parent the parent element containing this select list.
     */
    public CoralSelectList(final SelenideElement parent) {
        super(parent.$("coral-selectlist"));
    }

    /**
     * @param selector the selector to wrap this object on.
     */
    public CoralSelectList(final String selector) {
        super(selector);
    }

    /**
     * @return all the items in this list.
     */
    public ElementsCollection items() {
        return element().$$(SELECTOR_ITEM_ELEMENT);
    }

    /**
     * @return all the items of a group in this list.
     */
    public ElementsCollection items(SelenideElement group) {
        return group.findAll(SELECTOR_ITEM_ELEMENT);
    }

    /**
     * @return all the groups in this list.
     */
    public ElementsCollection groups() {
        return element().findAll(SELECTOR_GROUP_ELEMENT);
    }

    /**
     * @param label label of the targeted group in this list.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getGroupByLabel(final String label) {
        return element().$(String.format("%s[label=\"%s\"]", SELECTOR_GROUP_ELEMENT, label));
    }

    /**
     * @param value value of the targeted item in this list.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByValue(final String value) {
        return element().$(String.format("%s[value=\"%s\"]", SELECTOR_ITEM_ELEMENT, value));
    }

    /**
     * @return the selected item element or null if it doesn't exist.
     */
    public SelenideElement selectedItem() {
        return element().$(String.format("%s[selected]", SELECTOR_ITEM_ELEMENT));
    }

    /**
     * Returns the label of the selected item.
     *
     * @return the selected label.
     */
    public String selectedItemLabel() {
        return selectedItem().find(SELECTOR_ITEM_ELEMENT_CONTENT).getText();
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
     * Clicks an item.
     *
     * @param index - the index of the item in the rail toggle.
     */
    public void selectByIndex(final int index) {
        final ElementsCollection elems = items();
        if (elems.size() > index) {
            SelenideElement elem = elems.get(index);
            clickableClick(elem);
        }
    }

    /**
     * Clicks an item.
     *
     * @param label - the selector of the item in the rail toggle.
     */
    public void selectByLabel(final String label) {
        ElementsCollection elements = items();
        final SelenideElement item = elements.find(Condition.text(label));
        if (item != null) {
            clickableClick(item);
        }
    }

    /**
     * Determines if an option exists on the cyclebutton.
     *
     * @param selector - the selector of the item in the rail toggle.
     * @return true if an option exists or not on the cyclebutton.
     */
    public boolean hasItemBySelector(final String selector) {
        return element().$(String.format("%s%s", SELECTOR_ITEM_ELEMENT, selector)).exists();
    }
}
