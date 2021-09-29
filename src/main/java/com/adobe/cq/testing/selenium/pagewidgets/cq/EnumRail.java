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

public enum EnumRail {

  /**
   * Timeline option in the Rail CycleButton popover.
   */
  TIMELINE("Timeline", "timeline"),
  /**
   * Content Only option in the Rail CycleButton popover.
   */
  CONTENT_ONLY("Content Only", null),
  /**
   * Content Tree option in the Rail CycleButton popover.
   */
  CONTENT_TREE("Content Tree", "content-tree"),
  /**
   * References option in the Rail CycleButton popover.
   */
  REFERENCES("References", "references"),
  /**
   * Filter option in the Rail CycleButton popover.
   */
  FILTER("Filter", null);

  private final String panelName;
  private final String title;

  /**
   * @param i18nTitle the title in the cycle buttons list as i18n value.
   * @param relPanelName the name for the associated panel or null if none (i.e Content Only, Filter).
   */
  EnumRail(final String i18nTitle, final String relPanelName) {
    this.panelName = relPanelName;
    this.title = i18nTitle;
  }

  /**
   * @return the associated panel name for this rail cycle button item.
   */
  public String getPanelName() {
    return panelName;
  }

  /**
   * @return the i18n title to be converted / localized.
   */
  public String getI18nTitle() {
    return title;
  }
}
