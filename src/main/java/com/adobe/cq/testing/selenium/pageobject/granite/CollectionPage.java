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

package com.adobe.cq.testing.selenium.pageobject.granite;

import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralActionBar;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralCycleButton;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralQuickActions;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;
import com.adobe.cq.testing.selenium.pagewidgets.cq.EnumRail;
import com.adobe.cq.testing.selenium.pagewidgets.granite.*;
import com.adobe.cq.testing.selenium.pagewidgets.granite.columnview.ColumnView;

import java.util.EnumMap;

import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitForElementAnimationFinished;
import static com.adobe.cq.testing.selenium.pagewidgets.Helpers.waitNetworkIdled;
import static com.adobe.cq.testing.selenium.pagewidgets.I18N.geti18nString;
import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;

public class CollectionPage extends ShellPage {

    private static final String BASE_COLLECTION_SELECTOR = ".foundation-collection";
    private static final CoralCycleButton RAIL_CYCLE_BUTTON = new CoralCycleButton("[id=\"shell-collectionpage-rail-toggle\"]");
    private static final long NETWORK_IDLE_INTERVAL = 250;

    private final CoralActionBar actionbarElement;
    private final CoralQuickActions quickactions;
    private EnumMap<ViewType, Collection> viewIconToWrapperClassMap = new EnumMap<>(ViewType.class);
    private Collection currentCollectionInstance;

    /**
     * @param path of the collection page to open the view on it's collection.
     */
    public CollectionPage(final String path) {
        super(null, path);
        viewIconToWrapperClassMap.put(ViewType.CARD, new Masonry(BASE_COLLECTION_SELECTOR));
        viewIconToWrapperClassMap.put(ViewType.LIST, new Table(BASE_COLLECTION_SELECTOR));
        viewIconToWrapperClassMap.put(ViewType.COLUMN, new ColumnView(BASE_COLLECTION_SELECTOR));
        actionbarElement = new CoralActionBar();
        quickactions = new CoralQuickActions();
    }

    /**
     * Access to the collection inside the Sites Page.
     *
     * @return CollectionComponent.
     */
    public Collection collection() {
        // @todo Determine if this selection is needed [data-foundation-collection-id='${collectionId}']
        if (currentCollectionInstance == null) {
            throw new IllegalStateException("Please call switchToView(ViewType) first");
        }
        return currentCollectionInstance;
    }

    /**
     * Access to the left rail.
     *
     * @return RailComponent.
     */
    public Rail leftRail() {
        return new Rail();
    }

    /**
     * Access the rail toggle cycle button.
     *
     * @return CycleButtonComponent.
     */
    public CoralCycleButton railToggle() {
        return RAIL_CYCLE_BUTTON;
    }

    /**
     * Access the collection page breadcrumbs used to control the navigation inside this collection page.
     *
     * @return the breadcrumbs component or null if the selector for the underlying
     * collection was not yet set
     */
    public Breadcrumbs breadcrumbs() {
        return new Breadcrumbs();
    }

    /**
     * @return the actionbar object.
     */
    public CoralActionBar actionBar() {
        return actionbarElement;
    }

    /**
     * @return quickactions object.
     */
    public CoralQuickActions quickActions() {
        return quickactions;
    }

    /**
     * Opens the rail by using the name of the rail.
     *
     * @param railtype - a known type of rail.
     */
    public void openRail(final EnumRail railtype) {
        // Don't open if already open
        railToggle().click();
        railToggle().selectList().selectByLabel(geti18nString(railtype.getI18nTitle()));
        if (railtype.getPanelName() != null) {
            // we need to wait for the panel to transition.
            waitForElementAnimationFinished(leftRail().getCssSelector());
        }
    }

    private void switchToView(final ViewType viewType, final Collection viewCollection) {
        final String collectionSelector = viewCollection.getCssSelector();

        boolean viewChanged = new CollectionSwitcher().switchToView(viewType);
        if (viewChanged) {
            waitNetworkIdled(NETWORK_IDLE_INTERVAL);
            waitCoralReady(collectionSelector);
        }

        currentCollectionInstance = viewCollection;
    }

    /**
     * @param viewType any from supported view (currently viewCard, viewTable, and viewList)
     * @return self
     */
    public CollectionPage switchToView(final ViewType viewType) {
        Collection viewCollection = viewIconToWrapperClassMap.get(viewType);

        if (viewCollection != null) {
            switchToView(viewType, viewCollection);
        } else {
            throw new UnsupportedOperationException("Unsupported view");
        }
        return this;
    }

    /**
     * @param viewType the view type to use as current view
     */
    public void setCurrentView(final ViewType viewType) {
        currentCollectionInstance = viewIconToWrapperClassMap.get(viewType);
    }

    /**
     * Determines if the collection pages has a rail item in the rail toggle.
     *
     * @param name - the name of the item
     * @return true weather or not the item exists in the rail toggle
     */
    public boolean hasRailItem(final String name) {
        final CoralSelectList selectList = railToggle().selectList();
        return selectList.hasItemBySelector(String.format(" [data-granite-toggleable-control-name='%s']", name));
    }

}
