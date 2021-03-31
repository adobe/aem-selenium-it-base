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

package com.adobe.cq.testing.selenium.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClient;
import org.apache.sling.testing.clients.SlingHttpResponse;
import org.apache.sling.testing.clients.util.FormEntityBuilder;
import org.apache.sling.testing.clients.util.ResourceUtil;
import org.apache.sling.testing.clients.util.URLParameterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class Coverage {

    private static final Logger LOG = LoggerFactory.getLogger(Coverage.class);

    private static final String DEFAULT_COVERAGE_CONFIG = "coverage.config";

    private boolean jsCoverAvailable;
    private String config;
    private SlingClient adminClient;
    private String configName = DEFAULT_COVERAGE_CONFIG;
    private boolean configPresent;
    private boolean autoClean;

    public Coverage(final SlingClient client) {
        adminClient = client;
        try {
            config = ResourceUtil.readResourceAsString(configName);
            configPresent = true;
            SlingHttpResponse response = adminClient.doGet("/apps/granite/testing/clientlibs/jscover.js");
            jsCoverAvailable = response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
        } catch (IOException | ClientException e) {
            LOG.error("Cannot initialize", e);
        }
    }

    /**
     * enables or disables the autoclean mode.
     *
     * @param withAutoClean true autoclean mode enabled
     * @return SetupCoverageRule
     */
    public Coverage withAutoClean(final boolean withAutoClean) {
        autoClean = withAutoClean;
        return this;
    }

    public boolean isJSCoverAvailable() {
        return jsCoverAvailable;
    }

    public void invalidateLibraryCaches() throws ClientException {
        FormEntityBuilder entityBuilder = FormEntityBuilder.create();
        entityBuilder.addParameter("invalidate", "true");
        adminClient.doPost("/libs/granite/ui/content/dumplibs.rebuild.html", entityBuilder.build());
    }

    public void cleanCoverageJSON() throws ClientException {
        FormEntityBuilder entityBuilder = FormEntityBuilder.create();
        entityBuilder.addParameter("clear", "1");
        adminClient.doPost("/bin/jscover/store", entityBuilder.build());
    }

    public void setupService() throws ClientException {
        if (autoClean) {
            cleanCoverageJSON();
        }
        if (StringUtils.isNotBlank(config)) {
            SlingHttpResponse response = adminClient.doGet(
            "/apps/system/config/com.adobe.granite.ui.clientlibs.jscover.impl.JSCoverInstrumentationServiceImpl.config"
            );
            FormEntityBuilder entityBuilder = FormEntityBuilder.create();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
                entityBuilder.addParameter("jcr:primaryType", "nt:file");
                entityBuilder.addParameter("jcr:content/jcr:primaryType", "nt:resource");
            }
            entityBuilder.addParameter("jcr:content/jcr:data", config);
            entityBuilder.addParameter("jcr:content/jcr:data@TypeHint", "Binary");
            entityBuilder.addParameter("jcr:content/jcr:lastModifiedBy", "");
            entityBuilder.addParameter("jcr:content/jcr:lastModified", "");
            adminClient.doPost(
            "/apps/system/config/com.adobe.granite.ui.clientlibs.jscover.impl.JSCoverInstrumentationServiceImpl.config",
                entityBuilder.build());
            LOG.info("Coverage setup done");
        }
    }

    public void generateExport() throws ClientException {
        if (isConfigPresent() && isJSCoverAvailable()) {
            URLParameterBuilder parameterBuilder = URLParameterBuilder.create()
                    .add("overwrite", Boolean.toString(!autoClean));
            adminClient.doGet("/bin/jscover/export", parameterBuilder.getList(), HttpStatus.SC_OK);
            LOG.info("Coverage exported");
        }
    }

    public void storeCoverage(final String coverageData) throws ClientException {
        FormEntityBuilder entityBuilder = FormEntityBuilder.create();
        entityBuilder.addParameter("data", coverageData);
        adminClient.doPost("/bin/jscover/store", entityBuilder.build());
        LOG.info("Coverage stored");
    }

    public boolean isConfigPresent() {
        return configPresent;
    }


    public void tearDown() {
        if (adminClient != null && isConfigPresent() && isJSCoverAvailable()) {
            try {
                generateExport();
            } catch (ClientException e) {
                throw new AssertionError("Coverage Export Failed due to " + e.getMessage());
            }
        }
    }

    public void setup() {

        if (adminClient != null && isConfigPresent() && isJSCoverAvailable()) {
            // invalidate previously cached clientlibs
            try {
                invalidateLibraryCaches();
                setupService();
            } catch (ClientException e) {
                throw new AssertionError("Coverage Setup Failed due to " + e.getMessage());
            }
        }

    }

}
