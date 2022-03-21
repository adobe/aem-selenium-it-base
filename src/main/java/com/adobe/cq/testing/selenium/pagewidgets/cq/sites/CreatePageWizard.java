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

package com.adobe.cq.testing.selenium.pagewidgets.cq.sites;

import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Collection;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Masonry;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.*;

public class CreatePageWizard extends Wizard {

  private static final String CREATE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/createpagewizard.html";

  private static final Collection COLLECTION = new Masonry(".foundation-collection");
  private static final String PANEL_IS_SELECTED_INPUT = "coral-panel.is-selected input";

  private static final SelenideElement TITLE_FIELD = TITLE.getFullyDecoratedElement(PANEL_IS_SELECTED_INPUT);

  private static final SelenideElement NAME_FIELD = new FormField("pageName")
      .getFullyDecoratedElement(PANEL_IS_SELECTED_INPUT);
  private static final SelenideElement PAGE_TITLE_FIELD = PAGE_TITLE.getFullyDecoratedElement(PANEL_IS_SELECTED_INPUT);
  private static final SelenideElement NAV_TITLE_FIELD = NAV_TITLE.getFullyDecoratedElement(PANEL_IS_SELECTED_INPUT);
  private static final AutoCompleteField TAGS_FIELD = new AutoCompleteField(TAG.getName());
  private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");

  /**
   * @return true if the wizard is opened.
   */
  public boolean isOpened() {
    return WebDriverRunner.url().contains(CREATE_WIZARD_URL);
  }

  /**
   * @param templateId the id of the template (path in apps or libs usually).
   */
  public void selectTemplate(final String templateId) {
    COLLECTION.selectItem(templateId);
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
   * @return the pageTitle field.
   */
  public SelenideElement pageTitle() {
    return PAGE_TITLE_FIELD;
  }

  /**
   * @return the navTitle field.
   */
  public SelenideElement navTitle() {
    return NAV_TITLE_FIELD;
  }

  /**
   * @return the siteOwner field.
   */
  public AutoCompleteField tags() {
    return TAGS_FIELD;
  }

  /**
   * @return the confirm dialog displayed at last step.
   */
  public Dialog confirmDialog() {
    return CONFIRM_DIALOG;
  }

}
