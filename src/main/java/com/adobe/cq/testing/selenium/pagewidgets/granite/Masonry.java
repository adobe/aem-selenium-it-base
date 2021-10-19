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

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralQuickActions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

/**
 * The Masonry component wrapper.
 */
public final class Masonry extends Collection {

    /**
     * Creates the wrapper element for the masonry identified by the provided selector.
     *
     * @param selector - the CSS selector identifying the masonry html element to wrap.
     */
    public Masonry(final String selector) {
        super(selector);
        setAllItemsSelector(selector + " coral-masonry-item");
        setLazyLoadingSupported(true);
        setHasMoreElementsSelector(selector + " + .granite-collection-loading-title-wrapper .granite-collection-loading-title");
    }

    /**
     * Scroll to the end of the masonry.
     * @return self
     */
    public Collection scrollToEnd() {
        final ElementsCollection elements = getItems();
        int size = elements.size();
        if (size > 0) {
            elements.get(size - 1).scrollIntoView("");
        }
        return this;
    }

    /**
     * @param item the element on which toggle item is called, default it click on coral-checkbox.
     * @return self
     */
    @Override
    protected Collection toggleItem(final SelenideElement item) {
        final CoralCheckbox coralCheckbox = new CoralCheckbox(item);
        if (coralCheckbox.isVisible()) {
            coralCheckbox.click();
        } else {
            item.hover();
            if (coralCheckbox.isVisible()) {
                coralCheckbox.click();
            } else {
                clickableClick(new CoralQuickActions().getQuickAction("check"));
            }
        }
        return this;
    }

    /**
     * Scroll to the top of the masonry.
     * @return self
     */
    public Collection scrollToTop() {
        final ElementsCollection elements = getItems();
        int size = elements.size();
        if (size > 0) {
            elements.get(0).scrollIntoView("");
        }
        return this;
    }
}
