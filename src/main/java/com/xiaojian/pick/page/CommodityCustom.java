package com.xiaojian.pick.page;

import com.xiaojian.pick.entity.Commodity;
import lombok.Data;

/**
 * @author 小贱
 * @date 2020/10/27 - 8:22
 */
@Data
public class CommodityCustom extends Commodity {

    private String startTime;   // 日期范围：开始时间
    private String endTime;     // 日期范围：终止时间
}
