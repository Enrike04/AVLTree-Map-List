[![Build](https://github.com/cscenter/kotlin-avl/actions/workflows/HW3.yml/badge.svg)](https://github.com/cscenter/kotlin-avl/actions/workflows/HW3.yml)

# Task 2. Balanced Search Tree

Implement a BST (i.e. AVL) with comparable keys. Using it, implement interfaces:
- Map 
- Mutable map
- List: `subList()` can be __skipped__ or implemented via creating a separate tree, there are no tests for it
- Bonus: Mutable list (you should write tests yourself)

Tests use methods declared in `HelperFunctions.kt` to get instances of interfaces, you should replace TODOs with code that returns your implementations.

Notice that `detekt` limits number of methods in a class. Please implement each interface separately whenever possible, instead of creating a single almighty class.

# Detekt and Diktat
To run detekt: `gradlew customDetekt`  
To run diktat: `gradlew diktatCheck`  
Or find corresponding Gradle tasks in IDEA's Gradle toolbar in 'Tasks > verification'.  
Reports of both detekt and diktat can be found as HTML files in '$projectDir/build/reports'.

**For the instructor, how to grade an assignment:**

1 point for each item + 2 points for general approach and code architecture.
