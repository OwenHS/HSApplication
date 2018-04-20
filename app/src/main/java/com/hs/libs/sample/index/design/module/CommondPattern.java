package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.commond.CreateCommand;
import com.hs.libs.sample.index.design.module.commond.EditCommond;
import com.hs.libs.sample.index.design.module.commond.Menu;
import com.hs.libs.sample.index.design.module.commond.MenuItem;
import com.hs.libs.sample.index.design.module.commond.OpenCommand;

/**
 * 命令模式测试类
 * <p>
 * Sunny软件公司欲开发一个基于Windows平台的公告板系统。
 * 该系统提供了一个主菜单(Menu)，在主菜单中包含了一些菜单项(MenuItem)，
 * 可以通过Menu类的addMenuItem()方法增加菜单项。菜单项的主要方法是click()，
 * 每一个菜单项包含一个抽象命令类，具体命令类包括OpenCommand(打开命令)，
 * CreateCommand(新建命令)，EditCommand(编辑命令)等，命令类具有一个execute()方法，
 * 用于调用公告板系统界面类(BoardScreen)的open()、create()、edit()等方法。
 * <p>
 * 试使用命令模式设计该系统，以便降低MenuItem类与BoardScreen类之间的耦合度。
 * Created by huangshuo on 17/10/27.
 */

public class CommondPattern extends AbstractPattern {
    @Override
    public String getTitle() {
        return "命令模式";
    }

    @Override
    public void testPattern() {
        Menu menu = new Menu();
        menu.addMenuItem(new MenuItem("打开", new OpenCommand()));
        menu.addMenuItem(new MenuItem("修改", new EditCommond()));
        menu.addMenuItem(new MenuItem("新建", new CreateCommand()));

        menu.display();

        menu.onclick(2);
        menu.onclick(0);
        menu.onclick(1);
    }
}
