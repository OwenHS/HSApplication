package com.hs.widget.datepick.view.listener;


import com.hs.widget.datepick.view.bean.DateBean;
import com.hs.widget.datepick.view.bean.DateType;

/**
 * Created by codbking on 2016/9/22.
 */

public interface WheelPickerListener {
     void onSelect(DateType type, DateBean bean);
}
