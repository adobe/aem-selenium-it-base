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

package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pageobject.granite.CollectionPage;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralActionBar;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralAnchorList;
import com.adobe.cq.testing.selenium.pagewidgets.cq.actions.CreateActions;
import com.adobe.cq.testing.selenium.pagewidgets.cq.sites.CreatePageWizard;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.SelenideElement;


import javax.annotation.Nonnull;

import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver.firstOpened;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * The sites main page object.
 */
public class SitesPage extends CollectionPage {

    private static final String BASE_PATH = "/sites.html";

    private final CreateActions createActions;

    /**
     * Default constructor.
     */
    public SitesPage() {
        super(BASE_PATH);
        createActions = new CreateActions();
    }

    /**
     * Open custom assets page.
     * @param path - optional additional path to the default page.
     * @return SitesPage.
     */
    @Override
    public SitesPage open(@Nonnull final String path) {
        super.open(String.format("%s%s", BASE_PATH, path));
        return this;
    }

    /**
     * @return SitesPage opened on default location.
     */
    @SuppressWarnings("unchecked")
    @Override
    public SitesPage open() {
        super.open();
        return this;
    }

    /**
     * @return a SitesCreateAction which can be used to trigger create actions from right side.
     */
    public CreateActions createAction() {
        return createActions;
    }

    /**
     * @return associated combo action for this sites page.
     */
    public SitesToolbarActions actions() {
        return new SitesToolbarActions();
    }

    /**
     * Common actions for Sites.
     */
    public static final class SitesPageSmartActions {

        private final SitesPage sitesPage;

        private SitesPageSmartActions(final SitesPage context) {
            sitesPage = context;
        }

        /**
         * @param type action type to use
         * @return the action element.
         */
        private SelenideElement getCreateAction(final CreateActions.SitesCreateActionType type) {
            // Click on create action
            final CreateActions action = sitesPage.createAction();
            action.click();

            // Click on Folder
            return action.getAction(type);
        }


        /**
         * Combo action to open the create site wizard from current context.
         * @return the create site wizard.
         */
        public CreateSiteWizard openCreateSiteWizard() {
            // Click on create action
            final SelenideElement createAction = getCreateAction(CreateActions.SitesCreateActionType.SITE);
            ExpectNav.on(() -> clickableClick(createAction));

            CreateSiteWizard wizard = new CreateSiteWizard();
            wizard.waitReady();
            return wizard;
        }

        /**
         * Combo action to open the create site wizard from current context.
         * @return the create site wizard.
         */
        public CreatePageWizard openCreatePageWizard() {
            final SelenideElement createAction = getCreateAction(CreateActions.SitesCreateActionType.PAGE);
            ExpectNav.on(() -> clickableClick(createAction));

            CreatePageWizard wizard = new CreatePageWizard();
            wizard.waitReady();
            return wizard;
        }

        /**
         * Combo action to open the create workflow wizard from current context.
         * @return the create workflow wizard.
         */
        public CreateWorkflowWizard openCreateWorkflowWizard() {
            ExpectNav.on(() -> openActionBarCreate().selectByLabel("Workflow"));
            CreateWorkflowWizard wizard = new CreateWorkflowWizard();
            wizard.waitReady();
            return wizard;
        }



        /**
         * Combo action to open the create create live copy wizard from current context.
         * @return the create live copy wizard.
         */
        public CreateLiveCopyWizard openCreateLiveCopyWizard() {
            ExpectNav.on(() -> openActionBarCreate().selectByLabel("Live Copy"));
            CreateLiveCopyWizard wizard = new CreateLiveCopyWizard();
            wizard.waitReady();
            return wizard;
        }

        /**
         * Open the action bar create action.
         * @return the list of possible action in the actionbar create.
         */
        public CoralAnchorList openActionBarCreate() {
            final CoralActionBar actionBar = sitesPage.actionBar();
            actionBar.waitVisible();
            clickableClick(sitesPage.actions().getCreate());
            return new CoralAnchorList(firstOpened().element());
        }

        /**
         * Open the action bar restore action.
         * @return the list of possible action in the actionbar restore.
         */
        public CoralAnchorList openActionBarRestore() {
            final CoralActionBar actionBar = sitesPage.actionBar();
            actionBar.waitVisible();
            clickableClick(sitesPage.actions().getRestore());
            return new CoralAnchorList(firstOpened().element());
        }

        /**
         * Combo action to open the restore version wizard from current context.
         * @return the restore version wizard.
         */
        public RestoreVersionWizard openRestoreVersionWizard() {
            ExpectNav.on(() -> openActionBarRestore().selectByLabel("Restore Version"));
            RestoreVersionWizard wizard = new RestoreVersionWizard();
            wizard.waitReady();
            return wizard;
        }

        /**
         * Combo action to open the restore version wizard from current context.
         * @return the restore version wizard.
         */
        public RestoreTreeWizard openRestoreTreeWizard() {
            ExpectNav.on(() -> openActionBarRestore().selectByLabel("Restore Tree"));
            RestoreTreeWizard wizard = new RestoreTreeWizard();
            wizard.waitReady();
            return wizard;
        }

    }

    // All known actions
    public static final class SitesToolbarActions {

        private SitesToolbarActions() {
        }

        // Sites action bar items
        private static SelenideElement deselect = $("button.granite-collection-deselect");
        private static SelenideElement create = $("button.cq-siteadmin-admin-actions-create-activator");
        private static SelenideElement edit = $("button.cq-siteadmin-admin-actions-edit-activator");
        private static SelenideElement properties = $("button.cq-siteadmin-admin-actions-properties-activator");
        private static SelenideElement folderProperties = $("button.cq-siteadmin-admin-actions-folderproperties-activator");
        private static SelenideElement lock = $("button.cq-siteadmin-admin-actions-lockpage-activator");
        private static SelenideElement unlock = $("button.cq-siteadmin-admin-actions-unlockpage-activator");
        private static SelenideElement copy = $("button.cq-siteadmin-admin-actions-copy-activator");
        private static SelenideElement move = $("button.cq-siteadmin-admin-actions-move-activator");
        private static SelenideElement more = $("button[coral-actionbar-more]");
        private static SelenideElement quickpublish = $("button.cq-siteadmin-admin-actions-quickpublish-activator");
        private static SelenideElement publish = $("button.cq-siteadmin-admin-actions-publish-activator");
        private static SelenideElement publishLater = $("button.cq-siteadmin-admin-actions-publishlater-activator");
        private static SelenideElement unpublish = $("button.cq-siteadmin-admin-actions-unpublish-activator");
        private static SelenideElement delete = $("button.cq-siteadmin-admin-actions-delete-activator");
        private static SelenideElement selectAll = $("button.foundation-collection-selectall");
        private static SelenideElement restore = $("button.cq-siteadmin-admin-actions-restore-activator");
        private static SelenideElement restoreVersion = $("a.cq-siteadmin-admin-restoreversion");


        public SelenideElement getDeselect() {
            return deselect;
        }

        public SelenideElement getCreate() {
            return create;
        }

        public SelenideElement getEdit() {
            return edit;
        }

        public SelenideElement getProperties() {
            return properties;
        }

        public SelenideElement getFolderProperties() {
            return folderProperties;
        }

        public SelenideElement getLock() {
            return lock;
        }

        public SelenideElement getUnlock() {
            return unlock;
        }

        public static SelenideElement getCopy() {
            return copy;
        }

        public SelenideElement getMove() {
            return move;
        }

        public SelenideElement getMore() {
            return more;
        }

        public SelenideElement getQuickPublish() {
            return quickpublish;
        }

        public SelenideElement getPublish() {
            return publish;
        }

        public SelenideElement getPublishLater() {
            return publishLater;
        }

        public SelenideElement getUnpublish() {
            return unpublish;
        }

        public SelenideElement getDelete() {
            return delete;
        }

        public SelenideElement getSelectAll() {
            return selectAll;
        }

        public SelenideElement getRestore() {
            return restore;
        }
    }

}
