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
