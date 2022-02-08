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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;

import static com.codeborne.selenide.Selenide.$;

public class CloudServicesTab extends AEMBaseComponent {

    private String addConfiguration = "placeholder='Add Configuration'";

    /**
     * Construct a wrapper on CloudServices panel content.
     * @param panelId the associated element id.
     */
    public CloudServicesTab(final String panelId) {
        super("#" + panelId);
    }

    /**
     * Add the cloud configuration
     * @param value configuration path
     */
    public void addCloudConfiguration(String value) {
        $( "["+addConfiguration + "] > button").click();
        CoralSelectList coralSelectList = new CoralSelectList($("["+addConfiguration + "]"));
        if(!coralSelectList.isVisible()) {
            CoralSelect selectList = new CoralSelect(addConfiguration);
            coralSelectList = selectList.openSelectList();
        }
        coralSelectList.selectByValue(value);
    }

    /**
     * Delete the added Cloud configuration
     */
    public void deleteCloudConfiguration() {
        $("button[data-title='Cloud Proxy Configuration']").click();
    }

}
