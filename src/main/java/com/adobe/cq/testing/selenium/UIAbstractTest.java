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

package com.adobe.cq.testing.selenium;

import com.adobe.cq.testing.selenium.junit.annotations.UITest;
import com.adobe.cq.testing.selenium.junit.extensions.BrowserProxyExtension;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import com.codeborne.selenide.logevents.SelenideLogger;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.bonigarcia.seljup.Options;
import io.github.bonigarcia.seljup.SeleniumExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.adobe.cq.testing.selenium.Constants.DEFAUT_WEBDRIVER_TIMEOUT;

@UITest
public abstract class UIAbstractTest implements LogEventListener {

    private static final AtomicInteger CAPTURE_COUNT = new AtomicInteger(0);
    private Logger logger = LoggerFactory.getLogger(getClass());

    @RegisterExtension
    protected static BrowserProxyExtension browserProxyExtension = new BrowserProxyExtension();

    @RegisterExtension
    protected static SeleniumExtension seleniumExtension = new SeleniumExtension();

    @BeforeAll
    public static void setUpOnce() throws URISyntaxException, SocketException {
    }

    static {
        Configuration.timeout = DEFAUT_WEBDRIVER_TIMEOUT;
    }

    @Options
    ChromeOptions chromeOptions = new ChromeOptions();

    public UIAbstractTest() {
        if (browserProxyExtension.isEnabled()) {
            chromeOptions.addArguments("--ignore-certificate-errors", "--user-data-dir=/tmp/insecurechrome");
            final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.PROXY, browserProxyExtension.getSeleniumProxy());
            chromeOptions.merge(desiredCapabilities);
        }
    }

    @BeforeEach
    @SuppressFBWarnings
    public final void setUp(final WebDriver driver) {
        WebDriverRunner.setWebDriver(driver);
        driver.manage().window().maximize();
        if (!SelenideLogger.hasListener("uitest")) {
            SelenideLogger.addListener("uitest", this);
        }
    }

    @AfterEach
    public final void tearDown() {
        dumpBrowserLogs();
    }

    @AfterAll
    public static final void tearDownOnce() {
    }

    private void dumpBrowserLogs() {
//        List<String> logs = Selenide.getWebDriverLogs(LogType.BROWSER);
//        logger.error("================== BROWSER LOGS =======================");
//        logs.forEach(entry -> logger.error(entry));
//        logger.error("=======================================================");
    }

    @Override
    public void afterEvent(final LogEvent logEvent) {
        logger.info("subject={} element={} duration={} status={} error={}",
            logEvent.getSubject(), logEvent.getElement(), logEvent.getDuration(), logEvent.getStatus(), logEvent.getError());
    }

    @Override
    public void beforeEvent(final LogEvent logEvent) {
        logger.info("subject={} element={}", logEvent.getSubject(), logEvent.getElement());
    }
}
