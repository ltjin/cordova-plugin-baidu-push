//
//  AppDelegate+Notification.m
//  POOCYE
//
//  Created by kland on 15/11/12.
//
//

#import "AppDelegate+Notification.h"
#import "MainViewController.h"
#import "BPush.h"
#import "BaiduPush.h"

@implementation AppDelegate (Notification)

- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    CGRect screenBounds = [[UIScreen mainScreen] bounds];
    
#if __has_feature(objc_arc)
    self.window = [[UIWindow alloc] initWithFrame:screenBounds];
#else
    self.window = [[[UIWindow alloc] initWithFrame:screenBounds] autorelease];
#endif
    self.window.autoresizesSubviews = YES;
    
#if __has_feature(objc_arc)
    self.viewController = [[MainViewController alloc] init];
#else
    self.viewController = [[[MainViewController alloc] init] autorelease];
#endif
    
    // Set your app's start page by setting the <content src='foo.html' /> tag in config.xml.
    // If necessary, uncomment the line below to override it.
    // self.viewController.startPage = @"index.html";
    
    // NOTE: To customize the view's frame size (which defaults to full screen), override
    // [self.viewController viewWillAppear:] in your view controller.
    
    self.window.rootViewController = self.viewController;
    [self.window makeKeyAndVisible];
    
    [BaiduPush setLaunchOptions:launchOptions];
    application.applicationIconBadgeNumber = 0;
    
    return YES;
}
-(void)application:(UIApplication *)application didRegisterUserNotificationSettings:(UIUserNotificationSettings *)notificationSettings{
    [application registerForRemoteNotifications];
}
-(void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken{
    NSLog(@">>>>>>>>>>>DeviceToken: %@", [[NSString alloc] initWithData:deviceToken encoding:NSUTF8StringEncoding]);
    [BPush registerDeviceToken:deviceToken];
}
-(void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error{
    NSLog(@"DeviceToken 获取失败，原因：%@", error);
}
-(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo{
    
    [BPush handleNotification:userInfo];
    
    NSMutableDictionary *newUserInfo = [[NSMutableDictionary alloc] initWithObjectsAndKeys:@"onNotificationArrived", @"eventType",nil];
    
    [newUserInfo addEntriesFromDictionary:userInfo];
    NSLog(@"消息到达：%@", newUserInfo);
    
    [BaiduPush notificationReceived:newUserInfo];
    application.applicationIconBadgeNumber = 0;
}

@end
