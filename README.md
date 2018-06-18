# Console_chat
java version: JDK 10.0.1

### How to build and run jar files:
#### Maven:
1. Download thw latest version of maven from http://maven.apache.org/download.cgi
2. Unzip the archive to any folder you want
3. Windows: Add M2_HOME in the Windows environment, and point it to your Maven folder. Linux: add "export M2_HOME=/opt/maven" into /etc/profile
4. Windows: Update PATH variable, append Maven bin folder – %M2_HOME%\bin. Linux: export PATH=$PATH:$M2_HOME/bin" into /etc/profile
#### Build jars:
1. Run console
2. Go to the project folder: path_to_directory\console_chat
3. Use maven build command - it will create target folder inside client and server folders with jar files and other outputs
```
mvn clean package
```
4. Move jar files from target folder to client or server folder.
5. Run jar file
```
java -jar client.jar
java -jar server.jar
```
### Instructions:
#### Server:
1. You can change the port number inside serverConfig.yml file
2. To shutdown the server you must type: /exit
3. When client has connected to the server, message "User Nickname is connected" will be printed
4. When client has disconnected from the server, message "User Nickname has quitted" wiil be printed
#### Client:
1. You must run server first to be able to connect clients to it.
1. You can change server name and port insile clientConfig.yml file.
2. First you must specify your nickname. If it's occupied, you must enter a new one.
3. You can write private messages by following the example: /w Nickname YourText
4. To exit chat you can type: /exit
