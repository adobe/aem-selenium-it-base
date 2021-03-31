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

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralActionBar;
import com.adobe.cq.testing.selenium.pagewidgets.cq.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.TimeoutException;

import static com.adobe.cq.testing.selenium.Constants.DEFAULT_CLICK_UNTIL_RETRIES;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.clickBaseComponentAction;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Base editor page class for inheritance.
 */
public abstract class EditorPage extends BasePage {

    private static final Logger LOG = LoggerFactory.getLogger(EditorPage.class);

    private static final String EDITOR_PATH = "/editor.html";
    private static final String OVERLAY_WRAPPER = "#OverlayWrapper";
    private static final String CONTENT_WRAPPER = "#ContentWrapper";
    private static final String CONTENT_FRAME = "#ContentFrame";
    private static final String EDITABLE = "[data-type='Editable']";
    private static final String COMPONENT_OVERLAY = EDITABLE + "[data-path='%s']";
    private static final String INSPECTABLE = "[data-type='Inspectable']";
    private static final String INSPECTABLE_COMPONENT_OVERLAY = INSPECTABLE + "[data-path='%s']";

    // JS script to be executed on browser to make sure all Coral components below coral-shell are initialized
    private static final String JS_EDITOR_READY_CONDITION = "window && window.Granite && window.Granite.author && true";
    private static final String JS_EDITOR_READY_SCRIPT = "if (%s) {%n"
            + "var ns = window.Granite.author;%n"
            + "return ns.pageInfo && ns.pageInfo !== null;%n"
            + "}%n" + "return false";

    private PageInfo pageInfo = new PageInfo();

    private static String getEditedPagePath(final String pagePath) {
        return EDITOR_PATH + pagePath + ".html";
    }

    private EditableToolbar editableToolbar = new EditableToolbar(this);

    private SelenideElement previewButton = $("button.editor-GlobalBar-item[data-layer='Preview']");

    protected SelenideElement selectModeButton = $("a.editor-GlobalBar-layerSwitcher");

    private SelenideElement undoButton = $("button.editor-GlobalBar-item[data-history-control='undo']");
    private SelenideElement redoButton = $("button.editor-GlobalBar-item[data-history-control='redo']");
    private ActionComponent<StylesSelector> stylesButton = new ActionComponent($("button.js-editor-StyleSelector-toggle"), () -> new StylesSelector(StylesSelector.Variant.PAGE), false);


    private ElementsCollection textComponents = $$("div.cq-Overlay[data-text='Text']");

    private CoralActionBar actionBar = new CoralActionBar();

    private String pageName;

    public EditorPage(final URI base, final String pagePath) {
        super(base, getEditedPagePath(pagePath));
    }
    public EditorPage(final String pagePath) {
        super(null, getEditedPagePath(pagePath));
    }

    public String getPageName() {
        return pageName;
    }

    public PageInfo getPageInfo() { return pageInfo; }

    public SelenideElement getOverlayWrapper() { return $(OVERLAY_WRAPPER); }
    public SelenideElement getContentWrapper() { return $(CONTENT_WRAPPER); }
    public SelenideElement getContentFrame() { return $(CONTENT_FRAME); }

    public SelenideElement getUndoButton() { return undoButton; }
    public SelenideElement getRedoButton() { return redoButton; }
    public SelenideElement getStylesButton() { return stylesButton.element(); }
    public StylesSelector clickStyles() { return clickBaseComponentAction(stylesButton); }
    public <T extends EditorPage> T closeStyles(StylesSelector stylesSelector) {
        if (stylesSelector != null && stylesSelector.element().is(Condition.visible)) {
            clickableClick(stylesButton.element());
            stylesSelector.element().shouldNotBe(Condition.visible);
        }
        return (T) this;
    }

    public ElementsCollection getTextComponents() {
        return textComponents;
    }

    protected <T extends EditorPage> T enterMode(final SelenideElement targetingModeButton, final SelenideElement targetingLayerButton) throws TimeoutException {
        selectModeButton.shouldBe(Condition.visible);
        clickUntil(selectModeButton, targetingModeButton, Condition.visible, DEFAULT_CLICK_UNTIL_RETRIES, 1);
        clickUntil(targetingModeButton, targetingLayerButton, Condition.visible, DEFAULT_CLICK_UNTIL_RETRIES, 1);
        return (T) this;
    }

    public <T extends EditorPage> T enterPreviewMode() {
        clickableClick(previewButton);
        $(OVERLAY_WRAPPER).shouldNotBe(Condition.visible);
        return (T) this;
    }

    public boolean isInPreviewMode() {
        return hasWithPolling(previewButton, Condition.visible) &&
                hasWithPolling(previewButton, Condition.cssClass("is-selected")) &&
                hasWithPolling(getOverlayWrapper(), Condition.hidden) &&
                hasWithPolling(getOverlayWrapper(), Condition.cssClass("is-hidden"));
    }

    public EditableToolbar openEditableToolbar(final String resourcePath) throws TimeoutException {
        SelenideElement targetActionBar = getComponentOverlay(resourcePath);
        clickUntil(targetActionBar, editableToolbar.element(), Condition.visible, DEFAULT_CLICK_UNTIL_RETRIES, 1);
        return editableToolbar;
    }

    public SelenideElement getComponentOverlay(final String resourcePath) {
        return $(getComponentOverlaySelector(resourcePath)).should(Condition.exist);
    }

    private String getComponentOverlaySelector(final String resourcePath) {
        return String.format(COMPONENT_OVERLAY, resourcePath);
    }

    public SelenideElement getInspectableComponentOverlay(final String resourcePath) {
        return $(String.format(INSPECTABLE_COMPONENT_OVERLAY, resourcePath)).should(Condition.exist);
    }

    public boolean isComponentOverlaySelected(SelenideElement componentOverlay) {
        return componentOverlay.has(Condition.cssClass("is-selected"));
    }

    public ElementsCollection getEditables() {
        return $$(EDITABLE);
    }

    public ElementsCollection getInspectables() {
        return $$(INSPECTABLE);
    }

    public CoralActionBar actionBar() {
        return actionBar;
    }

    private boolean isReadyCondition() {
        return Boolean.TRUE.equals(Selenide.executeJavaScript("return " + JS_EDITOR_READY_CONDITION));
    }

    private boolean isPageReady() {
        return Boolean.TRUE.equals(Selenide.executeJavaScript(String.format(JS_EDITOR_READY_SCRIPT, JS_EDITOR_READY_CONDITION)));
    }

    @Override
    public void waitReady() {
        LOG.info("waitPageReady");
        Wait().until(webdriver -> isReadyCondition());
        LOG.info("precondition checked");
        Wait().until(webdriver -> isPageReady());
        LOG.info("pageReady checked");
    }
}
