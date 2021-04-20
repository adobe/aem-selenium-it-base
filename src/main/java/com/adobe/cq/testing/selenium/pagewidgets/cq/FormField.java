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

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public final class FormField {

  /**
   * The usual page properties Tag field.
   */
  public static final FormField TAG = new FormField("./cq:tags");

  /**
   * The usual page properties Hide In Navigation field.
   */
  public static final FormField HIDE_IN_NAV = new FormField("./hideInNav");

  /**
   * The usual page properties Title field.
   */
  public static final FormField TITLE = new FormField("./jcr:title");

  /**
   * The usual asset folder properties Title field.
   */
  public static final FormField ASSET_FOLDER_TITLE = new FormField("./jcr:content/jcr:title");

  /**
   * Page Title name when creating a page.
   */
  public static final FormField PAGE_TITLE = new FormField("./pageTitle");

  /**
   * Navigation Title.
   */
  public static final FormField NAV_TITLE = new FormField("./navTitle");

  /**
   * The usual page properties Description field.
   */
  public static final FormField DESCRIPTION = new FormField("./jcr:description");

  /**
   * The usual jcr primaryType field.
   */
  public static final FormField PRIMARY_TYPE = new FormField("./jcr:primaryType");

  /**
   * The usual operation name field.
   */
  public static final FormField OP_NAME = new FormField(":name");

  /**
   * The usual design path field.
   */
  public static final FormField DESIGN_PATH = new FormField("./cq:designPath");

  /**
   * The usual design path field.
   */
  public static final FormField VANITY_PATH = new FormField("./sling:vanityPath");

  private final String name;
  private final String selector;
  private final SelenideElement element;

  /**
   * @param fieldName the field / attribute name value.
   */
  public FormField(final String fieldName) {
    this.name = fieldName;
    this.selector = String.format("[name='%s']", fieldName);
    this.element = $(selector);
  }

  /**
   * @return the element for this field.
   */
  public SelenideElement getElement() {
    return element;
  }

  /**
   * @return the field name used to build the selector.
   */
  public String getName() {
    return name;
  }

  /**
   * @return the selector string
   */
  public String toString() {
    return selector;
  }

  /**
   * @param prefix (i.e input, coral-checkbox, etc.. ...)
   * @param suffix (i.e any selector appended...)
   * @return the element with a selector in the form of "prefix[name='{fieldName}']suffix"
   */
  public SelenideElement getFullyDecoratedElement(final String prefix, final String... suffix) {
    return $(prefix + this + String.join(" ", suffix));
  }

  /**
   * @param suffix (i.e any selector appended...)
   * @return the element with a selector in the form of "[name='{fieldName}']suffix"
   */
  public SelenideElement getDecoratedElement(final String... suffix) {
    return $(this + String.join(" ", suffix));
  }

}
