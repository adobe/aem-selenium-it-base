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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

public class InlineEditor extends AEMBaseComponent {

    private static final Logger LOG = LoggerFactory.getLogger(InlineEditor.class);

    private static final String INLINEEDITOR_IDENTIFIER = "#InlineEditingUI";
    private static final String CONTENT_FRAME = "ContentFrame";
    private static final String TEXT_CONTENT_SELECTOR = ".text.cq-Editable-dom";
    private static final String TEXT_CONTENT_EDITABLE_SELECTOR = TEXT_CONTENT_SELECTOR + "[contenteditable]";

    private static final String SAVE_BUTTON = "[data-action='control#save']";
    private static final String CLOSE_BUTTON = "[data-action='control#close']";

    private SelenideElement saveButton = $(SAVE_BUTTON);
    private SelenideElement closeButton = $(CLOSE_BUTTON);

    private EditableToolbar editableToolbar;
    private RichTextToolbar richTextToolbar;

    public InlineEditor(EditableToolbar editableToolbar) {
        super(INLINEEDITOR_IDENTIFIER);
        this.editableToolbar = editableToolbar;
        richTextToolbar = new RichTextToolbar();
    }

    public EditableToolbar save() {return clickAction(saveButton); }
    public EditableToolbar close() {return clickAction(closeButton); }

    private EditableToolbar clickAction(SelenideElement button) {
        clickableClick(button);
        element().shouldNotBe(Condition.visible);
        editableToolbar.element().shouldBe(Condition.visible);
        return editableToolbar;
    }

    public RichTextToolbar getRichTextToolbar() { return richTextToolbar; }

    public String getTextContent() {
        return setOrGetTextContent(null);
    }

    public String setTextContent(String textContent) {
        return setOrGetTextContent(textContent);
    }

    private String setOrGetTextContent(String textContent) {
        switchTo().frame(CONTENT_FRAME);
        String newTextContent = "";
        try {
            if (textContent == null) {
                newTextContent = $(TEXT_CONTENT_EDITABLE_SELECTOR).should(Condition.exist).getText();
            } else {
                newTextContent = $(TEXT_CONTENT_EDITABLE_SELECTOR).should(Condition.exist).setValue(textContent).getText();
            }
        } catch (NoSuchElementException nse) {
            LOG.warn("No inline editing element found - open inline editor first by click to editable toolbar edit button: " + nse.getLocalizedMessage());
        } finally {
            switchTo().defaultContent();
        }
        return newTextContent;
    }
}
