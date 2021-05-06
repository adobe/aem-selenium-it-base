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

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Component class representing the side panel in editor page.
 */
public final class SidePanel extends BaseSidePanel<SidePanel> {

    private static final String TAB_COMPONENTS_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='components']";
    private static final String TAB_ASSETS_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='assets']";
    private static final String TAB_CT_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='content tree']";
    private static final String TAB_COMPONENTINSPECTOR_IDENTIFIER = "#SidePanel .cq-DeveloperRail coral-tab[data-foundation-tracking-event*='components']";
    private static final String TAB_ERRORS_IDENTIFIER = "#SidePanel coral-tab[data-foundation-tracking-event*='errors']";

    private static final String PANEL_ERRORS_IDENTIFIER = "coral-panel .sidepanel-tab-errorinspector";

    private SelenideElement tabAssets = $(TAB_ASSETS_IDENTIFIER);
    private SelenideElement tabComponents = $(TAB_COMPONENTS_IDENTIFIER);
    private SelenideElement tabCT = $(TAB_CT_IDENTIFIER);
    private SelenideElement tabComponentInspector = $(TAB_COMPONENTINSPECTOR_IDENTIFIER);
    private SelenideElement tabErrors = $(TAB_ERRORS_IDENTIFIER);
    private SelenideElement panelErrors = $(PANEL_ERRORS_IDENTIFIER);

    public SidePanel() {
        super();
    }

    /**
     * @return True if the Errors Panel is displayed, so open and visible.
     */
    public boolean isErrorsTabDisplayed() { return panelErrors.isDisplayed(); }

    /**
     * @return The Errors Panel element.
     */
    public SelenideElement getErrorsPanel() { return panelErrors; }



    /**
     * Click the Errors Tab to display the Errors Panel element.
     * @return The Errors Panel element.
     */
    public SelenideElement selectErrorsTab() {
        show();
        tabErrors.click();
        panelErrors.should(exist).shouldBe(visible);
        return panelErrors;
    }

}
