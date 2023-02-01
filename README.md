# Milestone 2
* This milestone adds two static methods to the XML class found at src/main/java/org/json
````
static JsonObject toJsonObject(Reader reader, JSONPointer path)
````
This method extracts ,and returns, a subObject at the key path defined by the JSONPointer parameter. It parses through
the XML file until reaching the subObject and returns it without the need to parse the rest of the file (resulting in a performance
gain from the first milestone implementation). 
````
static JSONObject toJSONObject(Reader reader, JSONPointer path, JSONObject replacement)
````
This method reaches a subObject at the key path defined by the JSONPointer parameter and replaces it with the JSONObject
parameter. It parses through the XML file until reaching the subObject and injects the replacement JSONObject at this
point, skipping past the old subObject (resulting in a performance gain from the first milestone implementation).
<br/> <br/>
* Additionally a new class of JUnit tests can be found at src/test/java/org/json/junit
* The new class Milestone2Test has automated tests for the added functionalites in this milestone
* Build instructions remain the same from the original JSON-java package, detailed below...

# Build Instructions

The org.json package can be built from the command line, Maven, and Gradle. The unit tests can be executed from Maven, Gradle, or individually in an IDE e.g. Eclipse.
 
**Building from the command line**

*Build the class files from the package root directory src/main/java*
````
javac org/json/*.java
````

*Create the jar file in the current directory*
````
jar cf json-java.jar org/json/*.class
````

*Compile a program that uses the jar (see example code below)*
````
javac -cp .;json-java.jar Test.java (Windows)
javac -cp .:json-java.jar Test.java (Unix Systems)
````

*Test file contents*

````
import org.json.JSONObject;
public class Test {
    public static void main(String args[]){
       JSONObject jo = new JSONObject("{ \"abc\" : \"def\" }");
       System.out.println(jo.toString());
    }
}
````

*Execute the Test file*
```` 
java -cp .;json-java.jar Test (Windows)
java -cp .:json-java.jar Test (Unix Systems)
````

*Expected output*

````
{"abc":"def"}
````

 
**Tools to build the package and execute the unit tests**

Execute the test suite with Maven:
```
mvn clean test
```

Execute the test suite with Gradlew:

```
gradlew clean build test
```

# Notes

For more information, please see [NOTES.md](https://github.com/stleary/JSON-java/blob/master/docs/NOTES.md)

# Files

For more information on files, please see [FILES.md](https://github.com/stleary/JSON-java/blob/master/docs/FILES.md)

# Release history:

For the release history, please see [RELEASES.md](https://github.com/stleary/JSON-java/blob/master/docs/RELEASES.md)
