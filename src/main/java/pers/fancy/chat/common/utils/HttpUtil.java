package pers.fancy.chat.common.utils;

import pers.fancy.chat.common.bean.vo.SendServerVO;
import pers.fancy.chat.common.constant.CommonConstant;
import pers.fancy.chat.common.constant.HttpConstant;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.util.CharsetUtil;

import java.io.*;

/**
 * HTTP接口处理方法
 *
 * @author 李醴茝
 */
public class HttpUtil {

    public static String checkType(FullHttpRequest msg) {
        msg.retain();
        String url = msg.uri();
        System.out.println(url);
        HttpMethod method = msg.method();
        String meName = method.name();
        if (url.equals(HttpConstant.GET_SIZE) && meName.equals(HttpConstant.GET)) {
            return HttpConstant.GET_SIZE1;
        } else if (url.equals(HttpConstant.SEND_FROM_SERVER) && meName.equals(HttpConstant.POST)) {
            return HttpConstant.SEND_FROM_SERVER1;
        } else if (url.equals(HttpConstant.GET_LIST) && meName.equals(HttpConstant.GET)) {
            return HttpConstant.GET_LIST1;
        } else if (url.equals(HttpConstant.SEND_INCHAT) && meName.equals(HttpConstant.POST)) {
            return HttpConstant.SEND_INCHAT1;
        } else {
            return HttpConstant.NOT_FIND_URI;
        }
    }

    public static SendServerVO getToken(FullHttpRequest msg) throws UnsupportedEncodingException {
        msg.retain();
        SendServerVO sendServerVO = new SendServerVO();
        String content = msg.content().toString(CharsetUtil.UTF_8);
        String[] stars = content.split("&");
        for (int i = 0, len = stars.length; i < len; i++) {
            String item = stars[i].toString();
            String firstType = item.substring(0, 5);
            String value = item.substring(6, item.length());
            if (CommonConstant.TOKEN.equals(firstType)) {
                //Token
                sendServerVO.setToken(value);
            } else if (CommonConstant.VALUE.endsWith(firstType)) {
                //value
                sendServerVO.setValue(value);
            }
        }
        return sendServerVO;
    }


}
