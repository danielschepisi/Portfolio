//
//  ViewController.m
//  Third CoreData
//
//  Created by Daniel Schepisi on 13/3/19.
//  Copyright © 2019 Daniel Schepisi. All rights reserved.
//

#import "ViewController.h"
#import "AppDelegate.h"
#import "Person+CoreDataClass.h"

@interface ViewController ()

@property (weak, nonatomic) IBOutlet UILabel *logLabel;
@property (weak, nonatomic) IBOutlet UITextField *personTextField;
@property (weak, nonatomic) IBOutlet UILabel *numObStorage;


@property (nonatomic) AppDelegate *appDelegate;

-(void) updateLogLabel;

@end

@implementation ViewController


- (IBAction)clearData:(id)sender {
   
    NSManagedObjectContext *moc = self.appDelegate.persistentContainer.viewContext;
    NSFetchRequest *fetch = [NSFetchRequest fetchRequestWithEntityName:@"Person"];
    
    
    NSError *error = nil;
    NSLog(@"\n tester2\n ");
    //error occurs here
    NSArray *results = [moc executeFetchRequest:fetch error:&error];
    NSLog(@"\n tester3\n ");
    if(!results){
        NSLog(@"Error fetching Person objects %@\n%@", [error localizedDescription], [error userInfo] );
        abort();
    }
    
    for(Person *p in results){
        [moc deleteObject:p];
        
        [self.appDelegate saveContext];
        
        [self updateLogLabel];
        [self updateObjectNumber];
    }
}

- (void)updateObjectNumber{
    NSManagedObjectContext *moc = self.appDelegate.persistentContainer.viewContext;
    NSFetchRequest *fetch = [NSFetchRequest fetchRequestWithEntityName:@"Person"];
    
    NSError *error = nil;
    NSArray *results = [moc executeFetchRequest:fetch error:&error];
    
    NSUInteger count = [results count];
    self.numObStorage.text = [NSString stringWithFormat:@"%lu", (unsigned long)count];
}

- (void)viewDidLoad {
    [super viewDidLoad];

    self.appDelegate = (AppDelegate *) [[UIApplication sharedApplication] delegate];
    [self updateLogLabel];
    [self updateObjectNumber];
    
    self.logLabel.layer.borderColor = [UIColor blackColor].CGColor;
    self.logLabel.layer.borderWidth = 10;
}


- (IBAction)personButtonTapped:(id)sender {
    
    Person *person = [self.appDelegate createPerson];
    person.name = self.personTextField.text;
    [self.appDelegate saveContext];
    [self updateLogLabel];
    [self updateObjectNumber];
    self.personTextField.text = @"";
}


-(void) updateLogLabel{
    
    NSManagedObjectContext *moc = self.appDelegate.persistentContainer.viewContext;
    NSFetchRequest *fetch = [NSFetchRequest fetchRequestWithEntityName:@"Person"];
    
    
    NSError *error = nil;
    NSArray *results = [moc executeFetchRequest:fetch error:&error];
    if(!results){
        NSLog(@"Error fetching Person objects %@\n%@", [error localizedDescription], [error userInfo] );
        abort();
    }
    
    NSMutableString *buffer = [NSMutableString stringWithString:@""];
    for(Person *p in results){
        [buffer appendFormat:@"\n%@", p.name, nil];
    }
    
    self.logLabel.text = buffer;
}



@end
