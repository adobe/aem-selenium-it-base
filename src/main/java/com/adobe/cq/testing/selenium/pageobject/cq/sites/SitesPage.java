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

package com.adobe.cq.testing.selenium.pageobject.cq.sites;

import com.adobe.cq.testing.selenium.pageobject.granite.ShellPage;

import javax.annotation.Nonnull;

/**
 * The sites main page.
 */
public class SitesPage extends ShellPage {

    private static final String BASE_PATH = "/sites.html";

    /**
     * Default constructor.
     */
    public SitesPage() {
        super(null, BASE_PATH);
    }

    /**
     * Open custom assets page.
     * @param path - optional additional path to the default page.
     * @return SitesPage.
     */
    @Override
    public SitesPage open(@Nonnull final String path) {
        super.open(String.format("%s%s", BASE_PATH, path));
        return this;
    }

    /**
     * @return SitesPage opened on default location.
     */
    @SuppressWarnings("unchecked")
    @Override
    public SitesPage open() {
        super.open();
        return this;
    }
}
