package com.atguigu.gmall.search.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NikerunZoo
 * @date 2020/10/6 0006 18:31
 */
@Data
public class SearchResponseAttrVO {

    private Long productAttributeId;//1
    //当前属性值的所有值
    private List<String> value = new ArrayList<>();
    //属性名称
    private String name;//网络制式，分类
}
