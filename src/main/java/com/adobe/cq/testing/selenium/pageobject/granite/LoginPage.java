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

package com.adobe.cq.testing.selenium.pageobject.granite;

import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import java.net.URI;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * Login page object.
 */
public final class LoginPage extends BasePage {

    private static final String LOGIN_PAGE_PATH = "/libs/granite/core/content/login.html";

    private static final SelenideElement ACCORDION_ON_CLOUD = $("#coral-collection-id-0");

    private static final long CLOUD_LOGIN_PAUSE = 15000;
    public static final String LOGIN_TOKEN = "login-token";

    /**
     * Construct a LoginPage.
     * @param baseURL the starting path for the site.
     */
    public LoginPage(final URI baseURL) {
        super(baseURL, LOGIN_PAGE_PATH);
    }

    /**
     * Construct a LoginPage from current site.
     */
    public LoginPage() {
        super(LOGIN_PAGE_PATH);
    }

    /**
     * Find out if the user is logged in so we can
     * avoid performing logging in again.
     *
     * @return true if login token is present.
     */
    public static boolean isLoggedIn() {
        return WebDriverRunner.getWebDriver().manage().getCookieNamed(LOGIN_TOKEN) != null;
    }

    /**
     * @return true if this is a cloud login screen.
     */
    public boolean isCloudLogin() {
        return ACCORDION_ON_CLOUD.exists();
    }

    /**
     * Get the username element.
     *
     * @return the username element.
     */
    public SelenideElement usernameField() {
        return $("#username");
    }

    /**
     * Get the password element.
     *
     * @return the password element.
     */
    public SelenideElement passwordField() {
        return $("#password");
    }

    /**
     * Get the submit element.
     *
     * @return submit button element.
     */
    public SelenideElement submitButton() {
        return $("#submit-button");
    }

    /**
     * Click on submit button.
     */
    public void submit() {
        clickableClick(submitButton());
    }

    /**
     * Login with a custom username and password.
     *
     * @param username Custom valid AEM username.
     * @param password Custom valid AEM password.
     * @return this object.
     */
    public StartPage loginAs(final String username, final String password) {
        final StartPage startPage = new StartPage();
        if (!LoginPage.isLoggedIn()) {
            open();
            if (isCloudLogin()) {
                clickableClick(ACCORDION_ON_CLOUD);
            }
            usernameField().setValue(username);
            passwordField().setValue(password);
            ExpectNav.on(this::submit);
            startPage.waitReady();
        } else {
            startPage.open();
        }
        return startPage;
    }
}
