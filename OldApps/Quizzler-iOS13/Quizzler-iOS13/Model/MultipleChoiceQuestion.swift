//
//  MultipleChoiceQuestion.swift
//  Quizzler-iOS13
//
//  Created by Daniel Schepisi on 20/3/20.
//  Copyright © 2020 The App Brewery. All rights reserved.
//

import Foundation

struct MultipleChoiceQuestion {
    
    let text: String
    let choices: [String]
    let correctAnswer: String
    
    
    init(q: String, a: [String], correctAnswer: String ){
        
        text = q
        choices = a
        self.correctAnswer = correctAnswer
        
    }
    
    
    
    
}
