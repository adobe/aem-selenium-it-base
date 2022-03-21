/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2020 Adobe
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe and its suppliers, if any. The intellectual
 * and technical concepts contained herein are proprietary to Adobe
 * and its suppliers and are protected by all applicable intellectual
 * property laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe.
 **************************************************************************/
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
