package pers.fancy.chat.common.exception;

/**
 * 未找到正常注册的连接异常
 *
 * @author 李醴茝
 */
public class NotFindLoginChannelException extends RuntimeException {

    private static final long serialVersionUID = -2614068393411382075L;

    public NotFindLoginChannelException(String message) {
        super(message);
    }

}
