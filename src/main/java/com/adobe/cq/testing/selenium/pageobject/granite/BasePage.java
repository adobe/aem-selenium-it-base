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

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralToast;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.switchToAemContentFrame;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitNetworkIdled;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitDocumentLoadCompleted;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.codeborne.selenide.Selenide.$;

/**
 * Base page class for inheritance.
 */
public class BasePage {

    private static final int NETWORK_POLLING_INTERVAL = 250;
    private static final SelenideElement UI_MASK = $("div.foundation-ui-mask");

    private final URI baseURI;
    private String path;

    /**
     * Register the page's path.
     *
     * @param base     baseURI to work with.
     * @param pagePath absolute path.
     *                 URI without the first forward slash.
     */
    public BasePage(final URI base, final String pagePath) {
        baseURI = base;
        path = pagePath;
    }

    /**
     * @param pagePath relative path from current opened site.
     */
    public BasePage(final String pagePath) {
        baseURI = null;
        path = pagePath;
    }

    /**
     * @return path for this page.
     */
    public String getPath() {
        return path;
    }


    /**
     * @return the currently edited path, extracted from the .foundation-content-path element data attribute.
     */
    public String getEditedPath() {
        return $(".foundation-content-path").data("foundation-content-path");
    }


    /**
     * @return fully qualified url for the current path.
     */
    public String getUrl() {
        URIBuilder uriBuilder;
        if (baseURI == null) {
            final boolean graniteExists = Selenide.executeJavaScript("return typeof Granite !== 'undefined';");
            if (graniteExists) {
                final String externalized = Selenide.executeJavaScript("return Granite.HTTP.externalize(arguments[0]);", path);
                uriBuilder = getURIAbsolutePath(externalized);
            } else {
                uriBuilder = getURIAbsolutePath(path);
            }
        } else {
            uriBuilder = new URIBuilder(baseURI);
            String basePath = uriBuilder.getPath();
            if (basePath.endsWith("/")) {
                basePath = basePath.substring(0, basePath.length() - 1);
            }
            uriBuilder.setPath(basePath + path);
        }

        String url;
        try {
            url = uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Cannot get the url due to syntax", e);
        }
        return url;
    }

    /**
     * @param absolutePath the absolute path on the site.
     * @return fully qualified URI based on the current browsed site.
     */
    private URIBuilder getURIAbsolutePath(final String absolutePath) {
        URIBuilder uriBuilder;
        try {
            URIBuilder uriAbsolute = new URIBuilder(absolutePath);
            final List<NameValuePair> queryParams = uriAbsolute.getQueryParams();
            uriBuilder = new URIBuilder(WebDriverRunner.url());
            uriBuilder.clearParameters();
            uriBuilder.setFragment(uriAbsolute.getFragment());
            if (!queryParams.isEmpty()) {
                uriBuilder.setParameters(queryParams);
            }
            uriBuilder.setPath(uriAbsolute.getPath());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Cannot parse the url " + WebDriverRunner.url(), e);
        }
        return uriBuilder;
    }

    /**
     * Open page with the provided path in the current window.
     *
     * @param pagePath The path to open without trailing
     * @param <T>      concrete implementation type
     * @return this object
     */
    public <T extends BasePage> T open(@Nonnull final String pagePath) {
        path = pagePath;
        return open();
    }

    /**
     * Open this page in the browser.
     *
     * @param <T> concrete implementation type
     * @return this object.
     */

    public <T extends BasePage> T open() {
        Selenide.open(getUrl());
        waitReady();
        return (T) this;
    }

    /**
     * Open page with the provided path in a new window.
     *
     * @param pagePath The path to open without trailing /
     * @return this object.
     */
    public BasePage openInNewWindow(final String pagePath) {
        if (pagePath != null) {
            path = pagePath;
        }

        // todo check how to implement in a new window properly

        return this;
    }

    /**
     * Reload the current page in the browser.
     */
    public void refresh() {
        Selenide.refresh();
    }

    /**
     * Wait that the page is ready (according to coral).
     */
    public void waitReady() {
        // ensure frame on unified shell
        switchToAemContentFrame();
        waitNetworkIdled(NETWORK_POLLING_INTERVAL);
        waitDocumentLoadCompleted();
        waitCoralReady();
    }

    /**
     * Wait that the foundation UI mask exist.
     */
    public void waitMasked() {
        UI_MASK.should(Condition.exist);
    }

    /**
     * Wait that the foundation UI mask doesn't exist.
     */
    public void waitUnmasked() {
        UI_MASK.shouldNot(Condition.exist);
    }

    /**
     * Check if the URL of the page is the same (context path insensitive).
     * This is limited but we don't have a more programmatically
     * solution in place.
     *
     * @return true if sites is open.
     */
    public boolean isOpen() {
        boolean result;
        try {
            URIBuilder uriBuilder = new URIBuilder(WebDriverRunner.url());
            result = uriBuilder.getPath().contains(path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }


    /**
     * @param message a message that is expected to be displayed for a short time (toast).
     */
    public void waitForMessage(final String message) {
        CoralToast toast = new CoralToast();
        toast.waitVisible();
        toast.element().shouldHave(Condition.matchText(message));
        toast.waitVanish();
    }
}
