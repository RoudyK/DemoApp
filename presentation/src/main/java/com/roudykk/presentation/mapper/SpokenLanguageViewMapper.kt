package com.roudykk.presentation.mapper

import com.roudykk.domain.model.SpokenLanguage
import com.roudykk.presentation.model.SpokenLanguageView
import javax.inject.Inject

class SpokenLanguageViewMapper @Inject constructor() : ViewMapper<SpokenLanguageView, SpokenLanguage> {

    override fun mapFromView(view: SpokenLanguageView): SpokenLanguage {
        return SpokenLanguage(name = view.name,
                isoName = view.isoName)
    }


    override fun mapToView(domain: SpokenLanguage): SpokenLanguageView {
        return SpokenLanguageView(name = domain.name,
                isoName = domain.isoName)
    }

}