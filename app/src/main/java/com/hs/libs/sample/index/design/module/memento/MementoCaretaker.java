package com.hs.libs.sample.index.design.module.memento;

/**
 * Created by huangshuo on 17/10/24.
 */
//象棋棋子备忘录管理类：负责人
public class MementoCaretaker {
    private ChessmanMemento memento;

    public ChessmanMemento getMemento() {
        return memento;
    }

    public void setMemento(ChessmanMemento memento) {
        this.memento = memento;
    }
}
