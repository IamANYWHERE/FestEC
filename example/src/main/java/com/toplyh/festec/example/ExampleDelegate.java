package com.toplyh.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.RestClientBuilder;
import com.toplyh.latte.core.net.callback.IError;
import com.toplyh.latte.core.net.callback.IFailure;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.util.file.FileUtil;

import butterknife.BindView;

public class ExampleDelegate extends LatteDelegate {

    @BindView(R.id.tv_1)
    TextView mTextView;

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }

    private void testRestClient(){
        RestClient.builder()
                .url("https://news.baidu.com/")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();

    }
}
