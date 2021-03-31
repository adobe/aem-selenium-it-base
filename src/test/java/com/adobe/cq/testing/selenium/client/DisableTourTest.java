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

package com.adobe.cq.testing.selenium.client;

import com.adobe.cq.testing.selenium.mock.MockedAEMServer;
import com.adobe.cq.testing.selenium.utils.DisableTour;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.sling.testing.clients.ClientException;
import org.apache.sling.testing.clients.SlingClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockserver.model.HttpRequest;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;

import static com.adobe.cq.testing.selenium.TestConstants.DEFAULT_MOCKED_AEM_PORT;

public class DisableTourTest {

    private static final String TEST_USER_HASH_NODE = RandomStringUtils.randomAlphanumeric(32);
    private static final String TEST_USER_HOME = "/home/users/" + TEST_USER_HASH_NODE;

    @RegisterExtension
    protected static MockedAEMServer mockedAEMServer = MockedAEMServer.getInstance(DEFAULT_MOCKED_AEM_PORT);

    @BeforeAll
    public static void setupAll() {
        mockedAEMServer.mockGetStringResponse("/libs/granite/security/search/authorizables.json", getBody());
        mockedAEMServer.mockGetStringResponse(TEST_USER_HOME + ".preferences.json", getPreferences());
        mockedAEMServer.mockPostStringResponse(TEST_USER_HOME + "/preferences", "OK");
    }

    @Nonnull
    private static String getBody() {
        JsonObject jsonOutput = new JsonObject();
        JsonArray authorizables = new JsonArray();
        JsonObject admin = new JsonObject();
        admin.addProperty("userId", "test-user");
        admin.addProperty("home", TEST_USER_HOME);
        authorizables.add(admin);
        jsonOutput.add("authorizables", authorizables);
        return jsonOutput.toString();
    }

    @Nonnull
    private static String getPreferences() {
        JsonObject jsonOutput = new JsonObject();
        jsonOutput.addProperty("language", "fr");
        jsonOutput.addProperty("cq.authoring.editor.template.showTour", "false");
        return jsonOutput.toString();
    }

    @Test
    public void restoreDefaultsSucceed(final SlingClient client) throws ClientException {
        DisableTour disableTour = new DisableTour(client);
        disableTour.restoreDefaults();
        mockedAEMServer.getClient().verify(HttpRequest.request().withPath(".*/preferences").withMethod("POST"));
    }

    @Test
    public void disableDefaultToursSucceed(final SlingClient client) throws ClientException, UnsupportedEncodingException {
        DisableTour disableTour = new DisableTour(client);
        disableTour.disableDefaultTours();
        mockedAEMServer.getClient().verify(HttpRequest.request().withPath(".*/preferences").withMethod("POST"));
    }

}
