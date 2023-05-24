/*
 * Copyright 2022 Adobe Systems Incorporated
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

import com.adobe.cq.testing.selenium.junit.annotations.UserTimeZone;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.impl.ScreenShotLaboratory;
import com.codeborne.selenide.impl.Screenshot;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Config;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.adobe.cq.testing.selenium.Constants.DEFAUT_WEBDRIVER_TIMEOUT;

public class WebdriverSetupExtension implements BeforeAllCallback, BeforeEachCallback,
        AfterEachCallback, LogEventListener {

    private static final String LISTENER_NAME = "uitest";
    public static final String EVENT_LOGGER_MESSAGE_FMT = "subject={} element={} duration={} status={} error={}";
    private Logger logger = null;

    private static final ThreadLocal<WebDriverManager> localWdm = new ThreadLocal<>();

    public static final String BUILD_REPORTS_SCREENSHOTS = "build/reports/tests/screenshots";
    public static final String BUILD_REPORTS_RECORDINGS = "build/reports/tests/recordings";

    static {
        // Configure Selenide default timeout
        Configuration.timeout = DEFAUT_WEBDRIVER_TIMEOUT;
        Configuration.reportsFolder = BUILD_REPORTS_SCREENSHOTS;
        // Configure to have chrome-in-docker as the default as it used to be
        String browserType = System.getProperty("sel.jup.default.browser", "chrome-in-docker");
        System.setProperty("wdm.defaultBrowser", browserType);
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        // Pre-create the reports folder
        try {
            Files.createDirectories(Path.of(BUILD_REPORTS_SCREENSHOTS));
            if (isRecordingEnabled()) {
                Files.createDirectories(Path.of(BUILD_REPORTS_RECORDINGS));
            }
        } catch (IOException ex) {
            LoggerFactory.getLogger(WebdriverSetupExtension.class).error("Could not create reports folder", ex);
        }
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        logger = LoggerFactory.getLogger(extensionContext.getTestClass().orElseThrow(IllegalStateException::new));
        logger.info("This extension instance {}", this);
        WebDriverManager wdm = setupWebdriver(extensionContext);
        WebDriver driver = wdm.create();
        wdm.getWebDriverList().forEach(wd -> logger.info("Docker webdriver {}", wd));
        WebDriverRunner.setWebDriver(driver);
        driver.manage().window().maximize();
        String listenerName = getListenerName();
        if (!SelenideLogger.hasListener(listenerName)) {
            SelenideLogger.addListener(listenerName, this);
        }
        localWdm.set(wdm);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        WebDriverManager wdm = localWdm.get();
        if (!Objects.isNull(wdm)) {
            try {
                dumpBrowserLogs();
                SelenideLogger.removeListener(LISTENER_NAME);
                boolean failed = extensionContext.getExecutionException().isPresent();
                saveScreenshotOnFail(extensionContext.getTestMethod().orElseThrow().getName(), failed);
                quitHandleRecordings(wdm, !failed);
            } finally {
                wdm.quit();
            }
        }
        localWdm.remove();
    }

    private void saveScreenshotOnFail(String prefix, boolean failed) {
        try {
            if (failed) {
                String screenshotFileName = String.format("%s_%d",
                        prefix,
                        System.currentTimeMillis());
                Screenshot screenshot = ScreenShotLaboratory.getInstance()
                        .takeScreenshot(WebDriverRunner.driver(), screenshotFileName, true, true);
                logger.error("Test Failed: image at {}, html at {}", screenshot.getImage(), screenshot.getSource());
            }
        } catch (Exception err) {
            logger.error("Cannot save screenshot due to error", err);
        }
    }

    private void quitHandleRecordings(WebDriverManager manager, boolean removeRecordings) {
        // Get recording files (to be deleted after quit)
        List<Path> recordingList = new ArrayList<>();
        if (isRecordingEnabled() && isRunningInDocker() && keepRecordingOnFailureOnly() && removeRecordings) {
            recordingList.add(manager.getDockerRecordingPath(WebDriverRunner.getWebDriver()));
        }

        manager.quit();

        // Delete recordings (if any)
        recordingList.forEach(path -> {
            try {
                logger.debug("Deleting on exit {} (since test does not fail)",
                        path);
                path.toFile().deleteOnExit();
            } catch (Exception e) {
                logger.warn("Exception trying to delete recording {}",
                        path);
            }
        });
    }

    private boolean isRunningInDocker() {
        WebDriverManager wdm = localWdm.get();
        return null != wdm && null != wdm.getDockerBrowserContainerId();
    }

    @Override
    public void afterEvent(final LogEvent logEvent) {
        logger.info(EVENT_LOGGER_MESSAGE_FMT,
            logEvent.getSubject(), logEvent.getElement(), logEvent.getDuration(), logEvent.getStatus(), logEvent.getError());
    }

    @Override
    public void beforeEvent(final LogEvent logEvent) {
        logger.info("subject={} element={}", logEvent.getSubject(), logEvent.getElement());
    }

    private String getListenerName() {
        return String.format("%s-%d", LISTENER_NAME, Thread.currentThread().getId());
    }

    private String getUserTimeZone(ExtensionContext ec) {
        UserTimeZone annotation = ec.getTestMethod().orElseThrow(IllegalStateException::new).getAnnotation(UserTimeZone.class);
        if (Objects.isNull(annotation)) {
            annotation = ec.getTestClass().orElseThrow(IllegalAccessError::new).getAnnotation(UserTimeZone.class);
            if (Objects.isNull(annotation)) {
                return "none";
            }
        }
        return annotation.tz();
    }

    private WebDriverManager setupWebdriver(ExtensionContext ec) {
        // Backward compatibility
        String userTimeZone = getUserTimeZone(ec);
        WebDriverManager wdm = WebDriverManager.getInstance();
        ChromeOptions chromeOptions = new ChromeOptions();
        wdm.capabilities(chromeOptions);
        chromeOptions.addArguments("--remote-allow-origins=*");
        if (BrowserProxyExtension.isEnabled()) {
            chromeOptions.addArguments("--ignore-certificate-errors", "--user-data-dir=/tmp/insecurechrome");
            final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(CapabilityType.PROXY, BrowserProxyExtension.getSeleniumProxy());
            chromeOptions.merge(desiredCapabilities);
        }
        if (!StringUtils.equals("none", userTimeZone)) {
            logger.info("Setting docker timezone with {}", userTimeZone);
            wdm.config().setDockerTimezone(userTimeZone);
        }
        wdm.config().setDockerScreenResolution("1920x1080x24");
        wdm.config().setDockerStopTimeoutSec(30);
        if (isRecordingEnabled()) {
            wdm.enableRecording();
            wdm.config().setDockerRecordingFrameRate(4);
            wdm.config().setDockerRecordingOutput(Paths.get(BUILD_REPORTS_RECORDINGS));
            wdm.config().setDockerRecordingPrefix(String.format("%s_", ec.getTestMethod().orElseThrow().getName()));
        }
        return wdm;
    }

    private boolean isRecordingEnabled() {
        boolean useCustomImage = StringUtils.isNotBlank(new Config().getDockerCustomImage());
        return !useCustomImage && Boolean.parseBoolean(System.getProperty("sel.jup.recording", "false"));
    }

    private boolean keepRecordingOnFailureOnly() {
        return Boolean.parseBoolean(System.getProperty("sel.jup.recording.when.failure", "true"));
    }

    private void dumpBrowserLogs() {
        try {
            List<String> logs = Selenide.getWebDriverLogs(LogType.BROWSER);
            logger.error("================== BROWSER LOGS =======================");
            logs.forEach(entry -> logger.error(entry));
            logger.error("=======================================================");
        } catch (UnsupportedCommandException ex) {
            logger.error("dumpBrowserLogs not possible on this browser");
        }
    }

    public static WebDriverManager getWebdriverManager() {
        return localWdm.get();
    }

}
