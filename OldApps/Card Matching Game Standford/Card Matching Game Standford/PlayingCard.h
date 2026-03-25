//
//  PlayingCard.h
//  Card Matching Game Standford
//
//  Created by Daniel Schepisi on 27/10/2017.
//  Copyright © 2017 Daniel Schepisi. All rights reserved.
//

//#ifndef PlayingCard_h
//#define PlayingCard_h
//
//
//#endif /* PlayingCard_h */


#import "Card.h"

@interface PlayingCard : Card

@property (strong, nonatomic) NSString *suit;
@property (nonatomic) NSUInteger rank;


+ (NSArray *)validSuits;
+ (NSUInteger)maxRank;


@end
