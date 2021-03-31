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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.codeborne.selenide.Selenide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.Wait;
import static com.codeborne.selenide.Selenide.open;

public final class CoralReady {

    private static final Logger LOG = LoggerFactory.getLogger(CoralReady.class);
    private static final int DOM_POLLING_INTERVAL = 250;

    private CoralReady() {
    }

    // JS script to be executed on browser to make sure all Coral components below coral-shell are initialized
    private static final String JS_CORAL_READY_CONDITION = "window && window.Coral && window.Coral.commons && window.Coral.commons.ready && true";
    private static final String JS_CORAL_READY_SCRIPT =
              "if (%s) {%n"
            + "window.Coral.commons.ready('%s', arguments[0])"
            + "}%n";

    public static boolean assertCoralReadyCondition() {
        return Boolean.TRUE.equals(Selenide.executeJavaScript("return " + JS_CORAL_READY_CONDITION));
    }

    public static <T> T openReady(final String absoluteOrRelavive, final Class<T> pageObject) {
        LOG.info("openReady({})", absoluteOrRelavive);
        T open = open(absoluteOrRelavive, pageObject);
        waitCoralReady();
        return open;
    }

    public static void waitCoralReady() {
        waitCoralReady("body");
    }

    public static void waitCoralReady(final String selector) {
        LOG.info("waitCoralReady({})", selector);
        Wait().until(webdriver -> CoralReady.assertCoralReadyCondition());
        LOG.debug("CoralReady Precondition checked");
        Selenide.executeAsyncJavaScript(String.format(JS_CORAL_READY_SCRIPT, JS_CORAL_READY_CONDITION, selector));
        LOG.debug("CoralReady checked");
        Helpers.waitDOMIdled(DOM_POLLING_INTERVAL);
    }
}
