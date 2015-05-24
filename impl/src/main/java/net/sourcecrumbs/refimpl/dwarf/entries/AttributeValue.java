/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.google.common.primitives.UnsignedBytes;

import net.sourcecrumbs.refimpl.dwarf.constants.AttributeForm;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.entries.expr.DwarfExpression;
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


    public DIE getReferencedDie(CompilationUnit relativeUnit)
    {
        return getReferencedDie(relativeUnit, ByteOrder.nativeOrder());
    }

    public DIE getReferencedDie(CompilationUnit relativeUnit, ByteOrder byteOrder)
    {
        long offset;
        switch (form) {
            case DW_FORM_ref1:
            case DW_FORM_ref2:
            case DW_FORM_ref4:
            case DW_FORM_ref8:
                offset = getDataAsLong(byteOrder);
                break;
            default:
                return null;
        }

        return relativeUnit.getRootDIE().findDieByOffset(offset);
    }

    public DwarfExpression getExpression(ByteOrder byteOrder)
    {
        switch(form) {
            case DW_FORM_exprloc:
                break;
            default:
                break;
        }

        return null;
    }
}
