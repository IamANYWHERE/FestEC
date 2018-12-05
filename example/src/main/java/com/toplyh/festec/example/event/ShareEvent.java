package com.toplyh.festec.example.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.festec.example.share.ShareHelper;
import com.toplyh.latte.core.fragments.web.event.Event;
import com.toplyh.latte.core.util.log.LatteLogger;

public class ShareEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.d("shareEvent",params);
        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
        final String imageUrl = object.getString("imageUrl");
        final String url = object.getString("url");
        final String title = object.getString("title");
        final String text = object.getString("text");

        ShareHelper.showShareWeChat(getContext(),title,text,imageUrl,url);
        return null;
    }
}
