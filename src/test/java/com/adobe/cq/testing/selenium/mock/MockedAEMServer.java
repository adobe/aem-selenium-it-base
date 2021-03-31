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

package com.adobe.cq.testing.selenium.mock;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.client.MockServerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public final class MockedAEMServer implements BeforeAllCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockedAEMServer.class);

    private static HashMap<Integer, MockedAEMServer> instances = new HashMap();

    private MockServerClient client;

    private int port;

    private MockedAEMServer(final int pt) {
        this.port = pt;
        this.client = startClientAndServer(port);
    }

    public static MockedAEMServer getInstance(final int port) {
        MockedAEMServer mockedAEMServer = instances.get(port);
        if (mockedAEMServer == null) {
            mockedAEMServer = new MockedAEMServer(port);
            instances.put(port, mockedAEMServer);
        }
        return mockedAEMServer;
    }

    public void beforeAll(final ExtensionContext ec) {
        LOGGER.info("Before ALL");
        client.reset();
        initDefaultExpectations();
    }

    public MockServerClient getClient() {
        return client;
    }

    public void mockGetFileResponse(final String path, final String filePath) throws IOException {
        client.when(
                request()
                        .withMethod("GET")
                        .withPath(path)
        ).respond(
                response()
                        .withBody(IOUtils.toString(MockedAEMServer.class.getResourceAsStream(filePath), StandardCharsets.UTF_8))
                        .withStatusCode(HttpStatus.SC_OK)
        );
    }

    public void mockGetStringResponse(final String path, final String body) {
        client.when(
                request()
                        .withMethod("GET")
                        .withPath(path)
        ).respond(
                response()
                        .withBody(body)
                        .withStatusCode(HttpStatus.SC_OK)
        );
    }

    public void mockPostStringResponse(final String path, final String body) {
        client.when(
                request()
                        .withMethod("POST")
                        .withPath(path)
        ).respond(
                response()
                        .withBody(body)
                        .withStatusCode(HttpStatus.SC_OK)
        );
    }

    public void mockMethodRedirectResponse(final String method, final String path, final String redirectTo) {
        client.when(
                request()
                        .withMethod(method)
                        .withPath(path)
        ).respond(
                response()
                        .withHeader("Location", redirectTo)
                        .withStatusCode(HttpStatus.SC_MOVED_TEMPORARILY)
        );
    }

    private void initDefaultExpectations() {
        try {
            mockMethodRedirectResponse("POST", "/libs/granite/core/content/login.html/j_security_check", "/aem/start.html");
            mockMethodRedirectResponse("GET", "/", "/libs/granite/core/content/login.html?resource=");
            mockMethodRedirectResponse("GET", "/sites.html", "/sites.html/content");
            mockGetStringResponse("/libs/cq/security/userinfo.json", "{ \"home\" : \"/home/users/dummy\"}");
            mockGetStringResponse("/home/users/dummy/preferences", "{}");
            mockGetFileResponse("/libs/granite/core/content/login.html", "/mocked_login_page.html");
            mockGetFileResponse("/etc.clientlibs/clientlibs/mocked_coral.js", "/mocked_clientlib.js");
            mockGetFileResponse("/aem/start.html", "/mocked_start_page.html");
            mockGetFileResponse("/another_start_page.html", "/mocked_start_page.html");
            mockGetFileResponse("/sites.html/content", "/mocked_sites.html");
            mockGetFileResponse("/editor.html/content/some/page.html", "/mocked_editorpage.html");
            mockGetFileResponse("/etc.clientlibs/clientlibs/mocked_editorpage.js", "/mocked_editorpage_clientlib.js");
            mockGetStringResponse("/apps/granite/testing/clientlibs/jscover.js", "OK");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
