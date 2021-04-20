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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

/**
 * {@link ResponseFilter} that intercepts and downloads files.
 *
 * Inspired from https://github.com/selenide/selenide/issues/196#issuecomment-168674086
 */
public class FileDownloader implements ResponseFilter {

    /**
     * Default download path for temporarily downloaded files
     */
    public static final String CONFIG_DEFAULT_DOWNLOAD_PATH = "build/reports/test";
    private static final Logger logger = LoggerFactory.getLogger(FileDownloader.class);

    private Set<String> contentTypes = new HashSet<>();
    private File tempDir = new File(CONFIG_DEFAULT_DOWNLOAD_PATH);
    private boolean deleteTempFiles = false;

    /**
     * Adds a new {@code Content-Type} header value that should be intercepted.
     *
     * @param contentType The {@code Content-Type} header value that should be intercepted
     * @return Self, for fluent method calls
     */
    public FileDownloader addContentType(String contentType) {
        contentTypes.add(contentType);
        return this;
    }

    /**
     * Sets if temporarily downloaded files should be deleted on process exit.
     *
     * @param deleteTempFiles {@code true} if temporary files should be deleted, {@code false} otherwise
     * @return Self, for fluent method calls
     */
    public FileDownloader deleteTempFiles(boolean deleteTempFiles) {
        this.deleteTempFiles = deleteTempFiles;
        return this;
    }

    /**
     * Sets the desired temporary files download path.
     *
     * @param path The desired download path
     * @return Self, for fluent method calls
     */
    public FileDownloader setDownloadPath(String path) {
        this.tempDir = new File(path);
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        return this;
    }

    /**
     * Creates a {@link FileDownloader} that intercepts specified {@code Content-Type}
     *
     * @param contentType The {@code Content-Type} to be intercepted
     * @return Self, for fluent method calls
     */
    public static FileDownloader withContent(String contentType)
    {
        return new FileDownloader().addContentType(contentType);
    }

    /**
     * Creates a {@link FileDownloader} that intercepts specified {@code Content-Type}s.
     *
     * @param contentType The {@code Content-Type}s to be intercepted
     * @return Self, for fluent method calls
     */
    public static FileDownloader withContents(String ... contentType)
    {
        FileDownloader downloader = new FileDownloader();
        for (int i=0; i < contentType.length; i++)
        {
            downloader.addContentType(contentType[i]);
        }
        return downloader;
    }

    /**
     * Filtering method that intercepts and downloads the files.
     *
     * @see ResponseFilter#filterResponse(HttpResponse, HttpMessageContents, HttpMessageInfo)
     */
    @Override
    public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        if (tempDir == null) {
            setDownloadPath(CONFIG_DEFAULT_DOWNLOAD_PATH);
        }
        String contentType = response.headers().get("Content-Type");
        if (contentTypes.contains(contentType)) {

            logger.info("Filter download for " + messageInfo.getOriginalRequest().uri());
            String tempPath = null;
            try {
                String postfix = contentType.substring(contentType.indexOf('/') + 1);
                File tempFile = File.createTempFile("downloaded", "." + postfix, tempDir);
                tempPath = tempFile == null ? "" : tempFile.getAbsolutePath();
                if (deleteTempFiles) {
                    tempFile.deleteOnExit();
                }
                FileOutputStream outputStream = new FileOutputStream(tempFile);
                outputStream.write(contents.getBinaryContents());
                outputStream.close();
                logger.info("Saved file to " + tempPath);
                contents.setTextContents(tempPath);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                response.headers().remove("Content-Type");
                response.headers().remove("Content-Encoding");
                response.headers().remove("Content-Disposition");
                response.headers().add("Content-Type", "text/html");
                response.headers().add("Content-Length", "" + tempPath.length());
            }
        }
    }
}