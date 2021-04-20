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

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.clickDialogAction;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public class PolicyDialog extends Dialog {

    private static final String CSS_SELECTOR = "coral-dialog";
    private static final String CANCEL_CSS = "button.cq-dialog-cancel";
    private static final String DONE_CSS = "button.cq-dialog-submit";
    private static final String POLICY_CREATE_CSS = "button.js-Policy-create";
    private static final String POLICY_DUPLICATE_CSS = "button.js-Policy-duplicate";
    private static final String EDIT_DESIGN_CSS = "button[trackingelement='edit design']";

    // dialog buttons
    private ActionComponent<Dialog> editDesignButton = new ActionComponent($(EDIT_DESIGN_CSS), () -> new Dialog(), false);
    CoralSelect policySelect = new CoralSelect("placeholder=\"New policy\"");
    private SelenideElement cancelButton = $(CANCEL_CSS);
    private SelenideElement doneButton = $(DONE_CSS);
    private SelenideElement policyCreateButton = $(POLICY_CREATE_CSS);
    private SelenideElement policyDuplicateButton = $(POLICY_DUPLICATE_CSS);

    // dialog form fields
    private static final SelenideElement TITLE_FIELD = new FormField("./jcr:title")
            .getFullyDecoratedElement("coral-dialog.is-open input");
    private static final SelenideElement DESCRIPTION_FIELD = new FormField("./jcr:description")
            .getFullyDecoratedElement("coral-dialog.is-open textarea");

    public PolicyDialog() {
        super(CSS_SELECTOR);
    }

    public SelenideElement getEditDesignButton() { return editDesignButton.element(); }
    public CoralSelect getPolicySelect() { return policySelect; }
    public SelenideElement getCancelButton() { return cancelButton; }
    public SelenideElement getDoneButton() { return doneButton; }

    public Dialog clickEditDesign() { return Helpers.clickDialogAction(editDesignButton); }
    public PolicyDialog selectPolicy(String policyPath) {
        getPolicySelect().selectItemByValue(policyPath);
        waitPageUnmasked();
        return this;
    }

    public PolicyDialog clickCancel() { return clickButton(cancelButton); }
    public PolicyDialog clickDone() { return clickButton(doneButton); }
    public PolicyDialog clickPolicyCreate() { return clickPolicyButton(policyCreateButton); }
    public PolicyDialog clickPolicyDuplicate() { return clickPolicyButton(policyDuplicateButton); }

    public PolicyDialog clickButton(SelenideElement button) {
        clickableClick(button.shouldBe(Condition.visible, Condition.enabled));
        waitVanish();
        return this;
    }

    public PolicyDialog clickPolicyButton(SelenideElement button) {
        clickableClick(button.shouldBe(Condition.visible, Condition.enabled));
        Helpers.waitDOMIdled(250);
        waitPageUnmasked();
        return this;
    }

    /**
     * @return the policy title field.
     */
    public SelenideElement policyTitle() { return TITLE_FIELD; }

    /**
     * @return the policy description field.
     */
    public SelenideElement policyDescription() {
        return DESCRIPTION_FIELD;
    }
}
