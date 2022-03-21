/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2020 Adobe
 * All Rights Reserved.
 *
 * NOTICE: All information contained herein is, and remains
 * the property of Adobe and its suppliers, if any. The intellectual
 * and technical concepts contained herein are proprietary to Adobe
 * and its suppliers and are protected by all applicable intellectual
 * property laws, including trade secret and copyright laws.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe.
 **************************************************************************/
package com.adobe.cq.testing.selenium.pagewidgets.granite;

import com.adobe.cq.testing.selenium.pageobject.granite.ViewType;
import com.adobe.cq.testing.selenium.pagewidgets.common.AEMBaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.adobe.cq.testing.selenium.pagewidgets.cq.AutoCompleteField;
import com.adobe.cq.testing.selenium.pagewidgets.granite.columnview.ColumnView;
import com.adobe.cq.testing.selenium.pagewidgets.granite.columnview.ColumnViewItem;
import org.openqa.selenium.Keys;

import java.util.EnumMap;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitNetworkIdled;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.$;

/**
 * Helper to manage the picker overlay.
 * It is displayed as a lazy loaded overlay so it inherits from OverlayComponent.
 */
public final class Picker extends Dialog {

    private Collection collection;
    private EnumMap<ViewType, Collection> viewIconToWrapperClassMap = new EnumMap<>(ViewType.class);
    private static final AutoCompleteField<AEMBaseComponent> FULL_TEXT_SEARCH = new AutoCompleteField<>("fulltext");
    private ViewType currentViewType;
    private boolean isSingleCollection;

    private static final long NETWORK_IDLE_INTERVAL = 250;
    private static final String BASE_COLLECTION_SELECTOR = ".foundation-collection";
    private static final String PICKER_CONTENT_SELECTOR = ".foundation-layout-panel-content:not([hidden]) ";

    /**
     * Constructor for the PickerOverlayComponent on coral-dialog.foundation-picker-collection.is-open.
     */
    public Picker() {
        this("coral-dialog.foundation-picker-collection.is-open");
    }

    /**
     * Constructor for the PickerOverlayComponent.
     *
     * @param selector the css selector identifying the top picker html element
     */
    public Picker(final String selector) {
        this(selector, null);
    }

    /**
     * Creates a picker that cannot switch between views. It has only one view defined in the constructor.
     *
     * @param selector query selector to attach this picker on
     * @param viewType initial ViewType
     */
    public Picker(final String selector, ViewType viewType) {
        super(selector);
        initViews();
        setViewType(viewType);
    }

    private void initViews() {
        viewIconToWrapperClassMap.put(ViewType.CARD, new Masonry(PICKER_CONTENT_SELECTOR + BASE_COLLECTION_SELECTOR));
        viewIconToWrapperClassMap.put(ViewType.LIST, new Table(PICKER_CONTENT_SELECTOR + BASE_COLLECTION_SELECTOR));
        viewIconToWrapperClassMap.put(ViewType.COLUMN, new ColumnView(PICKER_CONTENT_SELECTOR + BASE_COLLECTION_SELECTOR));
    }

    /**
     * Gets the html element associated with the provided collection item id.
     *
     * @param itemId the id of the collection item whose corresponding html element is required
     * @return the html element corresponding to the provided item id
     */
    public ColumnViewItem getItem(final String itemId) {
        if (currentViewType == ViewType.COLUMN) {
            return ((ColumnView)collection).getItem(itemId);
        }
        return null;
    }

    /**
     * Toggle selection of the html element associated with the provided collection item id.
     *
     * @param itemId the id of the collection item to whom we want to toggle select
     * @return self
     */
    public Picker toggleItem(final String itemId) {
        collection.toggleSelectionItem(itemId);
        return this;
    }

    /**
     * Activate the html element associated with the provided collection item id.
     *
     * @param itemId the id of the collection item to whom we want to activate
     * @return self
     */
    public Picker activateItem(final String itemId) {
        collection.activate(itemId);
        return this;
    }

    /**
     * Closes the picker overlay by clicking on its close button.
     *
     * @return self
     */
    public Picker close() {
        final String closeButtonCss = getCssSelector() + " coral-dialog-content"
            + " button[is='coral-button'][coral-close][variant='quiet']";
        clickableClick($(closeButtonCss));
        waitVanish();
        return this;
    }

    /**
     * Submit the picker overlay with selected Items.
     *
     * @return self
     */
    public Picker submit() {
        final String submitButtonCss = getCssSelector() + " coral-dialog-content"
            + " button.granite-pickerdialog-submit[is='coral-button']";
        clickableClick($(submitButtonCss));
        waitVanish();
        return this;
    }

    private void switchToView(final ViewType viewType, final Collection viewCollection) {
        final String collectionSelector = viewCollection.getCssSelector();

        boolean viewChanged = new CollectionSwitcher().switchToView(viewType);
        if (viewChanged) {
            waitNetworkIdled(NETWORK_IDLE_INTERVAL);
            waitCoralReady(collectionSelector);
        }

        currentViewType = viewType;
        collection = viewCollection;
    }

    /**
     * @param viewType any from supported view (currently viewCard, viewTable, and viewList)
     * @return self object
     */
    public Picker switchToView(final ViewType viewType) {
        Collection viewCollection = viewIconToWrapperClassMap.get(viewType);

        if (viewCollection != null && !isSingleCollection) {
            switchToView(viewType, viewCollection);
        } else {
            throw new UnsupportedOperationException("Unsupported view");
        }
        return this;
    }

    public Picker setViewType(final ViewType viewType) {
        isSingleCollection = true;
        currentViewType = viewType;
        collection = viewIconToWrapperClassMap.get(viewType);
        return this;
    }

    public Picker searchFulltext(String keywords) {
        FULL_TEXT_SEARCH.sendKeys(keywords, Keys.ENTER);
        return this;
    }

    public Collection collection() {
        return collection;
    }
}
