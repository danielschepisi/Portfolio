//
//  Card.h
//  Card Matching Game Standford
//
//  Created by Daniel Schepisi on 25/10/2017.
//  Copyright © 2017 Daniel Schepisi. All rights reserved.
//

//#ifndef Card_h
//#define Card_h


//#endif /* Card_h */

#import <Foundation/Foundation.h>



@interface Card : NSObject


@property (strong, nonatomic) NSString *contents;

@property (nonatomic, getter=isChosen) BOOL chosen;
@property (nonatomic, getter=isMatched) BOOL matched;


- (int)match:(NSArray *)otherCards;





@end
