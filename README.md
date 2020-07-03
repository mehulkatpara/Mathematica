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
Vector v = new ArrayVector(1, 2);                                       //=> <1, 2>
```
The easiest way to create a two-dimensional vector is by using the constructor that takes 2 arguments.

#### To Create a three-dimensional vector:
```
Vector v = new ArrayVector(1, 2, 3);                                    //=> <1, 2, 3>
```
The easiest way to create a three-dimensional vector is by using the constructor that takes 3 arguments.

#### To create a multi-dimensional vector
```
Vector v = new ArrayVector(new Number[]{1, 2, 3, 4});                   //=> <1, 2, 3, 4>
```
This is way preferable to create a vector with more than 3 dimensions; otherwise use the other two ways 
for the simplicity.<br/>
It throws __InvalidVectorDimension__ when, the array has less than 2 elements.<br/>
It throws __NullArgumentProvided__ when, the array contains null values.

#### To create a vector from a List
```
Vector v = new ArrayVector(List.of(1, 2, 3));                           //=> <1, 2, 3>
```
<ul>
    <li>It should have at least two elements, or throws __InvalidVectorDimension__.
    <li>The List must be a type of java.lang.Number
    <li>It should have non-null values.
</ul>

#### To create a vector from a set
```
Vector v = new ArrayVector(Set.of(1, 2, 3));                            //=> <1, 2, 3>
```
When you create a vector from a set:
<ul>
    <li>It should have at least two elements.
    <li>The Set must be a type of java.lang.Number
    <li>All the elements must be non-null.
</ul>

#### To create a vector from a Map
```
Vector v = new ArrayVector(Map.of("one", 1, "tow", 2, "three", 3));     //=> <1, 2, 3>
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

#### To get an inverse vector
```
Vector v = new ArrayVector(1, 2);
Vector i = VectorOperations.getInverseVector(v);    //=> <-1.0, -2.0>
```

#### To scale a vector
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

#### Add vectors
```
Vector v1 = new ArrayVector(0, 2, 4);
Vector v2 = new ArrayVector(0, 4, 8);

// If you have 2 vectors
Vector a = VectorOperations.addVector(v1, v2);          //=> <0.0, 6.0, 12.0>

// If you have a list of Vectors
List<Vector> vl = new ArrayList<>(List.of(v1, v2));
Vector b = VectorOperations.addVector(vl);              //=> <0.0, 6.0, 12.0>
```
It throws __InvalidParameterProvided__ when, the list contains 1 or 0 elements.<br/>
It throws __InvalidVectorDimension__ when, vectors have different dimensions.

#### Subtract vectors
```
Vector v1 = new ArrayVector(0, 4, 8);
Vector v2 = new ArrayVector(0, 2, 4);

// Subtract v2 from v1
Vector a = VectorOperations.addVector(v1, v2);          //=> <0.0, 2.0, 4.0>

// Subtract all the other vectors from the vector vl[0].
List<Vector> vl = new ArrayList<>(List.of(v1, v2));
Vector b = VectorOperations.addVector(vl);              //=> <0.0, 2.0, 4.0>
```
It throws __InvalidParameterProvided__ when, the list contains 1 or 0 elements.<br/>
It throws __InvalidVectorDimension__ when, vectors have different dimensions.

#### Transpose dimensions of vectors
```
Vector v = new ArrayVector(0, 4, 8);
Vector l = VectorOperations.transpose(v, 5);            //=> <0.0, 4.0, 8.0, 0.0, 0.0>
Vector s = VectorOperations.transpose(v, 2);            //=> <0.0, 4.0>

Vector err1 = VectorOperations.transpose(v, 1);         //=> less than 2
Vector err2 = VectorOperations.transpose(v, 3);         //=> the same as v.getDimension()
```
It throws __InvalidVectorDimension__ when,
<ul>
<li>The targeted dimension is less than 2.</li>
<li>The targeted dimension is the same as the vector dimension.</li>
</ul>

#### Dot product
```
Vector v1 = new ArrayVector(3, 4);
Vector v2 = new ArrayVector(4, 3);

double dp = VectorOperations.dotProduct(v1, v2)         //=> 24.0
```
It throws __InvalidVectorDimension__ when, both vectors have different dimensions.

#### Cross product
```
Vector v1 = new ArrayVector(2, 1, -1);
Vector v2 = new ArrayVector(-3, 4, 1);

Vector r = VectorOperations.crossProduct(v1, v2)         //=> <5.0, 1.0, 11.0>
```
It throws __InvalidVectorDimension__ when, both vectors are not three-dimensional.

#### Get an angle between two products
```
Vector v1 = new ArrayVector(2, 2);
Vector v2 = new ArrayVector(0, 3);

double a1 = VectorOperations.angle(v1, v2, true);       //=> 45.00000000000001  in Degrees
double a2 = VectorOperations.angle(v1, v2, false);      //=> 0.7853981633974484 in Radian
```
It throws __InvalidVectorDimension__ when, both vectors have different dimensions.