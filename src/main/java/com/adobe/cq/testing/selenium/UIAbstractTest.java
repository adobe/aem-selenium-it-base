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

import com.adobe.cq.testing.selenium.junit.annotations.SlingClientContext;
import com.adobe.cq.testing.selenium.junit.annotations.AEMITBase;
import org.apache.commons.lang3.StringUtils;

@SlingClientContext
@AEMITBase
/**
 * @deprecated Should no longer be used, instead classes should leverage the annotation AEMITBase
 */
@Deprecated(since="3.0", forRemoval = true)
@SuppressWarnings({"java:S1610", "java:S1123", "java:S1133"})
public abstract class UIAbstractTest {

    /**
     * If a Test requires to run in a different timezone, then this method can be overridden in the TestClass.
     * Note :- This works only when test is running in docker
     * @return a string representing the selected timezone (i.e Europe/Amsterdam etc..)
     * @deprecated Please use new UserTimeZone annotation to annotate your test class or test method
     */
    public String getUserTimeZone() {
        return StringUtils.EMPTY;
    }
}