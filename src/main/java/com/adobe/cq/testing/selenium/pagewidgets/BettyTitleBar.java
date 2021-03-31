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

package com.adobe.cq.testing.selenium.pagewidgets;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

/**
 * Page Object Model for BettyTitleBar widget.
 */
public final class BettyTitleBar {

    private SelenideElement bettyTitleBarElement = $("betty-titlebar");

    /**
     * @return the main element of BettyTitleBar
     */
    public SelenideElement element() {
        return bettyTitleBarElement;
    }

    /**
     * @return the title part element.
     */
    public SelenideElement title() {
        return bettyTitleBarElement.$("betty-titlebar-title");
    }

    /**
     * @return the primary part element.
     */
    public SelenideElement primary() {
        return bettyTitleBarElement.$("betty-titlebar-primary");
    }

    /**
     * @return the secondary part element.
     */
    public SelenideElement secondary() {
        return bettyTitleBarElement.$("betty-titlebar-secondary");
    }
}
