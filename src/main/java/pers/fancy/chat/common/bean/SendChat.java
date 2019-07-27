package pers.fancy.chat.common.bean;

import java.util.Map;


/**
 * @author 李醴茝
 */
public class SendChat {

    private String token;

    private Map<String,String> frame;

    public SendChat() {
    }

    public SendChat(String token, Map<String, String> frame) {
        this.token = token;
        this.frame = frame;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getFrame() {
        return frame;
    }

    public void setFrame(Map<String, String> frame) {
        this.frame = frame;
    }
}
