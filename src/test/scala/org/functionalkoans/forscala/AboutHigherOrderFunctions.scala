package org.functionalkoans.forscala

import support.KoanSuite

class AboutHigherOrderFunctions extends KoanSuite {

  koan("Meet lambda. Anonymous function") {
    def lambda = {
      x: Int => x + 1
    }
    def result = List(1, 2, 3) map lambda
    result should be(List(2, 3, 4))
  }

  koan("Meet closure. Closure is any function that closes over the environment") {
    var incrementer = 1
    def closure = {
      x: Int => x + incrementer
    }
    val result = List(1, 2, 3) map closure
    result should be(List(2, 3, 4))
    incrementer = 2
    val result1 = List(1, 2, 3) map closure
    result1 should be(List(3, 4, 5))
  }

  koan("function returning another function") {
    def addWithoutSyntaxSugar(x: Int) = {
      new Function1[Int, Int]() {
        def apply(y: Int): Int = x + y
      }
    }
    addWithoutSyntaxSugar(1).isInstanceOf[Function1[_,_]] should be(true)

    def add(x: Int) = (y: Int) => x + y
    add(1).isInstanceOf[Function1[_,_]] should be(true)
    add(2)(3) should be(5)

    def fiveAdder = add(5)
    fiveAdder(5) should be(10)
  }


  koan("function taking another function as parameter. Helps in compositioning functions") {
    def makeUpper(xs: List[String]) = xs map {_.toUpperCase}
    def makeWhatEverYouLike(xs: List[String], sideEffect: String => String) = {
      xs map sideEffect
    }

    makeUpper(List("abc", "xyz", "123")) should be(List("ABC", "XYZ", "123"))

    makeWhatEverYouLike(List("ABC", "XYZ", "123"), {
      x => x.toLowerCase
    }) should be(List("abc", "xyz", "123"))
    //using it inline
    List("Scala", "Erlang", "Clojure") map {_.length} should be(List(5,6,7))
  }

  koan("Currying is a technique to transform function with multiple parameters to function with one parameter") {
    def multiply(x: Int, y: Int) = x * y
    (multiply _).isInstanceOf[Function2[_, _, _]] should be(__)
    val multiplyCurried = (multiply _).curried
    multiply(4, 5) should be(__)
    multiplyCurried(3)(2) should be(__)
  }

  koan("Currying allows you to create specialized version of generalized function") {
    def customFilter(f: Int => Boolean)(xs: List[Int]) = {
      xs filter f
    }
    def onlyEven(x: Int) = x % 2 == 0
    val xs = List(12, 11, 5, 20, 3, 13, 2)
    customFilter(onlyEven)(xs) should be(__)

    val onlyEvenFilter = customFilter(onlyEven) _
    onlyEvenFilter(xs) should be(__)

  }
}
