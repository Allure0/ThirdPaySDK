package com.allure.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.allure.thirdpay.alipay.Alipay;
import com.allure.thirdpay.weixin.WXPay;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String WX_APPID = "wx24e5ffb1eac128d4";    //申请的wx appid

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
String weChatPay="appid=wx24e5ffb1eac128d4&partnerid=1423206002&prepayid=wx20180315180602eb0738cf190126647302&package=Sign=WXPay&noncestr=kvb1twfzys7xjw3fnbuiu1l5whumusex&timestamp=1521108362&sign=21397B1E1C978459570112F9A5F256E5";
    public void wxPay(View view){
        String wx_appid = WX_APPID;     //替换为自己的appid

        Map<String, String> map = new HashMap<String, String>();
        String[] a = weChatPay.split("&");
        for (int i = 0; i < a.length; i++) {
            String b = a[i].substring(0, a[i].indexOf("="));
            String c = a[i].substring(a[i].indexOf("=") + 1, a[i].length());
            try {
                c = URLDecoder.decode(c, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            map.put(b, c);
        }
        JSONObject jsonObj = new JSONObject(map);
        String content = jsonObj.toString();


        WXPay.init(getApplicationContext(), wx_appid);      //要在支付前调用
        WXPay.getInstance().doPay(content, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                Log.e("微信支付成功","微信支付成功");
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        Toast.makeText(getApplication(), "未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        Toast.makeText(getApplication(), "参数错误", Toast.LENGTH_SHORT).show();
                        break;

                    case WXPay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onCancel() {
                Log.e("微信支付取消","微信支付取消");
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    String aliPay="app_id=2016122304563248&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%222130609523%40qq.com%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22%5Cu5143%5Cu5b9d%22%2C%22body%22%3A%22%5Cu5143%5Cu5b9d%22%2C%22out_trade_no%22%3A%22Ali15210923955118796%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fcai.pre.wesai.com%2Fv2%2Fpayment%2Falipay%2Fnotify&sign_type=RSA&timestamp=2018-03-15+13%3A39%3A55&version=1.0&sign=S0pg9DDIj8r%2BY8I5BJglIv6SgcJzOVMV2k1yW1X7yJ%2Fmu12FOxLdhFFZOJT%2BSkBpWuooOlH%2FsN4r2LNk0zI9F7EGLKAAsAsQS1ydf06t5Ews2bYzxU8ob1ZPPJ1YKCeSk5eBpHJtadKiblrlU1t2uEMNGzakOaaDhZyo5BZDjOg%3D";
    public void aliPay(View view){
        new Alipay(this, aliPay, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDealing() {
                Toast.makeText(getApplication(), "支付处理中...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        Toast.makeText(getApplication(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_NETWORK:
                        Toast.makeText(getApplication(), "支付失败:网络连接错误", Toast.LENGTH_SHORT).show();
                        break;

                    case Alipay.ERROR_PAY:
                        Toast.makeText(getApplication(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        Toast.makeText(getApplication(), "支付错误", Toast.LENGTH_SHORT).show();
                        break;
                }

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplication(), "支付取消", Toast.LENGTH_SHORT).show();
            }
        }).doPay();
    }

}
