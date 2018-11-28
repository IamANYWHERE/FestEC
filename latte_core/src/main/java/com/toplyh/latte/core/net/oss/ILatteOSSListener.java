package com.toplyh.latte.core.net.oss;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

public interface ILatteOSSListener {

    void onSuccess(PutObjectRequest request, PutObjectResult result);

    void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException);
}
