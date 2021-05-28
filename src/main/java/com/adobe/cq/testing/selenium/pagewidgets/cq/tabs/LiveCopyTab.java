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

package com.adobe.cq.testing.selenium.pagewidgets.cq.tabs;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;


public class LiveCopyTab extends BaseComponent {

    private static final SelenideElement SYNCHRONIZE = $("coral-actionbar-item button[trackingelement='synchronize']");
    private static final SelenideElement RESET = $("coral-actionbar-item button[trackingelement='reset']");
    private static final SelenideElement SUSPEND = $("coral-actionbar-item button[trackingelement='suspend']");
    private static final SelenideElement RESUME = $("coral-actionbar-item button[trackingelement='resume']");
    private static final SelenideElement DETACH = $("coral-actionbar-item button[trackingelement='detach']");

    /**
     * Construct a wrapper on LiveCopyTab panel content.
     * @param panelId the associated element id.
     */
    public LiveCopyTab(final String panelId) {
        super("#" + panelId);
    }

    /**
     * Returns synchronize dialog
     * @return synchronize dialog
     */
    public Dialog synchronize() {
        SYNCHRONIZE.click();
        return new Dialog();
    }

    /**
     * Returns reset dialog
     * @return return reset dialog
     */
    public Dialog reset() {
        RESET.click();
        return new Dialog();
    }

    /**
     *
     * @return suspend livecopy without children dialog
     */
    public Dialog suspendWithoutChild() {
        $(SUSPEND).click();
        $("a[data-foundation-tracking-event*='\"element\":\"suspend\"']").click();
        return new Dialog();
    }

    /**
     *
     * @return suspend livecopy with children dialog
     */
    public Dialog suspendWithChildren() {
        $(SUSPEND).click();
        $("a[data-foundation-tracking-event*='\"element\":\"suspend with children\"']").click();
        return new Dialog();
    }

    /**
     *
     * @return Resume livecopy dialog
     */
    public Dialog resume() {
        RESUME.click();
        return new Dialog();
    }

    public Dialog detach() {
        DETACH.click();
        return new Dialog();
    }
}
