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

package net.sourcecrumbs.refimpl.elf.spec.sections;

import static net.sourcecrumbs.refimpl.elf.spec.constants.DataTypeSizes.*;

import org.codehaus.preon.annotation.BoundList;
import org.codehaus.preon.annotation.BoundNumber;

import net.sourcecrumbs.refimpl.elf.spec.sym.ElfSymbol;

/**
 * A symbol hash table contained in an ELF file
 *
 * @author mcnulty
 */
public class SymbolHashTable implements SectionContent {

    @BoundNumber(size = ElfWord)
    private int numBuckets;

    @BoundNumber(size = ElfWord)
    private int numChainEntries;

    @BoundList(size = "numBuckets", offset="index*32")
    private int[] buckets;

    @BoundList(size = "numChainEntries", offset="index*32")
    private int[] chainEntries;

    public int getNumBuckets() {
        return numBuckets;
    }

    public void setNumBuckets(int numBuckets) {
        this.numBuckets = numBuckets;
    }

    public int getNumChainEntries() {
        return numChainEntries;
    }

    public void setNumChainEntries(int numChainEntries) {
        this.numChainEntries = numChainEntries;
    }

    public int[] getBuckets() {
        return buckets;
    }

    public void setBuckets(int[] buckets) {
        this.buckets = buckets;
    }

    public int[] getChainEntries() {
        return chainEntries;
    }

    public void setChainEntries(int[] chainEntries) {
        this.chainEntries = chainEntries;
    }

    /**
     * Implementation of elf_hash function from ELF standard.
     *
     * @param symbolName the symbol name
     *
     * @return the hash code
     */
    private int symbolHashFunction(String symbolName) {
        int hashCode = 0;

        for (char c : symbolName.toCharArray()) {
            hashCode = (hashCode << 4) + (byte)c;

            int hashVariable = hashCode & 0xf0000000;
            if (hashVariable != 0) {
                hashCode = hashCode ^ (hashVariable >> 24);
            }

            hashCode = hashCode & -hashVariable;
        }

        return hashCode;
    }

    /**
     * @param symbolName the name of the symbol
     * @param symbolTable the associated symbol table in which to look for the symbol
     * @param stringTable the string table associated with the symbol table
     *
     * @retun the symbol or null if no symbol could be found
     */
    public ElfSymbol get(String symbolName, SymbolTable symbolTable, StringTable stringTable) {
        if (buckets != null && chainEntries != null) {
            int hashCode = symbolHashFunction(symbolName);
            if (hashCode > 0) {
                int symbolIndex = buckets[hashCode % numBuckets];
                while (symbolIndex != ElfSymbol.STN_UNDEF) {
                    ElfSymbol symbol = symbolTable.getSymbols()[symbolIndex];
                    if (symbol != null) {
                        if (symbolName.equals(stringTable.getString(symbol.getNameIndex()))) {
                            return symbol;
                        }

                        // A collision was found, need to walk the chain
                        symbolIndex = chainEntries[symbolIndex];
                    }else{
                        break;
                    }
                }while (symbolIndex != ElfSymbol.STN_UNDEF);
            }
        }

        return null;
    }
}
