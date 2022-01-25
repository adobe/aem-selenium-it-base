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

package com.adobe.cq.testing.selenium.pagewidgets.cq.tabs;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.cq.RolloutDialog;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class BlueprintTab extends BaseComponent {

    private static final SelenideElement ROLLOUT = $("coral-actionbar-item .cq-siteadmin-admin-properties-actions-blueprint[href*='rollout']");
    private static final SelenideElement LIVECOPY_OVERVIEW = $("coral-actionbar-item .cq-siteadmin-admin-properties-actions-blueprint[href*='livecopies']");

    /**
     * Construct a wrapper on BlueprintTab panel content.
     * @param panelId the associated element id.
     */

    public BlueprintTab(final String panelId) {
        super("#" + panelId);
    }

    /**
     * Click on rollout button
     * @return the rollout dialog
     */
    public RolloutDialog rollout() {
        clickableClick(ROLLOUT);
        return new RolloutDialog();
    }

    /**
     * Returns check if Rollout button is visible
     * @return true if Rollout button is visible otherwise false
     */
    public boolean isRolloutButtonVisible() {
        return ROLLOUT.isDisplayed();
    }

    /**
     * Returns true if livecopy Overview button is visible
     * @return true if livecopy Overview button is visible otherwise false
     */
    public boolean isLivecopyOverviewButtonVisible() {
        return LIVECOPY_OVERVIEW.isDisplayed();
    }

}
