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

public class CoralTagList extends BaseComponent {

    public CoralTagList(SelenideElement element) {
        super(element);
    }

    private static final String SELECTOR_ITEM_ELEMENT = "coral-tag";

    /**
     * @param attribute the attribute value for this tag list.
     */
    public CoralTagList(final String attribute) {
        super(String.format("coral-taglist[%s]", attribute));
    }

    /**
     * @param value value of the targeted item in this tag list.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByValue(final String value) {
        return element().$(String.format("%s[value=\"%s\"]", SELECTOR_ITEM_ELEMENT, value));
    }
    
    /**
     * @param text value of the targeted item in this tag list.
     * @return the element or null if it doesn't exist.
     */
    public SelenideElement getItemByText(final String text) {
        return element().$$(SELECTOR_ITEM_ELEMENT).findBy(Condition.text(text)).parent();
    }
    
    /**
     * 
     * @return the elements of the taglist
     */
    public ElementsCollection getItems() {
        return element().$$(SELECTOR_ITEM_ELEMENT);
    }
}
