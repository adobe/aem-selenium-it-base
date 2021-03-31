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

package com.adobe.cq.testing.selenium.junit.extensions;

import com.adobe.cq.testing.selenium.junit.extensions.SlingClientExtension;
import com.adobe.cq.testing.selenium.utils.Coverage;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.testing.clients.SlingClient;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static com.adobe.cq.testing.selenium.Constants.RUNMODE_AUTHOR;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public final class JSCoverageExtension implements AfterTestExecutionCallback {

    private static final Logger LOG = LoggerFactory.getLogger(JSCoverageExtension.class);

    public void afterTestExecution(final ExtensionContext ec) {

        final SlingClient client = SlingClientExtension.Store.getInstance().getOrCompute(ec, RUNMODE_AUTHOR, null, null, false);
        final Coverage coverage = new Coverage(client);

        if (client != null && coverage.isConfigPresent() && coverage.isJSCoverAvailable()) {
            // store coverage data
            final WebDriver webDriver = WebDriverRunner.getWebDriver();
            final Set<String> windowHandles = webDriver.getWindowHandles();
            windowHandles.forEach(h -> {
                webDriver.switchTo().window(h);
                storeCoverage(coverage);
            });
        }
    }

    private void storeCoverage(final Coverage coverage) {
        try {
            boolean isCoverageEnabled = executeJavaScript("return typeof jscoverage_serializeCoverageToJSON === 'function'");
            if (isCoverageEnabled) {
                Object returnedValue = executeJavaScript("return jscoverage_serializeCoverageToJSON();");
                coverage.storeCoverage(returnedValue.toString());
                Object localStorageValue = executeJavaScript("return localStorage[\"jscover\"]");
                if (localStorageValue != null) {
                    if (!StringUtils.equals(returnedValue.toString(), localStorageValue.toString())) {
                        coverage.storeCoverage(localStorageValue.toString());
                    } else {
                        LOG.info("LocalStorage JSCover same as current window value, skip saving...");
                    }
                    executeJavaScript("delete localStorage[\"jscover\"]");
                    LOG.info("LocalStorage JSCover detected, saved, then cleaned");
                } else {
                    LOG.info("No LocalStorage JSCover detected.");
                }
            }
        } catch (Exception e) {
            LOG.warn("Issue while collecting coverage on current page due to {}", e.getMessage());
        }
    }

}
