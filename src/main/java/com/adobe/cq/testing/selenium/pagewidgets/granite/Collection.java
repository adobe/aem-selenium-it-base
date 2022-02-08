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

import com.adobe.cq.testing.selenium.Constants;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCheckbox;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralColumnPreview;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralColumnviewItemThumbnail;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.Wait;

/**
 * The Collection component wrapper.
 */
public class Collection extends AEMBaseComponent {

    public static final String COLLECTION_ITEM_ID_ATTRIBUTE = "data-foundation-collection-item-id";
    public static final String DEFAULT_ITEM_SELECTOR_PATTERN = "%s .foundation-collection-item[data-foundation-collection-item-id='%s']";
    public static final String DEFAULT_ALLITEMS_SELECTOR = ".foundation-collection-item";
    private static final UnsupportedOperationException UNSUPPORTED_OPERATION = new UnsupportedOperationException("Unsupported operation");

    private boolean isLazyLoadingSupported = false;
    private String hasMoreElementsSelector = "fake";
    private String lazyItemClass = "is-lazyLoaded";
    private String pendingItemClass = "is-pending";
    private String itemSelectorPattern = DEFAULT_ITEM_SELECTOR_PATTERN;
    private String allItemsSelector = DEFAULT_ALLITEMS_SELECTOR;

    /**
     * Constructor to create a CollectionComponent.
     *
     * @param selector selector for the collection
     */
    public Collection(final String selector) {
        super(selector);
    }

    /**
     * @return collection id of the current element.
     */
    public String getId() {
        return element().getAttribute("data-foundation-collection-id");
    }

    /**
     * Gets all the items that belong to the current collection.
     *
     * @return ElementsCollection the items currently loaded in the collection.
     */
    public ElementsCollection getItems() {
        final ElementsCollection list = element().$$(allItemsSelector);
        list.forEach(item -> item.shouldBe(Constants.EXISTS_ENABLED_VISIBLE));
        return list;
    }

    /**
     * Gets all the selected items that belong to the current collection.
     *
     * @return ElementsCollection the items currently loaded and selected in the collection.
     */
    public ElementsCollection getSelectedItems() {
        return element().$$(".foundation-selections-item");
    }

    /**
     * Toggle the item selection by clicking on its checkbox.
     *
     * @param collectionItemID collectionItemID - id of the item, as indicated by `data-foundation-collection-item-id`.
     * @return self
     */
    public Collection toggleSelectionItem(final String... collectionItemID) {
        for (String id : collectionItemID) {
            final SelenideElement item = getCollectionItem(id);
            toggleItem(item);
        }
        return this;
    }

    /**
     * Checks if checkbox is visible to select item.
     *
     * @param collectionItemID id of the item, as indicated by `data-foundation-collection-item-id`.
     * @return boolean
     */
    public boolean isSelectable(final String collectionItemID) {
        final SelenideElement item = getCollectionItem(collectionItemID);
        final CoralCheckbox coralCheckbox = new CoralCheckbox(item);

        return coralCheckbox.isVisible();
    }

    /**
     * @param item the element on which toggle item is called, default it click on coral-checkbox.
     * @return self
     */
    protected Collection toggleItem(final SelenideElement item) {
        new CoralCheckbox(item).click();
        return this;
    }

    /**
     * Uses the 'right' function to select element based on the provided view.
     *
     * @param collectionItemID id of the item, as indicated by `data-foundation-collection-item-id`.
     * @return self
     */
    public Collection selectItem(final String... collectionItemID) {
        for (String id : collectionItemID) {
            final SelenideElement item = getCollectionItem(id);
            final CoralCheckbox coralCheckbox = new CoralCheckbox(item);
            if (!coralCheckbox.isChecked()) {
                toggleItem(item);
            }
        }
        return this;
    }

    /**
     * Uses the 'right' function to deselect element based on the provided view.
     *
     * @param collectionItemID id of the item, as indicated by `data-foundation-collection-item-id`.
     * @return self
     */
    public Collection deselectItem(final String... collectionItemID) {
        for (String id : collectionItemID) {
            final SelenideElement item = getCollectionItem(id);
            final CoralCheckbox coralCheckbox = new CoralCheckbox(item);
            if (coralCheckbox.isChecked()) {
                toggleItem(item);
            }
        }
        return this;
    }

    /**
     * Selects the specified element based on the provided view, specific to a view
     * that renders a list of thumbnail items.
     *
     * @param collectionItemID id of the item, as indicated by `data-foundation-collection-item-id`.
     * @return self
     */
    public Collection selectThumbnailItem(final String... collectionItemID) {
        for (String id : collectionItemID) {
            final SelenideElement item = getCollectionItem(id);
            final CoralColumnviewItemThumbnail coralColumnviewItemThumbnail = new CoralColumnviewItemThumbnail(item);
            if (!coralColumnviewItemThumbnail.isSelected()) {
                coralColumnviewItemThumbnail.click();
            }
        }
        return this;
    }

    /**
     * De-selects the specified element based on the provided view, specific to a view
     * that renders a list of thumbnail items.
     *
     * @param collectionItemID id of the item, as indicated by `data-foundation-collection-item-id`.
     * @return self
     */
    public Collection deselectThumbnailItem(final String... collectionItemID) {
        for (String id : collectionItemID) {
            final SelenideElement item = getCollectionItem(id);
            final CoralColumnviewItemThumbnail coralColumnviewItemThumbnail = new CoralColumnviewItemThumbnail(item);
            if (coralColumnviewItemThumbnail.isSelected()) {
                coralColumnviewItemThumbnail.click();
            }
        }
        return this;
    }

    /**
     * Get a collection item element.
     *
     * @param collectionItemID - The id used in data-foundation-collection-item-id
     * @return the element matching the collection id.
     */
    public SelenideElement getCollectionItem(final String collectionItemID) {
        final String collectionItemSelector = getItemSelector(collectionItemID);

        final SelenideElement collectionItemElement = $(collectionItemSelector).shouldBe(
                Constants.EXISTS_ENABLED_VISIBLE);
        collectionItemElement.scrollTo();
        return collectionItemElement;
    }

    /**
     * Check if a collection item element exists.
     *
     * @param collectionItemID - The id used in data-foundation-collection-item-id
     * @return true if the item element exist.
     */
    public boolean hasCollectionItem(final String collectionItemID) {
        final String collectionItemSelector = getItemSelector(collectionItemID);
        return $(collectionItemSelector).exists();
    }

    /**
     * Select an action on a specified collection item. This method currently assumes that the collection is based on
     * the masonry.
     *
     * @param collectionItemID - The id used in `data-foundation-collection-item-id`.
     * @param actionTitle      - The action title.
     * @return self
     */
    public Collection selectActionOnCollectionItem(final String collectionItemID, final String actionTitle) {
        final String collectionItemSelector = getItemSelector(collectionItemID);

        $(collectionItemSelector).shouldBe(Constants.EXISTS_ENABLED_VISIBLE).scrollTo();

        final String jsScript = "document.querySelector(arguments[0] + \" > coral-card\").trigger(\"mouseenter\")";

        Selenide.executeJavaScript(jsScript, collectionItemSelector);

        clickableClick($(collectionItemSelector + " coral-quickactions button[aria-label='" + actionTitle + "']"));
        return this;
    }

    /**
     * Creates the CSS collectionSelector for the collection item at the specified index and that should have
     * the provided CSS class.
     *
     * @param index    - the datasource index of the element represented by the collection item.
     * @param cssClass - the expected CSS class of the collection item.
     * @return the CSS collectionSelector identifying the targeted element.
     */
    private String getSelectorForItemAtIndexHavingClass(final int index, final String cssClass) {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        return getCssSelector() + " .foundation-collection-item." + cssClass + "[data-datasource-index='" + index + "']";
    }

    /**
     * Creates the CSS collectionSelector for the collection item at the specified index.
     *
     * @param index - the datasource index of the element represented by the collection item.
     * @return the CSS collectionSelector identifying the targeted element.
     */
    private String getSelectorForItemAtIndex(final int index) {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        return getCssSelector() + " .foundation-collection-item[data-datasource-index='" + index + "']";
    }

    /**
     * Determines if there is a collection item for the datasource element with the provided index. Only supported if
     * the collection supports lazy loading.
     *
     * @param index - the datasource index of the element represented by the collection item.
     * @return true if the collection has an item for the element with that index or false otherwise.
     */
    public boolean hasItemAtIndex(final int index) {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        return $(getSelectorForItemAtIndex(index)).isDisplayed();
    }

    /**
     * Determines if there is a lazy loaded collection item for the datasource element with the provided index.
     * Lazy loaded means the collection item is just a placeholder for the real item
     *
     * @param index - the datasource index of the element represented by the collection item.
     * @return true if the collection has an item for the element with that index or false otherwise.
     */
    public boolean hasLazyItemAtIndex(final int index) {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        return $(getSelectorForItemAtIndexHavingClass(index, lazyItemClass)).isDisplayed();
    }

    /**
     * Determines if there is a pending collection item for the datasource element with the provided index.
     * Pending means the collection item is just a placeholder for the real item, but the request is made to
     * get the real data for that element.
     *
     * @param index - the datasource index of the element represented by the collection item.
     * @return true if the collection has an item for the element with that index or false otherwise.
     */
    public boolean hasPendingItemAtIndex(final int index) {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        return $(getSelectorForItemAtIndexHavingClass(index, pendingItemClass)).isDisplayed();
    }

    /**
     * Waits for the collection to finish loading and checks if the "has more elements" marker is visible or not.
     *
     * @return true if the masonry shows the "has more elements" marker or false otherwise.
     */
    public boolean hasMoreElements() {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        return $(hasMoreElementsSelector).should().isDisplayed();
    }

    /**
     * Wait until the collection item for the element with the provided index has its metadata loaded.
     * The element has its metadata loaded if the collection item is neither waiting nor pending.
     *
     * @param index - index of the item in the collection.
     * @return self
     */
    public Collection waitForLoadingItemAtIndex(final int index) {
        if (!isLazyLoadingSupported) {
            throw UNSUPPORTED_OPERATION;
        }
        Wait().until(webdriver -> hasItemAtIndex(index) && !hasLazyItemAtIndex(index) && !hasPendingItemAtIndex(index));
        return this;
    }

    /**
     * Activates an item by clicking it.
     *
     * @param itemId the id of the item to activate;
     * @return self
     */
    public Collection activate(final String itemId) {
        waitForItem(itemId, false);
        clickableClick(getCollectionItem(itemId));
        return this;
    }

    /**
     * Waits till an item with the provided id is present in the dom.
     *
     * @param itemId  the id of the item to wait for
     * @param reverse flags if we check for existing or not
     * @return self
     */
    public Collection waitForItem(final String itemId, final boolean reverse) {
        final String selector = getItemSelector(itemId);
        if (reverse) {
            $(selector).shouldNot(Condition.exist);
        } else {
            $(selector).should(Condition.exist);
        }
        return this;
    }

    /**
     * Waits for the preview of the item to exist.
     *
     * @param itemId the id of the item we wait for
     * @return self
     */
    public Collection waitForItemPreview(final String itemId) {
        $("coral-columnview-preview[data-foundation-layout-columnview-columnid='" + itemId + "']").should(
                Condition.exist);
        return this;
    }

    /**
     * @return return the column preview.
     */
    public CoralColumnPreview columnPreview() {
        return new CoralColumnPreview();
    }

    /**
     * @return true if lazy loading is supported.
     */
    public boolean isLazyLoadingSupported() {
        return isLazyLoadingSupported;
    }

    /**
     * @param lazyLoadingSupported to set support of lazy loading.
     * @return self
     */
    public Collection setLazyLoadingSupported(final boolean lazyLoadingSupported) {
        isLazyLoadingSupported = lazyLoadingSupported;
        return this;
    }

    /**
     * @return selector to find if it has more element.
     */
    public String getHasMoreElementsSelector() {
        return hasMoreElementsSelector;
    }

    /**
     * @param selector set the selector to find if it has more.
     * @return self
     */
    public Collection setHasMoreElementsSelector(final String selector) {
        hasMoreElementsSelector = selector;
        return this;
    }

    /**
     * @return the selector to find lazy loading.
     */
    public String getLazyItemClass() {
        return lazyItemClass;
    }

    /**
     * @param selector the lazy loading selector.
     * @return self
     */
    public Collection setLazyItemClass(final String selector) {
        lazyItemClass = selector;
        return this;
    }

    /**
     * @return the class used to marked pending item.
     */
    public String getPendingItemClass() {
        return pendingItemClass;
    }

    /**
     * @param cssClass set the css classname to mark pending item.
     * @return self
     */
    public Collection setPendingItemClass(final String cssClass) {
        pendingItemClass = cssClass;
        return this;
    }

    /**
     * @param collectionItemID itemId used to build this selector.
     * @return the css selector for the given itemId.
     */
    private String getItemSelector(final String collectionItemID) {
        return String.format(
                itemSelectorPattern,
                getCssSelector(),
                collectionItemID
        );
    }

    /**
     * @param itemSelectorPattern define a different way to select items.
     * @return self
     */
    public Collection setItemSelectorPattern(final String itemSelectorPattern) {
        this.itemSelectorPattern = itemSelectorPattern;
        return this;
    }

    /**
     * @param allItemsSelector define a different way to select all items.
     * @return self
     */
    public Collection setAllItemsSelector(final String allItemsSelector) {
        this.allItemsSelector = String.format(allItemsSelector, getCssSelector());
        return this;
    }

}
