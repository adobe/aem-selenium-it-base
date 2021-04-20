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


package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class TimewarpDialog extends Dialog {

    private static final String CSS_SELECTOR = ".js-editor-TimewarpDialog";

    public TimewarpDialog() { super(CSS_SELECTOR); }

    public SelenideElement setDateButton() {
        return this.element().find("._coral-Button--cta").should(Condition.visible);
    }

    public SelenideElement cancelButton() {
        return this.element().find("._coral-Button--primary").should(Condition.visible);
    }


}
