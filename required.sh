#!/bin/bash
# installs lilypond into /usr/local/lilypond and /usr/local/bin as shortcut
curl -o /tmp/lilypond.sh https://lilypond.org/download/binaries/linux-64/lilypond-2.20.0-1.linux-64.sh
chmod a+x /tmp/lilypond.sh && /tmp/lilypond.sh --batch
