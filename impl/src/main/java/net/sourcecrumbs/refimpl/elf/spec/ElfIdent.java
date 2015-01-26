/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf.spec;


import java.nio.ByteOrder;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.BoundBuffer;
import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.elf.spec.constants.DataEncoding;
import net.sourcecrumbs.refimpl.elf.spec.constants.ElfClass;

/**
 * The identification bytes for an ELF file
 *
 * @author mcnulty
 */
public class ElfIdent {

    @BoundBuffer(match = { 0x7f, 'E', 'L', 'F'})
    private byte[] fileIdentification;

    @BoundNumber(size=UnsignedChar)
    private ElfClass elfClass;

    @BoundNumber(size=UnsignedChar)
    private DataEncoding dataEncoding;

    @BoundNumber(size=UnsignedChar)
    private byte idVersion;

    @BoundList(size="9", type=byte.class)
    private byte[] padding;

    public byte[] getFileIdentification() {
        return fileIdentification;
    }

    public void setFileIdentification(byte[] fileIdentification) {
        this.fileIdentification = fileIdentification;
    }

    public ElfClass getElfClass() {
        return elfClass;
    }

    public void setElfClass(ElfClass elfClass) {
        this.elfClass = elfClass;
    }

    public DataEncoding getDataEncoding() {
        return dataEncoding;
    }

    public void setDataEncoding(DataEncoding dataEncoding) {
        this.dataEncoding = dataEncoding;
    }

    public byte getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(byte idVersion) {
        this.idVersion = idVersion;
    }

    public byte[] getPadding() {
        return padding;
    }

    public void setPadding(byte[] padding) {
        this.padding = padding;
    }

    public int getClassLength() throws UnknownFormatException {
        switch (getElfClass()) {
            case ELFCLASS32:
                return 32;
            case ELFCLASS64:
                return 64;
            default:
                throw new UnknownFormatException("Unknown ELF class " + getElfClass());
        }
    }

    public ByteOrder getByteOrder() throws UnknownFormatException {
        switch (getDataEncoding()) {
            case ELFDATA2MSB:
                return ByteOrder.BIG_ENDIAN;
            case ELFDATA2LSB:
                return ByteOrder.LITTLE_ENDIAN;
            default:
                throw new UnknownFormatException("Unknown ELF data encoding " + getDataEncoding());
        }
    }
}
