/*
 * Copyright 2022 Adobe Systems Incorporated
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
package com.adobe.cq.testing.selenium.junit.annotations;

import com.adobe.cq.testing.selenium.junit.extensions.BrowserProxyExtension;
import com.adobe.cq.testing.selenium.junit.extensions.DisableToursExtension;
import com.adobe.cq.testing.selenium.junit.extensions.JSCoverageExtension;
import com.adobe.cq.testing.selenium.junit.extensions.WebdriverSetupExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code @AEMITBase} is an annotation to include required set of extension for AEM UI Test purpose mainly
 * <p>
 * It mainly includes the extensions to setup Webdriver and browser proxy, URI which are retrieved from latest SlingClientContext,
 * disable tours and setup JS coverage
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SlingClientContext
@ExtendWith(WebdriverSetupExtension.class)
@ExtendWith(BrowserProxyExtension.class)
@ExtendWith(DisableToursExtension.class)
@ExtendWith(JSCoverageExtension.class)
@Inherited
public @interface AEMITBase {
}
