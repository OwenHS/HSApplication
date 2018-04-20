package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.memento.Chessman;
import com.hs.libs.sample.index.design.module.memento.MementoCaretaker;

/**
 * Created by huangshuo on 17/10/24.
 */

public class MementoPattern extends AbstractPattern {
    @Override
    public String getTitle() {
        return "备忘录模式";
    }

    @Override
    public void testPattern() {
        MementoCaretaker mc = new MementoCaretaker();
        Chessman chess = new Chessman("车", 1, 1);
        display(chess);
        mc.setMemento(chess.save()); //保存状态
        chess.setY(4);
        display(chess);
        mc.setMemento(chess.save()); //保存状态
        display(chess);
        chess.setX(5);
        display(chess);
        System.out.println("******悔棋******");
        chess.restore(mc.getMemento()); //恢复状态
        display(chess);
    }

    public static void display(Chessman chess) {
        System.out.println("棋子" + chess.getLabel() + "当前位置为：" + "第" + chess.getX() + "行" + "第" + chess.getY() + "列。");
    }

}
