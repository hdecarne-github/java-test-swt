#!/bin/sh

export DISPLAY=:99.0
export DBUS_SESSION_BUS_ADDRESS=/dev/null
sh -e /etc/init.d/xvfb start
sleep 10 # give xvfb some time to start
metacity --sm-disable --replace 2> metacity.err &
sleep 10 # give metacity some time to start
