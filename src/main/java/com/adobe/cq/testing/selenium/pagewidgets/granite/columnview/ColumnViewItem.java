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

/* *
 * The ColumnView item component wrapper.
 */
package com.adobe.cq.testing.selenium.pagewidgets.granite.columnview;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.codeborne.selenide.Selenide.$;

public class ColumnViewItem {

    private final String itemSelector;
    private final String previewSelector;

    /**
     * @param collectionSelector parent collection selector.
     * @param relId relative selector for the item.
     */
    public ColumnViewItem(final String collectionSelector, final String relId) {
        itemSelector =
            collectionSelector + " coral-columnview-item[data-foundation-collection-item-id='" + relId + "']";
        previewSelector =
            collectionSelector + " coral-columnview-preview[data-foundation-layout-columnview-columnid='" + relId + "']";
    }

    /**
     * Waits till the preview of this column is visible.
     */
    public void waitForPreview() {
        $(previewSelector).shouldBe(EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Checks if the preview of this column is visible.
     *
     * @return the result of the check.
     */
    public boolean previewIsVisible() {
        return $(previewSelector).isDisplayed();
    }
    
    /**
     * Checks if the item is visible.
     *
     * @return the result of the check.
     */
    public boolean isVisible() {
        return $(itemSelector).isDisplayed();
    }


    /**
     * Returns whether or not the item is a parent in the columnview.
     *
     * @return whether or not the item is a parent.
     */
    public boolean isParent() {
        return $(itemSelector + "[variant='drilldown']").exists();
    }

    /**
     * Selects the item by clicking on the thumbnail.
     */
    public void select() {
        if ($(itemSelector + "[selected='true']").exists()) {
            return;
        }
        $(itemSelector + " coral-columnview-item-thumbnail").should(EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * Deselects the item by clicking on the thumbnail.
     */
    public void deselect() {
        if (!$(itemSelector + "[selected='true']").exists()) {
            return;
        }
        $(itemSelector + " coral-columnview-item-thumbnail").should(EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * Activates the item by click on it.
     */
    public void activate() {
        $(itemSelector).should(EXISTS_ENABLED_VISIBLE).click();
    }
}
