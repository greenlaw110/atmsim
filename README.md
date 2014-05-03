atmsim
======

A ATM Simulator program implemented in Java with the following limits:

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

Type `mvn compile exec:java` to enter the command line shell to the atm sim program. Then you can see something like

```
no atm found. prepare to create default atm:new ATM created:
ATM state
20th X 100 = 2000
50th X 40 = 2000

atm>
```

**Note** if you haven't run `mvn` before it might expect a bit long time for maven to download all required dependencies (not the dependencies of the application, but the ones required by maven plugins). Please don't get panic with the long list of downloads, they won't be there for the second time as long as you keep your $HOME/.m2 folder untouched.

Type `help` to see the help message:

```
atm>help
help: Show this help message
exit: exit this atm simulator program.
atm: create an ATM or view state of current ATM. Usage:
    atm [-20 <number of twenties notes>] [-50 <number of fifties notes]: create ATM with notes number specified
    atm: view ATM state
dispense: dispense notes from ATM. Usage:
    dispense <value>
strategy: set/view atm dispense strategy. Usage:
    strategy: show current strategy
    strategy <strategy>: set strategy. Available strategies:
        1 - Big note first: choose larger denomination notes first
        2 - Balanced note count: keep balance between buckets by note count
        3 - Balanced value: keep balance between buckets by value
```

Type `atm` to view current state of the atm:

```
atm>atm
ATM state
20th X 100 = 2000
50th X 40 = 2000
```

Type `atm` with parameters to create new atm instance:

```
atm>atm -20 1000 -50 400
new ATM created:
ATM state
20th X 1000 = 20000
50th X 400 = 20000
```

Type `dispense` to dispense notes from the atm:

```
atm>dispense 470
Successfully dispensed from ATM:
50th X 9 = 450
20th X 1 = 20

ATM state
20th X 999 = 19980
50th X 391 = 19550
```

Type `strategy` to view/change dispense strategy:

```
atm>help strategy
set/view atm dispense strategy. Usage:
    strategy: show current strategy
    strategy <strategy>: set strategy. Available strategies:
        1 - Big note first: choose larger denomination notes first
        2 - Balanced note count: keep balance between buckets by note count
        3 - Balanced value: keep balance between buckets by value

atm>strategy
Big note first: choose larger denomination notes first

atm>dispense 100
Successfully dispensed from ATM:
50th X 2 = 100

ATM state
20th X 100 = 2000
50th X 38 = 1900

atm>strategy 2
Strategy is now set to Balanced note count: keep balance between buckets by note count

atm>dispense 100
Successfully dispensed from ATM:
20th X 5 = 100

ATM state
20th X 95 = 1900
50th X 38 = 1900
```

**Note** you can use word complete like what you did in linux shell. E.g. type `di` and then type tab you will get `dispense` immediately

Dispense Strategy
--------------------

This program implemented three dispense strategies:

1. `BigNoteFirst`. This strategy always choose larger denomination notes if possible

2. `BalancedNoteCount`. This strategy keeps balance among buckets based on their remaining notes

3. `BalancedValue`. This strategy keeps balance among buckets based on their remaining value

The default strategy is `BalancedNoteCount`

Program Dependencies
------------------------

* [junit](http://junit.org): Used to do unit test
* [osgl-tool](https://github.com/greenlaw110/java-tool): My open source library that help me applying functional programming in Java 6/7. It also provides a lot an utilities that make Java programming be more fun and more expressive
* [jcommander](http://jcommander.org/): Used to process command line arguments, something like '-20 100 -50 60' etc. I use it to parse `atm` command arguments
* [jline](http://jline.sourceforge.net/): An nice library to provide console programming support. I use it to program the command line shell to the ATM simulator

Code Metric
------------------

Main source code

```
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                            22            201            407            773
-------------------------------------------------------------------------------
SUM:                            22            201            407            773
-------------------------------------------------------------------------------
```

Test source code

```

-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                             3             51             38            260
-------------------------------------------------------------------------------
SUM:                             3             51             38            260
-------------------------------------------------------------------------------
```