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


// Generic AEM page (header and footer)
package com.adobe.cq.testing.selenium.pageobject.granite;

import com.adobe.cq.testing.selenium.pagewidgets.granite.Onboarding;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import javax.annotation.Nonnull;
import java.net.URI;

import static com.codeborne.selenide.Selenide.$;

public class ShellPage extends BasePage {

    /**
     * Register the page's path.
     *
     * @param baseURI {@link BasePage} constructor.
     * @param path {@link BasePage} constructor.
     */
    public ShellPage(final URI baseURI, final String path) {
        super(baseURI, path);
    }

    /**
     * Open a shell page and dismiss the onboarding popover.
     *
     * @param path Custom path
     * @return this object
     */
    @Override
    public ShellPage open(@Nonnull final String path) {
        super.open(path);
        waitForContent();
        closeOnboarding();
        return this;
    }

    /**
     * Acts like open but also dismisses the on-boarding popover.
     */
    @Override
    public void refresh() {
        super.refresh();
        waitReady();
        waitForContent();
        closeOnboarding();
    }

    /**
     * Close the on-boarding popover component.
     * This is specific to ShellPages.
     */
    public void closeOnboarding() {
        Onboarding sitesOnboarding = new Onboarding();
        if (sitesOnboarding.isOpen()) {
            sitesOnboarding.close();
        }
    }



    /**
     * Needed when a page refresh is triggered by a
     * Component's action.
     */
    public void waitForContent() {
        getMainContent().shouldBe(Condition.visible);
    }


    /**
     * @return the main content element for the shell.
     */
    public SelenideElement getMainContent() {
        return $("coral-shell-content");
    }
}
