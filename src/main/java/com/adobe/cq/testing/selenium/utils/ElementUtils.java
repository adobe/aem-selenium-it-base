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

package com.adobe.cq.testing.selenium.utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.StaleElementReferenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static com.codeborne.selenide.Condition.and;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.screenshot;
import static org.awaitility.Awaitility.await;

public final class ElementUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ElementUtils.class);

    public static final long DEFAULT_DELAY_BEFORE = 250;
    private static final String MSG_ERR_CLICK_UNTIL = "Clicking on %s didn’t fullfill the expected condition %s for %s";
    private static final long SHORT_TIMEOUT = 500;
    private static long delayBefore = DEFAULT_DELAY_BEFORE;
    private static int DEFAULT_TIMEOUT = 4000;

    private ElementUtils() {
    }

    public static void setDelayBefore(final long newDelay) {
        delayBefore = newDelay;
    }

    public static void clickableClick(final SelenideElement element) {
        clickableClick(element, delayBefore);
    }

    public static void clickableClick(final SelenideElement element, final long delay) {
        Condition clickable = and("can be clicked", Condition.visible, Condition.enabled);
        element.shouldBe(clickable);
        delayBefore(delay);
        actions().moveToElement(element).click().perform();
    }

    /**
     * @param element to be clicked until
     * @param conditionalElement the element to be checked
     * @param expectedCondition the condition to be fulfilled for the conditional element
     * @param maxRetries the max number of retries
     * @param pacing the pacing in millisec
     * @throws TimeoutException
     */
    public static void clickUntil(final SelenideElement element, final SelenideElement conditionalElement,
                                  final Condition expectedCondition, final long maxRetries,
                                  final long pacing) throws TimeoutException {
        int retries = 0;
        boolean passed = false;
        while (!passed && (retries < maxRetries)) {
            try {
                if (!passed) {
                    Selenide.sleep(pacing);
                    element.should(exist);
                    try {
                        actions().moveToElement(element).click().perform();
                    } catch (StaleElementReferenceException ex) {
                        LOG.warn("Error on click action: " + ex.getMessage());
                    }
                }
                if (conditionalElement.has(expectedCondition)) passed = true;
                try {
                    await().pollInSameThread()
                            .timeout(Duration.ofMillis(SHORT_TIMEOUT))
                            .until(() -> conditionalElement.has(expectedCondition));
                    passed = true;
                } catch (ConditionTimeoutException ex) {
                    LOG.info("ConditionTimeout " + expectedCondition);
                }
            } catch (JavascriptException e) {
                LOG.error("Error due to {}", e.getMessage());
            } finally {
                retries++;
            }
        }
        if (!passed) {
            String screenshotFileName = "clickUntilTimeout-" + System.currentTimeMillis();
            screenshot(screenshotFileName);
            LOG.error("Timeout reached, created screenshot {}", screenshotFileName);
            throw new TimeoutException(String.format(MSG_ERR_CLICK_UNTIL, element.toString(),
                    expectedCondition.toString(), conditionalElement.toString()));
        }
    }

    public static boolean hasWithPolling(final SelenideElement element, final Condition condition, int timeout) {
        boolean result = false;
        try {
            await().pollInSameThread()
                    .timeout(Duration.ofMillis(timeout))
                    .until(() -> element.has(condition));
            result = true;
        } catch (ConditionTimeoutException ex) {
            LOG.info("ConditionTimeout " + condition);
        } finally {
            return result;
        }
    }

    public static boolean hasWithPolling(final SelenideElement element, final Condition condition) {
        return hasWithPolling(element, condition, DEFAULT_TIMEOUT);
    }

    /**
     * Execute a sleep before clicking
     */
    public static void delayBefore() {
        delayBefore(delayBefore);
    }

    public static void delayBefore(final long delay) {
        Selenide.sleep(delay);
    }
}
