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

package com.adobe.cq.testing.selenium;

import com.adobe.cq.testing.client.CQClient;
import com.adobe.cq.testing.selenium.junit.annotations.DisableTourConfig;
import com.adobe.cq.testing.selenium.mock.MockedAEMServer;
import com.adobe.cq.testing.selenium.pageobject.PageEditorPage;
import com.adobe.cq.testing.selenium.pageobject.cq.sites.SitesPage;
import com.adobe.cq.testing.selenium.pageobject.granite.LoginPage;
import com.adobe.cq.testing.selenium.pageobject.granite.StartPage;
import com.adobe.cq.testing.selenium.pagewidgets.BettyTitleBar;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockserver.model.HttpRequest;

import java.net.URI;

import static com.adobe.cq.testing.selenium.TestConstants.DEFAULT_MOCKED_AEM_PORT;

@DisableTourConfig(includeDefault = false)
public final class UITest extends UIAbstractTest {

    @RegisterExtension
    protected static MockedAEMServer mockedAEMServer = MockedAEMServer.getInstance(DEFAULT_MOCKED_AEM_PORT);

    @BeforeEach
    public void loginBeforeEach(final CQClient client, final URI baseURI) {
        LoginPage loginPage = new LoginPage(baseURI);
        loginPage.loginAs(client.getUser(), client.getPassword());
    }

    @AfterEach
    public void afterEach() {
        verifyJSCoverageCalls();
    }

    @Test
    @DisplayName("Test default behavior should login and find 'content-page' element from default start page")
    public void assertLoggedInDefaultPage() {
        StartPage startPage = new StartPage();
        startPage.getMainContent().should(Condition.exist);
    }

    private void verifyJSCoverageCalls() {
        mockedAEMServer.getClient().verify(HttpRequest.request().withPath("/bin/jscover/store"));
    }

    @Test
    @DisplayName("BettyBarTitle present on Sites Admin Page")
    public void assertBettyBarTitlePresent() {
        SitesPage adminPage = new SitesPage();
        adminPage.open();
        new BettyTitleBar().element().shouldBe(Condition.exist);
    }

    @Test
    @DisplayName("EditorPage is ready")
    public void assertEditorPageReady() {
        PageEditorPage editorPage = new PageEditorPage("/content/some/page");
        editorPage.open();
    }
}
