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

package com.adobe.cq.testing.selenium.pagewidgets.cq.tabs;

import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public final class PermissionsTab extends AEMBaseComponent {

  private static final SelenideElement ADD_PERMISSION = $("button.js-cq-sites-AddPermissionsDialog-show");
  private static final SelenideElement EDIT_CUG = $("button.js-cq-sites-ClosedUserGroupDialog-show");
  private static final SelenideElement EFFECTIVE_PERMISSIONS = $("button.js-cq-sites-EffectivePermissionsDialog-show ");
  private static final ElementsCollection CUG_ITEMS = $$("table.js-cq-sites-ClosedUserGroup-permissions tr.js-cq-sites-PermissionsProperties-row");
  private static final ElementsCollection PERMISSIONS_ITEMS = $$("table.js-cq-sites-UserGroup-permissions tr.js-cq-sites-PermissionsProperties-row");
  private static final String EDIT_PERMISSION = "button.js-cq-sites-PermissionsProperties-edit";
  private static final String DELEETE_PERMISSION = "button.js-cq-sites-PermissionsProperties-delete";

  /**
   * Construct a wrapper on PermissionsTab panel content.
   * @param panelId the associated element id.
   */
  public PermissionsTab(final String panelId) {
    super("#" + panelId);
  }

  /**
   * @return the add permission button element.
   */
  public SelenideElement getAddPermission() {
    return ADD_PERMISSION;
  }

  /**
   * @return the edit cug button element.
   */
  public SelenideElement getEditCUG() {
    return EDIT_CUG;
  }

  /**
   * Click on add permission button.
   * @return the permission dialog.
   */
  public AddPermissionsDialog addPermission() {
    clickableClick(ADD_PERMISSION);
    return new AddPermissionsDialog();
  }

  /**
   * Click on edit cug button.
   * @return the edit cug dialog.
   */
  public EditCUGDialog editCUG() {
    clickableClick(EDIT_CUG);
    return new EditCUGDialog();
  }

  /**
   * @return the list of elements for current CUG list.
   */
  public ElementsCollection cugList() {
    return CUG_ITEMS;
  }

  /**
   * @return the list of elements for current permissions list.
   */
  public ElementsCollection permissionsList() {
    return PERMISSIONS_ITEMS;
  }

  /**
   * Checks if permission to read is granted to a user in permission list
   * @param userName user name
   * @return true if read permission is granted to user
   */
  public boolean isReadPermissionGranted(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    if(!userRow.isDisplayed())
      return false;
    return userRow.$$("td").get(1).$("[icon='check']").isDisplayed();
  }

  /**
   * Checks if permission to modify is granted to a user in permission list
   * @param userName  user name
   * @return true if modify permission is granted to user
   */
  public boolean isModifyPermissionGranted(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    if(!userRow.isDisplayed())
      return false;
    return userRow.$$("td").get(2).$("[icon='check']").isDisplayed();
  }

  /**
   * Checks if permission to delete is granted to a user in permission list
   * @param userName user name
   * @return true if delete permission is granted to user
   */
  public boolean isDeletePermissionGranted(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    if(!userRow.isDisplayed())
      return false;
    return userRow.$$("td").get(3).$("[icon='check']").isDisplayed();
  }

  /**
   * Checks if permission to replicate is granted to a user in permission list
   * @param userName user name
   * @return true if replicate permission is granted to user
   */
  public boolean isReplicatePermissionGranted(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    if(!userRow.isDisplayed())
      return false;
    return userRow.$$("td").get(4).$("[icon='check']").isDisplayed();
  }

  /**
   * Checks if permission to create is granted to a user in permission list
   * @param userName user name
   * @return true if create permission is granted to user
   */
  public boolean isCreatePermissionGranted(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    if(!userRow.isDisplayed())
      return false;
    return userRow.$$("td").get(5).$("[icon='check']").isDisplayed();
  }

  /**
   * OpensEditPermission Dialog for existing user
   * @param userName
   *
   * @return EditPermissionDialog
   */
  public EditPermissionDialog editPermission(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    userRow.find(EDIT_PERMISSION).click();
    return new EditPermissionDialog();
  }

  /**
   * Delete the permission for a user
   * @param userName
   */
  public void deleteUserPermission(String userName) {
    SelenideElement userRow = PERMISSIONS_ITEMS.filter(Condition.matchText(userName)).first();
    userRow.find(DELEETE_PERMISSION).click();
    new Dialog().clickWarning();
  }

  /**
   *
   * @return effective permission dialog
   */
  public EffectivePermissionDialog openEffectivePermissions() {
      EFFECTIVE_PERMISSIONS.click();
      return new EffectivePermissionDialog();
  }

  public class EditCUGDialog extends Dialog {

    private final AutoCompleteField autoCompleteField = new AutoCompleteField("css:.js-cq-sites-CUGPermissionsDialog-authorizableList");

    /**
     * Construct the associated CUG Dialog.
     */
    public EditCUGDialog() {
      super("coral-dialog.js-cq-sites-CUGPermissionsDialog");
    }

    /**
     * @return the autocomplete field for finding cug authorizable.
     */
    public AutoCompleteField cugFinder() {
      return autoCompleteField;
    }

  }

  public class EditPermissionDialog extends Dialog {

    private final CoralCheckbox cbRead;
    private final CoralCheckbox cbCreate;
    private final CoralCheckbox cbModify;
    private final CoralCheckbox cbDelete;
    private final CoralCheckbox cbReplicate;

    /**
     * Construct the associated CUG Dialog.
     */
    public EditPermissionDialog() {
      super("coral-dialog.js-cq-sites-EditPermissionsDialog");

      cbRead = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"read\"]");
      cbCreate = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"create\"]");
      cbModify = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"modify\"]");
      cbDelete = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"delete\"]");
      cbReplicate = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"replicate\"]");
    }

    /**
     * @return the read checkbox.
     */
    public CoralCheckbox read() {
      return cbRead;
    }

    /**
     * @return the create checkbox.
     */
    public CoralCheckbox create() {
      return cbCreate;
    }

    /**
     * @return the modify checkbox.
     */
    public CoralCheckbox modify() {
      return cbModify;
    }

    /**
     * @return the delete checkbox.
     */
    public CoralCheckbox delete() {
      return cbDelete;
    }

    /**
     * @return the replicate checkbox.
     */
    public CoralCheckbox replicate() {
      return cbReplicate;
    }

  }

  public class AddPermissionsDialog extends Dialog {

    private final AutoCompleteField autoCompleteField = new AutoCompleteField("css:.js-cq-sites-CreatePermissionsDialog-authorizableList");

    private final CoralCheckbox cbRead;
    private final CoralCheckbox cbCreate;
    private final CoralCheckbox cbModify;
    private final CoralCheckbox cbDelete;
    private final CoralCheckbox cbReplicate;

    /**
     * Construct the associated CUG Dialog.
     */
    public AddPermissionsDialog() {
      super("coral-dialog.js-cq-sites-CreatePermissionsDialog");

      cbRead = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"read\"]");
      cbCreate = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"create\"]");
      cbModify = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"modify\"]");
      cbDelete = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"delete\"]");
      cbReplicate = new CoralCheckbox(getCssSelector() + " coral-checkbox[name=\"replicate\"]");
    }

    /**
     * @return the autocomplete field for the authorizable list.
     */
    public AutoCompleteField authorizableList() {
      return autoCompleteField;
    }

    /**
     * @return the read checkbox.
     */
    public CoralCheckbox read() {
      return cbRead;
    }

    /**
     * @return the create checkbox.
     */
    public CoralCheckbox create() {
      return cbCreate;
    }

    /**
     * @return the modify checkbox.
     */
    public CoralCheckbox modify() {
      return cbModify;
    }

    /**
     * @return the delete checkbox.
     */
    public CoralCheckbox delete() {
      return cbDelete;
    }

    /**
     * @return the replicate checkbox.
     */
    public CoralCheckbox replicate() {
      return cbReplicate;
    }

  }

  public class EffectivePermissionDialog extends Dialog {
    public EffectivePermissionDialog() {
      super("coral-dialog.js-cq-sites-EffectivePermissionsDialog");
    }

    public void close() {
      $(getCssSelector() + " button[handle='closeButton']").click();
    }
  }

}
