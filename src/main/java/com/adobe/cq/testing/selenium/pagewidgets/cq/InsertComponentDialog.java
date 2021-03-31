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

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForListSizeChange;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class InsertComponentDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog.InsertComponentDialog";
    private static final String SEARCH_FIELD = "input[type='search']";
    private static final String CLOSE_BUTTON = "button[handle='closeButton']";

    private CoralSelectList componentList = new CoralSelectList(element());

    public InsertComponentDialog() { super(CSS_SELECTOR); }

    public void selectComponent(String resourceType) {
        clickableClick(componentList.getItemByValue(resourceType));
        element().shouldNotBe(Condition.visible);
    }

    public ElementsCollection getComponentList() {
        return componentList.items();
    }

    public void close() {
        clickableClick(element().find(CLOSE_BUTTON));
        element().shouldNotBe(Condition.visible);
    }

    public ElementsCollection search(String searchText) {
        int initialSize = getComponentList().size();
        element().find(SEARCH_FIELD).setValue(searchText);
        waitForListSizeChange(initialSize, getComponentList(), 1000);
        return getComponentList();
    }

}
