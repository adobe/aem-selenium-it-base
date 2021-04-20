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

package com.adobe.cq.testing.selenium.utils;

import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.actions;

/**
 * Define commons Keyboard Shortcuts which would add a small delay before doing the action.
 */
public final class KeyboardShortCuts {

  /**
   * The default delay to apply before performing the keyboard shortcut action.
   */
  public static final long PAUSE_BEFORE_KEYS = 250;

  private KeyboardShortCuts() {

  }

  /**
   * Press Arrow Left.
   */
  public static void keyLeft() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.ARROW_LEFT).perform();
  }

  /**
   * Press Arrow Right.
   */
  public static void keyRight() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.ARROW_RIGHT).perform();
  }

  /**
   * Press Arrow Up.
   */
  public static void keyUp() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.ARROW_UP).perform();
  }

  /**
   * Press Arrow Down.
   */
  public static void keyDown() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.ARROW_DOWN).perform();
  }

    /**
     * Press End.
     */
    public static void keyEnd() {
        actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.END).perform();
    }

    /**
     * Press Start.
     */
    public static void keyStart() {
        actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.HOME).perform();
    }

  /**
   * Press Shift + Arrow Left (holding Shift).
   */
  public static void keyShiftUp() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS))
        .keyDown(Keys.SHIFT).sendKeys(Keys.ARROW_UP).keyUp(Keys.SHIFT).perform();
  }

  /**
   * Press Shift + Tab (holding Shift).
   */
  public static void keyShiftTab() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS))
        .keyDown(Keys.SHIFT).sendKeys(Keys.TAB).keyUp(Keys.SHIFT).perform();
  }

  /**
   * Press Tab.
   */
  public static void keyTab() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS)).sendKeys(Keys.TAB).perform();
  }

  /**
   * Press Shift + Arrow Down (holding Shift).
   */
  public static void keyShiftDown() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS))
        .keyDown(Keys.SHIFT).sendKeys(Keys.ARROW_DOWN).keyUp(Keys.SHIFT).perform();
  }

  /**
   * Press Space.
   */
  public static void keySpace() {
    actions().pause(Duration.ofMillis(PAUSE_BEFORE_KEYS))
        .sendKeys(Keys.SPACE).perform();
  }

}
