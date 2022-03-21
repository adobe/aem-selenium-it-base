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

import com.adobe.cq.testing.selenium.pageobject.cq.sites.CreateWorkflowWizard;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralStepList;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.adobe.cq.testing.selenium.Constants.EXISTS_ENABLED_VISIBLE;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;

public class TimelinePanel extends AEMBaseComponent {

  public static final SelenideElement COMMENT_FIELD = new FormField("cq-common-admin-timeline-toolbar-actions-comment")
      .getFullyDecoratedElement("input");

  public static final SelenideElement VERSION_LABEL = new FormField("label")
      .getFullyDecoratedElement("input");
  public static final SelenideElement VERSION_COMMENT = new FormField("comment")
      .getFullyDecoratedElement("textarea");

  private static final SelenideElement MORE_ACTIONS_BUTTON = $("[data-rel=\"cq-common-admin-timeline-toolbar-actions-main\"] coral-icon");
  private static final SelenideElement SAVE_AS_VERSION_BUTTON = $("[data-rel=\"cq-common-admin-timeline-toolbar-actions-version\"]");
  private static final SelenideElement START_WORKFLOW_BUTTON = $("button.cq-common-admin-timeline-toolbar-actions-workflow");
  private static final SelenideElement FORM_VERSION = $("form.cq-common-admin-timeline-toolbar-actions-version-form");
  private static final SelenideElement VERSION_CREATE_BUTTON = $(".cq-common-admin-timeline-toolbar-actions-version-ok");
  private static final SelenideElement TIMELINE_MORE_BUTTON = $("button.cq-common-admin-timeline-more-button");

  private static final String TIMELINE_EVENT_SELECTOR = ".cq-common-admin-timeline-event";
  private static final String TIMELINE_TITLES_SELECTOR = ".cq-common-admin-timeline-event-title";
  private static final String TIMELINE_BALLOONS_SELECTOR = ".cq-common-admin-timeline-event-balloon";

  private SelenideElement filter;

  /**
   * Construct the Timeline Panel on the left Rail.
   */
  public TimelinePanel() {
    super("coral-panel[data-shell-collectionpage-rail-panel=\"timeline\"]");
    filter = element().$("button[variant=\"_custom\"]");
  }

  /**
   * @return true if this panel is selected and visible.
   */
  public boolean isOpened() {
    return element().has(Condition.cssClass("is-selected")) && element().isDisplayed();
  }

  /**
   * @param comment add a comment text to current timeline.
   */
  public void addComment(final String comment) {
    COMMENT_FIELD.shouldBe(EXISTS_ENABLED_VISIBLE).sendKeys(comment, Keys.ENTER);
  }

  /**
   * @param label add a label to that version.
   * @param comment add a comment text to that version timeline.
   */
  public void saveVersion(final String label, final String comment) {
    clickableClick(MORE_ACTIONS_BUTTON);
    clickableClick(SAVE_AS_VERSION_BUTTON);

    FORM_VERSION.shouldBe(Condition.visible);
    VERSION_LABEL.shouldBe(Condition.visible).sendKeys(label);
    VERSION_COMMENT.shouldBe(Condition.visible).sendKeys(comment);

    clickableClick(VERSION_CREATE_BUTTON);
  }

  /**
   * @return all timeline event elements.
   */
  public ElementsCollection getTimelineEvents() {
    return element().$$(TIMELINE_EVENT_SELECTOR);
  }

  /**
   * @return all timeline expandable event elements.
   */
  public ElementsCollection getExpandableTimelineEvents() {
    return element().$$(TIMELINE_EVENT_SELECTOR).filter(Condition.cssClass("is-expandable"));
  }

  /**
   * @return the timeline expandable active event element.
   */
  public TimelineWorkflowEvent getActiveExpandableTimelineEvents() {
    return new TimelineWorkflowEvent();
  }

  /**
   * @param title the title for this expandable event.
   * @return the selected timeline event after opening it, or null if not found.
   */
  public TimelineWorkflowEvent selectExpandableEventByTitle(final String title) {
    final SelenideElement matchingEvent = getTimelineTitles().filter(Condition.text(title)).first();
    TimelineWorkflowEvent activeEvent = null;
    if (matchingEvent != null) {
      final SelenideElement expandableEvent = matchingEvent.closest("section");
      if (!expandableEvent.has(Condition.cssClass("is-active"))) {
        clickableClick(expandableEvent); // activate it
      }
      activeEvent = getActiveExpandableTimelineEvents();
    }
    return activeEvent;
  }

  /**
   * @return all timeline balloons elements.
   */
  public ElementsCollection getTimelineBalloons() {
    return element().$$(TIMELINE_BALLOONS_SELECTOR);
  }

  /**
   * @return all timeline titles elements.
   */
  public ElementsCollection getTimelineTitles() {
    return element().$$(TIMELINE_TITLES_SELECTOR);
  }

  /**
   * Click on the more action.
   */
  public void more() {
    clickableClick(TIMELINE_MORE_BUTTON);
  }

  /**
   * @return filter list button.
   */
  public CoralSelectList filter() {
    clickableClick(filter);
    CoralPopOver popOver = CoralPopOver.firstOpened();
    popOver.waitVisible();
    return new CoralSelectList(popOver.element());
  }

  /**
   * @return true if the more button is displayed.
   */
  public boolean hasMore() {
    return TIMELINE_MORE_BUTTON.isDisplayed();
  }

  /**
   * @param modelId the model Id to use.
   * @param title the title added to the workflow.
   */
  public void startWorkflow(final String modelId, final String title) {
    clickableClick(MORE_ACTIONS_BUTTON);
    ExpectNav.on(() -> clickableClick(START_WORKFLOW_BUTTON));
    CreateWorkflowWizard createWfWiz = new CreateWorkflowWizard();
    Wait().until(w -> createWfWiz.isOpened());
    createWfWiz.selectModel(modelId);
    createWfWiz.title().sendKeys(title);
    createWfWiz.next();
    CoralStepList stepList = createWfWiz.stepList();
    Wait().until(w -> "Scope".equals(stepList.getCurrentStepLabel()));
    // Click create now
    ExpectNav.on(createWfWiz::next);
  }
}
