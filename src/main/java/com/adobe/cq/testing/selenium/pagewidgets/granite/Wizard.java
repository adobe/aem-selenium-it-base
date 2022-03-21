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
package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralStepList;
import com.adobe.cq.testing.selenium.utils.ExpectNav;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

/**
 * The wizard coral component wrapper.
 */
public class Wizard extends AEMBaseComponent {

    private static final CoralStepList CORAL_STEPLIST = new CoralStepList();

    /**
     * Default constructor with the default selector.
     */
    public Wizard() {
        super(".foundation-layout-wizard2");
    }

    /**
     * Constructor with the parent element.
     *
     * @param parentElement parent element
     */
    public Wizard(SelenideElement parentElement) {
        super(parentElement.$(".foundation-layout-wizard2"));
    }

    /**
     * The button used to show the cancel of the Wizard.
     *
     * @return cancelButton element.
     */
    public SelenideElement cancelButton() {
        final String selector = "coral-panel[selected] a[data-foundation-wizard-control-action='cancel']";
        return element().$(selector).should(Condition.exist);
    }

    /**
     * The button used to show the next step of the Wizard.
     *
     * @return nextButton element.
     */
    public SelenideElement nextButton() {
        final String selector = "coral-panel[selected] button[data-foundation-wizard-control-action='next']";
        return element().$(selector).should(Condition.exist);
    }

    /**
     * The button used to show the previous step of the Wizard.
     *
     * @return previousButton element.
     */
    public SelenideElement previousButton() {
        final String selector = "coral-panel[selected] button[data-foundation-wizard-control-action='prev']";
        return element().$(selector).should(Condition.exist);
    }

    /**
     * Moves the wizard to the next step.
     */
    public void next() {
        clickableClick(nextButton());
    }

    /**
     * Moves the wizard to the previous step.
     */
    public void previous() {
        clickableClick(previousButton());
    }

    /**
     * Cancel the wizard.
     */
    public void cancel() {
        ExpectNav.on(() -> clickableClick(cancelButton()));
    }

    /**
     * @return a steplist object.
     */
    public CoralStepList stepList() {
        return CORAL_STEPLIST;
    }

    /**
     * @param uri the uri to land on the wizard implementation.
     * @param <T> the same.
     * @return itself.
     */
    @SuppressWarnings({"unchecked"})
    protected <T extends Wizard> T open(final String uri) {
        new BasePage(uri).open();
        return (T) this;
    }
}
