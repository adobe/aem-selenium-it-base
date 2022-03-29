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

package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Masonry;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class CreateSiteWizard extends Wizard {

  private static final String CREATE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/createsitewizard.html";

  private static final Collection COLLECTION = new Masonry(".foundation-collection");
  private static final SelenideElement TITLE_FIELD = new FormField("./jcr:title")
      .getFullyDecoratedElement("coral-panel.is-selected input");
  private static final SelenideElement NAME_FIELD = new FormField("label")
      .getFullyDecoratedElement("coral-panel.is-selected input");
  private static final AutoCompleteField SITE_OWNER_FIELD = new AutoCompleteField("./cq:siteOwner");
  private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");

  /**
   * @return true if the wizard is opened.
   */
  public boolean isOpened() {
    return WebDriverRunner.url().contains(CREATE_WIZARD_URL);
  }

  /**
   * @param blueprintId the id of the blueprint (path in apps or libs usually).
   */
  public void selectBlueprint(final String blueprintId) {
    COLLECTION.selectItem(blueprintId);
    COLLECTION.waitVisible();
  }

  /**
   * @return the title field.
   */
  public SelenideElement title() {
    return TITLE_FIELD;
  }

  /**
   * @return the name field.
   */
  public SelenideElement name() {
    return NAME_FIELD;
  }

  /**
   * @return the siteOwner field.
   */
  public AutoCompleteField siteOwner() {
    return SITE_OWNER_FIELD;
  }

  /**
   * @return the confirm dialog displayed at last step.
   */
  public Dialog confirmDialog() {
    return CONFIRM_DIALOG;
  }

}
