package com.flameking.lottery.rpc;

import com.flameking.lottery.rpc.dto.ActivityDto;
import com.flameking.lottery.rpc.req.ActivityReq;
import com.flameking.lottery.rpc.req.DrawReq;
import com.flameking.lottery.rpc.req.QuantificationDrawReq;
import com.flameking.lottery.rpc.res.ActivityRes;
import com.flameking.lottery.rpc.res.DrawRes;

/**
 * 抽奖活动展台接口
 *
 * @author WangWei
 * @dateTime 2023-06-19 18:22:03
 */
public interface IActivityBooth {

//     IPage<Activity> getPage(ActivityQuery query);

     /**
      * 活动展台
      * 1. 创建活动
      * 2. 更新活动
      * 3. 查询活动
      *
      * @param req
      * @return ActivityRes
      */
     ActivityRes queryActivityById(ActivityReq req);

     Long create(ActivityDto activityDto);
//
//     boolean update(Activity activity);
//
//     boolean del(String id);

     /**
      * 指定活动抽奖
      * @param drawReq 请求参数
      * @return        抽奖结果
      */
     DrawRes doDraw(DrawReq drawReq);

     /**
      * 量化人群抽奖
      * @param quantificationDrawReq 请求参数
      * @return                      抽奖结果
      */
     DrawRes doQuantificationDraw(QuantificationDrawReq quantificationDrawReq);


}

