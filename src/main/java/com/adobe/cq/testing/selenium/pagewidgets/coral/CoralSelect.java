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

import java.util.concurrent.TimeoutException;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickUntil;

public final class CoralSelect extends BaseComponent {

    private static final String SELECTOR_ITEM_ELEMENT = "coral-select-item";
    private static final String SELECTOR_BUTTON = "button";

    /**
     * @param attribute the attribute value for this select.
     */
    public CoralSelect(final String attribute) {
        super(String.format("coral-select[%s]", attribute));
    }
    
    /**
     * @param parent element in which the coral-select is found.
     */
    public CoralSelect(final SelenideElement parent) {
      super(parent);
    }

    /**
     * @return all the items in this select.
     */
    public ElementsCollection items() {
        return element().$$(SELECTOR_ITEM_ELEMENT);
    }

    /**
     * @param value value of the targeted item in this select.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByValue(final String value) {
        return element().$(String.format("%s[value=\"%s\"]", SELECTOR_ITEM_ELEMENT, value));
    }

    /**
     * Clicks the button to open the CoralSelectList
     */
    private void beforeSelect() {
        SelenideElement activeButton = element().find(SELECTOR_BUTTON);
        CoralPopOver popOver = popover();
        try {
            clickUntil(activeButton, popOver.element(), Condition.visible, 10, 500);
        } catch (TimeoutException e) {
            throw new IllegalStateException("Cannot open the CoralSelect, something when wrong with clickUntil");
        }
    }

    /**
     * Clicks an item by it's index.
     *
     * @param index index of the targeted item in this select.
     */
    public void selectItemByIndex(final int index) {
        CoralSelectList list = openSelectList();
        list.selectByIndex(index);
    }

    /**
     * Clicks an item by it's label.
     *
     * @param label label of the targeted item in this select.
     */
    public void selectItemByLabel(final String label) {
        CoralSelectList list = openSelectList();
        list.selectByLabel(label);
    }

    /**
     * Clicks an item by it's value.
     *
     * @param value value of the targeted item in this select.
     */
    public void selectItemByValue(final String value) {
        CoralSelectList list = openSelectList();
        list.selectByValue(value);
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
        return selectedItem().getText();
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

    /**
     * @return currently opened popover for selection.
     */
    public CoralPopOver popover() {
        return new CoralPopOver("coral-popover.is-open[focusonshow=\"coral-selectlist\"]");
    }

    public CoralSelectList selectList() {
        CoralPopOver popOver = popover();
        popOver.waitVisible();
        return new CoralSelectList(popOver.element());
    }

    public CoralSelectList openSelectList() {
        beforeSelect();
        return selectList();
    }

}
