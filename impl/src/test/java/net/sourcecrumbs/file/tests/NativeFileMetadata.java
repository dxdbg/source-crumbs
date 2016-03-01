/*
 * Copyright (c) 2011-2016, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.file.tests;

import java.util.Map;

/**
 * @author mcnulty
 */
public class NativeFileMetadata
{
    private String objectSha1;

    private String executableSha1;

    private String machine;

    private String platform;

    private Map<String, String> flags;

    private String compiler;

    public String getObjectSha1()
    {
        return objectSha1;
    }

    public void setObjectSha1(String objectSha1)
    {
        this.objectSha1 = objectSha1;
    }

    public String getExecutableSha1()
    {
        return executableSha1;
    }

    public void setExecutableSha1(String executableSha1)
    {
        this.executableSha1 = executableSha1;
    }

    public String getMachine()
    {
        return machine;
    }

    public void setMachine(String machine)
    {
        this.machine = machine;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public Map<String, String> getFlags()
    {
        return flags;
    }

    public void setFlags(Map<String, String> flags)
    {
        this.flags = flags;
    }

    public String getCompiler()
    {
        return compiler;
    }

    public void setCompiler(String compiler)
    {
        this.compiler = compiler;
    }
}
