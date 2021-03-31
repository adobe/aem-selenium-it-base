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


package com.adobe.cq.testing.selenium.pagewidgets.cq;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.CoralSelectList;

public class StylesSelector extends BaseComponent {

    private static final String CSS_SELECTOR_PAGE = "coral-popover[data-editor-styleselector-type='page']";
    private static final String CSS_SELECTOR_COMPONENT = "coral-popover[data-editor-styleselector-type='component']";

    public StylesSelector(Variant variant) {
        super(variant.getVariantCss());
    }

    public CoralSelectList getStylesSelectList() {
        return new CoralSelectList(element());
    }

    public enum Variant {
        PAGE(CSS_SELECTOR_PAGE),
        COMPONENT(CSS_SELECTOR_COMPONENT);

        private final String variantCss;

        Variant(final String css) {
            this.variantCss = css;
        }

        public String getVariantCss() {
            return variantCss;
        }
    }

}
