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

package com.adobe.cq.testing.selenium.junit.extensions;

import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.openqa.selenium.Proxy;

import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;

import static com.adobe.cq.testing.selenium.utils.Network.proxyURL;
import static net.lightbody.bmp.proxy.CaptureType.REQUEST_CONTENT;
import static net.lightbody.bmp.proxy.CaptureType.REQUEST_COOKIES;
import static net.lightbody.bmp.proxy.CaptureType.RESPONSE_CONTENT;
import static net.lightbody.bmp.proxy.CaptureType.RESPONSE_COOKIES;

public class BrowserProxyExtension implements ParameterResolver, BeforeTestExecutionCallback,
    AfterTestExecutionCallback {

  private static BrowserMobProxyServer proxy;
  private static Proxy seleniumProxy;
  private static int proxyPort = Integer.parseInt(System.getProperty("useProxyPort", "0"));

  static {
    if (proxyPort > 0) {
      proxy = new BrowserMobProxyServer();
      proxy.setTrustAllServers(true);
      proxy.enableHarCaptureTypes(REQUEST_CONTENT, RESPONSE_CONTENT, REQUEST_COOKIES, RESPONSE_COOKIES);
      proxy.start(proxyPort);
      seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
      String proxyURL = proxyURL(proxy);
      seleniumProxy.setHttpProxy(proxyURL);
      seleniumProxy.setSslProxy(proxyURL);
    }
  }

  @Override
  public void afterTestExecution(final ExtensionContext extensionContext) throws Exception {
    if (isEnabled()) {
      final Har har = proxy.getHar();
      har.writeTo(FileUtils.getFile(extensionContext.getTestMethod().get().getName() + ".har"));
      proxy.endHar();
    }
  }

  @Override
  public void beforeTestExecution(final ExtensionContext extensionContext) throws Exception {
    if (isEnabled()) {
      proxy.newHar("Capture " + extensionContext.getTestMethod().get().getName());
    }
  }

  public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return isOfType(parameterContext, Proxy.class, BrowserMobProxyServer.class);
  }

  private boolean isOfType(final ParameterContext context, final Class... clazz) {
    return Arrays.stream(clazz).anyMatch(p -> p.isAssignableFrom(context.getParameter().getType()));
  }

  public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
    return isOfType(parameterContext, Proxy.class) ? seleniumProxy : proxy;
  }

  public boolean isEnabled() {
    return proxyPort > 0;
  }

  public Proxy getSeleniumProxy() {
    return seleniumProxy;
  }
}
