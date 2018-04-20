package com.hs.libs.sample.index.design.module.build;

/**
 * 角色抽象类（建造者模式简单写法）
 * Created by huangshuo on 17/10/19.
 */

public class RoleSimple {
    private String type;
    private String sex;
    private String face;
    private String costume;
    private String hairstyle;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getCostume() {
        return costume;
    }

    public void setCostume(String costume) {
        this.costume = costume;
    }

    public String getHairstyle() {
        return hairstyle;
    }

    public void setHairstyle(String hairstyle) {
        this.hairstyle = hairstyle;
    }

    /**
     * 建造类
     */
    public static class Builder {

        private RoleSimple role;

        private String type;
        private String sex;
        private String face;
        private String costume;
        private String hairstyle;

        public void buildType(String type) {
            this.type = type;
        }

        public void buildSex(String sex) {
            this.sex = sex;
        }

        public void buildFace(String face) {
            this.face = face;
        }

        public void buildCostume(String costume) {
            this.costume = costume;
        }

        public void buildHairstyle(String hairstyle) {
            this.hairstyle = hairstyle;
        }

        public void build(){
            role = new RoleSimple();
        }
    }

}
