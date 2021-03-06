package com.atguigu.gmall.pms.service.impl;

import com.atguigu.gmall.pms.dao.AttrAttrgroupRelationDao;
import com.atguigu.gmall.pms.dao.AttrDao;
import com.atguigu.gmall.pms.dao.ProductAttrValueDao;
import com.atguigu.gmall.pms.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gmall.pms.entity.AttrEntity;
import com.atguigu.gmall.pms.entity.AttrGroupEntity;
import com.atguigu.gmall.pms.entity.ProductAttrValueEntity;
import com.atguigu.gmall.pms.vo.GroupVo;
import com.atguigu.gmall.pms.vo.ItemGruopVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.pms.dao.AttrGroupDao;
import com.atguigu.gmall.pms.service.AttrGroupService;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private ProductAttrValueDao attrValueDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryGroupByPage(QueryCondition condition, Long catId) {
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<>();
        if (catId != null) {
            wrapper.eq("catelog_id",catId);
        }
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(condition),
                wrapper
        );

        return new PageVo(page);
    }

    @Override
    public GroupVo queryGroupWithAttrsByGid(Long gid) {

        GroupVo groupVo = new GroupVo();
        //查询gruop
        AttrGroupEntity groupEntity = this.getById(gid);
        BeanUtils.copyProperties(groupEntity,groupVo);

        //根据gid查询查询关联关系，并获取attrIds
        List<AttrAttrgroupRelationEntity> relations = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gid));
        if (CollectionUtils.isEmpty(relations)) {
            return groupVo;
        }
        groupVo.setRelations(relations);

        //根据attrIds查询，所有的规格参数
        List<Long> attrIds = relations.stream().map(relationEntity -> relationEntity.getAttrId()).collect(Collectors.toList());
        List<AttrEntity> attrEntities = this.attrDao.selectBatchIds(attrIds);
        groupVo.setAttrEntities(attrEntities);

        return groupVo;
    }

    @Override
    public List<GroupVo> queryGroupWithAttrsByCid(Long cId) {
        //根据cid查询三级分类下的所有属性分组
        List<AttrGroupEntity> groupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cId));


        //1.根据分组中id查询中间表
        //2.根据中间表中attrIds查询参数
        //2.数据类型的转化：attrGroupEntity-->groupVo
        return groupEntities.stream().map(attrGroupEntity ->
                this.queryGroupWithAttrsByGid(attrGroupEntity.getAttrGroupId())).collect(Collectors.toList());

    }

    @Override
    public List<ItemGruopVO> queryItemGruopVOByCidAndSpuId(Long cid, Long spuId) {
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", cid));
        return  attrGroupEntities.stream().map(gruop ->{
            ItemGruopVO itemGruopVO = new ItemGruopVO();

            itemGruopVO.setName(gruop.getAttrGroupName());

            //查询规格参数及值
            List<AttrAttrgroupRelationEntity> relationEntities = this.relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", gruop.getAttrGroupId()));
            List<Long> attrIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

            List<ProductAttrValueEntity> attrValueEntities = this.attrValueDao.selectList(new QueryWrapper<ProductAttrValueEntity>().eq("spu_id", spuId).in("attr_id", attrIds));
            itemGruopVO.setBaseAttrs(attrValueEntities);

            return itemGruopVO;
        }).collect(Collectors.toList());

    }

}