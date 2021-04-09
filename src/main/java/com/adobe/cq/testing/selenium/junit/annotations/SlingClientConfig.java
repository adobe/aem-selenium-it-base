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

import com.adobe.cq.testing.selenium.Constants;

import java.lang.annotation.*;

/**
 * {@code @SlingClientConfig} is annotation to define the default configuration of a AEM
 *
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SlingClientConfig {
    int port() default 4502;
    String host() default "localhost";
    String scheme() default "http";
    String contextPath() default "/";
    String runMode() default Constants.RUNMODE_AUTHOR;
    String username() default "admin";
    String password() default "admin";
}
