package pers.fancy.chat.common.bean.vo;

import pers.fancy.chat.common.constant.CommonConstant;


/**
 * @author 李醴茝
 */
public class SendServer {

    private String type = CommonConstant.SERVER;

    private String value;

    public SendServer(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
