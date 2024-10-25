The shell scripts in this directory were tested against SU's CS1 server.

While they can be run standalone for development purposes, they can be called
by a build automation tool, like Jenkins, and serve as source-controller build
scripts.

An AWS EC2 t2.micro server running AWS Linux and Jenkins used these scripts 
during this class project to achieve a source-controlled CI/CD pipeline.

********************************************************************************
JAVA INSTALLATION INSTRUCTIONS
********************************************************************************
You will need to install the JDK per the instructions below for the scripts to
run since they have relative path dependencies for the JRE and Java compiler.

Environment Setup:
1. Clone GitHub repo
git clone https://github.com/mosers1/Java-Battleship.git

2. Download JDK 8u211 for Linux x64 from here:
https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* NOTE: You'll need to accept the license agreement and may need to make an Oracle account.

3. Transfer file to CS1 (or other server) and place them at same location as
the top-level Java-Battleship directory as shown below:

[user@cs1 buildSystem]$ ls -al
total 190428
drwxrwx---. 3 user user        73 May 30 23:59 .
drwxrwx---. 4 user user        97 May 30 23:58 ..
drwxrwx---. 7 user user      4096 May 30 23:59 Java-Battleship
-rw-r-----. 1 user user 194990602 May 30 23:59 jdk-8u211-linux-x64.tar.gz

4. Extract the tarball
tar -xvzf jdk-8u211-linux-x64.tar.gz

[user@cs1 buildSystem]$ ls -al
total 190432
drwxrwx---. 4 user user        96 May 31 00:01 .
drwxrwx---. 4 user user        97 May 30 23:58 ..
drwxrwx---. 7 user user      4096 May 30 23:59 Java-Battleship
drwxr-x---. 7 user user      4096 Apr  1 20:51 jdk1.8.0_211
-rw-r-----. 1 user user 194990602 May 30 23:59 jdk-8u211-linux-x64.tar.gz

5. To build the test suite and run it:
   a. cd Java-Battleship/scripts
   b. Change permissions to make scripts executable
      chmod 777 *
   c. Run the build script
      ./buildTestSuite.sh
   d. Run the test suite
      ./runTestSuite.sh <numIter> [e-mailAddr]
   e. Perform stress test
      ./stressTestApp.sh  ***(TBD)***

6. Create symlinks to java and javac [optional]
One can use the "ln -s" command to create symbolic links for "java" and "javac"
found in $PATH to point to your new installation.
