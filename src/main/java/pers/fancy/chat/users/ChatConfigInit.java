package pers.fancy.chat.users;

import pers.fancy.chat.common.bean.InitNetty;


/**
 * @author 李醴茝
 */
public class ChatConfigInit extends InitNetty {

    @Override
    public int getWebport() {
        return 8090;
    }

    @Override
    public Boolean getDistributed() {
        return false;
    }

    @Override
    public boolean isSsl() {
        return false;
    }

}
