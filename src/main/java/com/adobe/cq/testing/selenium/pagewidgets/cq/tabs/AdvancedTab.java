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

import com.adobe.cq.testing.selenium.pagewidgets.DesignPicker;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralMultiField;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelect;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.cq.FormField;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.DESIGN_PATH;
import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.SLING_ALIAS;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

public final class AdvancedTab extends AEMBaseComponent {
    private SelenideElement designFieldButton = $(DESIGN_PATH + " button");
    private String languageSelect = "./jcr:language";
    public static final FormField authenticationRequired = new FormField("./cq:authenticationRequired");
    private String advanceConfigInheritance = ".cq-cloudconfig-configpathbrowser coral-checkbox.inheritance";

    /**
     * Construct a wrapper on AdvancedTab panel content.
     * @param panelId the associated element id.
     */
    public AdvancedTab(final String panelId) {
        super("#" + panelId);
    }

    /**
     * opens the design picker
     * @return DesignPicker
     */
    public DesignPicker openDesignPicker() {
        clickableClick(designFieldButton);
        DesignPicker designPicker = new DesignPicker();
        designPicker.getDesignPickerMainElement().shouldBe(Condition.exist, Condition.visible);
        return designPicker;
    }

    /**
     * select the language
     * @param language value e.g. en, sq, en_au
     */
    public void selectLanguage(String language) {
        $( "[name='" + languageSelect + "'] > button").click();
        CoralSelectList coralSelectList = new CoralSelectList($("[name='" + languageSelect + "']"));
        if(!coralSelectList.isVisible()) {
            CoralSelect selectList = new CoralSelect("name='" + languageSelect + "'");
            coralSelectList = selectList.openSelectList();
        }

        final WebDriver webDriver = WebDriverRunner.getWebDriver();
        WebElement element = webDriver.findElement(By.cssSelector("coral-selectlist-item[value='"+language+"']"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
        coralSelectList.selectByValue(language);
    }

    /**
     * @return the sling alias input element.
     */
    public SelenideElement slingAlias() {
        return SLING_ALIAS.getFullyDecoratedElement("input");
    }

    /**
     * Get the selected language
     * @return the selected language
     */
    public String getLanguageSelected(){
        return $("[name='" + this.languageSelect + "']").$("[handle='label']").getText();
    }

    /**
     * @return multifield to define allowed templates
     */
    public CoralMultiField allowedTemplates() {
        return new CoralMultiField("./cq:allowedTemplates");
    }

    /**
     * @return the checkbox for authentication required.
     */
    public CoralCheckbox authenticationRequired() {
        return new CoralCheckbox("coral-checkbox" + authenticationRequired);
    }

    public CoralCheckbox advanceConfigInheritance() {
        return new CoralCheckbox(advanceConfigInheritance);
    }

}
