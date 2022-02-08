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
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;

public class SocialMediaTab extends AEMBaseComponent {

    /**
     * Construct a wrapper on SocialMediaTab panel content.
     * @param panelId the associated element id.
     */
    public SocialMediaTab(final String panelId) {
        super("#" + panelId);
    }

    /**
     *
     * @param platform platform name like "facebook"
     * @return the checkbox social media platforms
     */
    public CoralCheckbox socialMediaSharing(String platform) {
        return new CoralCheckbox("[value='" + platform +"']");
    }

}
