atmsim
======

An ATM Simulator program with the following limits:

* It support $50 and $20 notes only. (Very easy to update the program to support all other types of notes)
* It doesn't take concurrency into the consideration

Installation
--------------

1. Download the package from [github](https://github.com/greenlaw110/atmsim/archive/master.zip) or you can clone the [git repository](https://github.com/greenlaw110/atmsim) directly

2. Unpack the zip package you choose to download the package

3. Install maven from http://maven.apache.org/. Or

    * If you are using debian, type `apt-get install maven`
    * If you are using PRM, please follow [this](http://stackoverflow.com/questions/6298865/how-to-install-maven-into-red-hat-enterprise-linux-6) SO
    
Run the application
--------------------

Type `mvn package exec:java` to enter the command line shell to the atm sim program. Then you can see something like

```
no atm found. prepare to create default atm:new ATM created:
ATM state
20th X 10 = 200
50th X 10 = 500

atm>
```

Type `help` to see the help message:

```
atm>help
help    Show this help message
exit    exit this atm simulator program.
create_atm    create an ATM with notes number specified.
        Usage: create_atm [-20 <number of twenties notes>] [-50 <number of fifties notes]
atm_state    report atm state
dispense    dispense notes from ATM. Usage:
        dispense <value>
```

Type `atm_state` to view current state of the atm:

```
atm>atm_state
ATM state
20th X 10 = 200
50th X 10 = 500
```

Type `dispense` to dispense notes from the atm:

```
atm>dispense 470
Successfully dispensed from ATM:
50th X 9 = 450
20th X 1 = 20

ATM state
20th X 9 = 180
50th X 1 = 50
```

Type `create_atm` to create new atm instance:

```
atm>create_atm -20 1000 -50 400
new ATM created:
ATM state
20th X 1000 = 20000
50th X 400 = 20000
```

**Note** you can use word complete like what you did in linux shell. E.g. type `cre` and then type tab you will get `create_atm` immediately

Program Depencises
------------------------

* [junit](http://junit.org): Used to do unit test
* [osgl-tool](https://github.com/greenlaw110/java-tool): My opensource project that provides a library help me appling funcitonal programming in Java 6/7. It also provides a lot an utilties that make Java programming be more fun and more expressive
* [jcommander](http://jcommander.org/): Used to process command line arguments, something like '-20 100 -50 60' etc
* [jline](http://jline.sourceforge.net/): An nice library to provide console programming support

Code Metric
------------------

Main source code

```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                            20            161            340            605
-------------------------------------------------------------------------------
SUM:                            20            161            340            605
-------------------------------------------------------------------------------
```

Test source code

```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                             2             27              7            138
-------------------------------------------------------------------------------
SUM:                             2             27              7            138
-------------------------------------------------------------------------------
```