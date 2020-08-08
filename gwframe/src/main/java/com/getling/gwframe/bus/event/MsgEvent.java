package com.getling.gwframe.bus.event;

/**
 * @Author: getling
 * @CreateDate: 2019/12/12 13:34
 * @Description:
 */
public class MsgEvent extends BusEvent {
    private String msg;

    public MsgEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
