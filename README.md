jokemon (Java + Pokemon)
=======

This project was a set of two games (Peaches and Cream) made as a super project in high school by four devs. The code is **awful**, but I decided to host it so that people can help make it better! NOTE: I plan on running the project through IntelliJ in early April 2014 to get some free refactoring. :)

Website and Downloads
=====================
http://pokemonjavaversion.webs.com/
###Individual versions (v1.1):
* [Peaches Version](https://dl.dropboxusercontent.com/u/35469595/Peaches.rar)
* [Cream Version](https://dl.dropboxusercontent.com/u/35469595/Cream.rar)

How to Run
========
Within the project directory:
```
javac *.java
java JokemonDriver
```
It now builds and runs!
View the batch file in the project root if you wish to run the project with more heap space.

Controls
========
Try using the following in various scenarios:
+ Arrow Keys
+ Spacebar
+ Escape Key
+ Enter

Currently, battles will **NOT** work on Macs.

Original Developers
======

###Camtendo
+ Project Lead 
+ Battle Engine 
+ Resource Loading
+ File Security (anti-cheat) 
+ Overworld Physics Engine 
+ Pokemon Programming
+ Trading Logic
+ Dialogue
+ Music Selection
+ Sound
+ Sprite Selection
+ Minigame Implementation
+ Plot

###Justinian
+ Main Programmer 
+ Battle Engine
+ Graphic Organizer
+ Dialogue
+ Music Selection
+ Map Design
+ Map Programmer
+ Item Designer and Programmer
+ Window Management

###Patches
+ Testing Lead
+ Original Overworld Programmer
+ Minigame Implementation

###Wasabi Sause
+ Pokedex Design and Implementation

Testers
=======
+ Chris Hand (Lead)
+ Patches (Lead)
+ Sam Eiserman
+ Karl Smith
+ Trevor Jackson
+ Kevin Hanna
+ Brian Healy
+ Jedd Patterson
+ Devin Freese

Changelog (Before Github)
=========

Version 1.1
---------------------------------
* Updated Pokeball sprites
* Updated Trade Background
* Fixed several glitches
* Added Moonstone to lighthouse and mart
* Added Elixirs and Ethers to high tier marts
* Changed Battlewindow Graphics slightly
* Fixed major TM 0 glitch

Release Candidate 1
-------------------
* Fixed Sprites that didn't appear (contained special characters)
* Added Pokedex registration for Pokemon that evolved via Evo Stone
* Hopefully Permanently fixed Pokedex Images
* Display all areas of Pokemon
* New Badge Sprites (Courtesy of Justin's girlfriend)
* Fixed misprint of stats
* Fully blocked off Binary City from Route 1
* Fixed Heal overflow and recalculations
* Added Town Map
* Fixed "Surfbike" glitch
* Moved Jimmy to correct spot in Args Harbor Gym
* Fixed infinite loop from self damage while awayFromBattle
* Added Mewtrix
* Added Credits
* Fixed Intville encounters glitch
* Fixed multiple maps
* Got rid of the blue box around the badges
* Fixed being able to get around bariers


Version 0.4
-----------
* Will begin considering release candidates.
* Added bonus Donation features
* Fixed duplicate "Champions Walk" and removed Victory Road in Fly Menu
* Added Battle Tower. Fully Functional.
* Added Boosts to trainers after beating the Elite 4 and Joe
* Added Super Rod to the MegaMart
* Fixed NullPointer from exiting pause menu while toggling
* Fully implemented MegaMart
* Fixed purchase of TMs
* Hopefully fixed overflow selling of items. Doubtful.
* More miscellaneous bug fixes

Version 0.3
-----------
* Can play game to completion. Hopefully.
* Pokemon that evolve via trade really evolve. Promise.
* Extended tile range so that all tiles are drawn
* Fixed Effort Value overflow (Your Pokemon will be WAY stronger now)
* Added method to set Pokemon stats to max (Debug only)
* Removed Escape Rope from game and added Omega Ball
* Added people to J Inc Building
* Added people to Java Hideout
* Added Type Display for Pokemon in party
* Added Level Display on PC
* Enabled Mega Mart...sorta. Talk to first floor lady.
* Miscellaneous Sign fixes

Version 0.2
-----------
* Pokemon that evolve via trade now evolve
* Strength now works on boulders
* Fixed "Phantom Bicycle Glitch"
* Introduced Supply and Demand to shops
* Fixed battle stages reset
* Fixed BattleWindow Lowercase font (Justin has the code) (Justin has fixed it)
* Fixed Pokedex Image import
* Added a few Pokedex Areas
* Fixed Multi-Turn moves possibly dealing 0 damage
* Fixed infinite repels
* Fixed poison text bug
* Fixed being able to walk around with a team of 6 fainted Pokemon
* Blocked route 0 partially until you get the first badge
* SAVE AND REBOOT GAME AFTER MUSIC CUTS OUT. DUE TO EXCEEDED JAVA HEAP.
* Fixed Pokedex typographical errors
* Fixed random NullPointerExceptions from BattleWindow
* Added Hotkeys to item menu
* Fixed Rival's house signboard
* Pokemon are healed prior to Gym Leader Battle
* Added remaining TMs to shops
* Changed TM costs to be relative
* Made Experience All buyable and sellable (Costs 50000)
* Fixed Streamreader transfers
* Fixed Enumville transfers
* Fixed random Enumville battle
* Can now surf in caves
* Fixed "superhappyfuntime"
* Fixed item icon
* Fixed Auto-HM usage by trading (Cheaters!)

Version 0.1
-------------
* Fixed Leech Seed bug on Pokemon with lower than 16 HP
* Fixed Nurse Joy tile png names
* Began inclusion of all pictures with jar to run it
* Altered Catch rate Algorithm (Made easier)
* Enhanced SMART AI
* Modified Pokemon nickname method (10 chars or less)
* Added prompt for 7 character names in beginning
* Increased Rival Pokemon level correctly
* Fixed loading of Pokemon images in Pokedex
* Fixed fatal CLASS CAST EXCEPTION in BattleWindow
* Fixed ability to fight Jenny without seeing Babb
* Disabled Cartographer Mode for Non-Developers
* Edited Run Away Algorithm for easier fleeing (Cowards :P)
* Fixed Poison drawing

Version 0.0.1 Released April 26
-------------------------------
* Base Version
