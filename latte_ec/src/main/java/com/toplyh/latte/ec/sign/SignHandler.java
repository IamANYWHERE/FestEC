package com.toplyh.latte.ec.sign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.core.app.AccountManager;
import com.toplyh.latte.ec.database.DatabaseManager;
import com.toplyh.latte.ec.database.UserProfile;

public class SignHandler {

    public static void onSignUp(String response,ISignListener iSignListener) {
        onSign(response,iSignListener);
        iSignListener.onSignUpSuccess();
    }
    private static void onSign(String response,ISignListener iSignListener){
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile profile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insertOrReplace(profile);

        //已经注册成功并登录成功了
        AccountManager.setSignState(true);
    }
    public static void onSignIn(String response,ISignListener iSignListener) {
        onSign(response,iSignListener);
        iSignListener.onSignInSuccess();
    }
}
