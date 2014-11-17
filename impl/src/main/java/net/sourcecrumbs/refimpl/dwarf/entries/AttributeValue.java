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

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.common.primitives.UnsignedBytes;

import net.sourcecrumbs.refimpl.dwarf.constants.AttributeForm;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.elf.spec.sections.StringTable;

/**
 * Represents an attribute value in a DIE in a DWARF file
 *
 * @author mcnulty
 */
public class AttributeValue {

    private final AttributeName name;

    private final AttributeForm form;

    private final byte[] data;

    public AttributeValue(AttributeName name, AttributeForm form, byte[] data) {
        this.name = name;
        this.form = form;
        this.data = data;
    }

    public AttributeName getName() {
        return name;
    }

    public AttributeForm getForm() {
        return form;
    }

    public byte[] getData() {
        return data;
    }

    public long getDataAsLong() {
        return getDataAsLong(ByteOrder.nativeOrder());
    }

    public long getDataAsLong(ByteOrder targetOrder) {
        long output = 0;
        int length = (data.length < 8 ? data.length : 8);
        if (targetOrder == ByteOrder.LITTLE_ENDIAN) {
            for (int i = 0; i < length; ++i) {
                output |= (data[i] << i*8);
            }
        }else{
            for (int i = length-1; i >= 0; --i) {
                output |= (data[i] << i*8);
            }
        }
        return output;
    }

    public int getDataAsInt() {
        return getDataAsInt(ByteOrder.nativeOrder());
    }

    public int getDataAsInt(ByteOrder targetOrder) {
        int output = 0;
        int length = (data.length < 4 ? data.length : 4);
        if (targetOrder == ByteOrder.LITTLE_ENDIAN) {
            for (int i = 0; i < length; ++i) {
                output |= (UnsignedBytes.toInt(data[i]) << i*8);
            }
        }else{
            for (int i = length-1; i >= 0; --i) {
                output |= (UnsignedBytes.toInt(data[i]) << i*8);
            }
        }
        return output;
    }

    public String getDataAsString() {
        return getDataAsString(StandardCharsets.UTF_8);
    }

    public String getDataAsString(StringTable table) {
        return getDataAsString(table, StandardCharsets.UTF_8);
    }

    public String getDataAsString(StringTable table, Charset charset) {
        switch (form) {
            case DW_FORM_string:
                return getDataAsString(charset);
            case DW_FORM_strp:
                return table.getString(getDataAsInt(), charset);
            default:
                break;
        }
        return "";
    }

    public String getDataAsString(Charset charset) {
        return new String(data, charset);
    }
}
