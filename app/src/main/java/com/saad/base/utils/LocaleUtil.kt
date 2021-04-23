package com.saad.base.utils

import java.util.*

class LocaleUtil {

    companion object {
        fun getLanguage(): String {
            return getLocal().language
        }

        fun getLocal(): Locale {
            return Locale.getDefault()
        }
    }
}