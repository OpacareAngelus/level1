package util

import java.util.*

/**This class contain all parsers which program need.*/

object Parsers {

    /**This parser take mail and convert it to the first name and second name.*/
    fun parseMail(mail: String): String {
        val (firstName, secondName) = mail.split("@").first().split(".")
        return "${
            firstName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
            }
        } ${
            secondName.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }"
    }
}