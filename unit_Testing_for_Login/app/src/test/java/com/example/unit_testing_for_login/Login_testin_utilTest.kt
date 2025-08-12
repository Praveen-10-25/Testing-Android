package com.example.unit_testing_for_login

import org.junit.Test
import com.google.common.truth.Truth.assertThat

class Login_testin_utilTest {

    @Test
    fun `empty user name`() {
        val result = Login_testin_util.validateRegistrationInput(
            "",
            "121212",
            "121212"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `valid username and correctly repeated password`(){
        val result=Login_testin_util.validateRegistrationInput(
            "phantom",
            "121212",
            "121212"
        )
        assertThat(result).isTrue()
    }
    @Test
    fun `username already taken`(){
        val result=Login_testin_util.validateRegistrationInput(
            "Phantom"
            ,"werewrew"
            ,"er23423"
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `empty pass`(){
        val result=Login_testin_util.validateRegistrationInput(
            "phantom",
            "",
            ""
        )
        assertThat(result).isFalse()
    }
    @Test
    fun `less than 5 char`(){
        val result=Login_testin_util.validateRegistrationInput(
            "phantom",
            "3434",
            "3434"
        )
        assertThat(result).isFalse()
    }

}