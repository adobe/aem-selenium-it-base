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

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$$;

public class RichTextToolbar extends BaseComponent {

    private static final String CSS_SELECTOR = ".rte-toolbar";

    public RichTextToolbar() {
        super($$(CSS_SELECTOR).filterBy(Condition.visible).first());
    }

    public enum Action {

        SAVE("control#save"),
        CLOSE("control#close"),
        FORMAT("#format"),
        JUSTIFY("#justify"),
        LISTS("#lists"),
        LINK("links#modifylink"),
        ANCHOR("links#anchor"),
        UNLINK("links#unlink"),
        FIND("findreplace#find"),
        REPLACE("findreplace#replace"),
        UNDO("undo#undo"),
        REDO("undo#redo"),
        TABLE("table#createoredit"),
        IMAGE("image#imageProps"),
        SPELLCHECK("spellcheck#checktext"),
        EDIT_CUT("edit#cut"),
        EDIT_COPY("edit#copy"),
        EDIT_PASTE("edit#paste-default"),
        EDIT_PASTE_PLAINTEXT("edit#paste-plaintext"),
        EDIT_PASTE_WORDHTML("edit#paste-wordhtml"),
        FORMAT_BOLD("format#bold"),
        FORMAT_ITALIC("format#italic"),
        FORMAT_UNDERLINE("format#underline"),
        FORMAT_SUBSCRIPT("subsuperscript#subscript"),
        FORMAT_SUPERSCRIPT("subsuperscript#superscript"),
        JUSTIFY_LEFT("justify#justifyleft"),
        JUSTIFY_CENTER("justify#justifycenter"),
        JUSTIFY_RIGHT("justify#justifyright"),
        JUSTIFY_JUSTIFY("justify#justifyjustify"),
        LISTS_UNORDERED("lists#unordered"),
        LISTS_ORDERED("lists#ordered"),
        LISTS_OUTDENT("lists#outdent"),
        LISTS_INDENT("lists#indent");

        private final String action;

        Action(final String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }

        public String getSelector() {
            return "[data-action='" + action + "']";
        }
    }

    public RichTextToolbar clickSaveButton() { return clickButton(Action.SAVE); }
    public RichTextToolbar clickCloseButton() { return clickButton(Action.CLOSE); }

    public RichTextToolbar clickFormatButton() { return clickButton(Action.FORMAT); }
    public RichTextToolbar clickJustifyButton() { return clickButton(Action.JUSTIFY); }
    public RichTextToolbar clickListButton() { return clickButton(Action.LISTS); }
    public RichTextToolbar clickLinkButton() { return clickButton(Action.LINK); }
    public RichTextToolbar clickUnlinkButton() { return clickButton(Action.UNLINK); }

    public RichTextToolbar clickBoldButton() { return clickButton(Action.FORMAT_BOLD); }
    public RichTextToolbar clickItalicButton() { return clickButton(Action.FORMAT_ITALIC); }
    public RichTextToolbar clickUnderlineButton() { return clickButton(Action.FORMAT_UNDERLINE); }

    public RichTextToolbar clickJustifyLeftButton() { return clickButton(Action.JUSTIFY_LEFT); }
    public RichTextToolbar clickJustifyCenterButton() { return clickButton(Action.JUSTIFY_CENTER); }
    public RichTextToolbar clickJustifyRightButton() { return clickButton(Action.JUSTIFY_RIGHT); }
    public RichTextToolbar clickJustifyJustifyButton() { return clickButton(Action.JUSTIFY_JUSTIFY); }

    public RichTextToolbar clickListsUnorderedButton() { return clickButton(Action.LISTS_UNORDERED); }
    public RichTextToolbar clickListsOrderedButton() { return clickButton(Action.LISTS_ORDERED); }
    public RichTextToolbar clickListsOutdentButton() { return clickButton(Action.LISTS_OUTDENT); }
    public RichTextToolbar clickListsIndentdButton() { return clickButton(Action.LISTS_INDENT); }

    public RichTextToolbar clickButton(Action action) {
        SelenideElement button = element().find(action.getSelector());
        button.shouldBe(Condition.visible);
        clickableClick(button);
        return this;
    }

    public SelenideElement getButton(Action action) {
        SelenideElement button = element().find(action.getSelector());
        button.should(Condition.exist);
        return button;
    }

}
