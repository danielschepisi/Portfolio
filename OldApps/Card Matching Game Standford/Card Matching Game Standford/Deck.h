//
//  Deck.h
//  Card Matching Game Standford
//
//  Created by Daniel Schepisi on 27/10/2017.
//  Copyright © 2017 Daniel Schepisi. All rights reserved.
//
//
//#ifndef Deck_h
//#define Deck_h
//
//
//#endif /* Deck_h */

#import <Foundation/Foundation.h>
#import "Card.h"

@interface Deck : NSObject

- (void)addCard:(Card *)card atTop:(BOOL)atTop;
- (void)addCard:(Card *)card;


- (Card *)drawRandomCard;











@end
