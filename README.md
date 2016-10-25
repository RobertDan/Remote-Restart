# Remote-Restart
Software to allow others (or yourself while away from your computer!) to start, close, or restart programs on your computer without giving out the computer/user profile's credentials.

Features planned:
Android App
Website (so no download is required, except for the "server" program)
Additional support for other tasks, like accessing individual files.

Current progress:

Networking - Complete. Client and Server successfully communicate over network via Socket connections, whether on the same computer, same network, or over the internet.

Config File - Mostly complete. Reads the contents of the config file, just need to make the server use those values. Leaving alone for the time being, as information I want to capture/require may change as the project progresses.

Task Management - In progress. Java code executes in the JVM, meaning the server shouldn't have access to the computers running programs. May need to launch programs through the JVM in order to be able to manage them. Will require further research.

User Interface - Not started. Once the backend is complete and I know exactly what information I will require, I will design and implement an interface to facilitate entering and viewing information.

Android App - Not started. Client program should be completed first. This should be easily transferred as Android uses Java natively. Modifications to the User Interface will probably be the biggest change(s).

Website - Not started. Similar to the Android App, this is an extra feature for down the road. Client program should be completed first.
