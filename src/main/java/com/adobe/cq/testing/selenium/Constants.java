
package com.adobe.cq.testing.selenium;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.Duration;

public final class Constants {

  private Constants() {
  }

  /**
   * Define a combo for element to exists, enabled and visible.
   */
  public static final Condition[] EXISTS_ENABLED_VISIBLE = new Condition[] {
      Condition.exist, Condition.visible, Condition.enabled
  };

  public static final long DEFAUT_WEBDRIVER_TIMEOUT = 30000;

  public static final int DEFAULT_WAIT_TIME = 3000;

  public static final String DEFAUT_AEM_AUTHOR_RUNMODE = "author";

  public static final int DEFAULT_CLICK_UNTIL_RETRIES = 30;


  public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.GLOBAL;

  public static final String SLING_CLIENTS_MAP_KEY = "slingClients";

  public static final String RUNMODE_AUTHOR = "author";
  public static final String RUNMODE_PUBLISH = "publish";
  public static final String DEFAULT_USER = "admin";
  public static final String DEFAULT_PASSWORD = "admin";
  public static final String DEFAULT_AUTHOR_URL = "http://localhost:4502/";
  public static final String DEFAULT_PUBLISH_URL = "http://localhost:4503/";


  public static final long DEFAULT_RETRY_DELAY = Duration.ofSeconds(1).toMillis();
  public static final long DEFAULT_TIMEOUT = Duration.ofSeconds(30).toMillis();

  public static final int DEFAULT_SMALL_SIZE = 8;

  public static final String GROUPID_CONTENT_AUTHORS = "content-authors";

  public static final String HOME_GROUPS = "/home/groups/";
  public static final String HOME_USERS = "/home/users/";
  public static final String CONTENT_ROOT = "/content";
  public static final String CONTENT_DAM = "/content/dam";
  public static final String PROP_CQ_CONF = "cq:conf";
  public static final String PROP_CQ_ALLOWED_TEMPLATES = "cq:allowedTemplates";



}
