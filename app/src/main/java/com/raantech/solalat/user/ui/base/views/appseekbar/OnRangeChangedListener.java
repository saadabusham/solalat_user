package com.raantech.solalat.user.ui.base.views.appseekbar;

import com.raantech.solalat.user.data.models.Price;

public interface OnRangeChangedListener {
    void onRangeChanged(AppSeekBar view, float leftValue, float rightValue, boolean isFromUser);

    void onStartTrackingTouch(AppSeekBar view, boolean isLeft);

    void onStopTrackingTouch(AppSeekBar view, boolean isLeft, Price condition);
}
