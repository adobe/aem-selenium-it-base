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

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class RichTextDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";
    private static final String EDITABLE = ".cq-RichText-editable";
    private static final String SOURCE_CODE_EDITOR = ".rte-sourceEditor";

    public RichTextDialog() {
        super(CSS_SELECTOR);
    }
    public RichTextDialog(SelenideElement element) {
        super(element);
    }

    public SelenideElement getEditable() { return element().find(EDITABLE); }

    public String getText() { return getEditable().shouldBe(Condition.visible).getText(); }

    public String getPlainText() { return getEditable().shouldBe(Condition.visible).getValue(); }

    public String setText(String text) {
        SelenideElement editableElement = getEditable().shouldBe(Condition.visible);
        editableElement.toWebElement().clear();
        editableElement.sendKeys(text);
        return getText();
    }

    public RichTextToolbar getToolbar() {
        RichTextToolbar toolbar = new RichTextToolbar();
        toolbar.element().shouldBe(Condition.visible);
        getEditable().shouldBe(Condition.visible);
        return toolbar;
    }

    public RichTextDialog clickCancel() { return super.clickDefault(); }
    public RichTextDialog clickDone() { return super.clickPrimary(); }
}
