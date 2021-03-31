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
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.stream.Stream;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.actions;
import static com.codeborne.selenide.Selenide.sleep;

public class CoralMultiField extends BaseComponent {

  private static final String SELECTOR_PATTERN = "coral-multifield[data-granite-coral-multifield-name=\"%s\"]";
  private static final int SMALL_DELAY = 250;

  private SelenideElement addButton;

  /**
   * @param name field name.
   */
  public CoralMultiField(final String name) {
    super(String.format(SELECTOR_PATTERN, name));
    addButton = element().$("button[coral-multifield-add]");
  }
  
  /**
   * @param parent element in which the coral-multifield is found.
   */
  public CoralMultiField(final SelenideElement parent) {
    super(parent);
    addButton = element().$("button[coral-multifield-add]");
  }

  /**
   * Click on add button.
   * @return added element.
   */
  public MultiFieldItem add() {
    clickableClick(addButton);
    sleep(SMALL_DELAY);
    return items().reduce((first, second) -> second).orElse(null);
  }

  /**
   * @return list of coral-multifield-item
   */
  public Stream<MultiFieldItem> items() {
    ElementsCollection items = element().$$("coral-multifield-item");
    return items.stream().map(i -> new MultiFieldItem(i));
  }

  public final class MultiFieldItem extends BaseComponent {
    /**
     * @param itemElement the item element to wrap this on.
     */
    private MultiFieldItem(final SelenideElement itemElement) {
      super(itemElement);
    }

    /**
     * Click on remove.
     */
    public void remove() {
      clickableClick(element().$("button[handle=\"remove\"]"));
    }

    /**
     * @param item the element to move it over
     */
    public void move(final MultiFieldItem item) {
      final SelenideElement moveButton = element().$("button[handle=\"move\"]");
      actions()
          .dragAndDrop(moveButton, item.element())
          .perform();
    }

    /**
     * @return the input field element.
     */
    public SelenideElement input() {
      return element().$("coral-multifield-item-content input");
    }

  }

}
