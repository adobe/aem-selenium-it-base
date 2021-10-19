/*
 * ************************************************************************
 *  ADOBE CONFIDENTIAL
 *  ___________________
 *
 *  Copyright 2020 Adobe
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
 * ************************************************************************
 */

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class CoralRadio extends BaseComponent {

  private final ElementsCollection radioElements;

  /**
   * @param parent any parent element which would contains a coral-radio.
   * @param name filter on that name.
   */
  public CoralRadio(final SelenideElement parent, final String name) {
    super(parent);
    radioElements = element().$$(String.format("coral-radio[name=\"%s\"]", name));
  }

  /**
   * @param value to find the related radio button.
   * @return the first matching element or null if not found.
   */
  public SelenideElement elementByValue(final String value) {
    final ElementsCollection filtered = radioElements.filter(Condition.attribute("value", value));
    return filtered.isEmpty() ? null : filtered.first();
  }

  /**
   * @param value to find the related radio button.
   */
  public void activateByValue(final String value) {
    clickableClick(elementByValue(value));
  }

  /**
   * @param value to find the related radio button.
   * @return true if the radio group has such possible value
   */
  public boolean hasValue(final String value) {
    return !radioElements.filter(Condition.attribute("value", value)).isEmpty();
  }

}
