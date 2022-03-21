/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2020 Adobe
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe and its suppliers, if any. The intellectual
 * and technical concepts contained herein are proprietary to Adobe
 * and its suppliers and are protected by all applicable intellectual
 * property laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe.
 **************************************************************************/
package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pageobject.granite.ViewType;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.IncludeChildrenDialog;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Picker;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Table;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public final class CreateWorkflowWizard extends Wizard {

    private static final String CREATE_WF_WIZARD_URL = "/mnt/override/libs/wcm/core/content/common/startbulkworkflows.html";

    private static final SelenideElement CREATE_WF_FORM = $("form.cq-sites-startbulkworkflows-form");

    private static final SelenideElement SELECTION_MODEL_BUTTON = $("button[variant=\"_custom\"]");

    private static final SelenideElement WORKFLOW_TITLE = new FormField("workflowTitle").getFullyDecoratedElement("input");

    private static final SelenideElement PACKAGE_TITLE = new FormField("packageTitle").getFullyDecoratedElement("input");

    private static final CoralCheckbox KEEP_WORKFLOW_PACKAGE = new CoralCheckbox("coral-checkbox.cq-sites-startbulkworkflows-keeppackage");

    private static final Collection COLLECTION = new Table(".foundation-collection");

    /**
     * @return true if the wizard is opened.
     */
    public boolean isOpened() {
        return WebDriverRunner.url().contains(CREATE_WF_WIZARD_URL) && CREATE_WF_FORM.isDisplayed();
    }

    /**
     * @param modelId set the model id.
     */
    public void selectModel(final String modelId) {
        clickableClick(SELECTION_MODEL_BUTTON);
        CoralPopOver popOver = CoralPopOver.firstOpened();
        CoralSelectList list = new CoralSelectList(popOver.element());
        final SelenideElement itemByValue = list.getItemByValue(modelId);
        itemByValue.scrollTo();
        clickableClick(itemByValue);
    }

    /**
     * @return the workflow title field element.
     */
    public SelenideElement title() {
        return WORKFLOW_TITLE;
    }

    /**
     * @return the workflow package title field element.
     */
    public SelenideElement packageTitle() {
        return PACKAGE_TITLE;
    }

    /**
     * @return the keep workflow package checkbox.
     */
    public CoralCheckbox keepWorkflowPackage() {
        return KEEP_WORKFLOW_PACKAGE;
    }

    /**
     * @return the collection associated to this wizard.
     */
    public Collection collection() {
        return COLLECTION;
    }

    /**
     * @return possible actions in this wizard.
     */
    public CreateWorkflowActions actions() {
        return new CreateWorkflowActions();
    }

    // All known create workflow actions
    public static final class CreateWorkflowActions {

        /**
         * The constructor for this helper.
         */
        private CreateWorkflowActions() {
        }

        // Editor action bar items
        private static final SelenideElement ADD_CONTENT_BUTTON = $("[trackingelement=\"add content\"]");

        private static final SelenideElement INCLUDE_CHILDREN_BUTTON = $("[trackingelement=\"include children\"]");

        private static final SelenideElement REMOVE_SELECTION_BUTTON = $("[trackingelement=\"remove selection\"]");

        /**
         * @return The Add Content button element.
         */
        public SelenideElement getAddContent() {
            return ADD_CONTENT_BUTTON;
        }

        /**
         * @return The Include Children button element.
         */
        public SelenideElement getIncludeChildren() {
            return INCLUDE_CHILDREN_BUTTON;
        }

        /**
         * @return The Remove Selection button element.
         */
        public SelenideElement getRemoveSelection() {
            return REMOVE_SELECTION_BUTTON;
        }

        /**
         * Click on Add Content button and wait for the dialog.
         *
         * @return the picker that is displayed.
         */
        public Picker addContent() {
            clickableClick(ADD_CONTENT_BUTTON);
            Picker pickerDialog = new Picker("coral-dialog.foundation-picker-collection", ViewType.COLUMN);
            pickerDialog.waitVisible();
            return pickerDialog;
        }

        /**
         * Click on Remove Selection button and wait for the dialog.
         *
         * @return the alert dialog that is displayed.
         */
        public Dialog removeSelection() {
            clickableClick(REMOVE_SELECTION_BUTTON);
            Dialog dialog = new Dialog("coral-dialog[role=\"alertdialog\"]");
            dialog.waitVisible();
            return dialog;
        }

        /**
         * Click on Include Children button and wait for the dialog.
         *
         * @return the include children dialog that is displayed.
         */
        public IncludeChildrenDialog includeChildren() {
            clickableClick(INCLUDE_CHILDREN_BUTTON);
            IncludeChildrenDialog dialog = new IncludeChildrenDialog();
            dialog.waitVisible();
            return dialog;
        }

    }
}
