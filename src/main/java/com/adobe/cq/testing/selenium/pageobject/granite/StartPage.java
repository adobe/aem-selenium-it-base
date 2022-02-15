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

package com.adobe.cq.testing.selenium.pageobject.granite;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.executeIfPresent;
import static com.adobe.cq.testing.selenium.pagewidgets.granite.Collection.COLLECTION_ITEM_ID_ATTRIBUTE;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * The home page.
 */
public class StartPage extends ShellPage {

    private static final String START_PAGE_PATH = "/aem/start.html";

    // Tablist action items
    private static final SelenideElement NAVIGATION_BUTTON = $("coral-tab[icon='compass']");
    private static final SelenideElement TOOLS_BUTTON = $("coral-tab[icon='hammer']");

    /**
     * Construct a StartPage.
     */
    public StartPage() {
        super(null, START_PAGE_PATH);
    }

    public StartPage clickNavigationButton() {
        return clickButton(NAVIGATION_BUTTON);
    }

    public StartPage clickToolsButton() {
        return clickButton(TOOLS_BUTTON);
    }

    private StartPage clickButton(SelenideElement button) {
        clickableClick(button);
        return this;
    }

    public void clickToolItemCard(ToolItemCard toolItemCard) {
        clickableClick($(toolItemCard.getSelector()));
    }

    public enum ToolItemCard {
        CRXDE_LITE("/mnt/overlay/cq/core/content/nav/tools/general/crxdelite", ToolsGroup.GENERAL),
        SEARCH_FORMS("/mnt/overlay/cq/core/content/nav/tools/general/customsearchfacets", ToolsGroup.GENERAL),
        TAGGING("/mnt/overlay/cq/core/content/nav/tools/general/tagging", ToolsGroup.GENERAL),
        TEMPLATES("/mnt/overlay/cq/core/content/nav/tools/general/templates", ToolsGroup.GENERAL),
        COMPONENTS("/mnt/overlay/cq/core/content/nav/tools/general/components", ToolsGroup.GENERAL),
        CONFIGURATION_BROWSER("/mnt/overlay/cq/core/content/nav/tools/general/configuration-browser", ToolsGroup.GENERAL),
        TRANSLATION_CONFIGURATION("/mnt/overlay/cq/core/content/nav/tools/general/translationRules", ToolsGroup.GENERAL),
        JOBS("/mnt/overlay/cq/core/content/nav/tools/general/asyncjobs", ToolsGroup.GENERAL),

        BLUEPRINTS("/mnt/overlay/cq/core/content/nav/tools/sites/blueprints", ToolsGroup.SITES),
        LAUNCHES("/mnt/overlay/cq/core/content/nav/tools/sites/launches", ToolsGroup.SITES),
        CONTEXTHUB("/mnt/overlay/cq/core/content/nav/tools/sites/contexthub", ToolsGroup.SITES),
        EXTERNAL_LINK_CHECKER("/mnt/overlay/cq/core/content/nav/tools/sites/linkchecker", ToolsGroup.SITES);

        private final String collectionItemId;
        private final ToolsGroup group;

        ToolItemCard(final String collectionItemId, ToolsGroup group) {
            this.collectionItemId = collectionItemId;
            this.group = group;
        }

        public String getCollectionItemId() {
            return collectionItemId;
        }

        public ToolsGroup getGroup() {
            return group;
        }

        public String getSelector() {
            return "coral-masonry-item[" + COLLECTION_ITEM_ID_ATTRIBUTE + "='" + getCollectionItemId() + "']";
        }
    }

    public void closeNewsletter() {
        AEMBaseComponent newsletter = new AEMBaseComponent(".vex-close");
        executeIfPresent(newsletter.element(), 1000, 5, () -> newsletter.click());
    }

    public void clickNavigationGroup(ToolsGroup toolsGroup) {
        clickableClick($(toolsGroup.getSelector()));
    }

    public enum ToolsGroup {
        GENERAL("/mnt/overlay/cq/core/content/nav/tools/general"),
        WORKFLOW("/mnt/overlay/cq/core/content/nav/tools/workflow"),
        SITES("/mnt/overlay/cq/core/content/nav/tools/sites"),
        ASSETS("/mnt/overlay/cq/core/content/nav/tools/assets"),
        RESOURCES("/mnt/overlay/cq/core/content/nav/tools/resources"),
        DEPLOYMENT("/mnt/overlay/cq/core/content/nav/tools/deployment"),
        SECURITY("/mnt/overlay/cq/core/content/nav/tools/security"),
        CLOUD_SERVICES("/mnt/overlay/cq/core/content/nav/tools/cloudservices"),
        OPERATIONS("/mnt/overlay/cq/core/content/nav/tools/operations");

        private final String id;

        ToolsGroup(final String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getSelector() {
            return "coral-card[" + COLLECTION_ITEM_ID_ATTRIBUTE + "='" + getId() + "']";
        }
    }
}
