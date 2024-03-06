package com.berkay.loginscreens.classes

class CategoryMaker {
    var id: Int = 0
    var categoryname: String = ""
    var switch : Boolean = false

    constructor(categoryname: String, switch : Boolean){
        this.categoryname = categoryname
        this.switch = switch
    }
    constructor()
}