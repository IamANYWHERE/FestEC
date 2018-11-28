package com.toplyh.latte.core.ui.camera;


import android.net.Uri;

import com.toplyh.latte.core.delegates.PermissionCheckerDelegate;
import com.toplyh.latte.core.util.file.FileUtil;

/**
 * 调用类
 */
public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse(FileUtil.createFile("crop_image",
                FileUtil.getFileNameByTime("IMG", "jpg"))
                .getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}
