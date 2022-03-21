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

package com.adobe.cq.testing.selenium.pagewidgets.cq.actions;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

public class CreateActions extends AEMBaseComponent {

  /**
   * Contructor for the Create button on top right side on Sites.
   */

  public CreateActions() {
    super("button.granite-collection-create.foundation-toggleable-control");
  }

  /**
   * @return the current list of actions available.
   */
  public ElementsCollection getActions() {
    CoralPopOver popOver = CoralPopOver.firstOpened();
    return popOver.element().$$("[is=\"coral-anchorlist-item\"]");
  }

  /**
   * @param createActionType any of the available action type.
   * @return the element to trigger the action.
   */
  public SelenideElement getAction(final CreateActionType createActionType) {
    CoralPopOver popOver = CoralPopOver.firstOpened();
    final String cssClass = createActionType.getCssClass();
    final String selector = cssClass != null ? "." + cssClass : String.format("[rel=\"%s\"]", createActionType.getRel());
    return popOver.element().$(selector);
  }

  public interface CreateActionType {
    String getRel();
    String getCssClass();
  }

  public enum SitesCreateActionType implements CreateActionType {
    /**
     * Action for Create Page.
     */
    PAGE("cq-siteadmin-admin-createpage"),
    /**
     * Action for Create Site.
     */
    SITE("cq-siteadmin-admin-createsite"),
    /**
     * Action for Create Live Copy.
     */
    LIVE_COPY("cq-siteadmin-admin-createlivecopy"),
    /**
     * Action for Create Launch.
     */
    LAUNCH("cq-siteadmin-admin-createlaunch"),
    /**
     * Action for Create Language Copy.
     */
    LANGUAGE_COPY("cq-siteadmin-admin-createlanguagecopy"),
    /**
     * Action for Create Folder.
     */
    FOLDER("cq-siteadmin-admin-createfolder"),
    /**
     * Action for Create CSV Report.
     */
    CSV_REPORT("cq-siteadmin-admin-createcsvreport");

    private final String rel;

    /**
     * @param relname the rel/class value used for this action.
     */
    SitesCreateActionType(final String relname) {
      this.rel = relname;
    }

    /**
     * @return the associated rel value for this action.
     */
    public String getRel() {
      return rel;
    }

    /**
     * @return the associated css class value for this action.
     */
    public String getCssClass() {
      return null;
    }
  }

  public enum DAMCreateActionType implements CreateActionType {
    /**
     * Action for Create DAM Folder.
     */
    FOLDER("dam-create-folder"),
    /**
     * Action for Upload DAM Files.
     */
    UPLOAD_FILES("cq-damadmin-admin-assets-upload"),
    /**
     * Action for Create DAM Content Fragment.
     */
    CONTENT_FRAGMENT("dam-create-fragment"),
    /**
     * Action for Create DAM Live Copy.
     */
    LIVE_COPY("cq-damadmin-admin-actions-createlivecopy"),
    /**
     * Action for Create DAM Metadata.
     */
    METADATA("dam-import-metadata");

    private String cssClass;

    /**
     * @param cssClass value used for this action.
     */
    DAMCreateActionType(final String cssClass) {
      this.cssClass = cssClass;
    }

    /**
     * @return the associated rel value for this action.
     */
    public String getRel() {
      return null;
    }

    public String getCssClass() {
      return cssClass;
    }
  }
}
