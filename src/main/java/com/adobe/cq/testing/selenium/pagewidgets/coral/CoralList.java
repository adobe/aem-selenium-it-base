/*
 * ************************************************************************
 *  ADOBE CONFIDENTIAL
 *  ___________________
 *
 *  Copyright 2022 Adobe
 *  All Rights Reserved.
 *
 *  NOTICE: All information contained herein is, and remains
 *  the property of Adobe and its suppliers, if any. The intellectual
 *  and technical concepts contained herein are proprietary to Adobe
 *  and its suppliers and are protected by all applicable intellectual
 *  property laws, including trade secret and copyright laws.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from Adobe.
 * *************************************************************************/
package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.Wait;

public final class CoralList extends AEMBaseComponent {

    private static final String SELECTOR_ITEM_ELEMENT = "coral-list-item";

    /**
     * @param cssSelector the cssSelector for filtering on coral-list elements.
     */
    public CoralList(final String cssSelector) {
        super(String.format("coral-list%s", cssSelector));
    }

    /**
     * @param attribute attribute selector string.
     * @param value value to match with.
     * @return the stream of CoralListItem that matches.
     */
    public Stream<CoralListItem> getItemsByAttribute(final String attribute, final String value) {
        return items().filter(e -> value.equals(e.element().attr(attribute)));
    }

    /**
     * @return the selected item element or null if it doesn't exist.
     */
    public CoralListItem selectedItem() {
        return new CoralListItem(element().$(String.format("%s[selected]", SELECTOR_ITEM_ELEMENT)));
    }

    /**
     * Determines if an item exists in the list.
     *
     * @param attribute attribute selector string.
     * @param value value to match with.
     * @return true if the list contains the attribute / value pairs.
     */
    public boolean hasItemByAttribute(final String attribute, final String value) {
        return items().anyMatch(e -> value.equals(e.element().attr(attribute)));
    }

    /**
     * @return a stream of CoralListItem from this list.
     */
    public Stream<CoralListItem> items() {
        return element().$$(SELECTOR_ITEM_ELEMENT).asFixedIterable().stream().map(CoralListItem::new);
    }

    /**
     * @return collection of CoralListItem from this list.
     */
    public ElementsCollection itemsCollection() {
        return element().$$(SELECTOR_ITEM_ELEMENT);
    }

    /**
     * Poll the list for any item with matching value for given attribute.
     * @param attribute attribute name
     * @param expectedValue expected value for this attribute.
     */
    public void waitForItemByAttribute(final String attribute, final String expectedValue) {
        Wait().until(webDriver -> {
            return hasItemByAttribute(attribute, expectedValue);
        });
    }

    public class CoralListItem extends AEMBaseComponent {

        private CoralListItem(SelenideElement element) {
            super(element);
        }

    }

}
