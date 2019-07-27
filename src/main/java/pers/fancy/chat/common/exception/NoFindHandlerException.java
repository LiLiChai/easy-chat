package pers.fancy.chat.common.exception;

/**
 * 找不到对应处理类
 *
 * @author 李醴茝
 */
public class NoFindHandlerException extends RuntimeException {

    private static final long serialVersionUID = 6724478022966267728L;

    public NoFindHandlerException(String message) {
        super(message);
    }

}
