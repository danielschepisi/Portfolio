//
//  ViewController.m
//  Social Media Options
//
//  Created by Daniel Schepisi on 25/2/19.
//  Copyright © 2019 Daniel Schepisi. All rights reserved.
//

#import "ViewController.h"
#import "Social/Social.h"

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UITextView *twitterTextView;
@property (weak, nonatomic) IBOutlet UITextView *facebookTextView;
@property (weak, nonatomic) IBOutlet UITextView *moreTextView;
@property (weak, nonatomic) IBOutlet UIToolbar *socialToolbar;

-(void) styleSubViews;
-(void) closeKeyboard;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self styleSubViews];
}

- (IBAction)postToTwitter:(id)sender {
    
    //close keyboard
    [self closeKeyboard];
    
    if([SLComposeViewController isAvailableForServiceType:SLServiceTypeTwitter]){
        //post to twitter
        SLComposeViewController *twitterVC = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeTwitter];
        if([self.twitterTextView.text length] <= 140){
        [twitterVC setInitialText:self.twitterTextView.text];
        }else{
            NSString *shortTwitter = [self.twitterTextView.text substringToIndex:140];
            [twitterVC setInitialText:shortTwitter];
        }
        [self presentViewController:twitterVC animated:YES completion:nil];
    }else{
        //post alert to login
        UIAlertController *alertLogin = [UIAlertController alertControllerWithTitle:@"Social Media" message:@"Please log into your Twitter account" preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction *okayAction = [UIAlertAction actionWithTitle:@"Okay" style:UIAlertActionStyleDefault handler:nil];
        [alertLogin addAction:okayAction];
        [self presentViewController:alertLogin animated:YES completion:nil];
    }
}

- (IBAction)postToFacebook:(id)sender {
    
    //close keyboard
    [self closeKeyboard];
    
    //post to facebook (skipped error message if not signed in)
    SLComposeViewController *facebookVC = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];
    [facebookVC setInitialText:self.facebookTextView.text];
    [self presentViewController:facebookVC animated:YES completion:nil];
    
}



- (IBAction)moreOptionsAction:(id)sender {
    
    //close keyboard
    [self closeKeyboard];
    
    NSString *moreTextString = self.moreTextView.text;
    UIActivityViewController *moreOptionsVC = [[UIActivityViewController alloc] initWithActivityItems:@[moreTextString] applicationActivities:nil];
    [self presentViewController:moreOptionsVC animated:YES completion:nil];
    }



- (IBAction)uselessShareAction:(id)sender {
    
    //close keyboard
    [self closeKeyboard];
    
    UIAlertController *alertController = [UIAlertController alertControllerWithTitle:@"Pay Note:" message:@"This button does nothing" preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okayAlert = [UIAlertAction actionWithTitle:@"Okie Dokie!" style:UIAlertActionStyleDefault handler:nil];
    [alertController addAction:okayAlert];
    [self presentViewController:alertController animated:YES completion:nil];
}

-(void) styleSubViews{
    
    //set background colours
    self.twitterTextView.layer.backgroundColor = [UIColor colorWithRed:1.0 green:0.0 blue:0.0 alpha:0.6].CGColor;
    self.facebookTextView.layer.backgroundColor = [UIColor colorWithRed:1.0 green:1.0 blue:0.0 alpha:0.6].CGColor;
    self.moreTextView.layer.backgroundColor = [UIColor colorWithRed:0.0 green:1.0 blue:0.0 alpha:0.6].CGColor;
    self.socialToolbar.layer.backgroundColor = [UIColor blackColor].CGColor;
    
    //set borders
    self.twitterTextView.layer.borderWidth = 1.4;
    self.twitterTextView.layer.borderColor = [UIColor blackColor].CGColor;
    self.facebookTextView.layer.borderWidth = 1.4;
    self.facebookTextView.layer.borderColor = [UIColor blackColor].CGColor;
    self.moreTextView.layer.borderWidth = 1.4;
    self.moreTextView.layer.borderColor = [UIColor blackColor].CGColor;
    self.socialToolbar.layer.borderWidth = 1.4;
    self.socialToolbar.layer.borderColor = [UIColor blackColor].CGColor;
    
    //round corners
    self.twitterTextView.layer.cornerRadius = 10.0;
    self.facebookTextView.layer.cornerRadius = 10.0;
    self.moreTextView.layer.cornerRadius = 10.0;
    self.socialToolbar.layer.cornerRadius = 10.0;
}


-(void) closeKeyboard{
    if([self.twitterTextView isFirstResponder]){
        [self.twitterTextView resignFirstResponder];
    }
    if([self.facebookTextView isFirstResponder]){
        [self.facebookTextView resignFirstResponder];
    }
    if([self.moreTextView isFirstResponder]){
        [self.moreTextView resignFirstResponder];
    }
}

@end
