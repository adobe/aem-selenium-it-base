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

package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;

public final class IncludeChildrenDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog.cq-common-include-children-dialog";
    private static final String INCLUDE_CHILDREN_SELECTOR = "coral-checkbox[name=\"includeChildren\"]";
    private static final String IMMEDIATE_CHILDREN_SELECTOR = "coral-checkbox[name=\"onlydirect\"]";
    private static final String MODIFIED_CHILDREN_SELECTOR = "coral-checkbox[name=\"onlymodified\"]";
    private static final String PUBLISHED_CHILDREN_SELECTOR = "coral-checkbox[name=\"reactivate\"]";

    private final SelenideElement immediateChildrenField;
    private final SelenideElement includeChildrenField;
    private final SelenideElement modifiedChildrenField;
    private final SelenideElement publishedChildrenField;

    /**
     * Construct a UserPreferences Component.
     */
    public IncludeChildrenDialog() {
        super(CSS_SELECTOR);
        includeChildrenField = element().$(INCLUDE_CHILDREN_SELECTOR);
        immediateChildrenField = element().$(IMMEDIATE_CHILDREN_SELECTOR);
        modifiedChildrenField = element().$(MODIFIED_CHILDREN_SELECTOR);
        publishedChildrenField = element().$(PUBLISHED_CHILDREN_SELECTOR);
    }

    /**
     * @return true if include children field is currently checked.
     */
    public boolean getIncludeChildrenField() {
        return getIsCheckedState(includeChildrenField);
    }

    /**
     * Check include children checkbox (if not yet checked).
     */
    public void checkIncludeChildrenField() {
        toggleIfNot(includeChildrenField, true);
    }

    /**
     * Uncheck include children checkbox (if currently checked).
     */
    public void uncheckIncludeChildrenField() {
        toggleIfNot(includeChildrenField, false);
    }
    
    /**
     * @return true if is only direct children field is currently checked.
     */
    public boolean getImmediateChildrenField() {
        return getIsCheckedState(immediateChildrenField);
    }

    /**
     * Check is only direct children checkbox (if not yet checked).
     */
    public void checkOnlyDirectChildrenField() {
        toggleIfNot(immediateChildrenField, true);
    }

    /**
     * Uncheck is only direct children checkbox (if currently checked).
     */
    public void uncheckOnlyDirectChildrenField() {
        toggleIfNot(immediateChildrenField, false);
    }

    /**
     * @return true if is only direct children field is currently checked.
     */
    public boolean getModifiedChildrenField() {
        return getIsCheckedState(modifiedChildrenField);
    }

    /**
     * Check is only direct children checkbox (if not yet checked).
     */
    public void checkModifiedChildrenField() {
        toggleIfNot(modifiedChildrenField, true);
    }

    /**
     * Uncheck is only direct children checkbox (if currently checked).
     */
    public void uncheckModifiedChildrenField() {
        toggleIfNot(modifiedChildrenField, false);
    }

    /**
     * @return true if is only direct children field is currently checked.
     */
    public boolean getPublishedChildrenField() {
        return getIsCheckedState(publishedChildrenField);
    }

    /**
     * Check is only direct children checkbox (if not yet checked).
     */
    public void checkPublishedChildrenField() {
        toggleIfNot(publishedChildrenField, true);
    }

    public NoChildrenWarningDialog noChildrenWarningDialog() {
        return new NoChildrenWarningDialog();
    }

    /**
     * Uncheck is only direct children checkbox (if currently checked).
     */
    public void uncheckPublishedChildrenField() {
        toggleIfNot(publishedChildrenField, false);
    }

    private void toggleIfNot(final SelenideElement element, boolean newState) {
        element.should(EXISTS_ENABLED_VISIBLE);
        if (getIsCheckedState(element) != newState) {
            element.click();
        }
    }

    private boolean getIsCheckedState(SelenideElement element) {
        return element.getAttribute("checked") != null;
    }

    public static final class NoChildrenWarningDialog extends Dialog {

        private static final String CSS_SELECTOR = "coral-dialog[role=\"alertdialog\"]";
        private static final String WARNING_TEXT = "There are no filtered child resources for the selected item.";
        private static final String CLOSE_BUTTON = "button[handle=\"closeButton\"]";

        SelenideElement closeButton;

        public NoChildrenWarningDialog() {
            super(CSS_SELECTOR);
            closeButton = element().$(CLOSE_BUTTON);
        }

        public void isObserved() {
            waitVisible();
            element().shouldHave(Condition.matchText(WARNING_TEXT));
            closeButton.click();
            waitVanish();
        }

    }
}
