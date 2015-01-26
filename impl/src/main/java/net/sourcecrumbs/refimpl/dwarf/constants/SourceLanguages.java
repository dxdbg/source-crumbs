/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.constants;

import org.codehaus.preon.annotation.BoundEnumOption;

import net.sourcecrumbs.api.transunit.SourceLanguage;

/**
 * Enumeration for SourceLanguages referenced by a DW_AT_language AttributeValue
 *
 * @author mcnulty
 */
public enum SourceLanguages
{
    @BoundEnumOption(0x0001)
    DW_LANG_C89,

    @BoundEnumOption(0x0002)
    DW_LANG_C,

    @BoundEnumOption(0x0003)
    DW_LANG_Ada83,

    @BoundEnumOption(0x0004)
    DW_LANG_C_plus_plus,

    @BoundEnumOption(0x0005)
    DW_LANG_Cobol74,

    @BoundEnumOption(0x0006)
    DW_LANG_Cobol85,

    @BoundEnumOption(0x0007)
    DW_LANG_Fortran77,

    @BoundEnumOption(0x0008)
    DW_LANG_Fortran90,

    @BoundEnumOption(0x0009)
    DW_LANG_Pascal83,

    @BoundEnumOption(0x000a)
    DW_LANG_Modula2,

    @BoundEnumOption(0x000b)
    DW_LANG_Java,

    @BoundEnumOption(0x000c)
    DW_LANG_C99,

    @BoundEnumOption(0x000d)
    DW_LANG_Ada95,

    @BoundEnumOption(0x000e)
    DW_LANG_Fortran95,

    @BoundEnumOption(0x000f)
    DW_LANG_PLI,

    @BoundEnumOption(0x0010)
    DW_LANG_ObjC,

    @BoundEnumOption(0x0011)
    DW_LANG_ObjC_plus_plus,

    @BoundEnumOption(0x0012)
    DW_LANG_UPC,

    @BoundEnumOption(0x0013)
    DW_LANG_D,

    @BoundEnumOption(0x0014)
    DW_LANG_Python,

    @BoundEnumOption(0x8000)
    DW_LANG_lo_user,

    @BoundEnumOption(0xffff)
    DW_LANG_hi_user,

    UNKNOWN;

    public SourceLanguage getSourceLanguage()
    {
        switch(this) {
            case DW_LANG_C:
            case DW_LANG_C99:
            case DW_LANG_C89:
                return SourceLanguage.C;
            case DW_LANG_C_plus_plus:
                return SourceLanguage.CXX;
            default:
                return SourceLanguage.UNKNOWN;
        }
    }
}
