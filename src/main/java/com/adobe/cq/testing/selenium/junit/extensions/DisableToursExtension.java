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
package com.adobe.cq.testing.selenium.junit.extensions;

import com.adobe.cq.testing.selenium.utils.AnnotationHelper;
import com.adobe.cq.testing.selenium.junit.annotations.DisableTourConfig;
import com.adobe.cq.testing.selenium.utils.DisableTour;
import org.apache.sling.testing.clients.SlingClient;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import static com.adobe.cq.testing.selenium.utils.Network.getRebasedURL;

public final class DisableToursExtension implements ParameterResolver, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        return isOfType(parameterContext, URI.class);
    }

    private boolean isOfType(final ParameterContext context, final Class... clazz) {
        return Arrays.stream(clazz).anyMatch(p -> p.isAssignableFrom(context.getParameter().getType()));
    }

    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        Object returnedValue;
        SlingClient client = SlingClientExtension.Store.getInstance().recallLatest(extensionContext);
        if (client == null) {
            throw new ParameterResolutionException("No client found, make sure to request a client before it can resolve this parameter");
        }
        try {
            returnedValue = getRebasedURL(client.getUrl());
        } catch (Exception e) {
            throw new ParameterResolutionException(e.getMessage());
        }
        return returnedValue;
    }

    private String[] getTours(final ExtensionContext ec) {
        Optional<DisableTourConfig> extensionConfig = AnnotationHelper.findOptionalAnnotation(ec, DisableTourConfig.class);
        return extensionConfig.isPresent() ? extensionConfig.get().tours() : new String[0];
    }

    private boolean doIncludeDefault(final ExtensionContext ec) {
        Optional<DisableTourConfig> extensionConfig = AnnotationHelper.findOptionalAnnotation(ec, DisableTourConfig.class);
        boolean doInclude = true;
        if (extensionConfig.isPresent()) {
            doInclude = extensionConfig.get().includeDefault();
        }
        return doInclude;
    }

    public void beforeTestExecution(final ExtensionContext context) throws Exception {
        boolean includeDefaults = doIncludeDefault(context);
        String[] additionalTours = getTours(context);
        if (includeDefaults || additionalTours.length > 0) {
            SlingClientExtension.Store instance = SlingClientExtension.Store.getInstance();
            SlingClient client = instance.recallLatest(context);
            if (client != null) {
                DisableTour disableTour = new DisableTour(client);
                if (includeDefaults) {
                    disableTour.disableDefaultTours();
                }
                if (additionalTours.length > 0) {
                    disableTour.disableTours(additionalTours);
                }
                context.getStore(ExtensionContext.Namespace.GLOBAL).put(getDisableTourStoreKey(), disableTour);
            }
        }
    }

    private String getDisableTourStoreKey() {
        return Thread.currentThread().getId() + "_disabletour";
    }

    public void afterTestExecution(final ExtensionContext context) throws Exception {
        DisableTour disableTour = context.getStore(ExtensionContext.Namespace.GLOBAL)
                .get(getDisableTourStoreKey(), DisableTour.class);
        if (disableTour != null) {
            disableTour.restoreDefaults();
        }
    }
}
