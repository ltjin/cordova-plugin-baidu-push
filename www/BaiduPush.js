/**
 * Created by ltjin on 15/11/5.
 */
var exec = require('cordova/exec');

//
var BaiduPush = function(api_key){
    this._handlers = {
        'registration' : [],
        'notification' : [],
        'notification.click' : [],
        'message' : [],
        'error' : []
    };

    if(typeof api_key === 'undefined'){
        throw new Error('The api_key argument is required.');
    }

    this._api_key = api_key;

    var that = this;
    var successCallback = function(result){
        if(result && result.eventType == 'onBind'){
            that.emit('registration', result);
        }else if(result && result.eventType == 'onNotificationClicked'){
            that.emit('notification.click', result);
        }else if(result && result.eventType == 'onNotificationArrived'){
            that.emit('notification', result);
        }else if(result && result.eventType == 'onMessage'){
            that.emit('message', result);
        }
    };

    var failCallback = function (err) {
        var e = (typeof err === 'string') ? new Error(err) : err;
        that.emit('error', e);
    };

    exec(successCallback, failCallback, 'BaiduPush', 'startWork', [this._api_key]);
};

BaiduPush.prototype.stopWork = function (successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.stopWork failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.stopWork failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'stopWork', []);
};

BaiduPush.prototype.resumeWork = function (successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.resumeWork failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.resumeWork failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'resumeWork', []);
};

BaiduPush.prototype.setTags = function (tags, successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.setTags failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.setTags failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'setTags', [tags]);
};

BaiduPush.prototype.delTags = function (tags, successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.delTags failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.delTags failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'delTags', [tags]);
};

BaiduPush.prototype.listTags = function (successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.listTags failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.listTags failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'listTags', []);
};

BaiduPush.prototype.setBadge = function (badge, successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.setBadge failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.setBadge failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'setBadge', [badge]);
};

BaiduPush.prototype.getBadge = function (successCallback, failCallback) {
    if(failCallback == null){failCallback = function () {}}

    if(typeof failCallback != 'function'){
        console.log('BaiduPush.setBadge failure: failure parameter not a function');
        return
    }

    if(typeof successCallback != 'function'){
        console.log('BaiduPush.setBadge failure: success callback parameter must be a function');
        return
    }

    exec(successCallback, failCallback, 'BaiduPush', 'getBadge', []);
};


BaiduPush.prototype.on = function (eventName, callback) {
    if(this._handlers.hasOwnProperty(eventName)){
        this._handlers[eventName].push(callback);
    }
};

BaiduPush.prototype.emit = function () {
    var args = Array.prototype.slice.call(arguments);
    var eventName = args.shift();

    if(!this._handlers.hasOwnProperty(eventName)){
        return false;
    }

    for(var i = 0, length = this._handlers[eventName].length; i < length; i++){
        this._handlers[eventName][i].apply(undefined,args);
    }

    return true;
};

module.exports = {
    init:function(api_key){
        return new BaiduPush(api_key);
    },

    BaiduPush:BaiduPush
};