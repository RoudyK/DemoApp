package com.demo.roudykk.demoapp.analytics.mixpanel

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI


class Mixpanel {

    companion object {
        private const val PROJECT_TOKEN = "4d0298b09e92358c753d297c95e808f2"

        fun getInstance(context: Context): MixpanelAPI? {
            return MixpanelAPI.getInstance(context, PROJECT_TOKEN)
        }
    }

}