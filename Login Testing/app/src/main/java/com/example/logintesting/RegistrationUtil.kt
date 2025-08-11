package com.example.logintesting

object RegistrationUtil {
    private val existingUsers= listOf("phantom,ghost,joker,Ronaldo")

    fun validateRegistrationInput(
        username:String,
        password:String,
        confirmPassword:String):Boolean{
        if(username.isEmpty()||password.isEmpty()){
            return false
        }
        if(username in existingUsers){
            return false
        }
        if(password != confirmPassword){
            return false
        }
        if(password.count{it.isDigit()}<2){
            return false
        }
    return true
    }
}