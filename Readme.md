# CommandExecutor<br />
### _This is a general dependency._

_Can be consumed by any project that has this requirement. This dependency can be used for executing commands, shell or bat file from java code on any OS._


### **Requirements**
___
**npm/Node.js**

**JDK > 11**
___

### **Usage**

### **Initialize the constructor**

### `CommandExecutor c = new CommandExecutor(directoryToExecuteCommand);`

### **Use the terminal executor to execute command** 

### `c.executeCommand(command);`

### **Or use it to execute a bat or shell file**

### `c.executeShellOrBat(shellOrBatFileName);`

<br />

### **Steps to build the jar dependency**
___
###`./mvnw clean install`

###`./mvnw clean compile`

###`./mvnw clean package` 
___
<br />

Your dependency will be generated in **./target/commandExecutor-0.0.1.jar** <br />

You can add this dependency in the **ProjectRootDirectory/lib/commandExecutor-0.0.1.jar**. Use it in the pom.xml file as below.


        <dependency>
            <groupId>com.lynxonSSS.command-executor</groupId>
            <artifactId>CommandExecutor</artifactId>
            <version>0.0.1</version>
            <scope>system</scope>
            <systemPath>${basedir}/lib/commandExecutor-1.0.1.jar</systemPath>
        </dependency>
