[![Build](https://github.com/cscenter/kotlin-avl/actions/workflows/HW3.yml/badge.svg)](https://github.com/cscenter/kotlin-avl/actions/workflows/HW3.yml)

# Task 2. AVL tree

Implement an AVL tree with comparable keys that holds values and all of the following:
- Base: min/max keys/values, height 
- Map 
- List: subList() can be skipped or implemented via creating a separate tree (no tests)
- Mutable map 
- Bonus: Mutable list (you should write tests yourself)

Notice that `detekt` is failing due to "too many functions" in `AvlTreeImpl`. Please implement each interface separately whenever possible, instead of creating an almighty AVL tree. Detekt and diktat reports are stored as Github Actions artifacts.

**For the instructor, how to grade an assignment:**

1 point for each item + 2 points for general approach and code architecture.
