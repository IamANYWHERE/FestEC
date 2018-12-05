package com.toplyh.latte.ec.pay;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.toplyh.latte.core.global.ConfigKeys;
import com.toplyh.latte.core.global.Latte;
import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.ui.loader.LatteLoader;
import com.toplyh.latte.core.weichat.LatteWeChat;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.pay.util.OrderInfoUtil2_0;

import java.util.Map;

public class FastPay implements View.OnClickListener {

    //设置支付回调监听
    private IAlPayResultListener mAlPayResultListener = null;
    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private int mOderID = -1;

    private final String ALPAY_APP_ID = "";
    private final String ALPAY_RSA2_PRIVATE = "";

    private FastPay(@NonNull LatteDelegate delegate) {
        mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static FastPay create(LatteDelegate delegate) {
        return new FastPay(delegate);
    }

    public void beginPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_form_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(params);

            window.findViewById(R.id.btn_dialog_pay_cancel).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_wechat).setOnClickListener(this);
            window.findViewById(R.id.btn_dialog_pay_alpay).setOnClickListener(this);
        }
    }

    public FastPay setPayResultListener(IAlPayResultListener listener) {
        this.mAlPayResultListener = listener;
        return this;
    }

    public FastPay setOrderId(int orderId) {
        this.mOderID = orderId;
        return this;
    }

    private final void alPay(int orderId) {
        final String singUrl = "about.json?a=" + orderId;
        //获取签名字符串
        RestClient.builder()
                .url(singUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //必须异步调用客户端支付接口
                        payV2();
                    }
                })
                .build()
                .post();
    }

    private void payV2() {
        if (TextUtils.isEmpty(ALPAY_APP_ID) || TextUtils.isEmpty(ALPAY_RSA2_PRIVATE)) {
            new AlertDialog.Builder(mActivity).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        }

        boolean rsa2 = true;
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(ALPAY_APP_ID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = ALPAY_RSA2_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final PayAsyncTask payAsyncTask = new PayAsyncTask(mActivity, mAlPayResultListener);
        payAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, orderInfo);
    }

    private void weChatPay(int orderId) {
        LatteLoader.stopLoading();
        final String weChatPrePayUrl = "";

        final IWXAPI iwxapi = LatteWeChat.getInstance().getWXAPI();
        final String wexinAppId = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
        iwxapi.registerApp(wexinAppId);
        RestClient.builder()
                .url(weChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //final JSONObject result = JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = "";
                        final String partnerId = "";
                        final String packageValue = "";
                        final String timestamp = "";
                        final String nonceStr = "";
                        final String paySign = "";

                        final PayReq payReq = new PayReq();
                        payReq.appId = wexinAppId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;

                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_dialog_pay_alpay) {
            alPay(mOderID);
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_wechat) {
            weChatPay(mOderID);
            mDialog.cancel();
        } else if (id == R.id.btn_dialog_pay_cancel) {
            mDialog.cancel();
        }
    }
}
