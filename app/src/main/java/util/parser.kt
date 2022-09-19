package com.example.myapplication2.util

/**This class contain all parsers which program need.*/

class Parsers {

    /**This parser take mail and convert it to the first name and second name.*/
    fun parseMail(mail: String): String {
        var (firstName, secondName) = mail.split("@").first().split(".")
        return "${firstName.capitalize()} ${secondName.capitalize()}"
    }
}