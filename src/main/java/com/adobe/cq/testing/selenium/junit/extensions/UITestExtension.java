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

package com.adobe.cq.testing.selenium.junit.extensions;

import com.adobe.cq.testing.selenium.junit.extensions.SlingClientExtension;
import com.adobe.cq.testing.selenium.junit.annotations.DisableTourConfig;
import com.adobe.cq.testing.selenium.utils.DisableTour;
import com.adobe.cq.testing.selenium.utils.AnnotationHelper;
import org.apache.sling.testing.clients.SlingClient;
import org.junit.jupiter.api.extension.*;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import static com.adobe.cq.testing.selenium.utils.Network.getRebasedURL;

public final class UITestExtension implements ParameterResolver, BeforeTestExecutionCallback, AfterTestExecutionCallback {

    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        return isOfType(parameterContext, URI.class);
    }

    private boolean isOfType(final ParameterContext context, final Class... clazz) {
        return Arrays.stream(clazz).anyMatch(p -> p.isAssignableFrom(context.getParameter().getType()));
    }

    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        Object returnedValue = null;
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
