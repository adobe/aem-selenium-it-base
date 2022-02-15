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

import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralButtonList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralTagList;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.util.concurrent.Callable;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class AutoCompleteField<V> extends ActionComponent<V> {

  private static final String FOUNDATION_AUTOCOMPLETE = "foundation-autocomplete";
  private static final String SELECTOR_CSS = "css:";
  public static final String ARIA_CONTROLS = "aria-controls";
  private SelenideElement inputField;
  private CoralButtonList buttonList;

  public AutoCompleteField(final String selector) {
    super(getSelector(selector));
    init();
  }

  /**
   * @param selector or name of The field name for this autocomplete.
   */
  public AutoCompleteField(final String selector, final Callable<V> callableCode, final boolean expectNav) {
    super(getSelector(selector), callableCode, expectNav);
    init();
  }

  /**
   * @param element to map it on.
   */
  public AutoCompleteField(final SelenideElement element, final Callable<V> callableCode, final boolean expectNav) {
    super(element, callableCode, expectNav);
    init();
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public <T extends BaseComponent> T  click() {
    clickableClick(element().$("button"));
    return (T) this;
  }

  private void init() {
    buttonList = new CoralButtonList(element());
    inputField = element().$("input[role=\"combobox\"]");
  }

  private static String getSelector(final String selector) {
    if (selector.startsWith(SELECTOR_CSS)) {
      return FOUNDATION_AUTOCOMPLETE + selector.substring(SELECTOR_CSS.length());
    } else {
      return new FormField(selector).getFullyDecoratedElement(FOUNDATION_AUTOCOMPLETE).getSearchCriteria();
    }
  }

  /**
   * @param keys type keys in the main input field.
   */
  public void sendKeys(final CharSequence... keys) {
    inputField.sendKeys(keys);
  }

  /**
   * Clear main input field.
   */
  public void clear() {
    inputField.setValue("");
  }

  /*
   * @return input field value.
   */
  public String value() {
    return inputField.getValue();
  }

  /**
   * @return associated button list.
   */
  public CoralButtonList buttonlist() {
    String buttonId = inputField.shouldHave(Condition.attribute(ARIA_CONTROLS)).getAttribute(ARIA_CONTROLS);
    return new CoralButtonList(String.format("#%s", buttonId));
  }

  /**
   * @return the suggestions
   */
  public CoralButtonList suggestions() {
      return new CoralButtonList(element().$("coral-overlay").should(Condition.exist));
  }
  
  /**
   * @return the taglist
   */
  public CoralTagList taglist() {
      return new CoralTagList(element());
  }

  
}
