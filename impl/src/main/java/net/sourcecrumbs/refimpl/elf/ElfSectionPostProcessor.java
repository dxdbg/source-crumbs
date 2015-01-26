/*
 * Copyright (c) 2011-2015, Dan McNulty
 * All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package net.sourcecrumbs.refimpl.elf;

import net.sourcecrumbs.api.files.UnknownFormatException;
import net.sourcecrumbs.refimpl.elf.spec.ElfFile;
import net.sourcecrumbs.refimpl.elf.spec.ElfIdent;
import net.sourcecrumbs.refimpl.elf.spec.ElfSection;

/**
 * Interface for augmenting the representation of an ELF section after initial parsing is complete. This is necessary
 * to inject interpretations of ELF sections that are not dictated by the standard such as DWARF debugging information.
 *
 * @author mcnulty
 */
public interface ElfSectionPostProcessor {

    /**
     * Post-process the specified ElfSection
     *
     * @param section the section
     *
     * @throws UnknownFormatException when the specified section doesn't match an expected format
     */
    void process(ElfSection section) throws UnknownFormatException;

    /**
     * Called when post-processing has been completed for all sections
     *
     * @param elfFile the final ElfFile
     *
     * @throws UnknownFormatException when any further processing encounters an unknown format
     */
    void completeProcessing(ElfFile elfFile) throws UnknownFormatException;
}
