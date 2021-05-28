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

import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;

public final class ThumbnailTab extends BaseComponent {
    private SelenideElement thumbnailGeneratePreviewActivator = $(".cq-wcm-pagethumbnail-activator");
    private SelenideElement thumbnailImg = $("img.cq-wcm-pagethumbnail-image");
    private SelenideElement defaultThumbnailImg = $("img.cq-wcm-pagethumbnail-image[alt='Page thumbnail']");
    private SelenideElement revert = $("button[type='reset']");

    /**
     * Construct a wrapper on ThumbnailTab panel content.
     * @param panelId the associated element id.
     */
    public ThumbnailTab(final String panelId) {
        super("#" + panelId);
    }

    public SelenideElement getThumbnailGeneratePreviewActivator() {
        return thumbnailGeneratePreviewActivator;
    }

    public SelenideElement getThumbnailImg() {
        return thumbnailImg;
    }

    public SelenideElement getRevert() {
        return revert;
    }

    public SelenideElement getDefaultThumbnailImg() {
        return defaultThumbnailImg;
    }

    public void generateThumbnailPreview() {
        thumbnailGeneratePreviewActivator.click();
        final WebDriver webDriver = WebDriverRunner.getWebDriver();
        new WebDriverWait(webDriver, Constants.DEFAUT_WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.invisibilityOf(getDefaultThumbnailImg().toWebElement()));
    }

    public void revertThumbnailPreview() {
        revert.click();
        final WebDriver webDriver = WebDriverRunner.getWebDriver();
        new WebDriverWait(webDriver, Constants.DEFAUT_WEBDRIVER_TIMEOUT)
                .until(ExpectedConditions.visibilityOf(getDefaultThumbnailImg().toWebElement()));
    }
}
