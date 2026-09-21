#!/bin/bash
cd "$(dirname "$0")/src"
javac com/smartcampus/Main.java && java com.smartcampus.Main
