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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.SelenideElement;

/**
 * Page Object Model for Calendar Picker widget. (i.e when opening a Date/Time field).
 */
public final class CalendarPicker extends AEMBaseComponent {

    /**
     * @param name "name" attribute value of the corresponding picker element.
     */
    public CalendarPicker(final String name) {
        super(String.format("coral-datepicker[name=\"%s\"]", name));
    }

    public CalendarPicker(final SelenideElement element) {
        super(element);
    }

    /**
     * @return the button element (i.e with a calendar like icon).
     */
    public SelenideElement calendarButton() {
        String pickerType = this.getPickerType();
        if ("time".equals(pickerType)) {
            return element().$("button coral-icon[icon=\"clock\"]");
        } else {
            return element().$("button coral-icon[icon=\"calendar\"]");
        }
    }

    /**
     * @return the currently opened popover element if any.
     */
    public CoralPopOver popover() {
        return CoralPopOver.firstOpened();
    }

    /**
     * @return the calendar day element marked as today if exist.
     */
    public SelenideElement today() {
        return popover().element().$("a.is-today");
    }

    /**
     * @param dayOfMonth the day of the current month.
     * @return the calendar day of the month from current month.
     */
    public SelenideElement date(final int dayOfMonth) {
        return popover().element().$$("a.is-currentMonth").get(dayOfMonth);
    }

    /**
     * @return the previous month button
     */
    public SelenideElement prev() {
        return popover().element().$("button[handle=\"prev\"]");
    }

    /**
     * @return the next month button
     */
    public SelenideElement next() {
        return popover().element().$("button[handle=\"next\"]");
    }

    /**
     * @return the clock fields.
     */
    public CoralClock clock() {
        return new CoralClock(popover().element());
    }

    public String getPickerType() {
        return element().getAttribute("type");
    }

    public String getValue() {
        return element().$("input[handle=\"hiddenInput\"]").getValue();
    }
}
