/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.dwarf.entries;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.dwarf.constants.AbbreviationTag;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeForm;
import net.sourcecrumbs.refimpl.dwarf.constants.AttributeName;
import net.sourcecrumbs.refimpl.dwarf.types.LEB128;

/**
 * A Debugging Information Entry (DIE) in a DWARF file
 *
 * @author mcnulty
 */
public class DIE
{
    private static final Logger logger = LoggerFactory.getLogger(DIE.class);

    private final long offset;

    private final DIE parent;

    private final boolean childrenPresent;

    private final List<DIE> children = new LinkedList<>();

    private final int abbreviationCode;

    private final AbbreviationTag tag;

    private final List<AttributeValue> attributeValues = new LinkedList<>();

    /**
     * Constructor.
     *
     * @param abbrevTable the abbreviation table for the associated compilation unit
     * @param buffer the buffer containing the data for the DIE
     * @param is32bitDwarf true, if the DIE is contained in a 32-bit DWARF file
     * @param offset the offset from the start of the compilation unit header to this DIE
     * @param addressSize the size of an address for the parent CompilationUnit
     * @param parent the parent of this DIE, can be null for the root DIE
     *
     * @throws UnknownFormatException when the underlying data is in an unexpected format
     */
    public DIE(AbbreviationTable abbrevTable,
            ByteBuffer buffer,
            long offset,
            boolean is32bitDwarf,
            byte addressSize,
            DIE parent) throws UnknownFormatException
    {
        this.offset = offset;

        LEB128 dieCode = new LEB128(buffer, false);
        this.abbreviationCode = (int)dieCode.getValue();

        if (abbreviationCode != 0) {
            AbbreviationDeclaration abbrevDeclaration = abbrevTable.getByCode(this.abbreviationCode);
            if (abbrevDeclaration == null) {
                throw new UnknownFormatException("Failed to locate abbreviation declaration with id " + this.abbreviationCode);
            }
            this.parent = parent;
            if (this.parent != null) {
               this.parent.getChildren().add(this);
            }

            this.childrenPresent = abbrevDeclaration.hasChildren();
            this.tag = abbrevDeclaration.getTag();

            for (AttributeSpecification spec : abbrevDeclaration.getSpecifications()) {
                byte[] valueData;
                switch(spec.getForm()) {
                    case DW_FORM_addr:
                        valueData = new byte[addressSize];
                        buffer.get(valueData);
                        break;
                    case DW_FORM_block1:
                    {
                        byte length = buffer.get();
                        valueData = new byte[length];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_block2:
                    {
                        short length = buffer.getShort();
                        valueData = new byte[length];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_block4:
                    {
                        int length = buffer.getInt();
                        valueData = new byte[length];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_exprloc:
                    case DW_FORM_block:
                    {
                        LEB128 length = new LEB128(buffer, false);
                        valueData = new byte[(int)length.getValue()];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_flag:
                    case DW_FORM_ref1:
                    case DW_FORM_data1:
                    {
                        valueData = new byte[1];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_ref2:
                    case DW_FORM_data2:
                    {
                        valueData = new byte[2];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_ref4:
                    case DW_FORM_data4:
                    {
                        valueData = new byte[4];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_ref_sig8:
                    case DW_FORM_ref8:
                    case DW_FORM_data8:
                    {
                        valueData = new byte[8];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_ref_udata:
                    case DW_FORM_udata:
                    {
                        LEB128 value = new LEB128(buffer, false);
                        buffer.position(buffer.position() - value.getLength());
                        valueData = new byte[value.getLength()];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_sdata:
                    {
                        LEB128 value = new LEB128(buffer, true);
                        buffer.position(buffer.position() - value.getLength());
                        valueData = new byte[value.getLength()];
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_flag_present:
                    {
                        valueData = new byte[0];
                        break;
                    }
                    case DW_FORM_ref_addr:
                    case DW_FORM_sec_offset:
                    case DW_FORM_strp:
                    {
                        if (is32bitDwarf) {
                            valueData = new byte[4];
                        }else{
                            valueData = new byte[8];
                        }
                        buffer.get(valueData);
                        break;
                    }
                    case DW_FORM_string:
                    {
                        List<Byte> charValues = new ArrayList<>();
                        byte charValue;
                        do {
                            charValue = buffer.get();
                            if (charValue != 0x00) {
                                charValues.add(charValue);
                            }
                        }while(charValue != 0x00);

                        valueData = new byte[charValues.size()];
                        for (int i = 0; i < charValues.size(); ++i) {
                            valueData[i] = charValues.get(i);
                        }
                        break;
                    }
                    default:
                        throw new UnknownFormatException(String.format("Failed to decode data for attribute form %s",
                                spec.getForm()));
                }

                AttributeName name = spec.getName();
                AttributeForm form = spec.getForm();
                if (name == null || form == null) {
                    logger.debug("Found unknown attribute value specification for DIE with tag {}",
                            abbrevDeclaration.getTag());
                }else {
                    attributeValues.add(new AttributeValue(name, form, valueData));
                }
            }
        }else{
            this.parent = null;
            this.childrenPresent = false;
            this.tag = AbbreviationTag.DW_TAG_null;
        }
    }

    public boolean getChildrenPresent() {
        return childrenPresent;
    }

    public int getAbbreviationCode() {
        return abbreviationCode;
    }

    public AbbreviationTag getTag() {
        return tag;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public long getOffset() {
        return offset;
    }

    public List<DIE> getChildren() {
        return children;
    }

    public DIE findDieByOffset(long offset)
    {
        // Base case: the offset matches this entry
        if (this.offset == offset) {
            return this;
        }

        for (DIE child : children) {
            DIE result = child.findDieByOffset(offset);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public static DIE buildDIETree(AbbreviationTable abbrevTable,
                                   ByteBuffer buffer,
                                   long rootOffset,
                                   boolean is32bitDwarf,
                                   byte addressSize)
        throws UnknownFormatException
    {
        DIE rootDIE = new DIE(abbrevTable, buffer, rootOffset, is32bitDwarf, addressSize, null);

        // Parse chain of siblings -- it is terminated by a DIE with an abbreviation code of 0
        LinkedList<DIE> parents = new LinkedList<>();
        parents.push(rootDIE);
        while(!parents.isEmpty()) {
            DIE currentParent = parents.pop();

            DIE currentChild;
            do {
                long childOffset = rootOffset + buffer.position();
                currentChild = new DIE(abbrevTable, buffer, childOffset, is32bitDwarf, addressSize,
                                       currentParent);
            }while(currentChild.getAbbreviationCode() != 0 && !currentChild.getChildrenPresent());

            if (currentChild.getAbbreviationCode() != 0 && currentChild.getChildrenPresent()) {
                // Start a new chain of siblings
                parents.push(currentParent);
                parents.push(currentChild);
            }
        }

        return rootDIE;
    }

    @Override
    public String toString()
    {
        return "[" + tag + "," + Long.toHexString(offset) + "]";
    }
}
