#!/usr/bin/python
#
# A script to validate the JSON serialization of an ELF file, using objdump and/or readelf
#
# This is some seriously awful hackery!
#

import sys
import json
import subprocess

constantMapping = {
    "ELF64" : "ELFCLASS64",
    "EXEC (Executable file)" : "ET_EXEC"
}

READELF_HEADERS = "readelf -h $file"
SECTION_NAME = "readelf -t $file | gawk 'match($0, /^[[:space:]]+[[[:space:]]*{}][[:space:]]+(.*)/, m) { print m[1] }'"
NAME_VARIABLE = "NAME=`" + SECTION_NAME + "`; echo name= $NAME >&2;"
SIZE_VARIABLE = "SIZE=`objdump -h $file | egrep [[:space:]]+${NAME}[[:space:]]+ | awk '{ print $3 }'`;"
OFFSET_VARIABLE = "OFFSET=`objdump -h $file | egrep [[:space:]]+${NAME}[[:space:]]+ | awk '{ print $6 }'`;"

jsonPathMappings = {
     ".header.entry.value" : READELF_HEADERS + " | gawk 'match($0, /Entry point address:[[:space:]]+(.*)/, m) { print m[1] }'",
     ".header.fileType" : READELF_HEADERS + " | gawk 'match($0, /Type:[[:space:]]+(.*)/, m) { print m[1] }'",
#    ".header.flags" : "",
     ".header.headerSize" : READELF_HEADERS + " | gawk 'match($0, /Size of this header:[[:space:]]+(.*)/, m) { print m[1] }' | awk '{ print $1 }'",
#    ".header.ident.dataEncoding" : "",
     ".header.ident.elfClass" : READELF_HEADERS + " | gawk 'match($0, /Class:[[:space:]]+(.*)/, m) { print m[1] }'",
     ".header.ident.fileIdentification" : READELF_HEADERS + " | gawk 'match($0, /Magic:[[:space:]]+(.*)/, m) { print m[1] }' | gawk '{ print $1 " " $2 " " $3 " " $4 }' | xxd -r -p | base64 --wrap=0",
#    ".header.ident.idVersion" : "",
#    ".header.ident.padding" : "",
#    ".header.machineType" : "",
     ".header.numProgramHeaders" : READELF_HEADERS + " | gawk 'match($0, /Number of program headers:[[:space:]]+(.*)/, m) { print m[1] }'",
     ".header.numSectionHeaders" : READELF_HEADERS + " | gawk 'match($0, /Number of section headers:[[:space:]]+(.*)/, m) { print m[1] }'",
     ".header.programHeaderOffset.value" : READELF_HEADERS + " | gawk 'match($0, /Start of program headers:[[:space:]]+(.*)/, m) { print m[1] }' | awk '{ print $1 }'",
     ".header.programHeaderSize" : READELF_HEADERS + " | gawk 'match($0, /Size of program headers:[[:space:]]+(.*)/, m) { print m[1] }' | awk '{ print $1 }'",
     ".header.sectionHeaderOffset.value" : READELF_HEADERS + " | gawk 'match($0, /Start of section headers:[[:space:]]+(.*)/, m) { print m[1] }' | awk '{ print $1 }'",
     ".header.sectionHeaderSize" : READELF_HEADERS + " | gawk 'match($0, /Size of section headers:[[:space:]]+(.*)/, m) { print m[1] }' | awk '{ print $1 }'",
     ".header.sectionNameStrIndex" : READELF_HEADERS + " | gawk 'match($0, /Section header string table index:[[:space:]]+(.*)/, m) { print m[1] }' | awk '{ print $1 }'",
#    ".header.version" : "",
     ".sections[{}].name": SECTION_NAME,
#    ".sections[{}].sectionContent.data" : NAME_VARIABLE + SIZE_VARIABLE + OFFSET_VARIABLE + "dd if=$file ibs=1c skip=`expr $(( 0x$OFFSET ))` count=`expr $(( 0x$SIZE ))` 2>/dev/null | base64 --wrap=0",
#    ".sections[{}].sectionContent.entries[{}].tag" : "",
#    ".sections[{}].sectionContent.entries[{}].tagValue.value" : "",
#    ".sections[{}].sectionContent.entries[{}].value.value" : "",
#    ".sections[{}].sectionContent.relocations[{}].addend.value" : "",
#    ".sections[{}].sectionContent.relocations[{}].info.value" : "",
#    ".sections[{}].sectionContent.relocations[{}].offset.value" : "",
#    ".sections[{}].sectionContent.symbols[{}].info" : "",
#    ".sections[{}].sectionContent.symbols[{}].name" : "",
#    ".sections[{}].sectionContent.symbols[{}].nameIndex" : "",
#    ".sections[{}].sectionContent.symbols[{}].other" : "",
#    ".sections[{}].sectionContent.symbols[{}].sectionIndex" : "",
#    ".sections[{}].sectionContent.symbols[{}].size.value" : "",
#    ".sections[{}].sectionContent.symbols[{}].symbolBinding" : "",
#    ".sections[{}].sectionContent.symbols[{}].symbolType" : "",
#    ".sections[{}].sectionContent.symbols[{}].value.value" : "",
#    ".sections[{}].sectionHeader.addressAlign.value" : "",
#    ".sections[{}].sectionHeader.address.value" : "",
#    ".sections[{}].sectionHeader.entrySize.value" : "",
#    ".sections[{}].sectionHeader.flags[{}]" : "",
#    ".sections[{}].sectionHeader.flagsField.value" : "",
#    ".sections[{}].sectionHeader.info" : "",
#    ".sections[{}].sectionHeader.link" : "",
#    ".sections[{}].sectionHeader.nameIndex" : "",
#    ".sections[{}].sectionHeader.offset.value" : "",
#    ".sections[{}].sectionHeader.size.value" : "",
#    ".sections[{}].sectionHeader.type" : "",
#    ".segments[{}].programHeader.alignment.value" : "",
#    ".segments[{}].programHeader.fileSize.value" : "",
#    ".segments[{}].programHeader.flags[{}]" : "",
#    ".segments[{}].programHeader.flagsField" : "",
#    ".segments[{}].programHeader.memorySize.value" : "",
#    ".segments[{}].programHeader.offset.value" : "",
#    ".segments[{}].programHeader.physicalAddress.value" : "",
#    ".segments[{}].programHeader.type" : ""
}

def replaceVars(path, filePath, params):
    path = path.replace("$file", filePath)
    for param in params:
        path = path.replace("{}", str(param), 1)

    return path

def matchOutput(currentPath, value, cmd):
    proc = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
    output = proc.communicate()[0]
    if str(value) != str(output.strip()):
        if type(value) is int:
            # try in hex
            if str(hex(value)) == output.strip():
                return
        # try a constant mapping
        if output.strip() in constantMapping:
            if str(value) == constantMapping[output.strip()]:
                return
        raise ValueError(currentPath + ": '" + str(value) + "' != '" + output.strip() + "'")

def validateJsonValues(payload, filePath, pathMappings, currentPath, params):
    if type(payload) is dict:
        for attr, value in payload.iteritems():
            validateJsonValues(value, filePath, pathMappings, currentPath + "." + attr, params)
    elif type(payload) is list:
        for idx, elem in enumerate(payload):
            validateJsonValues(elem, filePath, pathMappings, currentPath + "[{}]", params + (idx,))
    else:
        if currentPath in pathMappings:
            matchOutput(currentPath, payload, replaceVars(pathMappings[currentPath], filePath, params))


if len(sys.argv) != 3:
    print("Usage: script path-to-json path-to-elf")
    sys.exit(1)

jsonFile = sys.argv[1]
elfFile = sys.argv[2]

validateJsonValues(json.load(open(jsonFile)), elfFile, jsonPathMappings, "", tuple())

