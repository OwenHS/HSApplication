package com.hs.libs.sample.widgetdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hs.libs.sample.R;
import com.hs.libs.sample.widgetdemo.extend.ViewUtils;
import com.hs.widget.layout.ColumnLayout;

/**
 * TODO:功能说明
 *
 * @author: rexy
 * @date: 2017-06-05 15:03
 */
public class FragmentColumnLayout extends FragmentViewPicker {
    ColumnLayout mColumnLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_columnlayout,container,false);
        mColumnLayout= ViewUtils.view(root,R.id.columnLayout);
        buildRandomView(10, false);
        return root;
    }

    @Override
    protected void onFastAddView() {
        buildRandomView(1, false);
    }

    @Override
    protected void onAddOrRemoveView(View addView) {
        if(addView==null){
            int minRemainCount=3;
            if(mColumnLayout.getChildCount()>minRemainCount){
                mColumnLayout.removeViewAt(mColumnLayout.getChildCount()-1);
            }
        }else {
            mColumnLayout.addView(addView);
        }
    }
}