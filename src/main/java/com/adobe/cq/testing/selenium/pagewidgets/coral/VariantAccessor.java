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

package com.adobe.cq.testing.selenium.pagewidgets.coral;

import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;

public class VariantAccessor extends BaseComponent {

    public static final String VARIANT_ATTRIBUTE = "variant";

    public VariantAccessor(SelenideElement element) {
        super(element);
    }

    /**
     * @param variantType the variant attribute value.
     * @return the first related variant element.
     */
    public SelenideElement find(final VariantType variantType) {
        return findAll(variantType).first();
    }

    /**
     * @param variantType the variant attribute value.
     * @param tag tag on which should be variant attribute.
     * @return the related variant elements.
     */
    public ElementsCollection findAll(final VariantType variantType, String tag) {
        final SelenideElement currentElement = element();
        return currentElement.findAll(tag + "[" + VARIANT_ATTRIBUTE + "=\"" + variantType.toString() + "\"]");
    }

    /**
     * @param variantType the variant attribute value.
     * @return the related variant elements.
     */
    public ElementsCollection findAll(final VariantType variantType) {
        return findAll(variantType, "");
    }

    public void clickVariant(final VariantType variantType) {
        ElementsCollection variantElements = findAll(variantType);
        SelenideElement clickableVariantElement = variantElements.stream()
                .filter(selenideElement -> selenideElement.getTagName().equals("button") ||
                        selenideElement.getTagName().equals("a"))
                .findFirst().get();
        clickableClick(clickableVariantElement
                .shouldBe(Condition.visible, Condition.enabled));
    }

    public boolean isVariant(final VariantType variantType) {
        return element().has(Condition.attribute(VARIANT_ATTRIBUTE, variantType.toString()));
    }

    public enum VariantType {
        PRIMARY,
        SECONDARY,
        DEFAULT,
        MINIMAL,
        SUCCESS,
        ERROR,
        QUIETACTION,
        _CUSTOM,
        WARNING;

        public String toString() {
            return name().toLowerCase();
        }
    }

}
