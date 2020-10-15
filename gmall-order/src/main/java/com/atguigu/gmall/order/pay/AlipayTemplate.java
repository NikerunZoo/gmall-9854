package com.atguigu.gmall.order.pay;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author NikerunZoo
 * @date 2020/10/15 0015 16:42
 */
@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {

    //在支付宝创建的应用的id
    private   String app_id = "2016102600763118";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCUfTxbaWX3AwDSoxM43tGU0/m5zRXRzToCKxB3ILq9IsEOTLtWmaEY4fVLYmFGStiqRI91zFPJo18NrJzZxmkAzM5G9C6s6NoQpdTZJ9DPsV+oBvvYVuusZwCqLl7FNAJpgKtnhmWvJzdhyu1tPQcO7aZKitHLg1opl15gTUl3UDDVKoztCxHoU/CGj0jMa8ojY4UgY8FzuXlRNxA+2z/UWY3UhvXumxcHpmbxeyZ1KFppBr+wio2cEQAPz1ejj3YSF7zP82/9ZRJc8mY2viSpedv0BJ5jTTexQgoRd6GoItxBhG+JbXWcuUqTXXklEhsCMJi32e/NWOdmZslxHhn9AgMBAAECggEALRoE9TcEU4zD+TazJfj3656fIYJbeEg/sFzoOWblkYE25mMEG/+54NbxLJ6LMVCWktEUShFv+bT3sC2XzsXnKukKVp7gdoCbi6bzCaqAUDVt+pl0cXelSc5Xc1l2S6vKd8UvStiVlifxOHDswNtig3RHqCiWJJyXDD7/2XxgLi7g2hSnzmarcBBTGXm3fv85joUUErPFGV/zZEr/ppbaPN7vJnKAd9u5oh0y3Pev03tt9gWLGxeDTlG+9MH9vJiPIX9TlyYedPo08+G5AaPkd8uCDRWcm2872a/4Xk2N/QWerIti2Dkppm/ThWJny5nHl0hY79SiJ1EkIatR/DhaQQKBgQD8gJpPQUmXqJFC9+WqCQi6m1tEb6lx9m50ASjC/6h3cUpVatZCFY09rUPdNy1KK1GccJV+pIZBZzlCsbd7V2vGrVF/FgmS5hcn5ZRFAnIa7W5f1mcrnk5uSmHWgDyyTMsPOqGekja/oFLZwbrOLmuKw70LP5nD6XO83he07Hdl6QKBgQCWi8riz6fwUwCzp9vKBD7qa1751QWfsrpA9NGgfCaA1cJxeECztvIaTOV/BpXRPTguEbTpR3MmS8WxZp7Pyh3TBC/CivNv1x9I6Yi2Sm5fv8zoazGNm9FzMwZYRHrmKsLhf6UReotiu4nvog1/ShGmYD/ZzpnpbZaR+LDFxEfC9QKBgFD7ggInQ04TrzsWPJ9zNEx9JSFm2qaFTdmAwa0Ax+w+9oM8t6kMNndwiZJ6btkZ2Zvd8+RhKfnx/EhqrRku6Y8X6UpkK//kMcsG863KYgRG4B52qjIwHKF807IzAMSCoqGowUsVQ9/jsI7MdnhIJQ+lRY1FAbD2v7r0VBAfww95AoGAXedeqzFaELbFdkqa9xzE3dYRjQaKFn4WXZQX+eHW5n/vZHXc7fyMcUlL0XsbEkP8eggDb4to0RdsfeaD4xqwlcJDxDaF+FoYIpPA+evdgw4RU1whsbHi6NeH+qDkhBNiMeQ743Yr4W8XcAkGIELkVYIt8b9EE+2OgQi9wu5W0M0CgYAKA+ref00d1LLuqy41itWZfJNH8GTyobV2au06M9k4IQSKMUGyyi7MR7SOUqaFLL5hl6/P7SlyqCSSRCWlvkgNZJMbpVOI4b+k+HLAuWfAVBL3AWYpYg5iLytFQkJuP6e8Vhww6YeajTA7KMw7FD9hjRZkMwwFr2KRp+O/NoEssA==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzq29gPt0BOU7bKbw2G68J1NpHilZ8QpdtMVCWsxhkB6/XJIodKWWt3Y/zLH8khHJcJBQdJVXANK9SG9FA2HdaqhZ2Vvs+IOuRd7LjQparOZh2AJl2aijnO3Mb0ImQM4JVcOHm9h0wMoZTY9yrzj1/OxT40erIZQc7c37sruO9nHZt5txSrBDTwPgcu2YDoXHxp72iUPPPkgiq0UNEZlvsaPKgr3iH8qq2V3xUtQff8POeKiQ595MFF8fxeA44nXnaRzGNBhDLE81YWAMdrSRoyTI05/JjbmR9oAi4Sx+B8F318LaUaNfDcwqfIMP/tPu+Y+adxl2qbaTOe8fyIG4QwIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url;

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url;

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}