# jaggerScenario
Two scenarios for local Discovery KeywordSearchService4 with three parameters which are provided by CSV file. 
One scenario for Jmeter.
**The goal was to write two indentical scenarios for Jagger and Jmeter, they should get query parameters from CSV file and send  GET requests to Discovery. Responses should be in xml format (scenarios should validate succes code and type of response). Also there is should be a custom metric (the amount of the response body in bytes). **

## Getting started
To use this application you need to set up jagger:
* [jagger](https://github.com/griddynamics/jagger) - Jagger project;
* [jagger](http://griddynamics.github.io/jagger/doc/index.html) - Jagger user manual.

To launch your load scenario, set 'jagger.load.scenario.id.to.execute' property's value equal to the load scenario id.
You can do it via system properties or in the 'environment.properties' file.

```
cd ./target/{artifactdId}-{version}-full/
./start.sh profiles/basic/environment.properties 
```
or 
```
cd ./target/{artifactdId}-{version}-full/
./start.sh profiles/basic/environment.properties -Djagger.load.scenario.id.to.execute = scenarioId
```
### Scenario1
ScenarioID = ls_1
Classes from com.griddynamics.jagger.invoker.v2.
Gets xml response from Discovery, cannot count the amount of bytes for the response body. 

### Scenario2
ScenarioID = ls_2
Classes from com.griddynamics.jagger.invoker.scenario.
Gets json response from Discovery, can count the amount of bytes for the response body. 

### Scenario
Scenario.jmx file. Test plan for JMeter. Gets xml response from Discovery, count the amount of bytes for the response body (min, max, average), shows the result of calculation in log. 
Two JSR223 PostProcessor (groovy language) are used for this. 
```
def size = prev.getBodySizeAsLong()
log.info("Size in bytes is: " + size.toString())
def pathToFile = vars.get("file")
File file = new File(pathToFile)
file.append(size + ",")
```
```
def pathToFile = vars.get("file")
String fileContents = new File(pathToFile).text
       String[] strArr = fileContents.split(",")
       int[] intArr = new int[strArr.length]
       for (int i = 0; i < strArr.length; i++) {
       intArr[i] = strArr[i].toInteger()
       }

        int maximum = 0
        int minimum = 1000
        int avr = 0
        
        for(int j = 0; j < intArr.length; j++){
         avr = avr + intArr[j]
         if (minimum > intArr[j]){
                minimum = intArr[j]
            }
            if (maximum < intArr[j]){
                maximum = intArr[j]
            }
        }
```

The main problem is that postProcessors (and everything else in JMeter) are called after each request (not just each sample), which can cause some issues if there are a lot of requests. 



