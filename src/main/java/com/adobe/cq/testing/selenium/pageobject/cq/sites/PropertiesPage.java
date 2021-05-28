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

package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pageobject.granite.BasePage;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralMultiField;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralPopOver;
import com.adobe.cq.testing.selenium.pagewidgets.cq.sites.PageSelector;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.Arrays;
import java.util.Optional;

import static com.adobe.cq.testing.selenium.pagewidgets.cq.FormField.*;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public final class PropertiesPage extends BasePage {

    private static final String SITE_PROPERTIES_URL = "/mnt/overlay/wcm/core/content/sites/properties.html?";
    private static final String TRACKING_ELEMENT = "\"element\":\"%s\"";

    private final SelenideElement cancelButton = $("#shell-propertiespage-closeactivator");
    private final SelenideElement saveCloseButton = $("#shell-propertiespage-doneactivator");
    private final SelenideElement saveButton = $("#shell-propertiespage-saveactivator");
    private final SelenideElement dropDownButton = $("coral-actionbar-secondary button[icon=\"chevronDown\"]");
    private final ElementsCollection coralTabs = $$("coral-tab");
    private final SelenideElement bulkForm = $(".cq-siteadmin-admin-properties-bulk");

    /**
     * @param pagePath page for which it will open the page properties editor (to support bulk mode).
     */
    public PropertiesPage(final String... pagePath) {
        super(SITE_PROPERTIES_URL + getItemParams(pagePath));
    }

    /**
     * @param pagePath single or multiple page properties.
     * @return itself.
     */
    public PropertiesPage openBulk(final String... pagePath) {
        return super.open(SITE_PROPERTIES_URL + getItemParams(pagePath));
    }

    @Override
    public void waitReady() {
        super.waitReady();
        cancelButton.should(Condition.visible);
        saveCloseButton.should(Condition.visible);
    }

    /**
     * @param pagePath pagePath to open
     * @return parameter string part of the url (item=....&item=....&etc...)
     */
    private static String getItemParams(final String... pagePath) {
        final StringBuilder sb = new StringBuilder();
        Arrays.stream(pagePath).forEach(p -> {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append("item=").append(p);
        });
        return sb.toString();
    }

    public <T extends Object> T clickTab(final String tabName, final Class<T> clazz) {
        Optional<SelenideElement> tabElement = coralTabs.stream()
                .filter(selenideElement -> selenideElement.getAttribute("data-foundation-tracking-event")
                        .contains(getTrackingElementName(tabName)))
                .findFirst();
        if (!tabElement.isPresent()) {
            throw new AssertionError("Couldn't find " + tabName);
        }
        final SelenideElement tElement = tabElement.get();
        tElement.shouldHave(Condition.attribute("aria-controls"));
        final String panelId = tElement.getAttribute("aria-controls");
        clickableClick(tElement);
        T target;
        try {
            target = clazz.getDeclaredConstructor(String.class).newInstance(panelId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Class type " + clazz.getName() + " doesn't have default constructor");
        }
        return target;
    }

    public PropertiesPage save() {
        clickableClick(dropDownButton);
        // assert a popover is opened
        CoralPopOver popOver = CoralPopOver.firstOpened();
        popOver.isVisible();

        clickableClick(saveButton);
        return this;
    }

    public PropertiesPage cancel() {
        clickableClick(cancelButton);
        return this;
    }

    public void saveAndClose() {
        clickableClick(saveCloseButton);
    }

    public SelenideElement getBulkForm() {
        return bulkForm;
    }

    public SelenideElement getSaveButton() {
        return saveButton;
    }

    public SelenideElement getSaveCloseButton() {
        return saveCloseButton;
    }

    public ElementsCollection getCoralTabs() {
        return coralTabs;
    }

    private String getTrackingElementName(final String thumbnail) {
        return String.format(TRACKING_ELEMENT, thumbnail);
    }

    /**
     * @return available tabs / fields for page properties.
     */
    public Tabs tabs() {
        return new Tabs();
    }

    public class Tabs {

        public Basic basic() {
            return new Basic();
        }

        public LiveCopy liveCopy() {
            return new LiveCopy();
        }

        public CloudServices cloudServices() {
            return new CloudServices();
        }

        public class Basic {

            /**
             * @return the title field input element.
             */
            public SelenideElement title() {
                return TITLE.getFullyDecoratedElement("input");
            }

            /**
             * @return the pageTitle field input element.
             */
            public SelenideElement pageTitle() {
                return PAGE_TITLE.getFullyDecoratedElement("input");
            }

            /**
             * @return the subtitle field input element.
             */
            public SelenideElement subtitle() {
                return SUBTITLE.getFullyDecoratedElement("input");
            }

            /**
             * @return the Navigation field input element.
             */
            public SelenideElement navTitle() {
                return NAV_TITLE.getFullyDecoratedElement("input");
            }


            /**
             * @return the title field input element.
             */
            public SelenideElement toggleTitleInheritance() {
                return new BaseComponent("a.cq-msm-property-toggle-inheritance[data-toggle-property-inheritance='jcr:title'] coral-icon").element();
            }

            /**
             * @return the description field textarea element.
             */
            public SelenideElement description() {
                return DESCRIPTION.getFullyDecoratedElement("textarea");
            }

            /**
             * @return the checkbox for hide in nav.
             */
            public CoralCheckbox hideInNav() {
                return new CoralCheckbox("coral-checkbox" + HIDE_IN_NAV);
            }

            /**
             * @return the checkbox for sling redirect.
             */
            public CoralCheckbox slingRedirect() {
                return new CoralCheckbox("coral-checkbox" + SLING_REDIRECT);
            }


            /**
             * @return multifield to define vanity paths.
             */
            public CoralMultiField vanityPath() {
                return new CoralMultiField("./sling:vanityPath");
            }

            /**
             * Check if tag is selected for the page
             * @param tag
             * @return true is tag is selected for page otherwise false
             */
            public boolean isTagPresent(String tag) {
                ElementsCollection tagList = $("coral-taglist[name='./cq:tags']").$$("coral-tag");
                boolean present = false;
                for(int i = 0; i < tagList.size(); i++) {
                    if(tagList.get(i).getText().trim().equals(tag)) {
                        present = true;
                        break;
                    }
                }

                return present;
            }

        }

        public class CloudServices {
            /**
             * @return the checkbox for cloud inheritance field.
             */
            public CoralCheckbox cloudInheritance() {
                return new CoralCheckbox("coral-checkbox.cq-CloudServices-inheritance");
            }
        }

        public class LiveCopy {

            /**
             * @return the checkbox for live copy inheritance inheritance field.
             */
            public CoralCheckbox liveCopyInheritance() {
                return new CoralCheckbox("coral-checkbox.cq-siteadmin-admin-properties-livecopy-isdeep");
            }

            /**
             * @return the checkbox for inheriting roll out configs field.
             */
            public CoralCheckbox inheritRolloutConfigs() {
                return new CoralCheckbox("coral-checkbox.cq-siteadmin-admin-properties-livecopy-inheritrolloutconfigs");
            }
        }
    }

    public PageSelector getPageSelector() {
        return new PageSelector();
    }

    public PropertiesPage expectConfirmation() {
        waitForMessage("The form has been submitted successfully");
        return this;
    }
}
