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

package com.adobe.cq.testing.selenium.junit.annotations;

import java.lang.annotation.*;

import static com.adobe.cq.testing.selenium.Constants.DEFAUT_AEM_AUTHOR_RUNMODE;

/**
 * {@code @JSCoverageConfig} is an JUnit5 Extension to setup Javascript Coverage on AEM before running test
 * and collect coverage after each test.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JSCoverageConfig {
    String coverageConfig() default "coverage.config";

    String runMode() default DEFAUT_AEM_AUTHOR_RUNMODE;
}