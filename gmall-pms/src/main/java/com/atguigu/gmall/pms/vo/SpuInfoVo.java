package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.SpuInfoEntity;
import lombok.Data;

import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/9/27 0027 10:36
 */
@Data
public class SpuInfoVo extends SpuInfoEntity {


    private List<String> spuImages;

    private List<BaseAttrVo> baseAttrs;

    private List<SkuInfoVo> skus;
}
