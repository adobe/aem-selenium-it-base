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

public final class CoralActionBar extends BaseComponent {

    /**
     * Constructor to get the actionbar.
     */
    public CoralActionBar() {
        super("coral-actionbar");
    }

    /**
     * @return the selection status element.
     */
    public SelenideElement getSelectionStatus() {
        return element().$("span.foundation-admin-selectionstatus");
    }

    /**
     * @return all the items elements in the action bar.
     */
    public Stream<Item> items() {
        return element().$$("coral-actionbar-item").stream().map(Item::new);
    }

    public class Item extends BaseComponent {

        public Item(SelenideElement element) {
            super(element);
        }

    }
}
