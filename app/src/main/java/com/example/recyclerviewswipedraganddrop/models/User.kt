package com.example.recyclerviewswipedraganddrop.models

class User {

    var id: Int? = null
    var name: String? = null
    var password: String? = null

    constructor(id: Int?, name: String?, password: String?) {
        this.id = id
        this.name = name
        this.password = password
    }
}