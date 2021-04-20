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

import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.clickDialogAction;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Component class representing the page info in editor page.
 */
public final class PageInfo extends BaseComponent {

    private static final String PAGEINFO_IDENTIFIER = "#pageinfo-data";
    private static final String BUTTON_SELECTOR = "#pageinfo-trigger";
    private SelenideElement button = $(BUTTON_SELECTOR);

    private static final String OPEN_PROPERTIES = "Open Properties";
    private static final String ROLLOUT_PAGE = "Rollout Page";
    private static final String START_WORKFLOW = "Start Workflow";
    private static final String LOCK_PAGE = "Lock Page";
    private static final String UNLOCK_PAGE = "Unlock Page";
    private static final String PUBLISH_PAGE = "Publish Page";
    private static final String REQUEST_PUBLICATION = "Request Publication";
    private static final String UNPUBLISH_PAGE = "Unpublish Page";
    private static final String REQUEST_UNPUBLICATION = "Request Unpublication";
    private static final String PROMOTE_LAUNCH = "Promote Launch";
    private static final String OPEN_ANALYTICS = "Open Analytics &amp; Recommendations";
    private static final String EDIT_TEMPLATE = "Edit Template";
    private static final String VIEW_AS_PUBLISHED = "View as Published";
    private static final String VIEW_IN_ADMIN = "View in Admin";
    private static final String OPEN_IN_CLASSIC = "Open in Classic UI";
    private static final String HELP = "Help";

    private static final String INITIAL_PAGE_PROPERTIES = "Initial Page Properties";
    private static final String PAGE_POLICY = "Page Policy";
    private static final String PUBLISH_TEMPLATE = "Publish Template";

    private SelenideElement buttonPageProps = $("button[title='" + OPEN_PROPERTIES + "']");
    private SelenideElement buttonRolloutPage = $("button[title='" + ROLLOUT_PAGE + "']");
    private SelenideElement buttonStartWorkflow = $("button[title='" + START_WORKFLOW + "']");
    private SelenideElement buttonLockPage = $("button[title='" + LOCK_PAGE + "']");
    private SelenideElement buttonUnlockPage = $("button[title='" + UNLOCK_PAGE + "']");
    private SelenideElement buttonPublishPage = $("button[title='" + PUBLISH_PAGE + "']");
    private SelenideElement buttonRequestPublication = $("button[title='" + REQUEST_PUBLICATION + "']");
    private SelenideElement buttonUnpublishPage = $("button[title='" + UNPUBLISH_PAGE + "']");
    private SelenideElement buttonRequestUnpublication = $("button[title='" + REQUEST_UNPUBLICATION + "']");
    private SelenideElement buttonPromoteLaunch = $("button[title='" + PROMOTE_LAUNCH + "']");
    private SelenideElement buttonOpenAnalytics = $("button[title='" + OPEN_ANALYTICS + "']");
    private SelenideElement buttonEditTemplate = $("button[title='" + EDIT_TEMPLATE + "']");
    private SelenideElement buttonViewAsPublished = $("button[title='" + VIEW_AS_PUBLISHED + "']");
    private SelenideElement buttonViewInAdmin = $("button[title='" + VIEW_IN_ADMIN + "']");
    private SelenideElement buttonOpenInClassic = $("button[title='" + OPEN_IN_CLASSIC + "']");
    private SelenideElement buttonHelp = $("button[title='" + HELP + "']");

    private SelenideElement buttonInitialPageProps = $("button[title='" + INITIAL_PAGE_PROPERTIES + "']");
    private SelenideElement buttonPagePolicy = $("button[title='" + PAGE_POLICY + "']");
    private ActionComponent<PolicyDialog> pagePolicyButton = new ActionComponent("button[title='" + PAGE_POLICY + "']", () -> new PolicyDialog(), false);
    private SelenideElement buttonPublishTemplate = $("button[title='" + PUBLISH_TEMPLATE + "']");

    private Dialog unlockPageDialog = new Dialog("coral-dialog");
    private Dialog unpublishPageDialog = new Dialog("coral-dialog");

    public PageInfo() {
        super(PAGEINFO_IDENTIFIER);
    }

    /**
     * @return True if the PageInfo panel is displayed, so open and visible.
     */
    public boolean isOpen() {
        return element().isDisplayed();
    }

    /**
     * @return True if the PageInfo panel is not displayed, so not open and not visible.
     */
    public boolean isClosed() {
        return !isOpen();
    }

    /**
     * Opens the PageInfo panel if not already opened
     * @return Instance of current {@link PageInfo}
     */
    public PageInfo open() {
        if (isClosed()) {
            togglePageInfo();
            element().should(exist).shouldBe(visible);
        }
        return this;
    }

    /**
     * Close the PageInfo panel if not already closed
     * @return Instance of current {@link PageInfo}
     */
    public PageInfo close() {
        if (isOpen()) {
            togglePageInfo();
            element().should(exist).shouldNotBe(visible);
        }
        return this;
    }

    /**
     * @return The Open Page Properties button element.
     */
    public SelenideElement getOpenPagePropertiesButton() { return buttonPageProps; }

    /**
     * @return The Rollout Page button element.
     */
    public SelenideElement getRolloutPageButton() { return buttonRolloutPage; }

    /**
     * @return The Start Workflow button element.
     */
    public SelenideElement getStartWorkflowButton() { return buttonStartWorkflow; }

    /**
     * @return The Lock Page button element.
     */
    public SelenideElement getLockPageButton() { return buttonLockPage; }

    /**
     * @return The Unlock Page button element.
     */
    public SelenideElement getUnLockPageButton() { return buttonUnlockPage; }

    /**
     * @return The Publish Page button element.
     */
    public SelenideElement getPublishPageButton() { return buttonPublishPage; }

    /**
     * @return The Request Publication button element.
     */
    public SelenideElement getRequestPublicationButton() { return buttonRequestPublication; }

    /**
     * @return The Unpublish Page button element.
     */
    public SelenideElement getUnpublishPageButton() { return buttonUnpublishPage; }

    /**
     * @return The Request Unpublication button element.
     */
    public SelenideElement getRequestUnpublicationButton() { return buttonRequestUnpublication; }

    /**
     * @return The Promote Launch button element.
     */
    public SelenideElement getPromoteLaunchButton() { return buttonPromoteLaunch; }

    /**
     * @return The Open Analytics button element.
     */
    public SelenideElement getOpenAnalyticsButton() { return buttonOpenAnalytics; }

    /**
     * @return The Edit Template button element.
     */
    public SelenideElement getEditTemplateButton() { return buttonEditTemplate; }

    /**
     * @return The View as Published button element.
     */
    public SelenideElement getViewAsPublishedButton() { return buttonViewAsPublished; }

    /**
     * @return The View in Admin button element.
     */
    public SelenideElement getViewInAdminButton() { return buttonViewInAdmin; }

    /**
     * @return The Open in Classic button element.
     */
    public SelenideElement getOpenInClassicButton() { return buttonOpenInClassic; }

    /**
     * @return The Help button element.
     */
    public SelenideElement getHelpButton() { return buttonHelp; }

    /**
     * @return The Initial Page Properties button element (Template Editor).
     */
    public SelenideElement getInitialPagePropertiesButton() { return buttonInitialPageProps; }

    /**
     * @return The Page Policy button element (Template Editor).
     */
    public SelenideElement getPagePolicyButton() { return buttonPagePolicy; }

    /**
     * @return The Publish Template button element (Template Editor).
     */
    public SelenideElement getPublishTemplateButton() { return buttonPublishTemplate; }

    public PolicyDialog clickPagePolicy() { return clickDialogAction(pagePolicyButton); }
    public Dialog clickUnlockPageButton() { return clickDialogAction(getUnLockPageButton(), unlockPageDialog); }
    public Dialog clickUnpublishPageButton() { return clickDialogAction(getUnpublishPageButton(),unpublishPageDialog); }

    /**
     * Click on the button to toggle page info.
     */
    private void togglePageInfo() {
        waitCoralReady(BUTTON_SELECTOR);
        clickableClick(button, 2000);
    }

}
