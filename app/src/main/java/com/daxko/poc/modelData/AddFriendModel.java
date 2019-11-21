package com.daxko.poc.modelData;

public class AddFriendModel {
    String name;
    String memberId;
    boolean isAdd;

    public AddFriendModel(String name, String memberId, boolean isAdd) {
        this.name = name;
        this.memberId = memberId;
        this.isAdd = isAdd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }
}
