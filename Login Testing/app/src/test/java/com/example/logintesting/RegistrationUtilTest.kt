package com.example.logintesting



import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{
    @Test
    fun `empty user name`(){
        val result =RegistrationUtil.validateRegistrationInput(
            "",
            "121212",
            "121212"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `valid username and correctly repeated password`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "phantom",
            "121212",
            "121212"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun `username already taken`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "Phantom"
            ,"werewrew"
            ,"er23423"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `empty pass`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "phantom",
            "",
            ""
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `less than 5 char`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "phantom",
            "3434",
            "3434"
        )
        assertThat(result).isFalse()
    }

}