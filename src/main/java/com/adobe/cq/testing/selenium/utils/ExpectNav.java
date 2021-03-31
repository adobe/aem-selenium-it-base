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

import com.codeborne.selenide.Selenide;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.Wait;

public final class ExpectNav {

    private static final Logger LOG = LoggerFactory.getLogger(ExpectNav.class);
    private static final String JS_NAV_START = "return performance.timing.navigationStart";

    private ExpectNav() {
    }

    /**
     * @param runnable to be executed after taking current timeOrigin, and wait it changed.
     */
    public static void on(final Runnable runnable) {
        long beforeTimeOrigin = getNavigationStart();
        runnable.run();
        Wait().until(webDriver -> {
            long actualTimeOrigin = getNavigationStart();
            return actualTimeOrigin > beforeTimeOrigin;
        });
    }

    private static long getNavigationStart() {
        long timeOrigin = 0;
        try {
            timeOrigin = Selenide.executeJavaScript(JS_NAV_START);
        } catch (ClassCastException e) {
            LOG.debug("Cast exception while reading navigationStart on browser", e);
        }
        return timeOrigin;
    }

}
