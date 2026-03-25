//
//  GameScene.m
//  Collisions
//
//  Created by Daniel Schepisi on 14/5/19.
//  Copyright © 2019 Daniel Schepisi. All rights reserved.
//

#import "GameScene.h"

@implementation GameScene {
//    SKShapeNode *_spinnyNode;
//    SKLabelNode *_label;
    SKNode *_newNode;
}

- (void)didMoveToView:(SKView *)view {
 // Setup your scene here
    
    self.backgroundColor = [SKColor blackColor];
    self.scaleMode = SKSceneScaleModeFill;
    self.physicsBody = [SKPhysicsBody bodyWithEdgeLoopFromRect:self.frame];
    SKNode *redBall = [self childNodeWithName:@"redBall"];
    redBall.physicsBody.angularVelocity = 1.0;
    SKNode *greenBall = [self childNodeWithName:@"greenBall"];
    greenBall.physicsBody.angularVelocity = 0.5;
    
//    _newNode = [SKNode node];
//    _newNode.physicsBody.
    
    SKNode *blueBall = [self childNodeWithName:@"blueBall"];
    blueBall.physicsBody.angularVelocity = 0.0;
    //blueBall.hidden = YES;
    _newNode = blueBall;
    
    [self removeChildrenInArray:@[blueBall]];
    
//    blueBall.physicsBody =
    
    

//    // Get label node from scene and store it for use later
//    _label = (SKLabelNode *)[self childNodeWithName:@"//helloLabel"];
//
//    _label.alpha = 0.0;
//    [_label runAction:[SKAction fadeInWithDuration:2.0]];
//
//    CGFloat w = (self.size.width + self.size.height) * 0.05;
//
//    // Create shape node to use during mouse interaction
//    _spinnyNode = [SKShapeNode shapeNodeWithRectOfSize:CGSizeMake(w, w) cornerRadius:w * 0.3];
//   _spinnyNode.lineWidth = 2.5;
//    _spinnyNode.physicsBody.mass = 1.0;
//    _spinnyNode.physicsBody.velocity = CGVectorMake(200.0, 300.0);
    
    
    
    
    
//
//   [_spinnyNode runAction:[SKAction repeatActionForever:[SKAction rotateByAngle:M_PI duration:1]]];
//    [_spinnyNode runAction:[SKAction sequence:@[
//                                                [SKAction waitForDuration:0.5],
//                                                [SKAction fadeOutWithDuration:0.5],
//                                                [SKAction removeFromParent],
//                                                ]]];
  //  self.newNode = [SKNode n]
    
    
}


- (void)touchDownAtPoint:(CGPoint)pos {
    
   SKNode *n = [_newNode copy];
    n.position = pos;
    n.hidden = NO;
    [self addChild:n];
    

    
//    SKShapeNode *n = [_spinnyNode copy];
//    n.position = pos;
//    n.strokeColor = [SKColor greenColor];
//    [self addChild:n];
}

- (void)touchMovedToPoint:(CGPoint)pos {
    
    SKNode *n = [_newNode copy];
    n.position = pos;
    n.hidden = NO;
    [self addChild:n];

    
    
//    SKShapeNode *n = [_spinnyNode copy];
//    n.position = pos;
//    n.strokeColor = [SKColor blueColor];
//    [self addChild:n];
}

- (void)touchUpAtPoint:(CGPoint)pos {
    
    
//    SKNode *n = [_newNode copy];
//    n.position = pos;
//    n.hidden = NO;
//    [self addChild:n];
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event {
    // Run 'Pulse' action from 'Actions.sks'
   // [_label runAction:[SKAction actionNamed:@"Pulse"] withKey:@"fadeInOut"];
    
    for (UITouch *t in touches) {[self touchDownAtPoint:[t locationInNode:self]];}
}
- (void)touchesMoved:(NSSet *)touches withEvent:(UIEvent *)event{
    for (UITouch *t in touches) {[self touchMovedToPoint:[t locationInNode:self]];}
}
- (void)touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event {
    for (UITouch *t in touches) {[self touchUpAtPoint:[t locationInNode:self]];}
}
- (void)touchesCancelled:(NSSet *)touches withEvent:(UIEvent *)event {
    for (UITouch *t in touches) {[self touchUpAtPoint:[t locationInNode:self]];}
}


-(void)update:(CFTimeInterval)currentTime {
    // Called before each frame is rendered
}

@end
