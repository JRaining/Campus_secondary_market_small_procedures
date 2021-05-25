package com.xiaojian.pick.page;

import lombok.Data;

/**
 * @author 小贱
 * @date 2020/10/12 - 19:03
 */
@Data
public class AjaxResult {
    private boolean success;
    private String message;

    public AjaxResult(){

    }

    public AjaxResult(boolean success){
        this.success = success;
    }

    public AjaxResult(boolean success,String message){
        this.success = success;
        this.message = message;
    }
}
