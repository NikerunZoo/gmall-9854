package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author NikerunZoo
 * @date 2020/10/6 0006 9:27
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {

}
