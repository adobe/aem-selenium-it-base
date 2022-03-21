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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.adobe.cq.testing.selenium.pagewidgets.granite.columnview.ColumnView;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class RestoreVersionWizard extends Wizard {

    private static final String RESTORE_VERSION_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/restoreversionwizard.html";

    private static final SelenideElement TITLE_FIELD = new FormField("wizardTitle")
        .getFullyDecoratedElement("coral-panel.is-selected input");
    private static final ColumnView LIST_VIEW = new ColumnView(
        "coral-panel.is-selected .cq-sites-restoreversion-sourcepages");
    private static final SelenideElement COLLECTION_STATUS = new AEMBaseComponent(
        ".cq-common-collectionstatus").element();

    /**
     * @return true if the wizard is opened.
     */
    public boolean isOpened() {
        return WebDriverRunner.url().contains(RESTORE_VERSION_WIZARD_URL);
    }

    /**
     * @return the title field.
     */
    public SelenideElement title() {
        return TITLE_FIELD;
    }

    /**
     * @return the list view collection.
     */
    public Collection collection() {
        return LIST_VIEW;
    }

    /**
     * @return the collection status.
     */
    public SelenideElement collectionStatus() {
        return COLLECTION_STATUS;
    }

    /**
     * @param uuid  uuid of the select versions object
     * @param index of the select option
     */
    public void selectVersion(String uuid, int index) {
        CoralSelect select = new CoralSelect("uuid='" + uuid + "'");
        select.click();
        CoralPopOver popOver = CoralPopOver.firstOpened();
        popOver.waitVisible();
        CoralSelectList list = new CoralSelectList(popOver.element());
        list.selectByIndex(index);
    }

    /**
     * @param parent the parent page on which to create the launch.
     * @param source the source page for this launch.
     * @return the page object for this wizard.
     */
    public static RestoreVersionWizard open(final String parent, final String source) {
        return new RestoreVersionWizard().open(
            String.format("%s%s?%s", RESTORE_VERSION_WIZARD_URL, parent, source));
    }

}
