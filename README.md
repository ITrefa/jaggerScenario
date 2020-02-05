# jaggerScenario
Two scenarios for local Discovery KeywordSearchService4 with three parameters which are provided by CSV file. 

**The goal was to write two indentical scenarios for Jagger and Jmeter, they should get query parameters from CSV file and send  GET requests to Discovery. Responses should be in xml format (scenarios should validate success code and type of response). Also there is should be a custom metric (the amount of the response body in bytes).**

## Getting started
To use this application you need to set up jagger:
* [jagger](https://github.com/griddynamics/jagger) - Jagger project;
* [jagger](http://griddynamics.github.io/jagger/doc/index.html) - Jagger user manual.

To launch a load scenario, set 'jagger.load.scenario.id.to.execute' property's value equal to the load scenario id.
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


### Scenario
Scenario.jmx file. Test plan for JMeter. Gets xml response from Discovery, count the amount of bytes for the response body (min, max, average), shows the result of calculation in log. 
Two JSR223 PostProcessor (groovy language) are used for this. 



