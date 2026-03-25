//
//  ViewController.swift
//  Destini-iOS13
//
//  Created by Angela Yu on 08/08/2019.
//  Copyright © 2019 The App Brewery. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var storyLabel: UILabel!
    @IBOutlet weak var choice1Button: UIButton!
    @IBOutlet weak var choice2Button: UIButton!
    
    
    var storyBrain = StoryBrain()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        updateUI()

    }


    @IBAction func choiceMade(_ sender: UIButton) {
        
        if (sender.restorationIdentifier == "choice1"){
            
            storyBrain.setChoiceDestination(1)
            
        }else{
            storyBrain.setChoiceDestination(2)
        }
        
        updateUI()
    }
    
    
    
    
    
    func updateUI(){
        
        choice1Button.setTitle(storyBrain.getButtonText()[0], for: .normal)
        choice2Button.setTitle(storyBrain.getButtonText()[1], for: .normal)
        
        storyLabel.text = storyBrain.getStoryText()
        
        
    }
    
    
}

