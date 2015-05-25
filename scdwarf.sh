#!/bin/bash
#
# Script to run DwarfDump utility
#

if [ $# -ne 1 ]; then
    echo "Usage: $0 <path to file>"
    exit 1
fi

mvn -q -pl=impl exec:java -Dexec.mainClass=net.sourcecrumbs.refimpl.util.DwarfDump -Dexec.args=$1
