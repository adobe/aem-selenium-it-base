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
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public class BlueprintTab extends BaseComponent {

    private static final SelenideElement ROLLOUT = $("coral-actionbar-item a[trackingelement='rollout']");

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

    public class RolloutDialog extends Dialog {

        /**
         * Construct the associated Rollout Dialog.
         */
        public RolloutDialog() {
            super("coral-dialog.msm-rollout-dialog");
        }

        /**
         *
         * @return number of livecopies in rollout dialog
         */
        public int numberOfLiveCopies() {
            return content().$$("[handle=table] tbody tr").size();
        }

        /**
         *
         * @param livecopyPath path of the livecopy
         * @return true if the livecopy is selected for rollout
         */
        public boolean isLiveCopySelected(String livecopyPath) {
            SelenideElement livecopyRow =  content().$$("[handle=table] tbody tr").filter(Condition.attribute("data-path", livecopyPath)).first();
            if(!livecopyRow.isDisplayed())
                return false;
            return livecopyRow.$$("td").get(0).find("coral-checkbox").has(Condition.attribute("checked", "true"));
        }

        public void close() {
            this.clickDefault();
        }

        public void rolloutNow() {
            this.clickPrimary();
            // new dialog opens for scheduling
            new Dialog().clickPrimary();
        }

    }
}
