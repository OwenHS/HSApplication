package com.hs.libs.sample.index.design.module;

/**
 * Created by huangshuo on 17/10/30.
 *
 * Sunny软件公司欲开发一套图形界面类库。
 * 该类库需要包含若干预定义的窗格(Pane)对象，例如TextPane、ListPane、GraphicPane等，
 * 窗格之间不允许直接引用。
 * 基于该类库的应用由一个包含一组窗格的窗口(Window)组成，窗口需要协调窗格之间的行为。
 * 试采用中介者模式设计该系统
 *
 */

public class MediatorPattern extends AbstractPattern{
    @Override
    public String getTitle() {
        return "中介者模式";
    }

    @Override
    public void testPattern() {

    }
}
