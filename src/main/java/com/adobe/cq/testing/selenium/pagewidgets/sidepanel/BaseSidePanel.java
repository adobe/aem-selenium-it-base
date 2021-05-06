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

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;

/**
 * Component class representing the side panel in editor page.
 */
public class BaseSidePanel<V extends BaseSidePanel> extends BaseComponent {

    private static final String SIDE_PANEL_OPENED_CLASS = "sidepanel-opened";
    private static final String SIDE_PANEL_CLOSED_CLASS = "sidepanel-closed";
    private static final String SIDE_PANEL_IDENTIFIER = "#SidePanel";


    public BaseSidePanel() {
        super(SIDE_PANEL_IDENTIFIER);
    }

    /**
     * @return True if the SidePanel is open (displayed).
     */
    public boolean isShown() {
        return element().has(Condition.cssClass(SIDE_PANEL_OPENED_CLASS));
    }

    /**
     * @return True if the SidePanel is closed (not displayed).
     */
    public boolean isHidden() {
        return element().has(Condition.cssClass(SIDE_PANEL_CLOSED_CLASS));
    }

    /**
     * Opens the SidePanel if not already opened
     * @return self object
     */
    public V show() {
        if (isHidden()) {
            new ToggleSidePanelButton().toggleSidePanel();
            element().shouldHave(Condition.cssClass(SIDE_PANEL_OPENED_CLASS));
        }
        return (V) this;
    }

    /**
     * Close the SidePanel if not already closed
     * @return self object
     */
    public V hide() {
        if (isShown()) {
            new ToggleSidePanelButton().toggleSidePanel();
            element().shouldHave(Condition.cssClass(SIDE_PANEL_CLOSED_CLASS));
        }
        return (V) this;
    }

}
