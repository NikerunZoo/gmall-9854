package com.atguigu.gmall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.ums.entity.IntegrationChangeHistoryEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 积分变化历史记录
 *
 * @author NikerunZoo
 * @email 985423258@qq.com
 * @date 2020-09-24 22:21:05
 */
public interface IntegrationChangeHistoryService extends IService<IntegrationChangeHistoryEntity> {

    PageVo queryPage(QueryCondition params);
}

