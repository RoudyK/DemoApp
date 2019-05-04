package com.demo.roudykk.demoapp.controllers.models;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.ModelView;
import com.demo.roudykk.demoapp.ui.view.LinePagerIndicatorDecoration;

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
public class IndicatorCarousel extends Carousel {

    public IndicatorCarousel(Context context) {
        super(context);
        addItemDecoration(new LinePagerIndicatorDecoration());
    }

    @Nullable
    @Override
    protected SnapHelperFactory getSnapHelperFactory() {
        return super.getSnapHelperFactory();
    }

    @NonNull
    @Override
    protected LayoutManager createLayoutManager() {
        return super.createLayoutManager();
    }
}
