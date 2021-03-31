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

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;

import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.$$;

public final class CoralPopOver extends BaseComponent {

    public static final String CORAL_POPOVER_IS_OPEN = "coral-popover.is-open";

    /**
     * Construct CoralPopOver wrapper for selector.
     * @param selector the selector to find the element to wrap it on.
     */
    public CoralPopOver(final String selector) {
        super(selector);
    }

    /**
     * Construct CoralPopOver wrapper for element.
     * @param element the element to wrap it on.
     */
    public CoralPopOver(final SelenideElement element) {
        super(element);
    }

    /**
     * @return a stream with all the coral popover currently opened.
     */
    public static Stream<CoralPopOver> listOpened() {
        return $$(CORAL_POPOVER_IS_OPEN).stream().map(CoralPopOver::new);
    }

    /**
     * @return a stream with all the coral popover currently opened.
     */
    public static CoralPopOver firstOpened() {
        return new CoralPopOver(CORAL_POPOVER_IS_OPEN);
    }
}
