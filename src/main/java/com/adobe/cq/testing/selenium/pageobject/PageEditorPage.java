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


package com.adobe.cq.testing.selenium.pageobject;

import com.adobe.cq.testing.selenium.pagewidgets.cq.TimewarpDialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeoutException;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.hasWithPolling;
import static com.codeborne.selenide.Selenide.$;

/**
 * Editor page class representing Page Editor.
 */
public final class PageEditorPage extends EditorPage {

    private static final Logger LOG = LoggerFactory.getLogger(PageEditorPage.class);

    private SelenideElement editModeButton = $("button[is='coral-buttonlist-item'][data-layer='Edit']");
    private SelenideElement editLayerButton = $("button.editor-GlobalBar-item[data-layer='Edit']");

    private SelenideElement layoutModeButton = $("button[is='coral-buttonlist-item'][data-layer='Layouting']");
    private SelenideElement layoutLayerButton = $("button.editor-GlobalBar-item[data-layer='Layouting']");

    private SelenideElement developerModeButton = $("button[is='coral-buttonlist-item'][data-layer='Developer']");
    private SelenideElement developerLayerButton = $("button.editor-GlobalBar-item[data-layer='Developer']");

    private SelenideElement timewarpModeButton = $("button[is='coral-buttonlist-item'][data-layer='Timewarp']");
    private SelenideElement timewarpLayerButton = $("button.editor-GlobalBar-item[data-layer='Timewarp']");

    private SelenideElement annotateButton = $("button.editor-GlobalBar-item[data-layer='Annotate']");
    private SelenideElement annotateCloseButton = $("button.editor-GlobalBar-badge");
    private SelenideElement annotationAddButton = $("button.editor-GlobalBar-item[data-action='ENTER_ANNOTATE_MODE_ADD']");

    private SelenideElement lockButton = $("button#unlock-page-trigger");

    private SelenideElement resourceStatusBar = $(".editor-StatusBar-status");

    private TimewarpDialog timewarpDialog;

    public PageEditorPage(URI base, String pagePath) {
        super(base, pagePath);
    }

    public PageEditorPage(String pagePath) {
        super(pagePath);
    }

    public SelenideElement getLockButton() { return lockButton; }

    public SelenideElement getResourceStatusBar() {
        return resourceStatusBar;
    }

    /**
     * Switch to the Edit Mode of Page Editor.
     * @return self object
     * @throws TimeoutException if something wrong occurred
     */
    public PageEditorPage enterEditMode() throws TimeoutException {
        return enterMode(editModeButton, editLayerButton);
    }

    /**
     * Switch to the Layout Mode of Page Editor.
     * @return self object
     * @throws TimeoutException if something wrong occurred
     */
    public PageEditorPage enterLayoutMode() throws TimeoutException {
        return enterMode(layoutModeButton, layoutLayerButton);
    }

    /**
     * Switch to the Developer Mode of Page Editor.
     * @return self object
     * @throws TimeoutException if something wrong occurred
     */
    public PageEditorPage enterDeveloperMode() throws TimeoutException {
        return enterMode(developerModeButton, developerLayerButton);
    }

    /**
     * Switch to the Timewarp Mode of Page Editor.
     * @return self object
     * @throws TimeoutException if something wrong occurred
     */
    public PageEditorPage enterTimewarpMode() throws TimeoutException {
        return enterMode(timewarpModeButton, timewarpLayerButton);
    }

    public PageEditorPage enterAnnotateMode() {
        if (annotateButton.isDisplayed()) {
            clickableClick(annotateButton);
            selectModeButton.shouldNotBe(Condition.visible);
        }
        return this;
    }

    public PageEditorPage leaveAnnotateMode() {
        if (annotateCloseButton.isDisplayed()) {
            clickableClick(annotateCloseButton);
            selectModeButton.shouldBe(Condition.visible);
            annotateButton.shouldBe(Condition.visible);
        }
        return this;
    }

    public TimewarpDialog getTimewarpDialog() throws TimeoutException {
        if (!isInTimewarpMode()) {
            enterTimewarpMode();
        }
        return (timewarpDialog == null) ? timewarpDialog = new TimewarpDialog() : timewarpDialog;
    }

    public boolean isInEditMode() {
        return hasWithPolling(editLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.visible) &&
                !getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

    public boolean isInLayoutMode() {
        return hasWithPolling(layoutLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.visible) &&
                !getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

    public boolean isInDeveloperMode() {
        return hasWithPolling(developerLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.visible) &&
                !getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

    public boolean isInTimewarpMode() {
        return hasWithPolling(timewarpLayerButton, Condition.visible) &&
                hasWithPolling(getOverlayWrapper(), Condition.hidden) &&
                getOverlayWrapper().is(Condition.cssClass("is-hidden"));
    }

}
