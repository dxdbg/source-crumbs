/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.After;
import org.junit.Before;

/**
 * A base test that can be used to retrieve a file from a URL that is used for the test
 *
 * @author mcnulty
 */
public abstract class BaseNativeFileTest {

    /**
     * @return the URL for the file to download
     */
    protected abstract URL getFileUrl() throws MalformedURLException;

    /**
     * Method that controls whether the file is deleted after running the test
     *
     * @return true
     */
    protected boolean deleteAfterTest() {
        return true;
    }

    protected Path filePath;

    @Before
    public void obtainFile() throws IOException {
        filePath = Files.createTempFile("source-crumbs", "");
        URL url = getFileUrl();
        Files.copy(url.openStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    @After
    public void deleteFile() throws IOException {
        if (filePath != null && deleteAfterTest()) {
            Files.delete(filePath);
        }
    }
}
