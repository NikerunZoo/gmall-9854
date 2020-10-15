package com.atguigu.gmall.sms.service.impl;

import com.atguigu.gmall.sms.dao.SkuFullReductionDao;
import com.atguigu.gmall.sms.dao.SkuLadderDao;
import com.atguigu.gmall.sms.entity.SkuFullReductionEntity;
import com.atguigu.gmall.sms.entity.SkuLadderEntity;
import com.atguigu.gmall.sms.vo.SaleVO;
import com.atguigu.gmall.sms.vo.SkuSaleVo;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.Query;
import com.atguigu.core.bean.QueryCondition;

import com.atguigu.gmall.sms.dao.SkuBoundsDao;
import com.atguigu.gmall.sms.entity.SkuBoundsEntity;
import com.atguigu.gmall.sms.service.SkuBoundsService;
import org.springframework.transaction.annotation.Transactional;


@Service("skuBoundsService")
public class SkuBoundsServiceImpl extends ServiceImpl<SkuBoundsDao, SkuBoundsEntity> implements SkuBoundsService {
    @Autowired
    private SkuLadderDao skuLadderDao;
    @Autowired
    private SkuFullReductionDao skuFullReductionDao;

    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SkuBoundsEntity> page = this.page(
                new Query<SkuBoundsEntity>().getPage(params),
                new QueryWrapper<SkuBoundsEntity>()
        );

        return new PageVo(page);
    }
    @Transactional
    @Override
    public void saveSale(SkuSaleVo skuSaleVo) {

        //3.保存营销相关的3张表

        //3.1保存sms_sku_bounds

        SkuBoundsEntity skuBoundsEntity = new SkuBoundsEntity();
        skuBoundsEntity.setSkuId(skuSaleVo.getSkuId());
        skuBoundsEntity.setBuyBounds(skuSaleVo.getBuyBounds());
        skuBoundsEntity.setGrowBounds(skuSaleVo.getGrowBounds());
        List<Integer> work = skuSaleVo.getWork();
        skuBoundsEntity.setWork(work.get(3)*1+work.get(2)*2+work.get(1)*4+work.get(0)*8);
        this.save(skuBoundsEntity);

        //3.2保存sms_sku_ladder
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(skuSaleVo.getSkuId());
        skuLadderEntity.setDiscount(skuSaleVo.getDiscount());
        skuLadderEntity.setFullCount(skuSaleVo.getFullCount());
        skuLadderEntity.setAddOther(skuSaleVo.getLadderAddOther());
        this.skuLadderDao.insert(skuLadderEntity);

        //3.3保存sms_sku_full_reduction

        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        skuFullReductionEntity.setFullPrice(skuSaleVo.getFullPrice());
        skuFullReductionEntity.setAddOther(skuSaleVo.getFullAddOther());
        skuFullReductionEntity.setSkuId(skuSaleVo.getSkuId());
        skuFullReductionEntity.setReducePrice(skuSaleVo.getReducePrice());
        this.skuFullReductionDao.insert( skuFullReductionEntity);
    }

    @Override
    public List<SaleVO> querySalesBySkuId(Long skuId) {

       List<SaleVO> saleVOS = new ArrayList<SaleVO>();
        //查询积分信息
        SkuBoundsEntity skuBoundsEntity = this.getOne(new QueryWrapper<SkuBoundsEntity>().eq("sku_id", skuId));
        if (skuBoundsEntity !=null){
            SaleVO boundsVo = new SaleVO();
            boundsVo.setType("积分");
            StringBuffer sb = new StringBuffer();
            if (skuBoundsEntity.getGrowBounds() != null && skuBoundsEntity.getGrowBounds().intValue() >0){
                sb.append("成长积分送"+skuBoundsEntity.getGrowBounds());
            }
            if (skuBoundsEntity.getBuyBounds() != null && skuBoundsEntity.getBuyBounds().intValue() >0){
                if (StringUtils.isNotBlank(String.valueOf(sb))){
                    sb.append(",");
                }
                sb.append("赠送积分送"+skuBoundsEntity.getBuyBounds());
            }
            boundsVo.setDesc(sb.toString());
            saleVOS.add(boundsVo);
        }


        //查询打折信息
        SkuLadderEntity skuLadderEntity = this.skuLadderDao.selectOne(new QueryWrapper<SkuLadderEntity>().eq("sku_id", skuId));
        if (skuLadderEntity != null){
            SaleVO ladderVo = new SaleVO();
            ladderVo.setType("打折");
            ladderVo.setDesc("满"+skuLadderEntity.getFullCount()+"件，打"+skuLadderEntity.getDiscount().divide(new BigDecimal(10))+"折");
            saleVOS.add(ladderVo);
        }

        //查询满减信息
        SkuFullReductionEntity skuFullReductionEntity = this.skuFullReductionDao.selectOne(new QueryWrapper<SkuFullReductionEntity>().eq("sku_id", skuId));
        if (skuFullReductionEntity != null){
            SaleVO reductionVO = new SaleVO();
            reductionVO.setType("满减");
            reductionVO.setDesc("满"+skuFullReductionEntity.getFullPrice()+"元，减"+skuFullReductionEntity.getReducePrice()+"元");
            saleVOS.add(reductionVO);
        }



        return saleVOS;
    }

}