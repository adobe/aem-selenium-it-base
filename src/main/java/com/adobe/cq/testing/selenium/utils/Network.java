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

import net.lightbody.bmp.BrowserMobProxy;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;

public final class Network {

    private static final String USE_IP_PROPERTY = "useIP";

    private static final Logger LOG = LoggerFactory.getLogger(Network.class);

    private Network() {

    }

    /**
     * @param originalURI to be rewritten if localhost mainly
     * @return rewritten for localhost, untouched otherwise
     * @throws SocketException    exception
     * @throws URISyntaxException exception
     */
    public static URI getRebasedURL(final URI originalURI) throws SocketException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(originalURI);
        String host = originalURI.getHost();
        if ("localhost".equals(host) || host.indexOf('.') < 0) {
            String firstLocalIP = System.getProperty(USE_IP_PROPERTY, getFirstLocalIP());
            if (firstLocalIP != null) {
                uriBuilder.setHost(firstLocalIP);
            }
        }
        uriBuilder.setPath(StringUtils.removeEnd(originalURI.getPath(), "/"));
        return uriBuilder.build();
    }

    /**
     * @param proxy the source proxy to get the url
     * @return a url that docker can access.
     */
    public static String proxyURL(final BrowserMobProxy proxy) {
        String hostName = null;
        try {
            hostName = getRebasedURL(new URI("http://" + InetAddress.getLocalHost())).getHost();
            hostName = hostName + ":" + proxy.getPort();
        } catch (SocketException | UnknownHostException | URISyntaxException e) {
            LOG.error("Issue trying to compute proxy url", e);
        }
        return hostName;
    }

    private static String getFirstLocalIP() throws SocketException {
        String firstIP = null;
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (firstIP == null && networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (firstIP == null && inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress instanceof Inet4Address) {
                    String hostAddress = inetAddress.getHostAddress();
                    if (hostAddress.startsWith("192.168.")) {
                        firstIP = hostAddress;
                    }
                }
            }
        }
        return firstIP;
    }
}
