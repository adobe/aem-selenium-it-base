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

package com.adobe.cq.testing.selenium.pagewidgets.sidepanel;

import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object Model for the side panel toggle button.
 */
public final class ToggleSidePanelButton {

    private static final String BUTTON_SELECTOR = "#Content button.toggle-sidepanel";
    private SelenideElement button = $(BUTTON_SELECTOR);

    /**
     * Click on the button to toggle side panel.
     */
    public void toggleSidePanel() {
        waitCoralReady(BUTTON_SELECTOR);
        waitForElementAnimationFinished(button);
        clickableClick(button);
        waitForElementAnimationFinished(button);
    }

}
