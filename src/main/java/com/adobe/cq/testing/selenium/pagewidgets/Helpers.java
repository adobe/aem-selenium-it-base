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

package com.adobe.cq.testing.selenium.pagewidgets;

import com.adobe.cq.testing.client.CQClient;
import com.adobe.cq.testing.selenium.pagewidgets.common.ActionComponent;
import com.adobe.cq.testing.selenium.pagewidgets.common.BaseComponent;
import com.adobe.cq.testing.selenium.pagewidgets.coral.Dialog;
import com.codeborne.selenide.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Cookie.Builder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.adobe.cq.testing.selenium.Constants.DEFAULT_WAIT_TIME;
import static com.adobe.cq.testing.selenium.utils.ElementUtils.clickableClick;
import static com.codeborne.selenide.Selenide.*;

public final class Helpers {

  private static final Logger LOG = LoggerFactory.getLogger(Helpers.class);
  private static final String JS_NETWORK_IDLE = ""
    + "let resources = performance.getEntriesByType(\"resource\");"
    + "if (resources.find(e => { return e.responseEnd <= 0 })) {"
    + " return -1;"
    + "} else {"
    + " return resources.filter(e => { return e.name.indexOf(\"/pulse.data.json\") < 0 && e.name.indexOf(\"/token.json\") < 0; }).length;"
    + "}";
  private static final String JS_METRICS_IDLE = "return performance.getEntriesByName(arguments[0]).length";
  private static final String JS_FCP = "return performance.getEntriesByType(\"paint\").length";
  private static final String CK_AFFINITY = "affinity";

  private Helpers() {

  }

  /**
   * Waits until alert is visible.
   *
   * @param timeout - timeout in milliseconds.
   * @return true if it has an alert.
   */
  public static boolean waitForAlert(final int timeout) {
    boolean hasAlert = false;
    try {
      Wait().withTimeout(Duration.ofMillis(timeout))
          .until(webDriver -> webDriver.switchTo().alert().getText() != null);
      hasAlert = true;
    } catch (Exception e) {
      // ignore exceptions
    }
    return hasAlert;
  }

  /**
   * Waits until animation for the element is finished.
   *
   * @param selector The element sector
   */
  public static void waitForElementAnimationFinished(final String selector) {
    waitForElementAnimationFinished($(selector).shouldBe(Condition.visible));
  }

  /**
   * @param target the element that is targeted.
   */
  public static void waitForElementAnimationFinished(final SelenideElement target) {
    // @todo Externalize  Animation finished should be used for both hide/show events.

    // Wait until animation is finished
    final AtomicReference<Point> atomicPoint = new AtomicReference(new Point(0, 0));
    Wait().withTimeout(Duration.ofMillis(DEFAULT_WAIT_TIME)).until(webDriver -> {
      Point currentLocation = target.getLocation();
      Point previousLocation = atomicPoint.getAndSet(currentLocation);
      return previousLocation.getX() == currentLocation.getX()
          && previousLocation.getY() == currentLocation.getY();
    });
  }

  /**
   * @return list of urls current opened in the controlled browser.
   */
  public static List<String> listOpenURLs() {
    final WebDriver webDriver = WebDriverRunner.getWebDriver();
    final String currentWindowHandle = webDriver.getWindowHandle();
    final Set<String> windowHandles = webDriver.getWindowHandles();
    final ArrayList<String> listURLs = new ArrayList();
    windowHandles.forEach(h -> {
        webDriver.switchTo().window(h);
        listURLs.add(webDriver.getCurrentUrl());
    });
    webDriver.switchTo().window(currentWindowHandle);
    return Collections.unmodifiableList(listURLs);
  }

  public static void assertHasOpenedURL(final String url) {
    Optional<String> any = Helpers.listOpenURLs().stream().filter(u -> u.contains(url)).findAny();
    assert any.isPresent() : "Expected " + url + " to be opened";
  }

  /**
   * @param expectedUrl the pattern to find in the url.
   * @return true if switchTo the window with the given URL matcher happened.
   */
  public static boolean switchToURL(final String expectedUrl) {
    final WebDriver webDriver = WebDriverRunner.getWebDriver();
    final String currentWindowHandle = webDriver.getWindowHandle();
    final Set<String> windowHandles = webDriver.getWindowHandles();
    boolean found = false;
    for (String h : windowHandles) {
      webDriver.switchTo().window(h);
      found = webDriver.getCurrentUrl().contains(expectedUrl);
      if (found) {
        break;
      }
    }
    if (!found) {
      webDriver.switchTo().window(currentWindowHandle);
    }
    return found;
  }

  /**
   * Waits till a coral-dialog is open.
   */
  public static void waitForOpen() {
    waitForElementAnimationFinished("coral-dialog[open]");
  }

  /**
   * Clicks on a button based on its label.
   *
   * @param buttonLabel The label of the button
   */
  public static void clickActionButton(final String buttonLabel) {
    final String btnSelector = "coral-dialog[open] coral-dialog-footer";
    $(btnSelector).$("button*=" + buttonLabel).shouldBe(Condition.visible, Condition.enabled)
        .click();
  }

  /**
   * Clicks on a button for which it's expected a dialog is opened and returns the dialog when opened.
   *
   * @param button The button to click on and the dialog is expected to open
   * @param dialog The dialog that should be opened by button click
   * @return The provided dialog when it was opened by button click
   */
  public static <T extends Dialog> T clickDialogAction(SelenideElement button, T dialog) {
    clickableClick(button);
    dialog.waitVisible();
    return (T) dialog;
  }

  /**
   * Clicks (by execution ActionComponent#perform) on a ActionComponent (representing a button) for which it's expected
   * a dialog is opened and returns the dialog when opened.
   *
   * @param button The ActionComponent representing the button to click on and the dialog is expected to open
   * @param <T> The dialog class extending Dialog that is expected to by click to the button
   * @return The dialog provided for the ActionComponent instantiation when it was opened by button click
   */
  public static <T extends Dialog> T clickDialogAction(ActionComponent<T> button) {
    return clickBaseComponentAction(button);
  }

  /**
   * Clicks (by execution ActionComponent#perform) on a ActionComponent (representing i.e. a button) for which it's expected
   * an object extending BaseComponent is returned and returns the object when visible.
   *
   * @param actionComponent The ActionComponent representing the component (i.e. button) to click on and the returning component is expected to be returned
   * @param <T> The component class extending BaseComponent that is expected by click to the action component
   * @return The component provided for the ActionComponent instantiation when it was opened by action component click
   */
  public static <T extends BaseComponent> T clickBaseComponentAction(ActionComponent<T> actionComponent) {
    T returnComponent = actionComponent.perform();
    returnComponent.waitVisible();
    return returnComponent;
  }

  /**
   * Waits for the change of the size of a list, i.e. to get an indication if a filter result list was updated already
   *
   * @param originalSize: The size of the original list
   * @param list: The list to be checked for size change
   * @param timeout: The timeout to wait for
   * @return if the size of the list was changed within the timeout
   */
  public static boolean waitForListSizeChange(int originalSize, ElementsCollection list, final int timeout) {
    boolean listSizeChanged = false;
    try {
      Wait().withTimeout(Duration.ofMillis(timeout))
              .until(webDriver -> originalSize != list.size());
      listSizeChanged = true;
    } catch (Exception e) {
      // ignore exceptions
    }
    return listSizeChanged;
  }

  /**
   * Drad and drop an element to another element
   *
   * @param dragElement: The SelenideElement to be dragged
   * @param targetElement: The SelenideElement the dragElement should be dropped
   */
  public static void dragOnPage(final SelenideElement dragElement, SelenideElement targetElement) {
    dragElement.shouldBe(Condition.visible);
    targetElement.shouldBe(Condition.visible);
    actions()
            .dragAndDrop(dragElement, targetElement)
            .perform();
  }

  /**
   * This method will evaluate if the element is top most on it's position (top,left) An element
   * could be behind another element with another z-index and webdriver.io isVisible() will return
   * true anyway With that helper method it's possible to identify if the element is visible to the
   * user It will not consider partial overlays, the check is done at the top,left position.
   *
   * @param selector - CSS selector for the element to check
   * @return true if element is top most else false. If element is not existing false
   */
  public static boolean isElementTopMost(final String selector) {
    final String elementTopMostJS = ""
        + "        let selector = arguments[0];\n"
        + "        // Execute on browser the detection\n"
        + "        // eslint-disable-next-line no-undef\n"
        + "        let element = document.querySelector(selector);\n"
        + "        if (!element) {\n"
        + "            return false;\n"
        + "        }\n"
        + "        let clientRect = element.getBoundingClientRect();\n"
        + "        let elementX = clientRect.left;\n"
        + "        let elementY = clientRect.top;\n"
        + "        // eslint-disable-next-line no-undef\n"
        + "        let visibleElementAtPosition = document.elementFromPoint(elementX, elementY);\n"
        + "        return element === visibleElementAtPosition;\n";
    return Selenide.executeJavaScript(elementTopMostJS, selector);
  }


  private static boolean assertFCP() {
    return (long) Selenide.executeJavaScript(JS_FCP) == 2;
  }

  /**
   * Await until the window.performance metrics contains the First Content Paint.
   */
  public static void waitFirstContentPaint() {
    Wait().until(webdriver -> assertFCP());
  }

  private static boolean assertNetworkIdled(final long pollingInterval) {
    long initialCount = Selenide.executeJavaScript(JS_NETWORK_IDLE);
    boolean asserted = false;
    if (initialCount >= 0) {
      Selenide.sleep(pollingInterval);
      long afterCount = Selenide.executeJavaScript(JS_NETWORK_IDLE);
      if (afterCount >= 0) {
        asserted = initialCount == afterCount;
      } else {
        LOG.info("Network busy#2");
      }
    } else {
      LOG.info("Network busy#1");
    }
    return asserted;
  }

  private static boolean assertMetricsIdled(final long pollingInterval, final String name) {
    long initialCount = Selenide.executeJavaScript(JS_METRICS_IDLE, name);
    Selenide.sleep(pollingInterval);
    long afterCount = Selenide.executeJavaScript(JS_METRICS_IDLE, name);
    return initialCount == afterCount;
  }

  private static boolean assertDOMIdled(final long pollingInterval) {
    String previousHTML = WebDriverRunner.source();
    Selenide.sleep(pollingInterval);
    boolean same = WebDriverRunner.source().equals(previousHTML);
    if (!same) {
      LOG.info("DOM Changed detected !");
    }
    return same;
  }

  /**
   * Await that all open network connections are completed within consecutive polling interval.
   * @param pollingInterval polling interval for idle detection.
   */
  public static void waitNetworkIdled(final long pollingInterval) {
    LOG.info("waitNetworkIdled with polling interval={}ms", pollingInterval);
    Wait().until(webdriver -> assertNetworkIdled(pollingInterval));
  }
  
  /**
   * Await that the document load is fully completed.
   */
  public static void waitDocumentLoadCompleted() {
	LOG.info("waitDocumentLoadComleted");
	Wait().until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
  }

  /**
   * Await that page source (DOM) isn't changed within consecutive polling interval.
   * @param pollingInterval polling interval for idle detection.
   */
  public static void waitDOMIdled(final long pollingInterval) {
    LOG.info("waitDOMIdled with polling interval={}ms", pollingInterval);
    Wait().until(webdriver -> assertDOMIdled(pollingInterval));
  }

  /**
   * Await that number of window.performance metrics related name changed within consecutive polling interval.
   * @param pollingInterval polling interval for idle detection.
   * @param name marker name
   */
  public static void waitMetricsIdled(final long pollingInterval, final String name) {
    Wait().until(webdriver -> assertMetricsIdled(pollingInterval, name));
  }

  /**
   * @param client force to set the affinity cookie on the browser to match the client one.
   */
  public static void setAffinityCookie(final CQClient client) {
    final org.apache.http.cookie.Cookie affinity = client.getCookieStore().getCookies().stream()
        .filter(cookie -> cookie.getName().equals(CK_AFFINITY)).findFirst().orElse(null);
    if (affinity != null) {
      final Cookie existingCookie = WebDriverRunner.getWebDriver().manage()
          .getCookieNamed(CK_AFFINITY);
      if (existingCookie != null && !existingCookie.getValue().equals(affinity.getValue())) {
        LOG.info("Client affinity cookie and browser cookie have different value, synching ...");
        Cookie newCookie = new Builder(CK_AFFINITY, affinity.getValue())
            .domain(existingCookie.getDomain())
            .expiresOn(existingCookie.getExpiry())
            .isHttpOnly(existingCookie.isHttpOnly())
            .isSecure(existingCookie.isSecure())
            .path(existingCookie.getPath())
            .build();
        WebDriverRunner.getWebDriver().manage().deleteCookieNamed(CK_AFFINITY);
        WebDriverRunner.getWebDriver().manage().addCookie(newCookie);
        LOG.info("Setting browser affinity cookie with value {}", newCookie.getValue());
      }
    }
  }

  public static Cookie getCookie(String cookieName) { return WebDriverRunner.getWebDriver().manage().getCookieNamed(cookieName); }

  public static JsonNode getCookieValue(String cookieName) {
    JsonNode result = MissingNode.getInstance();
    Cookie cookie = getCookie(cookieName);
    if (cookie != null) {
      try {
        String value = cookie.getValue();
        String decodedValue = new URLCodec().decode(value);
        result = new ObjectMapper().readTree(decodedValue);
      } catch (DecoderException | IOException e) {
        LOG.error("Error while getting cookie value: " + e.getMessage());
      }
    }
    return result;
  }

}
