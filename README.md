# IoT Ladder Editor


<p align="center"><kbd>
  <img src="https://user-images.githubusercontent.com/5174326/110049165-10e0c400-7d30-11eb-9122-5d47fe090d74.png" width="800">
</kbd></p>

This project is a open source code Ladder Editor that generates code for IoT devices. Through the MQTT protocol it is possible to interact with the program variables.
The current version generates Arduino code (.ino) for ESP32.

It's a desktop Java application developed with Eclipse IDE + WindowBuilder plug-in.
## How to execute
1. Install Java 8 (JRE) from Oracle if doesn't already installed
2. To compile the generated code and load it on ESP32 board install the Arduino IDE and setup it, read [README_arduino_ide_setup.md](https://github.com/leofds/iot-ladder-editor/blob/main/README_arduino_ide_setup.md) to do this
3. Download the latest executable Jar file ([here](https://github.com/leofds/iot-ladder-editor/tree/main/target)) and double click to execute it.

## How to execute from source code
1. Install Java 8 (JDK) from Oracle
2. Download the Eclipse IDE, unzip and execute it
3. Import the project on Eclipse IDE, read the [README_importing_into_eclipse.md](https://github.com/leofds/iot-ladder-editor/blob/main/README_importing_into_eclipse.md) to do this
4. On the project, click with the right mouse click and select **Run As>Java Application**

## How to use the software
To understand about the Ladder Language read [README_ladder_language.md](https://github.com/leofds/iot-ladder-editor/blob/main/README_ladder_language.md).

Use drag and drop to place instructions on the *Rung* and associate input/output variables to instructions.

![output (1)](https://user-images.githubusercontent.com/5174326/110243440-1d843880-7f39-11eb-8fd7-9fa48134a221.gif)

