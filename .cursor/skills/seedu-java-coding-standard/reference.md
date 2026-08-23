# SE-EDU Java standard — examples

Source: https://se-education.org/guides/conventions/java/intermediate.html

## Test method names

```
sortList_emptyList_exceptionThrown()
getMember_memberNotFound_nullReturned()
sortList_emptyList()   // all empty-list cases
sortList()             // all cases for sortList
```

## Wrapped lines (8 extra spaces)

```
setText("Long line split"
        + "into two parts.");
if (isReady) {
    setText("Long line split"
            + "into two parts.");
}
```

## Control-flow shape

```
if (condition) {
    statements;
} else if (condition) {
    statements;
} else {
    statements;
}

try {
    statements;
} catch (Exception exception) {
    statements;
} finally {
    statements;
}

switch (condition) {
    case ABC:
        statements;
        // Fallthrough
    case DEF:
        statements;
        break;
    default:
        statements;
        break;
}
```

## Javadoc shape

```
/**
 * Returns lateral location of the specified position.
 * If the position is unset, NaN is returned.
 *
 * @param x X coordinate of position.
 * @param y Y coordinate of position.
 * @return Lateral location.
 * @throws IllegalArgumentException If zone is <= 0.
 */
public double computeLocation(double x, double y, int zone) {
    // ...
}
```

Single-line member Javadoc is allowed:

```
/** Number of connections to this database */
private int connectionCount;
```
