/*
 * Copyright (c) 2011-2013, Dan McNulty
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *      * Neither the name of the UDI project nor the
 *        names of its contributors may be used to endorse or promote products
 *        derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *  ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS AND CONTRIBUTORS BE
 *  LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *  CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
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
