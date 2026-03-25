//
//  ViewController.m
//  Geo Fence
//
//  Created by Daniel Schepisi on 30/4/19.
//  Copyright © 2019 Daniel Schepisi. All rights reserved.
//

#import "ViewController.h"
#import "MapKit/MapKit.h"

@interface ViewController () <MKMapViewDelegate, CLLocationManagerDelegate>

@property (weak, nonatomic) IBOutlet MKMapView *mapView;
@property (weak, nonatomic) IBOutlet UIButton *startTestButton;
//@property (weak, nonatomic) IBOutlet UILabel *label;


@property (strong, nonatomic) CLLocationManager *locationManager;
@property (nonatomic, assign) BOOL mapIsMoving;
@property (strong, nonatomic) MKPointAnnotation *currentAnno;
@property (strong, nonatomic) MKPointAnnotation *workshopAnno;
@property (strong, nonatomic) CLCircularRegion *geoRegion;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.startTestButton.enabled = NO;
    self.mapIsMoving = NO;
    
    //self up location mamanger
    self.locationManager = [[CLLocationManager alloc] init];
    self.locationManager.delegate = self;
    self.locationManager.allowsBackgroundLocationUpdates = YES;
    self.locationManager.pausesLocationUpdatesAutomatically = YES;
    self.locationManager.desiredAccuracy = kCLLocationAccuracyBest;
    self.locationManager.distanceFilter = 4; //meters
    
    //zoom in close
    CLLocationCoordinate2D noLocation = CLLocationCoordinate2DMake(-37.829851, 145.042964);
    MKCoordinateRegion viewRegion = MKCoordinateRegionMakeWithDistance(noLocation, 1000, 1000);
    MKCoordinateRegion adjustedRegion = [self.mapView regionThatFits:viewRegion];
    [self.mapView setRegion:adjustedRegion animated:YES];
    
    //create annotations
    [self addCurrentAnnotation];
    [self addWorkshopAnnotation];

    //set up geoRegion object
    [self setUpGeoRegion];
    
    
    //check if can do geofences
    if([CLLocationManager isMonitoringAvailableForClass:[CLCircularRegion class]] == YES){
        
        if(([CLLocationManager authorizationStatus] == kCLAuthorizationStatusAuthorizedWhenInUse)|| [CLLocationManager authorizationStatus] == kCLAuthorizationStatusAuthorizedAlways ){
            self.startTestButton.enabled = YES;
        } else {
            [self.locationManager requestAlwaysAuthorization];
        }
        
        UIUserNotificationType types = UIUserNotificationTypeBadge | UIUserNotificationTypeSound | UIUserNotificationTypeAlert;
        
        UIUserNotificationSettings *mySettings = [UIUserNotificationSettings settingsForTypes:types categories:nil];
        [[UIApplication sharedApplication] registerUserNotificationSettings:mySettings];
        
    } else {
        //self.label.text = @"Bogus";
    }
}


-(void) locationManager:(CLLocationManager *)manager didChangeAuthorizationStatus:(CLAuthorizationStatus)status{
    CLAuthorizationStatus currentStatus = [CLLocationManager authorizationStatus];
    if((currentStatus == kCLAuthorizationStatusAuthorizedWhenInUse)||(currentStatus == kCLAuthorizationStatusAuthorizedAlways)){
        self.startTestButton.enabled = YES;
    }
}


- (IBAction)startTestButtonPressed:(id)sender {
    self.mapView.showsUserLocation = YES;
    [self.locationManager startUpdatingLocation];
    [self.locationManager startMonitoringForRegion:self.geoRegion];
}

-(void) mapView:(MKMapView *)mapView regionWillChangeAnimated:(BOOL)animated{
    self.mapIsMoving = YES;
}

-(void) mapView:(MKMapView *)mapView regionDidChangeAnimated:(BOOL)animated{
    self.mapIsMoving = NO;
}

-(void) setUpGeoRegion{
    self.geoRegion = [[CLCircularRegion alloc] initWithCenter:CLLocationCoordinate2DMake(-37.831108, 145.053814)
                radius:10
            identifier:@"MyRegionIdentifier"];
}


-(void) addCurrentAnnotation {
    self.currentAnno = [[MKPointAnnotation alloc] init];
    self.currentAnno.coordinate = CLLocationCoordinate2DMake(0.0, 0.0);
    self.currentAnno.title = @"My Location";
}

-(void) addWorkshopAnnotation {
    self.workshopAnno = [[MKPointAnnotation alloc] init];
    self.workshopAnno.coordinate = CLLocationCoordinate2DMake(-37.831108, 145.053814);
    self.workshopAnno.title = @"Woody's Wonderful Workshop";
    [self.mapView addAnnotation:self.workshopAnno];
}


-(void) centerMap: (MKPointAnnotation *)centerPoint{
    [self.mapView setCenterCoordinate:centerPoint.coordinate animated:YES];
}

-(void) locationManager:(CLLocationManager *)manager didUpdateLocations:(nonnull NSArray<CLLocation *> *)locations{
    
    self.currentAnno.coordinate = locations.lastObject.coordinate;
    if(self.mapIsMoving == NO){
        [self centerMap:self.currentAnno];
    }
}


-(void) locationManager:(CLLocationManager *)manager didEnterRegion:(CLRegion *)region{
    UILocalNotification *note = [[UILocalNotification alloc] init];
    note.fireDate = nil;
    note.repeatInterval = 0;
    note.alertTitle = @"Welcome to Woody's Wonderful Woodshop!";
    note.alertBody = [NSString stringWithFormat:@"Use the coupon code WIGGLEWOODY to redeem one free session with Woody and her wonderful wood working abilities."];
    [[UIApplication sharedApplication] scheduleLocalNotification:note];
    
}





@end
