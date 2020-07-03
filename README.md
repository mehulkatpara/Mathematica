# Mathematica
This library is useful to perform complex mathematical calculations. 

## Vectors
A vector has magnitude and dimension. The library provides __Vector__ interface, and __ArrayVector__ as it's implementations.
In order to create a vector; both class and interface decide in the package:

```
package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.linears.vectors.Vector;
import org.katpara.mathematica.linears.vectors.ArrayVector;
```

### The basics:
#### To Create a two-dimensional vector:
```
Vector v = new ArrayVector(1, 2);
```
The easiest way to create a two-dimensional vector is by using the constructor that takes 2 arguments.

#### To Create a three-dimensional vector:
```
Vector v = new ArrayVector(1, 2, 3);
```
The easiest way to create a three-dimensional vector is by using the constructor that takes 3 arguments.

#### To create a multi-dimensional vector
```
Vector v = new ArrayVector(new Number[]{1, 2, 3, 4});
```
When you want to create a vector with more than 3 elements, you can create an array of numbers and pass it to the constructor.
You can create a two and three-dimensional constructor using this method but the techniques above, are recommended, since it's easier and used more frequently.

#### To create a vector from a List
```
Vector v = new ArrayVector(List.of(1, 2, 3));
```
<ul>
    <li>It should have at least two elements.
    <li>The List must be a type of java.lang.Number
    <li>It should have non-null values.
</ul>

#### To create a vector from a set
```
Vector v = new ArrayVector(Set.of(1, 2, 3));
```
When you create a vector from a set:
<ul>
    <li>It should have at least two elements.
    <li>The Set must be a type of java.lang.Number
    <li>All the elements must be non-null.
</ul>

#### To create a vector from a Map
```
Vector v = new ArrayVector(Map.of("one", 1, "tow", 2, "three", 3));
```
When you create a vector from a map:
<ul>
    <li>It should have at least two key-value pairs.
    <li>Values must be a subclass of java.lang.Number.
    <li>The keys will be ignored and all the values will be used to create a vector.
</ul>

### What can you do with the vector object?
When you create a vector, you can perform following operations

#### To get the dimension of the vector
```
int dimension = v.getDimension();
```

#### To get the magnitude of a vector | v |
```
double magnitude = v.getMagnitude();
```

### To get Elements of Vector:
```
Number[] n = v.getElements();
```
Please note, I rely on JVM to determine the type of the array.
If you pass all integer values, the type of the array is "Integer". etc.
However, I perform all the operations using the double values. So a vector of an integer type, may would result in a vector of double type after some operations.

### Vector Operations:

The __VectorOperations__ is a concrete class, that contains static methods to perform various operations.
Please note that regardless of the element types to create an initial vector, such as Integer, Long, Byte, Short, ete,
all the methods here will return the __Double__ elements.

### To get an inverse vector
```
Vector v = new ArrayVector(1, 2);
Vector i = VectorOperations.getInverseVector(v);    //=> <-1.0, -2.0>
```

### To scale a vector
```
Vector v = new ArrayVector(1, 2, 3);
Vector s = VectorOperations.scale(v, 3);            //=> <3.0, 6.0, 9.0>
```
Please Note the following:
| Scalar Condition      |       The output vector                            |
|-----------------------|:--------------------------------------------------:|
| scalar &gt; 1         | It scales up the vector in the same direction.     |
| 0 &lt; scalar &lt; 1  | It shrinks the vector in the same direction.       |
|  scalar = 0           | The scaled vector becomes a zero vector.           |
| -1 &lt; scalar &lt; 0 | It shrinks the vector in the opposite direction.   |
| scalar &lt; -1        | It scales up the vector in the opposite direction. |

### Add two vectors
```
Vector v1 = new ArrayVector(0, 2, 4);
Vector v2 = new ArrayVector(0, 4, 8);

Vector a = VectorOperations.addVector(v1, v2);      //=> <0.0, 6.0, 12.0>

List<Vector> vl = new ArrayList<>(List.of(v1, v2));
Vector b = VectorOperations.addVector(vl);      //=> <0.0, 6.0, 12.0>
```

