package com.xiaojian.pick.page;

import com.xiaojian.pick.entity.Seek;
import lombok.Data;

/**
 * @author 小贱
 * @date 2020/10/26 - 9:59
 */
@Data
public class SeekCustom extends Seek {

    private String startTime;   // 日期范围：开始时间
    private String endTime;     // 日期范围：终止时间

}
