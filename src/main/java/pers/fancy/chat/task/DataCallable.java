package pers.fancy.chat.task;

import pers.fancy.chat.bootstrap.data.InChatToDataBaseService;
import pers.fancy.chat.common.utils.MessageChangeUtil;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 获取结果的异步线程任务
 *
 * @author 李醴茝
 */
public class DataCallable implements Callable<Boolean> {

    /**
     * 用户读数据接口伪实现
     */
    private final InChatToDataBaseService inChatToDataBaseService;

    /**
     * 消息数据
     */
    private final Map<String, Object> maps;

    DataCallable(InChatToDataBaseService inChatToDataBaseService, Map<String, Object> maps) {
        this.inChatToDataBaseService = inChatToDataBaseService;
        this.maps = maps;
    }

    @Override
    public Boolean call() throws Exception {
        inChatToDataBaseService.writeMessage(MessageChangeUtil.Change(maps));
        return true;
    }
}
