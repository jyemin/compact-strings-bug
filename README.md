# compact-strings-bug

A reproduction of a bug in the JDK related to compact strings. It's been verified and is available [here](https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8262739).
                                                   
Scenario:

Using the [StringUtils.reverse](https://github.com/apache/commons-lang/blob/LANG_2_6/src/main/java/org/apache/commons/lang/StringUtils.java) method from `commons-lang:commons-lang:2.6`, repeatedly reverse the same string
in a loop until `Integer.MAX_VALUE` iterations are completed, or until the result of `StringUtils.reverse` returns
an unexpected result 5 times.

Expected behavior is that the loop will exit after `Integer.MAX_VALUE` iterations.

Actual behavior is that after several tens of thousands of iterations (different number of iterations in each run)
`StringUtils.reverse` starts returning an incorrect result.  Furthermore, once it returns an incorrect result, it
returns the same incorrect result on every subsequent iteration.  It also seems to be the case that there is a single
incorrect character in the reversed string.  It's always the last character, and instead of being the expected first 
character from the original string, it's the last character from the original string.  For example:

```
Original string: 123456
Expected reversed string: 654321

Iteration: 159080
Actual: 654326

Iteration: 159081
Actual: 654326

Iteration: 159082
Actual: 654326

Iteration: 159083
Actual: 654326

Iteration: 159084
Actual: 654326

Exiting after 5 failures
```

A six character string is the shortest that I've been able to reproduce this with.                 

To run the example, at the command line just execute `./gradlew run`.

The incorrect behavior does not reproduce in the following scenarios

1. The original string contains a multibyte character, e.g `"12345\u1234"` 
   (See commented out code in CompactStringBug class).
2. The optimizer is disabled via `"-Djava.compiler=NONE"` 
   (There is a commented-out line in build.gradle.kts which will set this option).
3. Compact strings are disabled via `"-XX:-CompactStrings"`
   (There is a commented-out line in build.gradle.kts which will set this option).
4. The `_inflateStringC` intrinsic is disabled via `"-XX:DisableIntrinsic=_inflateStringC"` 
   (There is a commented-out line in build.gradle.kts which will set this option).
                                                                                   
This has been reproduced on OS X with both Java 11 and Java 15.  For example:

```
$ java -version
openjdk version "11.0.2" 2019-01-15
OpenJDK Runtime Environment 18.9 (build 11.0.2+9)
OpenJDK 64-Bit Server VM 18.9 (build 11.0.2+9, mixed mode)

$ ./gradlew run

> Task :run FAILED
Original string: 123456
Expected reversed string: 654321

Iteration: 118213
Actual: 654326

Iteration: 118214
Actual: 654326

Iteration: 118215
Actual: 654326

Iteration: 118216
Actual: 654326

Iteration: 118217
Actual: 654326

Exiting after 5 failures

```

   
