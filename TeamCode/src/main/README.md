# Overload 9161
## [util](main/java/org/firstinspires/ftc/teamcode/util)
This is the main utility file that contains everything important to make the robot run
--
### [Hardware](main/java/org/firstinspires/ftc/teamcode/util/hardware)
This hardware file is meant for all of the methods and variables that either need to be called in many classes and/or need to be called many times
#### [Hardware.java](main/java/org/firstinspires/ftc/teamcode/util/hardware/Hardware.java)
This file is the main class and what the others are built on
#### [Hard_Auto.java](main/java/org/firstinspires/ftc/teamcode/util/hardware/Hard_Auto.java)
This is the class that initiates the robot in autonomus
This extends [Hardware.java](main/java/org/firstinspires/ftc/teamcode/util/hardware/Hardware.java)
#### [Hard_Thread.java](main/java/org/firstinspires/ftc/teamcode/util/hardware/Hard_Thread.java)
This is a thread that can be called in the beginning of an opmode which runs in parellel to the start. <br>
This extends [Hardware.java](main/java/org/firstinspires/ftc/teamcode/util/hardware/Hardware.java) for ease of use
### [FileManager](main/java/org/firstinspires/ftc/teamcode/util/FileManager)
This a place where all the file managing happens
#### [FileManager.java](main/java/org/firstinspires/ftc/teamcode/util/FileManager/FileManager.java)
This is a threaded logging device <br>
See the method "How-To" to see how to use this class <br>
@see [LogPlotter](https://github.com/Overload9161/Log-Plotter) for how to get the information from the robot
### [images](main/java/org/firstinspires/ftc/teamcode/util/images)
This file is useless for future games, it will be easier and more reliable to follow what others are doing
## [roadrunner](main/java/org/firstinspires/ftc/teamcode/roadrunner)
This is the file that deals with everything to do with RoadRunner <br>
@see [The main RoadRunner page](https://learnroadrunner.com) for how to use RoadRunner
