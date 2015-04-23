[![Build Status](https://drone.io/github.com/avilaplana/consoleSocialNetwork/status.png)](https://drone.io/github.com/avilaplana/consoleSocialNetwork/latest)

# Java

## Requirements

1. Java 1.7.0_13
2. apache-maven-3.3.1

Command to compile/test/run
 
**mvn compile test exec:java -Dexec.mainClass="com.test.SocialNetwork"**

# Scala 

## Requirements

1. Scala 2.11.5
2. SBT 0.13.7

Command to compile/test/run

**sbt compile test "run-main com.test.SocialNetwork"**

The exercise has been resolved using proper TDD approach:
1. Write test -> Test red
2. Minimum code -> Test green
3. Refactoring -> Test green


Notes: 

1. I have covered all the happy paths in the tests, so I have the certainty that there are no bugs.

2. I have no documentation such as javadoc because I consider that the tests should be the documentation to follow to understand
what our code does. 



Example: 
```
>
Alvaro -> this is a message 
>
Alvaro
 this is a message  (1 seconds ago)
>
Alvaro wall
Alvaro -  this is a message  (4 seconds ago)
>
Charlie -> this is a message from charlie
>
Charlie
 this is a message from charlie (1 seconds ago)
>
Charlie wall
Charlie -  this is a message from charlie (6 seconds ago)
>
Charlie follows Alvaro
>
Charlie
 this is a message from charlie (16 seconds ago)
>
Charlie wall
Charlie -  this is a message from charlie (19 seconds ago)
Alvaro -  this is a message  (33 seconds ago)
>
Alvaro -> second one
>
Charlie
 this is a message from charlie (27 seconds ago)
>
Charlie wall
Alvaro -  second one (10 seconds ago)
Charlie -  this is a message from charlie (35 seconds ago)
Alvaro -  this is a message  (49 seconds ago)
>
Peter -> this is a new user
>
Charlier follows Peter
>
Charlie
 this is a message from charlie (57 seconds ago)
>
Charlie wall
Alvaro -  second one (34 seconds ago)
Charlie -  this is a message from charlie (1 minutes ago)
Alvaro -  this is a message  (1 minutes ago)
>
Charlie follows Peter
>
Charlie
 this is a message from charlie (1 minutes ago)
>
Charlie wall
Peter -  this is a new user (26 seconds ago)
Alvaro -  second one (51 seconds ago)
Charlie -  this is a message from charlie (1 minutes ago)
Alvaro -  this is a message  (1 minutes ago)
>
Alvaro wall
Alvaro -  second one (58 seconds ago)
Alvaro -  this is a message  (1 minutes ago)
>
Alvaro
 second one (1 minutes ago)
 this is a message  (1 minutes ago)
>
Peter
 this is a new user (42 seconds ago)
>
Peter wall
Peter -  this is a new user (44 seconds ago)
>
```



