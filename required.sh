#!/bin/bash
# installs lilypond into ~/lilypond/usr
curl -L -o /tmp/lilypond.tar.gz https://gitlab.com/lilypond/lilypond/-/releases/v2.25.11/downloads/lilypond-2.25.11-linux-x86_64.tar.gz

#https://gitlab.com/lilypond/lilypond/-/releases/permalink/latest
#https://gitlab.com/lilypond/lilypond/-/releases/v2.25.11/downloads/lilypond-2.25.11-linux-x86_64.tar.gz

mkdir -p ~/lilypond/usr
mkdir -p /tmp/lilypond
tar -xf /tmp/lilypond.tar.gz -C /tmp/lilypond
mv /tmp/lilypond/*/* ~/lilypond/usr
lilypond --version
