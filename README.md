maprfs-example
===

A Maven project to perform basic MapR FS operations.

Usage
===

Clone this project, then:

```
mvn package
java -cp $(hadoop classpath):target/maprfs-example-1.0-SNAPSHOT.jar MapRTest maprfs:/// /tmp
```

You should see output like:

```
mkdir( /tmp/dir) went ok, now writing file
write( /tmp/file.w) went ok
reading file: /tmp/file.w
Read ok
```
