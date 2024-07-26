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

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralRadio;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    /**
     * Select a livecopy
     * @param livecopyPath path of the livecopy
     */
    public void selectLivecopy(String livecopyPath) {
        if(isLiveCopySelected(livecopyPath)) {
            return;
        } else {
            SelenideElement livecopyRow =  getLivecopy(livecopyPath);
            livecopyRow.$$("td").get(0).find("coral-checkbox").click();
        }
    }

    /**
     * Deselect a livecopy
     * @param livecopyPath path of the livecopy
     */
    public void deselectLivecopy(String livecopyPath) {
        if(isLiveCopySelected(livecopyPath)) {
            SelenideElement livecopyRow =  getLivecopy(livecopyPath);
            livecopyRow.$$("td").get(0).find("coral-checkbox").click();
        } else {
            return;
        }
    }

    /**
     * Click the SelectAll livecopy button
     */
    public void clickSelectAllLivecopy() {
        getSelectAll().click();
    }

    public boolean isSelectAllChecked() {
        CoralCheckbox selectAll = getSelectAll();
        return selectAll.isChecked() && !selectAll.isIndeterminate();
    }


    /**
     * Close the rollout dialog
     */
    public void close() {
        clickableClick($("button[title='Cancel']"));
    }

    /**
     * Perform rollout operation now
     */
    public void rolloutNow() {
        clickableClick($("button[title='Rollout']"));
        // new dialog opens for scheduling
        CoralRadio scheduleOptions = new CoralRadio($("coral-dialog[id='aem-sites-schedule-dialog']"), "reportSchedule");
        //"Now"
        assertTrue(scheduleOptions.elementByValue("now") != null,"Schedule Now should be present");

        clickableClick(scheduleOptions.elementByValue("now"));
        clickableClick($("button[trackingelement='continue']"));
    }

    public void submitRollout() {
        clickableClick($("button[title='Rollout']"));
    }

    /**
     *
     */
    public boolean isContinueEnabled() {
        return $("button[trackingelement='continue']").isEnabled();
    }

    private SelenideElement getLivecopy(String livecopyPath) {
        return content().$$("[handle=table] tbody tr").filter(Condition.attribute("data-path", livecopyPath)).first();
    }

    private CoralCheckbox getSelectAll() {
        return new CoralCheckbox("[labelled='Select All']");
    }
}
