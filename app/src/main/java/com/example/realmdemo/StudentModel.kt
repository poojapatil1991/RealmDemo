package com.example.realmdemo

import io.realm.RealmObject

open class StudentModel: RealmObject (){

    var rollNo: String = ""
    var name: String= ""
    var schoolName: String= ""
    var standard: String = ""

}