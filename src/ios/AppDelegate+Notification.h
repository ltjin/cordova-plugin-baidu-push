//
//  AppDelegate+Notification.h
//  POOCYE
//
//  Created by kland on 15/11/12.
//
//

#import "AppDelegate.h"

@interface AppDelegate (Notification)

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;
-(void)application:(UIApplication *)application didRegisterUserNotificationSettings:(UIUserNotificationSettings *)notificationSettings;
-(void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken;
-(void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error;
-(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo;

@end
