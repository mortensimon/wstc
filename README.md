FreeACS Fusion - Web Services Test Client
=========================================
This project is not part of the whole FreeACS product, but can be useful to 
test the Web Service Server. Get the full picture here: 
http://www.freeacs.com/

Dependencies
------------
https://github.com/freeacs/common.git  
I recommend setting up this project as a Project reference

https://github.com/freeacs/dbi.git  
I recommend setting up this project as a Project reference

Jarfiles needed to make runnable JAR file and run the project is part of the 
project (lib-folder), but these may of course be exchanged for newer 
versions (if necessary) upon making the JAR-file. 


Eclipse setup
-------------
Git view: Import git repo  
Git view: Import projects from git repo, import as general project    
Package/Navigator view: Change project facets to Java 1.7  
Package/Navigator view: Java Build Path:  project reference to the freeacs-common and dbi project      
Package/Navigator view: Add libs to classpath from lib-folder 


Overview
--------
The Web Service Test Client can perhaps be helpful in order to build similar
clients yourself. Look into the com.owera.xapsws.main package for examples.