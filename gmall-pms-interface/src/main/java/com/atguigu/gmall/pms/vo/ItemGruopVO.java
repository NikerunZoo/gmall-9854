package com.atguigu.gmall.pms.vo;

import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import lombok.Data;

import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/9 0009 18:26
 */
@Data
public class ItemGruopVO {

    private String name;

    private List<ProductAttrValueEntity> baseAttrs;
}
