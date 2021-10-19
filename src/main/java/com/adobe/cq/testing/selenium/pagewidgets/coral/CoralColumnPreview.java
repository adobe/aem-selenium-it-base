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

public class CoralColumnPreview extends BaseComponent {

  /**
   * The column preview element.
   */
  public CoralColumnPreview() {
    super("coral-columnview-preview");
  }

  /**
   * @return all the preview labels in this preview.
   */
  public ElementsCollection labels() {
    return element().$$("coral-columnview-preview-label");
  }

  /**
   * @return all the preview values in this preview.
   */
  public ElementsCollection values() {
    return element().$$("coral-columnview-preview-value");
  }

  /**
   * @return the asset element for this preview.
   */
  public SelenideElement asset() {
    return element().$("coral-columnview-preview-asset");
  }
}
