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

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CalendarPicker;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class RestoreTreeWizard extends Wizard {

  private static final String RESTORE_TREE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/restoretreewizard.html";
  private static final SelenideElement TITLE_FIELD = new FormField("wizardTitle")
      .getFullyDecoratedElement("coral-panel.is-selected input");
  private static final AEMBaseComponent TREE_VIEW = new AEMBaseComponent("coral-panel.is-selected coral-tree");
  private static final CalendarPicker TIMELINE_DATE = new CalendarPicker("date");
  private static final CoralCheckbox PREVENT_NVP = new CoralCheckbox("input[name=\"preserveNVP\"]");

  /**
   * @return true if the wizard is opened.
   */
  public boolean isOpened() {
    return WebDriverRunner.url().contains(RESTORE_TREE_WIZARD_URL);
  }

  /**
   * @return the title field.
   */
  public SelenideElement title() {
    return TITLE_FIELD;
  }

  /**
   * @return the tree view component.
   */
  public AEMBaseComponent tree() {
    return TREE_VIEW;
  }

  /**
   * @return the timeline calendar
   */
  public CalendarPicker timelineDate() { return TIMELINE_DATE; }

  /**
   * @return the prevent non versioned pages checkbox
   */
  public CoralCheckbox preventNvp() { return PREVENT_NVP; }

  public static RestoreTreeWizard open(final String parent, final String source) {
    return new RestoreTreeWizard().open(String.format("%s%s?%s", RESTORE_TREE_WIZARD_URL, parent, source));
  }

}
