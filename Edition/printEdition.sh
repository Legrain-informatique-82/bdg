#!/bin/bash

#cat $1 | $2 -toPostScript | lpr
#cat $1 | $2 -toPostScript $3| lpr

if [ -z "$3" ]; then
   cat $1 | $2 -toPostScript | lpr
fi

if [ -n "$3" ]; then
   cat $1 | $2 -toPostScript -$3| lpr
fi
