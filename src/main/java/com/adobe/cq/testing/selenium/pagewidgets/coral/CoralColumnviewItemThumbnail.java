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
import com.codeborne.selenide.SelenideElement;

public class CoralColumnviewItemThumbnail extends AEMBaseComponent {

    /**
     * @param selector wrapping on this direct selector.
     */
    public CoralColumnviewItemThumbnail(final String selector) {
        super(selector);
    }

    /**
     * @param parent element in which the coral-columnview-item-thumbnail is found.
     */
    public CoralColumnviewItemThumbnail(final SelenideElement parent) {
        super(parent.getSearchCriteria() + " coral-columnview-item-thumbnail");
    }

    /**
     * @return true if the parent element has the "is-selected" class
     */
    public boolean isSelected() {
        return element().parent().has(Condition.cssClass("is-selected"));
    }

}
