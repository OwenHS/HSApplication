package com.hs.widget.timepicket.listener;


import com.hs.widget.timepicket.TimePickerDialog;

/**
 * Created by jzxiang on 16/4/20.
 */
public interface OnDateSetListener {

    void onDateSet(TimePickerDialog timePickerView, long millseconds);

    void onCancel(TimePickerDialog timePickerView);
}
