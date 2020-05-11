#!/bin/bash -x

fluxbox >/dev/null 2>&1 &
sleep 20 # give programs some time to start
gnome-screenshot -c
gnome-screenshot -c --display=$DISPLAY
gnome-screenshot -f screen1.png
gnome-screenshot -f screen1.png
gnome-screenshot -f screen2.png --display=$DISPLAY
gnome-screenshot -f screen2.png --display=$DISPLAY
