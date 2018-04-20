package com.hs.libs.sample.widgetdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hs.libs.sample.R;
import com.hs.libs.sample.widgetdemo.extend.BaseFragment;
/**
 * TODO:功能说明
 *
 * @author: rexy
 * @date: 2017-06-05 15:03
 */
public class FragmentHierarchyLayout extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hierarchylayout,container,false);
    }
}
