package pers.fancy.chat.common.bean.vo;

import java.util.Date;


/**
 * @author 李醴茝
 */
public class GetSizeVO {

    private Integer online;

    private Date time;

    public GetSizeVO(Integer online, Date time) {
        this.online = online;
        this.time = time;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
