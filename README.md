maprfs-example
===

A Maven project to perform basic MapR FS operations.

Usage
===

Clone this project, then:

```
mvn package
java -cp $(hadoop classpath):target/maprfs-example*.jar MapRTest /tmp
```
