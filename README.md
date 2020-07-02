# Mathematica
This library is useful to perform complex mathematical calculations. 

## Vectors
A vector has magnitude and dimension. The library provides __Vector__ interface, and __ArrayVector__ as it's implementations.
In order to create a vector; both class and interface decide in the package:

```java
package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.linears.vectors.Vector;
import org.katpara.mathematica.linears.vectors.ArrayVector;
```

### To Create a two-dimensional vector:
The easiest way to create a two-dimensional vector is by using the constructor that takes 2 arguments.
```java
Vector v = new ArrayVector(1, 2);
```

### To Create a three-dimensional vector:
The easiest way to create a three-dimensional vector is by using the constructor that takes 3 arguments.
```java
Vector v = new ArrayVector(1, 2, 3);
```


### To create a multi-dimensional vector
When you want to create a vector with more than 3 elements, you can create an array of numbers and pass it to the constructor.
You can create a two and three-dimensional constructor using this method but the techniques above, are recommended, since it's easier and used more frequently.
```java
Vector v = new ArrayVector(new Number[]{1, 2, 3, 4});
```

### To create a vector from a List
<ul>
    <li>It should have at least two elements.
    <li>The List must be a type of java.lang.Number
    <li>It should have non-null values.
</ul>
    
```java
Vector v = new ArrayVector(List.of(1, 2, 3));
```

### To create a vector from a set
When you create a vector from a set:
<ul>
    <li>It should have at least two elements.
    <li>The Set must be a type of java.lang.Number
    <li>All the elements must be non-null.
</ul>

```java
Vector v = new ArrayVector(Set.of(1, 2, 3));
```

### To create a vector from a Map
When you create a vector from a map:
<ul>
    <li>It should have at least two key-value pairs.
    <li>Values must be a subclass of java.lang.Number.
    <li>The keys will be ignored and all the values will be used to create a vector.
</ul>

```java
Vector v = new ArrayVector(Map.of("one", 1, "tow", 2, "three", 3));
```