/*
 * ************************************************************************
 *  ADOBE CONFIDENTIAL
 *  ___________________
 *
 *  Copyright 2020 Adobe
 *  All Rights Reserved.
 *
 *  NOTICE: All information contained herein is, and remains
 *  the property of Adobe and its suppliers, if any. The intellectual
 *  and technical concepts contained herein are proprietary to Adobe
 *  and its suppliers and are protected by all applicable intellectual
 *  property laws, including trade secret and copyright laws.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Adobe.
 * *************************************************************************/

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
