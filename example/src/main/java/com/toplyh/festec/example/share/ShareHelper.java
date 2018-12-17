package com.toplyh.festec.example.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.toplyh.latte.core.image.GlideApp;
import com.toplyh.latte.core.weichat.LatteWeChat;

import java.io.ByteArrayOutputStream;


public class ShareHelper {

    public static void showShareWeChat(Context context, String title, String text, String imageUrl, String url) {
/*        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(text);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(imageUrl);//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl(url);
        // 启动分享GUI
        oks.show(context);*/
    }

    public static void share(Context context, String title, String text, String imageUrl, String url) {
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = title;
//        msg.description = text;
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), com.toplyh.latte.ec.R.mipmap.avatar);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] datas = baos.toByteArray();
//        msg.thumbData = datas;
//
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = "webpage";
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
//
//        LatteWeChat.getInstance().getWXAPI().sendReq(req);
    }
}
