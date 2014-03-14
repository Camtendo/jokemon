@echo off
cls
title Jokemon
echo Jokemon
echo By J Inc.
echo --------------------
echo This file boosts the performance of Jokemon.
echo WARNING, IF YOU DO NOT HAVE
echo AT LEAST 2 GB OF RAM, EXIT THIS WINDOW NOW.
echo Otherwise, continue as instructed by the window.
PAUSE
cls
echo Increasing Java Heap to 256 MB...
echo Importing depends...
tree Music /f
tree MapSprites /f
echo Done.
cls
echo Finding Source file
echo Starting Jokemon...
java -Xms32m -Xmx256m JokemonDriver
cls
