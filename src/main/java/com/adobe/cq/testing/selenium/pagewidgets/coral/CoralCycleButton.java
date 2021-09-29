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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public final class CoralCycleButton extends BaseComponent {

    /**
     * @param selectorSuffix usual selector to find this cycle button.
     */
    public CoralCycleButton(final String selectorSuffix) {
        super("coral-cyclebutton" + selectorSuffix);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T  click() {
        clickableClick(element().$("button"));
        return (T) this;
    }

    /**
     * @return list of the related coral popover that is opened.
     */
    public CoralSelectList selectList() {
        CoralSelectList list = null;
        CoralPopOver popOver = CoralPopOver.firstOpened();
        popOver.waitVisible();
        waitForElementAnimationFinished(popOver.getCssSelector());
        return new CoralSelectList(popOver.element());
    }

}
