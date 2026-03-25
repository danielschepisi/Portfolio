//
//  ViewController.m
//  Distance Between Cities
//
//  Created by Daniel Schepisi on 24/2/19.
//  Copyright © 2019 Daniel Schepisi. All rights reserved.
//

#import "ViewController.h"
#import "DistanceGetter/DGDistanceRequest.h"

@interface ViewController ()

@property (nonatomic) DGDistanceRequest * req;

@property (weak, nonatomic) IBOutlet UITextField *startLocation;

@property (weak, nonatomic) IBOutlet UITextField *endLocationA;
@property (weak, nonatomic) IBOutlet UITextField *endLocationB;
@property (weak, nonatomic) IBOutlet UITextField *endLocationC;
@property (weak, nonatomic) IBOutlet UITextField *endLocationD;

@property (weak, nonatomic) IBOutlet UILabel *distanceA;
@property (weak, nonatomic) IBOutlet UILabel *distanceB;
@property (weak, nonatomic) IBOutlet UILabel *distanceC;
@property (weak, nonatomic) IBOutlet UILabel *distanceD;

@property (weak, nonatomic) IBOutlet UIButton *distanceButton;

@end


@implementation ViewController

- (IBAction)calculateDistances:(id)sender {
    
    self.distanceButton.enabled = NO;
    
    self.req = [DGDistanceRequest alloc];
    
    NSString *start = self.startLocation.text;
    NSString *endLocA = self.endLocationA.text;
    NSString *endLocB = self.endLocationB.text;
    NSString *endLocC = self.endLocationC.text;
    NSString *endLocD = self.endLocationD.text;
    
    NSArray *destinations = @[endLocA, endLocB, endLocC, endLocD];
    

   self.req = [self.req initWithLocationDescriptions:destinations sourceDescription:start];
    
    //for ARC purposes
    __weak ViewController *weakSelf = self;
    
    //create block
    self.req.callback = ^void (NSArray *responseDistances){
        
        ViewController * strongSelf = weakSelf;
        if(!strongSelf) return;
        
        NSNull *badResult = [NSNull null];
        
        //calculate and display different distances
        if(responseDistances[0] != badResult){
            double tempDist =   ([responseDistances[0] floatValue])/1000.0 ;
            NSString *x = [NSString stringWithFormat:@"%.2f km", tempDist ];
            strongSelf.distanceA.text = x;
        }else{
            strongSelf.distanceA.text = @"Error";
        };
        
        if(responseDistances[1] != badResult){
            double tempDist =   ([responseDistances[1] floatValue])/1000.0 ;
            NSString *x = [NSString stringWithFormat:@"%.2f km", tempDist ];
            strongSelf.distanceB.text = x;
        }else{
            strongSelf.distanceB.text = @"Error";
        };
            
        if(responseDistances[2] != badResult){
            double tempDist =   ([responseDistances[2] floatValue])/1000.0 ;
            NSString *x = [NSString stringWithFormat:@"%.2f km", tempDist ];
            strongSelf.distanceC.text = x;
        }else{
            strongSelf.distanceC.text = @"Error";
        }
        
        if(responseDistances[3] != badResult){
            double tempDist =   ([responseDistances[3] floatValue])/1000.0 ;
            NSString *x = [NSString stringWithFormat:@"%.2f km", tempDist ];
            strongSelf.distanceD.text = x;
        }else{
            strongSelf.distanceD.text = @"Error";
        }
    
        strongSelf.req = nil;
        strongSelf.distanceButton.enabled = YES;
    };
    
    
    [self.req start];
}


@end
