package com.technzone.base.ui.auth.language

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.technzone.base.common.CommonEnums
import com.technzone.base.data.pref.configuration.ConfigurationPref
import com.technzone.base.ui.base.viewmodel.BaseViewModel

class LanguageViewModel @ViewModelInject constructor(
    private val configurationpref: ConfigurationPref
) : BaseViewModel() {

    val englishSelected: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>(true) }
    fun onEnglishClicked() {
        englishSelected.postValue(true)
    }

    fun onArabicClicked() {
        englishSelected.postValue(false)
    }

    fun saveLanguage() = liveData {
        configurationpref.setAppLanguageValue(if (englishSelected.value!!) CommonEnums.Languages.English.value else CommonEnums.Languages.Arabic.value)
        emit(null)
    }

}