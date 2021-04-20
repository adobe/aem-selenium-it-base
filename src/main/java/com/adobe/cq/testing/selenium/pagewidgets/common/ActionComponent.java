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

package com.adobe.cq.testing.selenium.pagewidgets.common;

import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class ActionComponent<V> extends BaseComponent {

    private final Logger LOG = LoggerFactory.getLogger(ActionComponent.class);
    private Callable<V> callable;
    private boolean expectNavOnPerform;

    public ActionComponent(final SelenideElement element) {
        super(element);
    }

    public ActionComponent(final String selector) {
        super(selector);
    }

    public ActionComponent(final SelenideElement element, final Callable<V> callableCode, final boolean expectNav) {
        super(element);
        this.callable = callableCode;
        this.expectNavOnPerform = expectNav;
    }

    public ActionComponent(final String selector, final Callable<V> callableCode, final boolean expectNav) {
        super(selector);
        this.callable = callableCode;
        this.expectNavOnPerform = expectNav;
    }

    /**
     * Simply click on that object and return the next object.
     */
    public V perform() {
        if (expectNavOnPerform) {
            ExpectNav.on(() -> click());
        } else {
            click();
        }
        return afterClick();
    }

    protected V afterClick() {
        V o = null;
        try {
            if (callable != null) {
                o = (V) callable.call();
            }
        } catch (Exception e) {
            LOG.error("Could not instantiate due to exception", e);
        }
        return o;
    }
}