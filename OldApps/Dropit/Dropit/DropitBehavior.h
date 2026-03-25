//
//  DropitBehaviour.h
//  Dropit
//
//  Created by Daniel Schepisi on 19/12/17.
//  Copyright © 2017 Daniel Schepisi. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface DropitBehavior : UIDynamicBehavior


-(void)addItem:(id <UIDynamicItem>)item;
-(void)removeItem:(id <UIDynamicItem>)item;

@end
