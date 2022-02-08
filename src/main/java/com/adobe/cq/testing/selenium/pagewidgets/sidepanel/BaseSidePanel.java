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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.Condition;

/**
 * Component class representing the side panel in editor page.
 */
public class BaseSidePanel<V extends BaseSidePanel> extends AEMBaseComponent {

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
