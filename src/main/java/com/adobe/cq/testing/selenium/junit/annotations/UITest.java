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

import com.adobe.cq.testing.selenium.junit.extensions.JSCoverageExtension;
import com.adobe.cq.testing.selenium.junit.extensions.UITestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

/**
 * {@code @UITest} is an extension for AEM UI Test purpose mainly
 * <p>
 * It mainly allow to resolve URI parameters in Tests. URI which are retrieved from latest SlingClientContext.
 * It also watch for failure and output browser console logs to the junit log output.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(UITestExtension.class)
@ExtendWith(JSCoverageExtension.class)
@Inherited
public @interface UITest {
}
