//
//  BaiduPush.h
//  POOCYE
//
//  Created by kland on 15/11/12.
//
//

#import <Cordova/CDV.h>
#import "BPush.h"

static id delegate = nil;
static CDVInvokedUrlCommand *pushCallback = nil;
static NSDictionary *launchOptions = nil;
static BOOL setFlag = false;

@interface BaiduPush : CDVPlugin{
    NSString *callback;
    
    BOOL clearBadge;
    
    NSDictionary *result;
}

@property (nonatomic, copy) NSString *callback;

@property BOOL clearBadge;
@property (nonatomic, strong) NSDictionary *result;

- (void)startWork:(CDVInvokedUrlCommand*)command;
- (void)stopWork:(CDVInvokedUrlCommand*)command;
- (void)resumeWork:(CDVInvokedUrlCommand*)command;
- (void)setTags:(CDVInvokedUrlCommand*)command;
- (void)delTags:(CDVInvokedUrlCommand*)command;
- (void)listTags:(CDVInvokedUrlCommand*)command;
- (void)setBadge:(CDVInvokedUrlCommand*)command;
- (void)getBadge:(CDVInvokedUrlCommand*)command;

+ (void)notificationReceived:(NSDictionary *)userInfo;

+ (void)setLaunchOptions:(NSDictionary *)options;

@end
