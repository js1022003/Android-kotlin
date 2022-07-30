package com.example.kotlinbasics

import java.lang.IllegalArgumentException


open class Car(val name : String, val brand: String){
    open var range : Double = 0.0

    fun extendRange(amount: Double){
        if(amount > 0){
            range += amount
        }
    }

    open fun drive(distance: Double){
        println("$distance Km")
    }
}

class ElectricCar(name: String, brand: String, batteryLife: Double)
    : Car(name, brand){

     override var range = batteryLife * 6


    }

fun main(){

    val numbers = intArrayOf(1,2,3,4,5,6)

    print(numbers.contentToString())
}
//fun main(){
//    var junseo = Person("Kim","Junseo",22)
//    junseo.age = 23
//    junseo.hobby = "Health"
//    println("${junseo.firstName} ${junseo.lastName} is ${junseo.age} years old")
//    junseo.statehobby()
//
//}
//
//class Person (firstName: String="John", lastName: String= "Doe"){
//    var firstName : String ? = null
//    var lastName : String ? = null
//    var age : Int? = null
//    var hobby : String = "playing the bass"
//
//    init{
//        this.firstName = firstName
//        this.lastName = lastName
//        println("${firstName} ${lastName} created")
//    }
//
//    constructor(firstName: String, lastName: String, age: Int)
//            :this(firstName, lastName){
//                this.age =age
//            }
//
//
//    fun statehobby(){
//        println("$firstName's hobby is ${hobby}")
//    }
//}
//
//fun main(){
//    var myCar = Car()
//    println("brand is ${myCar.Brand}")
//    myCar.maxSpeed = 250
//    println("Maxspeed is ${myCar.maxSpeed}")
//}
//
//class Car(){
//    lateinit var owner : String
//
//    val Brand: String = "BMW"
//        get(){
//            return field.toLowerCase()
//        }
//
//    var maxSpeed: Int = 250
////        get() = field
//        set(value) {
//            field = if(value>0) value else throw IllegalArgumentException("Maxspeed to be greater than 0")
//        }
//
//    init{
//        this.owner = "Frank"
//    }
//}
