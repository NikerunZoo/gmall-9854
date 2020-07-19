package com.atguigu.gmall.pms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.pms.entity.UndoLogEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 
 *
 * @author NikerunZoo
 * @email 985423258@qq.com
 * @date 2020-07-17 19:41:25
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageVo queryPage(QueryCondition params);
}

