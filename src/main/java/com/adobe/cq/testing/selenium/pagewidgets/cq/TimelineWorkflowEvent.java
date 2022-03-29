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

package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.utils.ExpectNav;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class TimelineWorkflowEvent extends AEMBaseComponent {

  /**
   * Constructor for the currently active workflow events.
   */
  public TimelineWorkflowEvent() {
    super("section.cq-common-admin-timeline-event.cq-common-admin-timeline-event--version.is-expandable.is-active");
  }

  /**
   * Click on preview.
   */
  public void preview() {
    clickableClick(element().$("a.cq-common-admin-timeline-preview-button"));
  }

  /**
   * Click on compare to current.
   */
  public void compare() {
    ExpectNav.on(() -> clickableClick(element().$("a.cq-common-admin-timeline-compare-button")));
  }

  /**
   * Click on revert to this version.
   */
  public void revert() {
    clickableClick(element().$("button.cq-common-admin-timeline-event-action-ok"));
  }
}
