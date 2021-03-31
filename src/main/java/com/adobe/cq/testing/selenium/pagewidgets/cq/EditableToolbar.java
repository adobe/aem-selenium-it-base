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

import com.adobe.cq.testing.selenium.pageobject.EditorPage;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.clickBaseComponentAction;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.clickDialogAction;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public class EditableToolbar extends BaseComponent {

    private static final String EDITABLETOOLBAR_IDENTIFIER = "#EditableToolbar";

    // generic buttons
    private ActionComponent<InsertComponentDialog> insertButton = new ActionComponent(EditableToolbarAction.INSERT.getButton(), () -> new InsertComponentDialog(), false);
    private ActionComponent<Dialog> configureButton = new ActionComponent(EditableToolbarAction.CONFIGURE.getButton(), () -> new Dialog(), false);
    private SelenideElement parentButton = EditableToolbarAction.PARENT.getButton();
    private SelenideElement editButton = EditableToolbarAction.EDIT.getButton();
    private SelenideElement layoutButton = EditableToolbarAction.LAYOUT.getButton();
    private SelenideElement copyButton = EditableToolbarAction.COPY.getButton();
    private SelenideElement cutButton = EditableToolbarAction.CUT.getButton();
    private SelenideElement pasteButton = EditableToolbarAction.PASTE.getButton();
    private SelenideElement panelSelectButton = EditableToolbarAction.PANEL_SELECT.getButton();
    private ActionComponent<Dialog>  deleteButton = new ActionComponent(EditableToolbarAction.DELETE.getButton(), () -> new Dialog( "coral-dialog"), false);
    private SelenideElement groupButton = EditableToolbarAction.GROUP.getButton();
    private ActionComponent<StylesSelector> stylesButton = new ActionComponent(EditableToolbarAction.STYLE.getButton(), () -> new StylesSelector(StylesSelector.Variant.COMPONENT), false);

    // template editor specific buttons
    private ActionComponent<PolicyDialog> policyButton = new ActionComponent(element().find(EditableToolbarAction.POLICY.getSelector()), () -> new PolicyDialog(), false);
    private ActionComponent<Dialog>  unlockStructureButton = new ActionComponent(EditableToolbarAction.STRUCTURE_OFF.getButton(), () -> new Dialog( "coral-dialog"), false); // unlock -> editable on page
    private ActionComponent<Dialog>  lockStructureButton = new ActionComponent(EditableToolbarAction.STRUCTURE_ON.getButton(), () -> new Dialog( "coral-dialog"), false); // lock -> structure only, not editable on page

    // layouting specific buttons
    private SelenideElement newlineButton = EditableToolbarAction.NEWLINE.getButton(); // float to new line
    private SelenideElement hideButton = EditableToolbarAction.HIDE.getButton();
    private SelenideElement unhideButton = EditableToolbarAction.UNHIDE.getButton();
    private SelenideElement restoreAllButton = EditableToolbarAction.UNHIDE.getButton();
    private SelenideElement amountButton = EditableToolbarAction.AMOUNT.getButton();
    private SelenideElement resetButton = EditableToolbarAction.RESET.getButton();
    private SelenideElement closeButton = EditableToolbarAction.CLOSE.getButton();

    // not toolbar related layouting specific buttons
    private static final String RESTORE_BUTTON = "button[data-path='%s']";

    private InlineEditor inlineEditor = new InlineEditor(this);
    private EditorPage editorPage;

    public <T extends EditorPage> EditableToolbar(T editorPage) {
        super(EDITABLETOOLBAR_IDENTIFIER);
        this.editorPage = editorPage;
    }

    public SelenideElement getButton(EditableToolbarAction editableToolbarAction) { return editableToolbarAction.getButton(); }

    public SelenideElement getInsertButton() { return insertButton.element(); }
    public SelenideElement getConfigureButton() { return configureButton.element(); }
    public SelenideElement getParentButton() { return parentButton; }
    public SelenideElement getEditButton() { return editButton; }
    public SelenideElement getLayoutButton() { return layoutButton; }
    public SelenideElement getCopyButton() { return copyButton; }
    public SelenideElement getCutButton() { return cutButton; }
    public SelenideElement getPasteButton() { return pasteButton; }
    public SelenideElement getPanelSelectButton() { return panelSelectButton; }

    public SelenideElement getDeleteButton() { return deleteButton.element(); }
    public SelenideElement getGroupButton() { return groupButton; }
    public SelenideElement getStylesButton() { return stylesButton.element(); }

    public SelenideElement getPolicyButton() { return policyButton.element(); }
    public SelenideElement getUnlockStructureButton() { return unlockStructureButton.element(); }
    public SelenideElement getLockStructureButton() { return lockStructureButton.element(); }

    public SelenideElement getNewlineButton() { return newlineButton; }
    public SelenideElement getHideButton() { return hideButton; }
    public SelenideElement getUnhideButton() { return unhideButton; }
    public SelenideElement getRestoreAllButton() { return restoreAllButton; }
    public SelenideElement getAmountButton() { return amountButton; }
    public SelenideElement getResetButton() { return resetButton; }
    public SelenideElement getCloseButton() { return closeButton; }
    public SelenideElement getRestoreButton(String resourcePath) { return $(String.format(RESTORE_BUTTON, resourcePath)); }

    public <T extends EditorPage> T clickCopy() { return clickVoidAction(copyButton); }
    public <T extends EditorPage> T clickCut() { return clickVoidAction(cutButton); }
    public <T extends EditorPage> T clickPaste() { return clickVoidAction(pasteButton); }
    public <T extends EditorPage> T clickHide() { return clickVoidAction(hideButton); }
    public <T extends EditorPage> T clickRestoreall() { return clickVoidAction(restoreAllButton); }
    public <T extends EditorPage> T clickRestore(String resourcePath) { return clickVoidAction(getRestoreButton(resourcePath)); }

    private <T extends EditorPage> T clickVoidAction(SelenideElement button) {
        clickableClick(button);
        element().shouldNotBe(Condition.visible);
        return (T) editorPage;
    }

    public InsertComponentDialog clickInsertComponent() { return clickDialogAction(insertButton); }
    public Dialog clickConfigure() { return clickDialogAction(configureButton); }
    public Dialog clickDelete() { return clickDialogAction(deleteButton); }

    public PolicyDialog clickPolicy() { return clickDialogAction(policyButton); }
    public Dialog clickUnlockStructure() { return clickDialogAction(unlockStructureButton); }
    public Dialog clickLockStructure() { return clickDialogAction(lockStructureButton); }

    public EditableToolbar clickLayout() { return clickEditableToolbarAction(layoutButton); }
    public EditableToolbar clickClose() { return clickEditableToolbarAction(closeButton); }
    public EditableToolbar clickNewLine() { return clickEditableToolbarAction(newlineButton); }
    public EditableToolbar clickGroup() { return clickEditableToolbarAction(groupButton); }
    public EditableToolbar clickParent() { return clickEditableToolbarAction(parentButton); }
    public EditableToolbar clickUnhide() { return clickEditableToolbarAction(unhideButton); }
    public EditableToolbar clickReset() { return clickEditableToolbarAction(resetButton); }
    public EditableToolbar clickPanelSelect() { return clickEditableToolbarAction(panelSelectButton); }

    protected EditableToolbar clickEditableToolbarAction(SelenideElement button) {
        clickableClick(button);
        element().shouldBe(Condition.visible);
        return this;
    }

    public StylesSelector clickStyles() { return clickBaseComponentAction(stylesButton); }
    public EditableToolbar closeStyles(StylesSelector stylesSelector) {
        if (stylesSelector != null && stylesSelector.element().is(Condition.visible)) {
            clickableClick(stylesButton.element());
            stylesSelector.element().shouldNotBe(Condition.visible);
        }
        return this;
    }

    public InlineEditor clickEdit() {
        clickableClick(editButton);
        inlineEditor.element().shouldBe(Condition.visible);
        return inlineEditor;
    }

    /**
     * Click the button to select the parent container corresponding to the provided index.
     * Note: For nested containers a click to the parent button opens a popover with the list of buttons for the parent
     * containers up in the hierarchy.
     *
     * @param index Hierarchy index of the parent to be selected, for direct parent use index=0, for grand parent use index=1 etc
     * @return The editable toolbar
     */
    public EditableToolbar selectParent(int index) {
        ElementsCollection parentButtons = $("coral-popover.is-open .cq-select-parent-list").findAll("button");
        clickableClick(parentButtons.get(index));
        return this;
    }

    public enum EditableToolbarAction {
        INSERT, CONFIGURE, PARENT, EDIT, LAYOUT, COPY, CUT, PASTE, DELETE, GROUP, STYLE,
        POLICY, STRUCTURE_OFF, STRUCTURE_ON,
        NEWLINE, HIDE, UNHIDE, AMOUNT, RESET, CLOSE, PANEL_SELECT;

        public String getSelector() {
            return "button[data-action='" + name() + "']";
        }

        public SelenideElement getButton() {
            return $(EDITABLETOOLBAR_IDENTIFIER).find(getSelector());
        }

    }

}