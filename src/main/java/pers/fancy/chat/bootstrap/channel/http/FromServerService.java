package pers.fancy.chat.bootstrap.channel.http;

public interface FromServerService<Integer> {

    Integer getCode();

    String findByCode(Integer code);
}
