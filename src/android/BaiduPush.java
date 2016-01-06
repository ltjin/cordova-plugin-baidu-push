package cn.com.kland.plugins.baidupush;

import android.content.Context;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltjin on 15/11/9.
 */

public class BaiduPush extends CordovaPlugin {
    //log tags
    private static final String LOG_TAG = "BaiduPush";

    //action constants
    private static final String START_WORK_ACTION = "startWork";
    private static final String STOP_WORK_ACTION = "stopWork";
    private static final String RESUME_WORK_ACTION = "resumeWork";
    private static final String SET_TAGS_ACTION = "setTags";
    private static final String DEL_TAGS_ACTION = "delTags";
    private static final String LIST_TAGS_ACTION = "listTags";
    private static final String SET_BADGE_ACTION = "setBadge";
    private static final String GET_BADGE_ACTION = "getBadge";

    private static final String API_KEY = "api_key";

    //application context and callback context
    private static Context applicationContext;
    private static CallbackContext pushCallback;
    private static JSONObject callbackData = null;

    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) throws JSONException {
        Log.v(LOG_TAG,"execute: action = " + action);

        applicationContext = this.cordova.getActivity().getApplicationContext();

        if(START_WORK_ACTION.equals(action)){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    pushCallback = callbackContext;
                    try{
                        String api_key = data.getString(0);
                        PushManager.startWork(applicationContext, PushConstants.LOGIN_TYPE_API_KEY, api_key);
                    }catch (JSONException e){
                        Log.e(LOG_TAG, "get a JSONException: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                    }
                }
            });
        }else if(STOP_WORK_ACTION.equals(action)){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    pushCallback = callbackContext;
                    PushManager.stopWork(applicationContext);
                }
            });
        }else if(RESUME_WORK_ACTION.equals(action)){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    pushCallback = callbackContext;
                    PushManager.resumeWork(applicationContext);
                }
            });
        }else if(SET_TAGS_ACTION.equals(action)){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    pushCallback = callbackContext;
                    try{
                        JSONArray tagsArr = data.getJSONArray(0);
                        List<String> tags = getTagsListFromData(tagsArr);
                        PushManager.setTags(applicationContext, tags);
                    }catch (JSONException e){
                        Log.e(LOG_TAG, "get a JSONException: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                    }
                }
            });
        }else if(DEL_TAGS_ACTION.equals(action)){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    pushCallback = callbackContext;
                    try{
                        JSONArray tagsArr = data.getJSONArray(0);
                        List<String> tags = getTagsListFromData(tagsArr);
                        PushManager.delTags(applicationContext, tags);
                    }catch (JSONException e){
                        Log.e(LOG_TAG, "get a JSONException: " + e.getMessage());
                        callbackContext.error(e.getMessage());
                    }
                }
            });
        }else if(LIST_TAGS_ACTION.equals(action)){
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    pushCallback = callbackContext;
                    PushManager.listTags(applicationContext);
                }
            });
        }else{
            Log.e(LOG_TAG, "Invalid action: " + action);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.INVALID_ACTION));
            return false;
        }

        return true;
    }

    public static void sendEvent(JSONObject json){
        PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, json);
        pluginResult.setKeepCallback(true);
        if(pushCallback != null){
            pushCallback.sendPluginResult(pluginResult);
        }
    }

    public static void sendError(String message){
        PluginResult pluginResult = new PluginResult(PluginResult.Status.ERROR, message);
        pluginResult.setKeepCallback(true);
        if(pushCallback != null){
            pushCallback.sendPluginResult(pluginResult);
        }
    }

    private List<String> getTagsListFromData(JSONArray data){
        List<String> tags = null;
        if(data != null && data.length() > 0){
            int length = data.length();
            tags = new ArrayList<String>(length);
            try {
                for(int i = 0; i < length; i++) {
                    tags.add(data.getString(i));
                }
            }catch (JSONException e){
                Log.e(LOG_TAG, "get a JSONException: " + e.getMessage());
            }

        }
        return tags;
    }
}