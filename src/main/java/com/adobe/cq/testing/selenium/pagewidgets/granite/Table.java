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
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * The Table component wrapper.
 */
public class Table extends Collection {

    private final String scrollContainerSelector;
    private final String selectAllCheckboxSelector;

    /**
     * Creates the wrapper element for the table identified by the provided selector.
     *
     * @param selector - the CSS selector identifying the table html element to wrap.
     */
    public Table(final String selector) {
        super(selector);

        setLazyLoadingSupported(true);
        setHasMoreElementsSelector(String.format("%s .granite-collection-loading-title", selector));
        scrollContainerSelector = String.format("%s [coral-table-scroll]", selector);
        selectAllCheckboxSelector = String.format("%s [coral-table-select]", selector);
    }

    /**
     * Scroll to the end of the table.
     */
    public void scrollToEnd() {
        final ElementsCollection elements = $$(scrollContainerSelector);
        if (!elements.isEmpty()) {
            elements.get(elements.size() - 1).scrollTo();
        }
    }

    /**
     * Scroll to the top of the table.
     */
    public void scrollToTop() {
        final ElementsCollection elements = $$(scrollContainerSelector);
        if (!elements.isEmpty()) {
            elements.get(0).scrollTo();
        }
    }

    /**
     * Gets the select all checkbox element of the this table collection component.
     * @return the checkbox element for the select all.
     */
    public SelenideElement selectAllCheckbox() {
        return $(selectAllCheckboxSelector).should(Constants.EXISTS_ENABLED_VISIBLE);
    }
}
