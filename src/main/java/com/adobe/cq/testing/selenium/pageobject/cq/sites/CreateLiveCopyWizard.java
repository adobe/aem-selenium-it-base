package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralTagList;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.Wizard;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

public class CreateLiveCopyWizard extends Wizard {

    private static final String CREATE_WIZARD_URL = "/mnt/overlay/wcm/core/content/sites/createlivecopywizard.html";
    private static final SelenideElement TITLE_FIELD = new FormField("title").getElement();
    private static final SelenideElement NAME_FIELD = new FormField("label").getElement();
    private static final CoralCheckbox IS_EXCLUDE_SUBPAGES = new CoralCheckbox(".cq-siteadmin-admin-createlivecopy-excudesubpages input");
    private static final CoralSelect ROLLOUT_CONFIGS = new CoralSelect("name=\"cq:rolloutConfigs\"");
    private static final CoralTagList ROLLOUT_CONFIG_TAGS = new CoralTagList("name=\"cq:rolloutConfigs\"");
    private static final Dialog CONFIRM_DIALOG = new Dialog("coral-dialog");


    /**
     * @return true if the wizard is opened.
     */
    public boolean isOpened() {
        return WebDriverRunner.url().contains(CREATE_WIZARD_URL);
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
     * @return the isExcludeSubPages field.
     */
    public CoralCheckbox isExcludeSubPages() {
        return IS_EXCLUDE_SUBPAGES;
    }

    /**
     * @return the rolloutConfigs field.
     */
    public CoralSelect rolloutConfigs() {
        return ROLLOUT_CONFIGS;
    }

    /**
     * @return the rolloutConfigs field.
     */
    public CoralTagList rolloutConfigTags() {
        return ROLLOUT_CONFIG_TAGS;
    }

    /**
     * @return the confirm dialog displayed at last step.
     */
    public Dialog confirmDialog() {
        return CONFIRM_DIALOG;
    }


}
