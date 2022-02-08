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
