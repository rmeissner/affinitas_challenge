package de.thegerman.affinitaschallenge.core.utils

import java.util.*

object StringUtils {

    /**
     * Returns true if a and b are equal, including if they are both null.
     *
     * *Note: Copied from TextUtils*

     * @param a first CharSequence to check
     * *
     * @param b second CharSequence to check
     * *
     * @return true if a and b are equal
     */
    fun equals(a: CharSequence?, b: CharSequence?): Boolean {
        if (a === b) {
            return true
        }
        val length = a?.length ?: 0
        if (a != null && b != null && length == b.length) {
            if (a is String && b is String) {
                return a == b
            } else {
                for (i in 0..length - 1) {
                    if (a[i] != b[i]) {
                        return false
                    }
                }
                return true
            }
        }
        return false
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * *Note: Copied from TextUtils*

     * @param str the string to be examined
     * *
     * @return true if str is null or zero length
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.isEmpty()
    }

    /**
     * Returns true if the string only contains letters (a-zA-Z)

     * @param s the string to be examined
     * *
     * @return true if s only contains letters
     */
    fun containsLettersOnly(s: String): Boolean {
        var valid = true
        val a = s.toCharArray()

        for (c in a) {
            valid = c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'

            if (!valid) {
                break
            }
        }
        return valid
    }

    fun upper(string: String): String {
        return string.toUpperCase(Locale.ENGLISH)
    }

    fun lower(string: String): String {
        return string.toLowerCase(Locale.ENGLISH)
    }

    fun format(format: String, vararg args: Any): String {
        return String.format(Locale.ENGLISH, format, *args)
    }
}
