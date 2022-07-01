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

package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForListSizeChange;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class InsertComponentDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog.InsertComponentDialog";
    private static final String LIST_SELECTOR = ".InsertComponentDialog-list";
    private static final String SELECT_LIST_SELECTOR = "coral-selectlist" + LIST_SELECTOR;
    private static final String SEARCH_FIELD = "input[type='search']";
    private static final String CLOSE_BUTTON = "button[handle='closeButton']";
    private static final String SELECTOR_ITEM_ELEMENT = "coral-list-item";

    private CoralSelectList componentSelectList = new CoralSelectList(SELECT_LIST_SELECTOR);
    private CoralList componentList = new CoralList(LIST_SELECTOR);

    public InsertComponentDialog() { super(CSS_SELECTOR); }

    /**
     * @param value value of the targeted item in this list.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByValue(final String value) {
        return element().$(String.format("%s[value=\"%s\"]", SELECTOR_ITEM_ELEMENT, value));
    }

    /**
     * select the specific component based on resource type
     * @param resourceType resource type to select a component
     */
    public void selectComponent(String resourceType) {
        if (componentSelectList.isExisting()) {
            clickableClick(componentSelectList.getItemByValue(resourceType));
        } else {
            clickableClick(this.getItemByValue(resourceType));
        }

        element().shouldNotBe(Condition.visible);
    }

    /**
     *
     * @return collections of available components
     */
    public ElementsCollection getComponentList() {
        if (componentSelectList.isExisting()) {
            return componentSelectList.items();
        } else {
            return componentList.itemsCollection();
        }
    }

    /**
     * closes the Current Dialg
     */
    public void close() {
        clickableClick(element().find(CLOSE_BUTTON));
        element().shouldNotBe(Condition.visible);
    }

    /**
     * filters the list of the components bases on search text
     * @param searchText search text
     * @return Collection of Elements which met the search
     */
    public ElementsCollection search(String searchText) {
        int initialSize = getComponentList().size();
        element().find(SEARCH_FIELD).setValue(searchText);
        waitForListSizeChange(initialSize, getComponentList(), 1000);
        return getComponentList();
    }

}
