package pers.fancy.chat.common.bean.vo;


/**
 * @author 李醴茝
 */
public class ResultVO<T> {

    private Integer code;

    private T data;

    public ResultVO(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultVO{" + "code=" + code + ", data=" + data + '}';
    }
}
