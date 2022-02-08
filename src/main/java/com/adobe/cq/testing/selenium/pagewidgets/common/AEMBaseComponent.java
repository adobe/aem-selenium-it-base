/*************************************************************************
 * ADOBE CONFIDENTIAL
 * ___________________
 *
 * Copyright 2021 Adobe
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
package com.adobe.cq.testing.selenium.pagewidgets.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.adobe.cq.testing.selenium.pagewidgets.coral.CoralReady.waitCoralReady;
import static com.codeborne.selenide.Selenide.$;

/**
 * Base component class for inheritance.
 */
public class AEMBaseComponent extends BaseComponent {

    private static final Logger LOG = LoggerFactory.getLogger(AEMBaseComponent.class);

    private static final String TRACKINGELEMENT_ATTRIBUTE = "trackingelement";
    private static final String TRACKING_EVENT_ATTRIBUTE = "data-foundation-tracking-event";
    private static final String COLLECTION_ACTION_ATTRIBUTE = "data-foundation-collection-action";

    private static final SelenideElement UI_MASK = $("div.foundation-ui-mask");

    /**
     * @param selector The full CSS selector that leads to the HTML element.
     */
    public AEMBaseComponent(final String selector) {
        super(selector);
    }

    /**
     * @param element selenide element to associate.
     */
    public AEMBaseComponent(final SelenideElement element) {
        super(element);
    }

    /**
     * Wait that the foundation UI mask exist.
     */
    public void waitPageMasked() {
        UI_MASK.should(Condition.exist);
    }

    /**
     * Wait that the foundation UI mask doesn't exist.
     */
    public void waitPageUnmasked() {
        UI_MASK.shouldNot(Condition.exist);
    }

    /**
     * wait ready (coral wize).
     *
     * @param <T> concrete type
     * @return component of concrete type
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public <T extends BaseComponent> T waitReady() {
        waitCoralReady(cssSelector);
        return (T) this;
    }

    /**
     * @return the "trackingelement" attribute value if present, null if not.
     */
    public String getTrackingElement() {
        String trackingelement = element().getAttribute(TRACKINGELEMENT_ATTRIBUTE);
        if (StringUtils.isBlank(trackingelement)) {
            // try getting data-foundation-tracking-event
            String trackingEvent = element().getAttribute(TRACKING_EVENT_ATTRIBUTE);
            if (StringUtils.isNotBlank(trackingEvent)) {
                JsonNode jsonNode = null;
                try {
                    jsonNode = new ObjectMapper().readTree(trackingEvent);
                } catch (IOException e) {
                    LOG.warn("Not a json string", e);
                }
                if (jsonNode != null) {
                    trackingelement = jsonNode.get("element").asText();
                }
            }
        }
        return trackingelement;
    }

    /**
     * @return the "data-foundation-collection-action" attribute value if present, null if not.
     */
    public String getAction() {
        return element().getAttribute(COLLECTION_ACTION_ATTRIBUTE);
    }
}
