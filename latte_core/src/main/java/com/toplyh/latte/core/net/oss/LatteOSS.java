package com.toplyh.latte.core.net.oss;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.toplyh.latte.core.app.ConfigKeys;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.latte.core.ui.loader.LatteLoader;
import com.toplyh.latte.core.ui.loader.LoaderStyle;
import com.toplyh.latte.core.util.log.LatteLogger;

import java.io.File;

public class LatteOSS {

    private final String LOCAL_FILE_PATH;
    private final ILatteOSSListener LISTENER;
    private final LoaderStyle LOAD_STYLE;
    private final Context CONTEXT;

    private LatteOSS(String local_file_path, ILatteOSSListener listener, LoaderStyle loaderStyle, Context context) {
        LOCAL_FILE_PATH = local_file_path;
        LISTENER = listener;
        LOAD_STYLE = loaderStyle;
        CONTEXT = context;
    }

    public void putImage() {
        File file = new File(LOCAL_FILE_PATH);
        if (!file.exists()) {
            LatteLogger.e("putImage", "file not exists");
        }

        if (this.CONTEXT != null && LOAD_STYLE != null) {
            LatteLoader.showLoading(this.CONTEXT, this.LOAD_STYLE);
        }
        final String bucketName = Latte.getConfiguration(ConfigKeys.BUCKET_NAME);
        final String dirName = Latte.getConfiguration(ConfigKeys.OSS_UPLOAD_DIR);
        final PutObjectRequest put = new PutObjectRequest(bucketName, dirName + "/avatar.jpg", LOCAL_FILE_PATH);
        final OSS oss = Latte.getConfiguration(ConfigKeys.OSS_CLIENT);
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LatteLoader.stopLoading();
                if (LISTENER != null) {
                    LISTENER.onSuccess(request, result);
                }
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                LatteLoader.stopLoading();
                if (LISTENER != null) {
                    LISTENER.onFailure(request, clientException, serviceException);
                }
            }
        });
    }

    public static class Builder {
        private String localFilePath;
        private ILatteOSSListener listener;
        private LoaderStyle loaderStyle;
        private Context context;

        public Builder listener(ILatteOSSListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder localFilePath(String localFilePath) {
            this.localFilePath = localFilePath;
            return this;
        }

        public Builder loaderStyle(Context context, LoaderStyle loaderStyle) {
            this.context = context;
            this.loaderStyle = loaderStyle;
            return this;
        }

        public Builder loaderStyle(Context context) {
            this.context = context;
            this.loaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
            return this;
        }

        public LatteOSS build() {
            return new LatteOSS(localFilePath, listener, loaderStyle, context);
        }
    }

}
