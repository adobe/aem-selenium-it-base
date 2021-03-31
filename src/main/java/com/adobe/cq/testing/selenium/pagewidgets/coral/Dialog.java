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
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.coral.VariantAccessor.VariantType.*;
import static com.codeborne.selenide.Selenide.$;

public class Dialog extends BaseComponent {

    protected VariantAccessor variantAccessor;

    /**
     * Constructor for default coral dialog.
     */
    public Dialog() {
        super("coral-dialog[open]");
        init();
    }

    /**
     * @param cssSelector css selector
     */
    public Dialog(final String cssSelector) {
        super(String.format("%s[open]", cssSelector));
        init();
    }

    public Dialog(final SelenideElement element) {
        super(element);
        init();
    }

    private void init() {
        variantAccessor = adaptTo(VariantAccessor.class);
    }

    /**
     * @return the title for this dialog.
     */
    public SelenideElement title() {
        return $(String.format("%s coral-dialog-header", getCssSelector()));
    }

    /**
     * @return the content for this dialog.
     */
    public SelenideElement content() {
        return $(String.format("%s coral-dialog-content", getCssSelector()));
    }

    /**
     * checks if the modal is from type success.
     * @return true if the modal indicates success, false otherwise
     */
    public boolean isSuccess() {
        return variantAccessor.isVariant(SUCCESS);
    }

    /**
     * checks if the modal is from type error.
     * @return true if the modal indicates error, false otherwise
     */
    public boolean isError() {
        return variantAccessor.isVariant(ERROR);
    }

    /**
     * Close this dialog with default action button.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends Dialog> T clickDefault() {
        variantAccessor.clickVariant(DEFAULT);
        return (T) this;
    }

    /**
     * Click on primary action.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends Dialog> T clickPrimary() {
        variantAccessor.clickVariant(PRIMARY);
        return (T) this;
    }

    /**
     * Click on secondary action.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends Dialog> T clickSecondary() {
        variantAccessor.clickVariant(SECONDARY);
        return (T) this;
    }

    /**
     * Click on warning action.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends Dialog> T clickWarning() {
        variantAccessor.clickVariant(WARNING);
        return (T) this;
    }

    public SelenideElement button(final VariantAccessor.VariantType variantType) {
        return variantAccessor.find(variantType);
    }
}
