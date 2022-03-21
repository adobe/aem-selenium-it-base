/*
 * Copyright 2022 Adobe Systems Incorporated
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

public final class CoralAnchorList extends AEMBaseComponent {

    private static final String SELECTOR_ITEM_ELEMENT = "a[is=\"coral-anchorlist-item\"]";

    /**
     * @param parent the parent element containing this select list.
     */
    public CoralAnchorList(final SelenideElement parent) {
        super(parent.$("coral-anchorlist"));
    }

    /**
     * @param selector the selector to wrap this object on.
     */
    public CoralAnchorList(final String selector) {
        super(selector);
    }

    /**
     * @return all the items in this list.
     */
    public ElementsCollection items() {
        return element().$$(SELECTOR_ITEM_ELEMENT);
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
            elem.shouldBe(Condition.enabled, Condition.visible);
            elem.click();
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

    public void selectByTrackingValue(final String trackingValue) {
        items().stream()
            .map(AEMBaseComponent::new)
            .filter(e -> trackingValue.equals(e.getTrackingElement()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Cannot find matching element for " + trackingValue)).click();
}
}
