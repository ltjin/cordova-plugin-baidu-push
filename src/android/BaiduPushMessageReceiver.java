package cn.com.kland.plugins.baidupush;

import android.content.Context;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by ltjin on 15/11/6.
 */
public class BaiduPushMessageReceiver extends PushMessageReceiver {
    /** TAG to Log */
    public static final String TAG = BaiduPushMessageReceiver.class
            .getSimpleName();

    /**
     * 调用PushManager.startWork后，sdk将对push
     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
     *
     * @param context
     *            BroadcastReceiver的执行Context
     * @param errorCode
     *            绑定接口返回值，0 - 成功
     * @param appid
     *            应用id。errorCode非0时为null
     * @param userId
     *            应用user id。errorCode非0时为null
     * @param channelId
     *            应用channel id。errorCode非0时为null
     * @param requestId
     *            向服务端发起的请求id。在追查问题时有用；
     * @return none
     */
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.w(TAG, responseString);
        try {
            JSONObject result = new JSONObject();
            result.put("appId", appid);
            result.put("userId", userId);
            result.put("channelId", channelId);
            result.put("requestId", requestId);
            result.put("errorCode", errorCode);
            result.put("eventType", "onBind");

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }

        if (errorCode == 0) {
            // 绑定成功
            Log.w(TAG, "绑定成功");
        }
    }

    /**
     * 接收透传消息的函数。
     *
     * @param context
     *            上下文
     * @param message
     *            推送的消息
     * @param customContentString
     *            自定义内容,为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = "透传消息 message=\"" + message
                + "\" customContentString=" + customContentString;
        Log.w(TAG, messageString);

        try {
            JSONObject result = null;
            if (!TextUtils.isEmpty(customContentString)) {
                result = new JSONObject(customContentString);
            }else{
                result = new JSONObject();
            }

            result.put("message",message);
            result.put("eventType", "onMessage");

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }

        // 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值

    }

    /**
     * 接收通知点击的函数。
     *
     * @param context
     *            上下文
     * @param title
     *            推送的通知的标题
     * @param description
     *            推送的通知的描述
     * @param customContentString
     *            自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知点击 title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.w(TAG, notifyString);

        try {
            JSONObject result = null;
            if (!TextUtils.isEmpty(customContentString)) {
                result = new JSONObject(customContentString);
            }else{
                result = new JSONObject();
            }

            result.put("title",title);
            result.put("description",description);
            result.put("eventType","onNotificationClicked");

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }

        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值

    }

    /**
     * 接收通知到达的函数。
     *
     * @param context
     *            上下文
     * @param title
     *            推送的通知的标题
     * @param description
     *            推送的通知的描述
     * @param customContentString
     *            自定义内容，为空或者json字符串
     */

    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {

        String notifyString = "onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.w(TAG, notifyString);

        // 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
        try {
            JSONObject result = null;
            if (!TextUtils.isEmpty(customContentString)) {
                result = new JSONObject(customContentString);
            }else{
                result = new JSONObject();
            }

            result.put("title",title);
            result.put("description",description);
            result.put("eventType","onNotificationArrived");

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }

    }

    /**
     * setTags() 的回调函数。
     *
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param successTags
     *            设置成功的tag
     * @param failTags
     *            设置失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onSetTags errorCode=" + errorCode
                + " sucessTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        try {
            JSONObject result = new JSONObject();
            result.put("requestId", requestId);
            result.put("errorCode", errorCode);
            result.put("eventType", "onSetTags");
            setJSONArray(result, "sucessTags", successTags);
            setJSONArray(result, "failTags", failTags);

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }
        Log.w(TAG, responseString);
    }

    /**
     * delTags() 的回调函数。
     *
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param successTags
     *            成功删除的tag
     * @param failTags
     *            删除失败的tag
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> successTags, List<String> failTags, String requestId) {
        String responseString = "onDelTags errorCode=" + errorCode
                + " sucessTags=" + successTags + " failTags=" + failTags
                + " requestId=" + requestId;
        Log.w(TAG, responseString);

        try {
            JSONObject result = new JSONObject();
            result.put("requestId", requestId);
            result.put("errorCode", errorCode);
            result.put("eventType", "onDelTags");
            setJSONArray(result, "sucessTags", successTags);
            setJSONArray(result, "failTags", failTags);

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }

    }

    /**
     * listTags() 的回调函数。
     *
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示列举tag成功；非0表示失败。
     * @param tags
     *            当前应用设置的所有tag。
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
        String responseString = "onListTags errorCode=" + errorCode + " tags="
                + tags;
        Log.w(TAG, responseString);

        try {
            JSONObject result = new JSONObject();
            result.put("requestId", requestId);
            result.put("errorCode", errorCode);
            result.put("eventType", "onListTags");
            setJSONArray(result, "tags", tags);

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }
    }

    /**
     * PushManager.stopWork() 的回调函数。
     *
     * @param context
     *            上下文
     * @param errorCode
     *            错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId
     *            分配给对云推送的请求的id
     */
    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.w(TAG, responseString);

        try {
            JSONObject result = new JSONObject();
            result.put("requestId", requestId);
            result.put("errorCode", errorCode);
            result.put("eventType", "onUnbind");

            BaiduPush.sendEvent(result);
        } catch (JSONException e){
            Log.e(TAG, "get a JSONException: " + e.getMessage());
            BaiduPush.sendError(e.getMessage());
        }

        if (errorCode == 0) {
            // 解绑定成功
            Log.w(TAG, "解绑成功");
        }
    }

    private static void setJSONArray(JSONObject data, String name, List<String> list){
        try {
            JSONArray jsonArray = new JSONArray();
            if(list != null && list.size() > 0){
                for(String listStr : list){
                    jsonArray.put(list);
                }
            }
            data.put(name, jsonArray);
        } catch (JSONException e) {
            BaiduPush.sendError(e.getMessage());
        }
    }

}