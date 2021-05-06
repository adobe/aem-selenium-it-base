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
