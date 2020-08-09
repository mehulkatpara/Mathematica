# Mathematica
This library is useful to perform complex mathematical calculations. Currently, I have implemented the linear algebra, but It will be expanded to cover more.

## Vectors
A vector has magnitude and dimension. 
The library provides __Vector__ interface, and __ArrayVector__ as it's implementations, where __ArrayVector__ class used an array to create and compute a vector.
The vectors are immutable objects, which basically means that once they are created, any operations on them would result in a new vector.
In order to create a vector; both class and interface decide in the package:

```
package org.katpara.mathematica.linears.vectors;

import org.katpara.mathematica.linears.vectors.Vector;
import org.katpara.mathematica.linears.vectors.ArrayVector;
```

### The basics:
#### To quickly create a vector using:
```
// Generates two, three and four dimensional vectors
ArrayVector.of(1, 2);                                       //=> <1, 2>
ArrayVector.of(1, 2, 3);                                    //=> <1, 2, 3>
ArrayVector.of(1, 2, 3, 4);                                 //=> <1, 2, 3, 4>
```

#### To create a unit vector:
```
// 3 dimensional unit vector
Vector v = ArrayVector.of(3);                                //=> <1, 1, 1>
```

#### To create a vector with random values
```
// (dimension, precision)
ArrayVector.of(3, Rounding.POINT.THREE)                     //=> <0.234, 0.768, 0.882>

// (dimension, min, max, precision)
ArrayVector.of(3, 10, 20, Rounding.POINT.TWO)               //=> <14.24, 18.03, 17.82>
```

#### To create a vector using a function
```
// (dimension, lambda)
ArrayVector.of(2, (e) -> e * 1);                            //=> <0.0, 1.0>
// (dimension, lambda, precision)
ArrayVector.of(2, (e) -> e * 1.2, Rounding.POINT.ONE);      //=> <0.0, 1.2>
// (dimension, min, max, lambda)
ArrayVector.of(3, -1, 0, (e) -> e * 10)                     //=> <-3.75..., -6.80..., -8.27...>
// (dimension, min, max, lambda, precision)
ArrayVector.of(4, 0, 1, Math::log, Rounding.POINT.THREE)    //=> <-1.883, -0.108, -0.001, -0.186>
```

#### To create a vector from an array
```
Vector v = new ArrayVector(new Number[]{1, 2, 3,4});        //=> <1, 2, 3, 4>
```
<ul>
    <li>It should have at least two elements, or throws __InvalidVectorDimensionException__.
    <li>The array must be a type or subtype of java.lang.Number (i.e. int, Integer, double, Double, etc)
    <li>It should have non-null values.
</ul>

#### To create a vector from a List
```
Vector v = new ArrayVector(List.of(1, 2, 3));                       //=> <1, 2, 3>
```
<ul>
    <li>It should have at least two elements, or throws __InvalidVectorDimensionException__.
    <li>The List must be a type or subtype of java.lang.Number (i.e. int, Integer, double, Double, etc)
    <li>It should have non-null values.
</ul>

#### To create a vector from a set
```
// Use LinkedHashSet to make sure the insertion order
Vector v = new ArrayVector(new LinkedHashSet<>(List.of(1, 2, 3)));  //=> <1, 2, 3>
```
When you create a vector from a set:
<ul>
    <li>It should have at least two elements.
    <li>The Set must be a type of java.lang.Number
    <li>All the elements must be non-null.
</ul>

#### To create a vector from a Map
```
// Use LinkedHashMap to make sure the insertion order
Map<Integer, Integer> m1 = new LinkedHashMap<>();
m1.put(1, 1);
m1.put(2, 2);

Vector v = new ArrayVector(m1);                                     //=> <1, 2, 3>
```
When you create a vector from a map:
<ul>
    <li>It should have at least two key-value pairs.
    <li>Values must be a subclass of java.lang.Number.
    <li>The keys will be ignored and all the values will be used to create a vector.
</ul>

### Basic vector properties

#### To get the dimension of the vector
```
int dimension = v.getDimension();
```

#### To get the magnitude of a vector | v |
```
double magnitude = v.getMagnitude();
double magnitude = v.getMagnitude(Rounding.POINT.FIVE);     // Magnitude rounded to 5 decimal points
```

### To get vector elements
```
Number[] n = v.toArray();
List<Number> n = v.toList();
```
Please note, I rely on JVM to determine the type of the array.
If you pass all integer values, the type of the array is "Integer". etc. However, operations can change a vector from an integer type to double.

### To get cosines of a vector
```
double[] cosines = v.getCosines(Vector.Angle.DEGREE);
double[] cosines = v.getCosines(Vector.Angle.RADIAN, Rounding.POINT.TWO);
```
The method returns the cosines of a vector with each respective axioms. If the vector has a n-number of dimensions then the resulting array would have n number of cosines.

### Vector Operations:

#### To check if two vectors are orthogonal
```
boolean result = v.isOrthogonal(w);             //=> true or false
```
It throws __InvalidVectorDimensionException__ when, both vectors have different dimensions.

#### To check if two vectors are parallel
```
boolean result = v.isParallel(w);               //=> true or false
```
It throws __InvalidVectorDimensionException__ when, both vectors have different dimensions.

#### To get an angle between two vectors
```
// To get an angle in degrees
double angle = v.angle(w, Vector.Angle.DEGREE);

// To get an angle in Radian, with 3 decimal decimals
double angle = v.angle(w, Vector.Angle.RADIAN, Rounding.POINT.THREE);
```
It throws __InvalidVectorDimensionException__ when, both vectors have different dimensions.

#### To get an inverse vector
```
Vector i = v.inverse(v);  
```

#### To scale a vector
```
Vector s = v.scale(3);            //=> <1, 2, 3> => <3.0, 6.0, 9.0>
```
Please Note the following:
| Scalar Condition      |       The output vector                            |
|-----------------------|:--------------------------------------------------:|
| scalar &gt; 1         | It scales up the vector in the same direction.     |
| 0 &lt; scalar &lt; 1  | It shrinks the vector in the same direction.       |
|  scalar = 0           | The scaled vector becomes a zero vector.           |
| -1 &lt; scalar &lt; 0 | It shrinks the vector in the opposite direction.   |
| scalar &lt; -1        | It scales up the vector in the opposite direction. |

#### addition
```
Vector v = v.add(5);                    // Scalar addition
Vector v = v.add(w);                    // Vector addition
Vector v = v.add(List.of(w, x, z));     // Add the list of vectors

```

It throws __InvalidParameterProvidedException__ when, the list contains 1 or 0 elements.<br/>
It throws __InvalidVectorDimensionException__ when, vectors have different dimensions.

#### Subtract vectors
```
Vector v = v.subtract(w);                    // Vector addition
```
It throws __InvalidParameterProvidedException__ when, the list contains 1 or 0 elements.<br/>
It throws __InvalidVectorDimensionException__ when, vectors have different dimensions.

#### Transpose dimensions of vectors
```
Vector v = new ArrayVector(0, 4, 8);
Vector l = v.transpose(5);                  //=> <0.0, 4.0, 8.0, 0.0, 0.0>
Vector s = v.transpose(v, 2);               //=> <0.0, 4.0>
```

It throws __InvalidVectorDimensionException__ when,
<ul>
<li>The targeted dimension is less than 2.</li>
<li>The targeted dimension is the same as the vector dimension.</li>
</ul>

#### Dot product
```
double dp = v.dot(w);                        //=> 24.0
double dp = v.dot(w, Rounding.POINT.SIX);    //=> 24.073894
```
It throws __InvalidVectorDimensionException__ when, both vectors have different dimensions.

#### Cross product
```
Vector c = v.cross(w);         //=> <5.0, 1.0, 11.0>
```
It throws __InvalidVectorDimensionException__ when, both vectors are not three-dimensional.

#### Projecting the vector W on V 
```
// Scalar Projection
double sp = v.scalarProjection(w);                          //=> 30.298382938293892
double sp = v.scalarProjection(w, Rounding.POINT.FOUR);     //=> 30.2983

// Vector Projection
Vector x = v.vectorProjection(w);

// Vector Rejection
Vector x = v.vectorRejection(w);
 
```
It throws __InvalidVectorDimensionException__ when, both vectors have different dimensions.