package com.atguigu.gmall.item.service;

import com.atguigu.core.bean.Resp;
import com.atguigu.gmall.item.config.ThreadPoolConfig;
import com.atguigu.gmall.item.feign.GmallPmsClient;
import com.atguigu.gmall.item.feign.GmallSmsClient;
import com.atguigu.gmall.item.feign.GmallWmsClient;
import com.atguigu.gmall.item.vo.ItemVO;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.vo.ItemGruopVO;
import com.atguigu.gmall.sms.vo.SaleVO;
import com.atguigu.gmall.wms.entity.WareSkuEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author NikerunZoo
 * @date 2020/10/9 0009 18:36
 */
@Service
public class ItemService {

    @Autowired
    private GmallPmsClient pmsClient;
    @Autowired
    private GmallWmsClient wmsClient;
    @Autowired
    private GmallSmsClient smsClient;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    public ItemVO queryItemVO(Long skuId) {
        ItemVO itemVO = new ItemVO();

        //设置skuId
        itemVO.setSkuId(skuId);



        //根据id查询sku
        CompletableFuture<Object> skuCompletableFuture = CompletableFuture.supplyAsync(() -> {

            Resp<SkuInfoEntity> skuResp = this.pmsClient.querySkuById(skuId);
            SkuInfoEntity skuInfoEntity = skuResp.getData();
            if (skuInfoEntity == null) {
                return itemVO;
            }
            itemVO.setSkuTitle(skuInfoEntity.getSkuTitle());
            itemVO.setSubTitle(skuInfoEntity.getSkuSubtitle());
            itemVO.setPrice(skuInfoEntity.getPrice());
            itemVO.setWeight(skuInfoEntity.getWeight());
            itemVO.setSpuId(skuInfoEntity.getSpuId());
            return skuInfoEntity;
        }, threadPoolExecutor);

        //根据sku中的spuId查询spu
        CompletableFuture<Void> spuCompletableFuture = skuCompletableFuture.thenAcceptAsync(sku -> {

            Resp<SpuInfoEntity> spuResp = this.pmsClient.querySpuById(((SkuInfoEntity) sku).getSpuId());
            SpuInfoEntity spuInfoEntity = spuResp.getData();

            if (spuInfoEntity != null) {

                itemVO.setSpuName(spuInfoEntity.getSpuName());
            }
        }, threadPoolExecutor);


        //根据skuId查询图片列表
        CompletableFuture<Void> imageCompletableFuture = CompletableFuture.runAsync(() -> {

            Resp<List<SkuImagesEntity>> skuImagesResp = this.pmsClient.querySkuImagesBySkuId(skuId);
            List<SkuImagesEntity> skuImagesEntities = skuImagesResp.getData();
            itemVO.setPics(skuImagesEntities);
        }, threadPoolExecutor);

        //根据sku中brandId和CategoryId查询品牌和分类
        CompletableFuture<Void> brandCompletableFuture = skuCompletableFuture.thenAcceptAsync(sku -> {

            Resp<BrandEntity> brandEntityResp = this.pmsClient.queryBrandById(((SkuInfoEntity) sku).getBrandId());
            BrandEntity brandEntity = brandEntityResp.getData();
            itemVO.setBrandEntity(brandEntity);
        }, threadPoolExecutor);

        CompletableFuture<Void> catCompletableFuture = skuCompletableFuture.thenAcceptAsync(sku -> {

            Resp<CategoryEntity> categoryEntityResp = this.pmsClient.queryCategoryById(((SkuInfoEntity) sku).getCatalogId());
            CategoryEntity categoryEntity = categoryEntityResp.getData();
            itemVO.setCategoryEntity(categoryEntity);
        }, threadPoolExecutor);

        //根据skuId查询营销信息
        CompletableFuture<Void> saleCompletableFuture = CompletableFuture.runAsync(() -> {

            Resp<List<SaleVO>> salesResp = this.smsClient.querySalesBySkuId(skuId);
            List<SaleVO> saleVOList = salesResp.getData();
            itemVO.setSalse(saleVOList);
        }, threadPoolExecutor);

        //根据skuId查询库存信息
        CompletableFuture<Void> storeCompletableFuture = CompletableFuture.runAsync(() -> {

            Resp<List<WareSkuEntity>> wareResp = this.wmsClient.queryWareSkusBySkuId(skuId);
            List<WareSkuEntity> wareSkuEntities = wareResp.getData();
            itemVO.setStore(wareSkuEntities.stream().anyMatch(wareSkuEntity -> wareSkuEntity.getStock() > 0));
        }, threadPoolExecutor);

        //根据spuId查询所有skuId,再去查询所有的销售属性
        CompletableFuture<Void> saleAttrCompletableFuture = skuCompletableFuture.thenAcceptAsync(sku -> {

            Resp<List<SkuSaleAttrValueEntity>> saleAttrValueResp = this.pmsClient.querySkuSaleAttrValuesBySpuId(((SkuInfoEntity) sku).getSpuId());
            List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = saleAttrValueResp.getData();
            itemVO.setSaleAttrs(skuSaleAttrValueEntities);
        }, threadPoolExecutor);


        //根据spuId查询商品描述（海报）
        CompletableFuture<Void> descCompletableFuture = skuCompletableFuture.thenAcceptAsync(sku -> {

            Resp<SpuInfoDescEntity> spuInfoDescResp = this.pmsClient.querySpuDescBySpuId(((SkuInfoEntity) sku).getSpuId());
            SpuInfoDescEntity descEntity = spuInfoDescResp.getData();
            if (descEntity != null) {
                String decript = descEntity.getDecript();
                String[] split = StringUtils.split(decript, ",");
                itemVO.setImages(Arrays.asList(split));
            }
        }, threadPoolExecutor);

        //根据spuId和cateId查询组及组下的规格参数（带值）
        CompletableFuture<Void> gruopCompletableFuture = skuCompletableFuture.thenAcceptAsync(sku -> {

            Resp<List<ItemGruopVO>> itemGruopResp = this.pmsClient.queryItemGruopVOByCidAndSpuId(((SkuInfoEntity) sku).getCatalogId(), ((SkuInfoEntity) sku).getSpuId());
            List<ItemGruopVO> itemGruopVOS = itemGruopResp.getData();
            itemVO.setGruops(itemGruopVOS);
        }, threadPoolExecutor);

        //阻塞先完成的线程，等所有线程都完成在返回
        CompletableFuture.allOf(spuCompletableFuture,imageCompletableFuture,brandCompletableFuture,catCompletableFuture,
                saleCompletableFuture,storeCompletableFuture,saleAttrCompletableFuture,descCompletableFuture,gruopCompletableFuture).join();

        return itemVO;
    }


    public static void main(String[] args) {


      /*  CompletableFuture.allOf(
                CompletableFuture.completedFuture("CompletableFuture1");
                CompletableFuture.completedFuture("CompletableFuture2");
                CompletableFuture.completedFuture("CompletableFuture3");
                CompletableFuture.completedFuture("CompletableFuture4");
        )*/


        CompletableFuture.supplyAsync(()->{
            System.out.println("runAsync.....");
//            int i= 1/0;
            return "hello supplyAsync";
        }).thenApply(t ->{
            System.out.println(t);
            return "hellp thenApply1";
        }).thenApply(t ->{
            System.out.println(t);
            return "hellp thenApply2";
        }).thenAccept(t ->{
            System.out.println(t);
        }).whenComplete( (t , u) ->{
            System.out.println(t);
            System.out.println(u);
        }).handleAsync((t,u) ->{
            System.out.println(t);
            System.out.println(u);
            return "hello handle";
        }).thenCombine(CompletableFuture.completedFuture("hello completedFuture"),(t,u)->{
            System.out.println(t);
            System.out.println(u);
            return "hello thenCombine";
        });






        //1.继承Thread基类
//        new MyThread().start();
        //2.实现runnable接口
//        new Thread(new MyRunnable()).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("-------------线程开启--------");
//                System.out.println("-------------线程结束--------");
//            }
//        }).start();
        //3.callable接口,有返回值，可以处理异常
//        FutureTask futureTask = new FutureTask<>(new MyCallable());
//        new Thread(futureTask).start();
//        try {
//            System.out.println(futureTask.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        //4.线程池
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 50, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(10), Executors.defaultThreadFactory(), (Runnable r, ThreadPoolExecutor executor) -> {
//            System.out.println("执行了拒绝策略");
//        });
//        for (int i = 0; i < 50; i++) {
//            threadPoolExecutor.execute(()->{
//                System.out.println("-------------线程开启--------");
//                System.out.println("-------------线程结束--------");
//            });
//        }



    }

}

class MyCallable implements Callable{

    @Override
    public Object call() throws Exception {
        System.out.println("-------------线程开启--------");
        System.out.println("-------------线程结束--------");
        return "hello";
    }
}
class MyRunnable implements Runnable{

    @Override
    public void run() {
        System.out.println("-------------线程开启--------");
        System.out.println("-------------线程结束--------");
    }
}

class MyThread extends Thread{

    @Override
    public void run() {
        System.out.println("-------------线程开启--------");
        System.out.println("-------------线程结束--------");
    }
}