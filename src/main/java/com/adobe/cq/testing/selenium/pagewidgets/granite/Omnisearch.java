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

package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pagewidgets.Helpers;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.concurrent.TimeoutException;

import static com.adobe.cq.testing.selenium.Constants.DEFAULT_WAIT_TIME;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.isElementTopMost;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.adobe.cq.testing.selenium.pagewidgets.I18N.geti18nString;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickUntil;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

/**
 * The Omnisearch component wrapper.
 */
public final class Omnisearch extends AEMBaseComponent {

    private static final String BUTTON_GRANITE_OMNISEARCH_TYPEAHEAD_CLOSE = "button.granite-omnisearch-typeahead-close";
    private static final String DIV_GRANITE_OMNISEARCH_TYPEAHEAD = "div.granite-omnisearch-typeahead";

    private final String actionBarItemPrefix;

    /**
     * Default constructor.
     */
    public Omnisearch() {
        super("granite-omnisearch-overlay");
        actionBarItemPrefix = ".granite-omnisearch-overlay coral-actionbar coral-actionbar-item ";
    }

    /**
     * Get the searchField element.
     *
     * @return searchField.
     */
    public SelenideElement searchField() {
        return $("input.granite-omnisearch-typeahead-input").should(Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Get the resultContent element.
     *
     * @return resultContent.
     */
    public SelenideElement resultContent() {
        return $("#granite-omnisearch-result").should(Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Get the resultMultiRows element.
     *
     * @return multiple rows elements.
     */
    public ElementsCollection resultMultiRows() {
        ElementsCollection collection = $$("#granite-omnisearch-result .granite-omnisearch-multiresult-row");
        collection.forEach(item -> item.should(Constants.EXISTS_ENABLED_VISIBLE));
        return collection;
    }

    /**
     * Get all simple search results without workflows.
     *
     * @return multiple item elements.
     */
    public ElementsCollection allSimpleSearchResults() {
        $("#granite-omnisearch-result").should(Constants.EXISTS_ENABLED_VISIBLE);
        return $$("coral-masonry[id='granite-omnisearch-result'] > coral-masonry-item");
    }

    /**
     * Open the omnisearch integration.
     */
    public void open() {
        if (Helpers.isUnifiedShellFrame()) {
            switchTo().defaultContent();
            clickableClick($(".spectrum-Tool.spectrum-Tool--quiet"));
            Helpers.switchToAemContentFrame();
        } else {
            clickableClick($("#granite-omnisearch-trigger"));
        }
        $(BUTTON_GRANITE_OMNISEARCH_TYPEAHEAD_CLOSE).should(Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Open the omnisearch integration via a call of the search url directly.
     */
    public void openViaSearchUrl() {
        Selenide.open("/aem/search.html");
        waitForElementAnimationFinished(DIV_GRANITE_OMNISEARCH_TYPEAHEAD);
    }

    /**
     * Close the omnisearch integration.
     */
    public void close() {
        waitForElementAnimationFinished(DIV_GRANITE_OMNISEARCH_TYPEAHEAD);

        waitForElementAnimationFinished(BUTTON_GRANITE_OMNISEARCH_TYPEAHEAD_CLOSE);
        $(BUTTON_GRANITE_OMNISEARCH_TYPEAHEAD_CLOSE).should(Condition.visible).click();
        $(BUTTON_GRANITE_OMNISEARCH_TYPEAHEAD_CLOSE).shouldNot(Condition.visible);
    }

    /**
     * Returns if omnisearch is visible to the user
     * If its open but not visible(e.g. overlayed with another element) this is false.
     * @return true if omnisearch is visible to the user.
     */
    @Override
    public boolean isVisible() {
        return isElementTopMost(DIV_GRANITE_OMNISEARCH_TYPEAHEAD);
    }

    /**
     * Toggle the filter.
     *
     * @throws TimeoutException
     */
    public void toggleFilter() throws TimeoutException {
        final String selector = "#granite-omnisearch-result-rail-toggle-button";
        final String  railPanelSelector = "coral-overlay div[id='granite-omnisearch-result-rail']";
        if ($(railPanelSelector).is(Condition.visible)) {
            // Use normal click if already opened
            $(selector).should(Constants.EXISTS_ENABLED_VISIBLE).click();
        } else {
            // Use click until if not yet opened
            clickUntil($(selector), $(railPanelSelector), Condition.visible, 5, 1000);
        }
        waitForElementAnimationFinished($(railPanelSelector));
    }

    /**
     * Search in omnisearch for a specific search text.
     *
     * @param searchText - The text for searching.
     */
    public void search(final String searchText) {
        searchField().setValue(searchText + "\uE007");
        $("#granite-omnisearch-result-content").should(Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Open a desired search result item in the workflow multi result.
     * @param rowNumber Result row, start index from 1
     * @param itemNumber Item in row, start index from 1
     */
    public void openWorkflowSearchResult(final int rowNumber, final int itemNumber) {
        final String selector = "#granite-omnisearch-result "
            + ".granite-omnisearch-multiresult-row:nth-child(" + rowNumber + ")";
        SelenideElement row = $(selector).should(Constants.EXISTS_ENABLED_VISIBLE);
        row
            .$("coral-masonry coral-masonry-item:nth-child(" + itemNumber + ")")
            .should(Constants.EXISTS_ENABLED_VISIBLE)
            .click();
    }

    /**
     * Open a desired search result item in the simple search result.
     *
     * @param itemNumber - Item to select
     */
    public void openSimpleSearchResult(final int itemNumber) {
        final String selector = "coral-masonry[id='granite-omnisearch-result'] > "
            + "coral-masonry-item:nth-child(" + itemNumber + ")";
        $(selector).should(Constants.EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * Open the first asset found in the search results.
     */
    public void openFirstAssetFromResults() {
        final String selector = "#granite-omnisearch-result coral-card[data-item-type=\"asset\"]";
        $$(selector)
            .shouldHave(CollectionCondition.sizeGreaterThan(0))
            .get(0)
            .should(Constants.EXISTS_ENABLED_VISIBLE).click();
    }


    /**
     * Open the first launch found in the search results.
     *
     * @return get first omnisearch result
     */
    public SelenideElement getFirstLaunchFromResults() {
        final String selector = "#granite-omnisearch-result .card-cq-launches";
        return $$(selector)
                .shouldHave(CollectionCondition.sizeGreaterThan(0))
                .get(0);
    }

    /**
     * Display suggestions for a specific search text.
     *
     * @param searchText The text to get the suggestions.
     */
    public void showSuggestions(final String searchText) {
        searchField().setValue(searchText);
        $("coral-overlay[target='.granite-omnisearch-typeahead']").should(
            Constants.EXISTS_ENABLED_VISIBLE);
        // On fresh instances of AEM the suggestion list is not completely ready after showing the overlay above
        Selenide.sleep(DEFAULT_WAIT_TIME);
    }

    /**
     * Select a predicate from the suggestion list.
     *
     * @param predicateValue - Predicate with value to select
     */
    public void selectPredicate(final String predicateValue) {
        SelenideElement predicate = $("button[data-granite-omnisearch-typeahead-suggestion-value='" + predicateValue + "']");
        predicate.should(Constants.EXISTS_ENABLED_VISIBLE).click();
        $("coral-overlay.granite-omnisearch-typeahead-overlay").shouldNot(Condition.visible);
    }

    /**
     * Get all tags inside the taglist.
     *
     * @return array of coral-tag
     */
    ElementsCollection getAllTagsFromTagList() {
        return $$(".granite-omnisearch-typeahead-tags coral-tag");
    }

    /**
     * Open the omnisearch integration bu clicking the filter toggle option.
     */
    public void openFromFilter() {
        final String i18nFilter = geti18nString("Filter");
        SelenideElement railToggle = $("#shell-collectionpage-rail-toggle");
        railToggle.should(Constants.EXISTS_ENABLED_VISIBLE).click();
        final String query = String.format("//coral-selectlist-item[contains(text(), '%s')]", i18nFilter);
        railToggle.find(By.xpath(query)).should(Constants.EXISTS_ENABLED_VISIBLE).click();
    }

    /**
     * Get the element representing the action bar item.
     *
     * @param selector The css selector used to distinguish the desired action bar item from the other items.
     *      This selector will be appended to a prefix selector that identifies the parent element of all the Omnisearch
     *      action bar items.
     * @return the actionbar item element
     */
    public SelenideElement getActionBarItem(final String selector) {
        return $(actionBarItemPrefix + selector);
    }

    /**
     * Get the input element for the path predicate from the left rail.
     *
     * @param name The name of the predicate.
     * @return the predicate input element
     */
    public SelenideElement getPredicateInput(final String name) {
        final String selector = String.format(".path-predicate foundation-autocomplete[name=\"%s\"] input[is=\"coral-textfield\"]", name);
        return $(selector).should(Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
     * Get the tag element for the path predicate from the tag list.
     *
     * @param name - The name of the predicate.
     * @return the predicate tag element.
     */
    public SelenideElement getPredicateTag(final String name) {
        return $("coral-taglist coral-tag[name='" + name + "']").should(
            Constants.EXISTS_ENABLED_VISIBLE);
    }

    /**
    * Get total count of search Result.
    *
    * @return return total count of search result.
    */
    public int getSearchResultCount() {
        return Integer.parseInt($("granite-pagingstatus.granite-collection-pagingstatus").should(
            Constants.EXISTS_ENABLED_VISIBLE)
            .getAttribute("guesstotal"));
    }
}
