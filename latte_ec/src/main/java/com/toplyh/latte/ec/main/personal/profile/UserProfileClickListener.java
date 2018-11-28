package com.toplyh.latte.ec.main.personal.profile;

import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.core.net.oss.ILatteOSSListener;
import com.toplyh.latte.core.net.oss.LatteOSS;
import com.toplyh.latte.core.ui.camera.LatteCamera;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.callback.IGlobalCallback;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.core.util.storage.LattePreference;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.main.personal.list.ListBean;
import com.toplyh.latte.ec.main.personal.settings.NameDelegate;
import com.toplyh.latte.ui.date.DateDialogUtil;

public class UserProfileClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};

    public UserProfileClickListener(LatteDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback() {
                    @Override
                    public void execute(Object args) {
                        Uri uri = (Uri) args;
                        new LatteOSS.Builder()
                                .loaderStyle(DELEGATE.getContext())
                                .localFilePath(uri.getPath())
                                .listener(new ILatteOSSListener() {
                                    @Override
                                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                                        String time = String.valueOf(System.currentTimeMillis());
                                        LattePreference.addCustomAppProfile(ImageHelper.SIGNATURE,time);
                                    }

                                    @Override
                                    public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {

                                    }
                                })
                                .build()
                                .putImage();
                    }
                });
                DELEGATE.startCameraWithCheck();
                break;
            case 2:
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.start(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onDateChange(String date) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(DELEGATE.getContext());
                break;
            default:
                break;
        }
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
