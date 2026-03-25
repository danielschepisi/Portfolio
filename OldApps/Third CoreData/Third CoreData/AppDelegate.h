//
//  AppDelegate.h
//  Third CoreData
//
//  Created by Daniel Schepisi on 13/3/19.
//  Copyright © 2019 Daniel Schepisi. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "Person+CoreDataClass.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (readonly, strong) NSPersistentContainer *persistentContainer;

- (void)saveContext;
-(Person*) createPerson;


@end

