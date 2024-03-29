#!/bin/bash
# installs lilypond into ~/lilypond/usr
curl -L -o /tmp/lilypond.tar.gz https://gitlab.com/lilypond/lilypond/-/releases/v2.25.11/downloads/lilypond-2.25.11-linux-x86_64.tar.gz

#https://gitlab.com/lilypond/lilypond/-/releases/permalink/latest
#https://gitlab.com/lilypond/lilypond/-/releases/v2.25.11/downloads/lilypond-2.25.11-linux-x86_64.tar.gz

mkdir -p /opt/lilypond
mkdir -p /tmp/lilypond
tar -xf /tmp/lilypond.tar.gz -C /tmp/lilypond
mv /tmp/lilypond/*/* /opt/lilypond
ln -s /opt/lilypond/bin/lilypond /usr/local/bin/lilypond
lilypond --version
