package com.roudykk.presentation.mapper

import com.roudykk.domain.model.SpokenLanguage
import com.roudykk.presentation.model.SpokenLanguageView
import javax.inject.Inject

class SpokenLanguageViewMapper @Inject constructor() : ViewMapper<SpokenLanguageView, SpokenLanguage> {


    override fun mapToView(domain: SpokenLanguage): SpokenLanguageView {
        return SpokenLanguageView(name = domain.name,
                isoName = domain.isoName)
    }

}