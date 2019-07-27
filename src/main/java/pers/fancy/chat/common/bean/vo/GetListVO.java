package pers.fancy.chat.common.bean.vo;

import java.util.Set;


/**
 * @author 李醴茝
 */
public class GetListVO {


    private Set<String> tokens;

    public GetListVO(Set<String> tokens) {
        this.tokens = tokens;
    }

    public Set<String> getTokens() {
        return tokens;
    }

    public void setTokens(Set<String> tokens) {
        this.tokens = tokens;
    }


}
