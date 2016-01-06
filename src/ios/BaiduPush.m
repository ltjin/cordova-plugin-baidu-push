//
//  BaiduPush.m
//  POOCYE
//
//  Created by kland on 15/11/12.
//
//

#import "BaiduPush.h"

@implementation BaiduPush

@synthesize callback;
@synthesize clearBadge;
@synthesize result;

-(void)startWork:(CDVInvokedUrlCommand *)command{
    delegate = self.commandDelegate;
    NSString* apiKey = [command.arguments objectAtIndex:0];
    pushCallback = command;
    if(apiKey == nil || !setFlag){
        [self failWithMessage:@"baidu push api key is requied" withError:nil];
    }else{
        if([[[UIDevice currentDevice] systemVersion] floatValue] >= 8.0){
            UIUserNotificationType types = UIUserNotificationTypeBadge | UIUserNotificationTypeSound | UIUserNotificationTypeAlert;
            UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
            [[UIApplication sharedApplication] registerUserNotificationSettings:settings];
        }else{
            UIRemoteNotificationType types = UIRemoteNotificationTypeBadge | UIRemoteNotificationTypeSound | UIRemoteNotificationTypeAlert;
            [[UIApplication sharedApplication] registerForRemoteNotificationTypes:types];
        }
        NSLog(@"Options >>>>>>> %@", launchOptions);
        [BPush registerChannel:launchOptions apiKey:apiKey pushMode:BPushModeDevelopment withFirstAction:nil withSecondAction:nil withCategory:nil isDebug:false];
        
        NSLog(@">>>>>>>>>>>> channelId: %@",[BPush getChannelId]);
        NSLog(@">>>>>>>>>>>> userId: %@",[BPush getUserId]);
        [BPush bindChannelWithCompleteHandler:^(id result, NSError *error) {
            NSDictionary *data = [NSDictionary dictionaryWithObjectsAndKeys:
                                  [BPush getAppId], @"appId",
                                  [BPush getChannelId], @"channelId",
                                  [BPush getUserId], @"userId",
                                  @"onBind",@"eventType",
                                  [result objectForKey:@"error_code"],@"errorCode",
                                  [result objectForKey:@"request_id"],@"requestId",nil];
            [self successWithResult:data];
        }];
    }
}

-(void)stopWork:(CDVInvokedUrlCommand *)command{
    
}

-(void)resumeWork:(CDVInvokedUrlCommand *)command{
    
}

-(void)setTags:(CDVInvokedUrlCommand *)command{
    
}

-(void)delTags:(CDVInvokedUrlCommand *)command{
    
}

-(void)listTags:(CDVInvokedUrlCommand *)command{
    
}

-(void)setBadge:(CDVInvokedUrlCommand *)command{
    
}

-(void)getBadge:(CDVInvokedUrlCommand *)command{
    
    
}

+ (void)notificationReceived:(NSDictionary *)userInfo{
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:userInfo];
    [pluginResult setKeepCallbackAsBool:YES];
    [delegate sendPluginResult:pluginResult callbackId:pushCallback.callbackId];
}

+ (void)setLaunchOptions:(NSDictionary *)options{
    NSLog(@"Options >>>>>>> %@", options);
    launchOptions = options;
    setFlag = true;
}

-(void)successWithResult:(NSDictionary *)data{
    if(pushCallback.callbackId != nil){
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsDictionary:data];
        [pluginResult setKeepCallbackAsBool:YES];
        [delegate sendPluginResult:pluginResult callbackId:pushCallback.callbackId];
    }
}

-(void)failWithMessage:(NSString *)message withError:(NSError *)error{
    NSString *errorMessage = (error) ?  [NSString stringWithFormat:@"%@ - %@", message, [error localizedDescription]] : message;
    CDVPluginResult *commandResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsString:errorMessage];
    
    [delegate sendPluginResult:commandResult callbackId:pushCallback.callbackId];
}

@end
