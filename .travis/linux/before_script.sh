#!/bin/bash -x

#sh -e /etc/init.d/xvfb start
#sleep 10 # give xvfb some time to start
xterm &
fluxbox &
sleep 20 # give programs some time to start
