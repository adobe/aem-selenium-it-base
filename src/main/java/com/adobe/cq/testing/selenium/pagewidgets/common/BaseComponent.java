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

package com.adobe.cq.testing.selenium.pagewidgets.common;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady;
import com.adobe.cq.testing.selenium.Constants;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * Base component class for inheritance.
 */
public class BaseComponent {

    private static final Logger LOG = LoggerFactory.getLogger(BaseComponent.class);

    private final String cssSelector;
    private static final SelenideElement UI_MASK = $("div.foundation-ui-mask");

    private SelenideElement currentElement;

    /**
     * @param selector The full CSS selector that leads to the HTML element.
     */
    public BaseComponent(final String selector) {
        cssSelector = selector;
        currentElement = $(cssSelector);
    }

    /**
     * @param element selenide element to associate.
     */
    public BaseComponent(final SelenideElement element) {
        cssSelector = element.getSearchCriteria();
        currentElement = element;
    }

    /**
     * @return true if it exists.
     */
    public boolean isExisting() {
        return element().exists();
    }

    /**
     * @return true if visible and in the viewport.
     */
    public boolean isVisibleWithinViewport() {
        return element().isDisplayed();
    }

    /**
     * Check if the element is visible in browser.
     *
     * @return true if element is currently visible.
     */
    public boolean isVisible() {
        return element().isDisplayed();
    }

    /**
     * Wait on the element for the animation to finish and Condition.visible to be true with default timeout.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T waitVisible() {
        Helpers.waitForElementAnimationFinished(cssSelector);
        element().shouldBe(Condition.visible);
        return (T) this;
    }

    /**
     * Wait on the element for the Condition.visible to be false with default timeout.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T waitVanish() {
        element().shouldNotBe(Condition.visible);
        return (T) this;
    }

    /**
     * Wait on the element for the Condition.enabled to be true with default timeout.
     */
    @SuppressWarnings({"unchecked"})
    public  <T extends BaseComponent> T waitEnabled() {
        element().shouldBe(Condition.enabled);
        return (T) this;
    }

    /**
     * Wait on the element for the Condition.disabled to be true with default timeout.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T waitDisabled() {
        element().shouldBe(Condition.disabled);
        return (T) this;
    }

    /**
     * Wait that the foundation UI mask exist.
     */
    public void waitPageMasked() {
        UI_MASK.should(Condition.exist);
    }

    /**
     * Wait that the foundation UI mask doesn't exist.
     */
    public void waitPageUnmasked() {
        UI_MASK.shouldNot(Condition.exist);
    }

    /**
     * Wait for the element to finish rendering.
     * This method might get extended with custom rules for
     * complex Components.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T render() {
        element().shouldBe(Constants.EXISTS_ENABLED_VISIBLE);
        Helpers.waitForElementAnimationFinished(cssSelector);
        return (T) this;
    }

    /**
     * Simply click on that object.
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T click() {
        clickableClick(element());
        return (T) this;
    }

    /**
     * @return the original cssSelector.
     */
    public String getCssSelector() {
        return cssSelector;
    }


    /**
     * shortcut on the element().scrollTo().
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T scrollTo() {
        element().scrollTo();
        return (T) this;
    }

    /**
     * @return currentElement.
     */
    public SelenideElement element() {
        return currentElement;
    }

    /**
     * wait ready (coral wize).
     */
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T waitReady() {
        CoralReady.waitCoralReady(cssSelector);
        return (T) this;
    }

    public <T extends BaseComponent> T adaptTo(final Class<T> adapterClass) {
        T output = null;
        try {
            output = adapterClass.getConstructor(SelenideElement.class).newInstance(element());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOG.error("Cannot instantiate {} due to error", adapterClass, e);
        }
        return output;
    }

}
