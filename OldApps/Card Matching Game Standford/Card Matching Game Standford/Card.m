//
//  Card.m
//  Card Matching Game Standford
//
//  Created by Daniel Schepisi on 25/10/2017.
//  Copyright © 2017 Daniel Schepisi. All rights reserved.
//

#import "Card.h"

@interface Card()

@end

@implementation Card



-(int)match:(NSArray *)otherCards
{
    
    int score = 0;
    
    for (Card *card in otherCards)
    {
        if ([card.contents isEqualToString:self.contents])
        {
            score = 1;
        }
    }
    
    
    
    
    return score;
}






































@end
