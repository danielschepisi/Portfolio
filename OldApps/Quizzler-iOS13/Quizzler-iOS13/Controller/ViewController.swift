//
//  ViewController.swift
//  Quizzler-iOS13
//
//  Created by Angela Yu on 12/07/2019.
//  Copyright © 2019 The App Brewery. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var progressBar: UIProgressView!
    @IBOutlet weak var trueButton: UIButton!
    @IBOutlet weak var falseButton: UIButton!
    @IBOutlet weak var thirdButton: UIButton!
    @IBOutlet weak var scoreLabel: UILabel!
    
    var quizBrain = QuizBrain()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        quizBrain.totalNumberOfQuestions = quizBrain.quiz.count
        updateUI()
    }
    
    @IBAction func answerButtonPressed(_ sender: UIButton) {
        
        if quizBrain.isAnswerCorrect(sender.currentTitle!) {
            sender.backgroundColor = UIColor.green
        }else{
            sender.backgroundColor = UIColor.red
        }
        
        quizBrain.increaseStats()
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.4){
            sender.backgroundColor = UIColor.clear
            self.updateUI()
        }
    }
    
    
    func updateUI(){
        questionLabel.text = quizBrain.getQuestionText()
        progressBar.progress = quizBrain.progress
        
        scoreLabel.text = "Score: \(quizBrain.getScore())"
    }
   


}

