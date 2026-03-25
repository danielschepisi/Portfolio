//
//  CardMatchingGame.h
//  Card Matching Game Standford
//
//  Created by Daniel Schepisi on 2/11/17.
//  Copyright © 2017 Daniel Schepisi. All rights reserved.
//

//#ifndef CardMatchingGame_h
//#define CardMatchingGame_h
//
//
//#endif /* CardMatchingGame_h */


#import <Foundation/Foundation.h>
#import "Deck.h"

@interface CardMatchingGame : NSObject

//designated initializer
- (instancetype)initWithCardCount:(NSUInteger)count
                        usingDeck: (Deck *)deck;

- (void)chooseCardAtIndex:(NSUInteger)index;
- (Card *)cardAtIndex:(NSUInteger)index;

@property (nonatomic, readonly) NSInteger score;

@end


















