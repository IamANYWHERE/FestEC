package com.toplyh.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.global.Latte;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.RestCreator;
import com.toplyh.latte.core.net.callback.IError;
import com.toplyh.latte.core.net.callback.IFailure;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.util.log.LatteLogger;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExampleDelegate extends LatteDelegate {

    @BindView(R.id.btn_1)
    TextView mTextView;

    @OnClick(R.id.btn_1)
    void click() {
        LatteLogger.d("shawn", "click");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mTextView = rootView.findViewById(R.id.btn_1);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testRestClient();
            }
        });
    }

    private void testRestClient() {
        RestCreator.getRestService()
                .get("http://mock.fulingjie.com/mock-android/data/address.json", new HashMap<String, Object>())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            if (call.isExecuted()) {
                                LatteLogger.d("shawn", "success");
                            }
                        } else {
                            LatteLogger.d("shawn","code = "+response.code()+" message = "+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        LatteLogger.e("shawn",t.getMessage());
                    }
                });
//        RestCl    ient.builder()
//                .url("https://raw.githubusercontent.com/IamANYWHERE/note/master/android/%E5%90%AF%E5%8A%A8%E6%A8%A1%E5%BC%8F.md?token=AV8u5aQhdUoMxQcIo-rpPuEAe5FhVCDeks5cnDSywA%3D%3D")
//                .loader(getContext())
//                .success(new ISuccess() {
//                    @Override
//                    public void onSuccess(String response) {
//                        LatteLogger.d("shawn","success");
//                    }
//                })
//                .failure(new IFailure() {
//                    @Override
//                    public void onFailure() {
//                        LatteLogger.d("shawn","failure");
//                    }
//                })
//                .error(new IError() {
//                    @Override
//                    public void onError(int code, String msg) {
//                        LatteLogger.d("shawn","error");
//                    }
//                })
//                .build()
//                .get();

    }
}
