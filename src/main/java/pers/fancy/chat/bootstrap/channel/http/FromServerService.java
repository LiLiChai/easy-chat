package pers.fancy.chat.bootstrap.channel.http;

/**
 * @author 李醴茝
 */
public interface FromServerService<Integer> {

    Integer getCode();

    String findByCode(Integer code);
}
