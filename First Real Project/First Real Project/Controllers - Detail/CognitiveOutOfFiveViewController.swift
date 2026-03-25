//
//  CognitiveOutOfFiveViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 13/5/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit

class CognitiveOutOfFiveViewController: UIViewController {

    var sweepModel: SweepModel? = nil
    
    var cognitiveLevelSelected: String? = nil
    var workCompletedLevelSelected: String? = nil
   
    //maybe add s later
    @IBOutlet var cognitiveLevelButton: [UIButton]!
    
    @IBOutlet var workCompletedButtons: [UIButton]!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.hidesBackButton = true

        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Continue", style: .plain, target: self, action: #selector(continueButtonPressed))
        
        title = "Student \(sweepModel!.sweepNumber)"
        
        // Do any additional setup after loading the view.
    }
    
    
    @objc func continueButtonPressed() {
        
        if cognitiveLevelSelected == nil || workCompletedLevelSelected == nil {
            
            let action = UIAlertAction(title: "OK", style: .default, handler: nil)
            
            let alertController = UIAlertController(title: "Warning:", message: "Please make sure to select both levels before advancing.", preferredStyle: .alert)
            
            alertController.addAction(action)
            
            self.present(alertController, animated: true)
            
            
        } else {
            
            if  sweepModel!.isFinishedSweepingAllStudents(withCognitiveLevel: cognitiveLevelSelected!, andWorkCompletedLevel: workCompletedLevelSelected!) {
                
                performSegue(withIdentifier: "showTeacherEvidenceVC", sender: self)

            } else {
                //reset view for next student
                cognitiveLevelSelected = nil
                workCompletedLevelSelected = nil
                
                for button in cognitiveLevelButton{
                    button.tintColor = #colorLiteral(red: 0.2196957469, green: 0.4681266546, blue: 1, alpha: 1)
                }
                for button in workCompletedButtons{
                    button.tintColor = #colorLiteral(red: 0.2196957469, green: 0.4681266546, blue: 1, alpha: 1)
                }
                
                title = "Student \(sweepModel!.sweepNumber)"

        }
        }
        
    }
    
    
    @IBAction func cognitiveLevelButtonPressed(_ sender: UIButton) {
        
        cognitiveLevelSelected = sender.titleLabel?.text
        
        for button in cognitiveLevelButton {
        
            if button == sender {
                button.tintColor = #colorLiteral(red: 0, green: 0.6901960784, blue: 0.4196078431, alpha: 1)
            } else {
                button.tintColor = #colorLiteral(red: 0.2196957469, green: 0.4681266546, blue: 1, alpha: 1)
            }
        }
    }
    
    
    @IBAction func workCompletedButtonPressed(_ sender: UIButton) {
        workCompletedLevelSelected = sender.titleLabel?.text
    
        for button in workCompletedButtons {
               
                   if button == sender {
                       button.tintColor = #colorLiteral(red: 0, green: 0.6901960784, blue: 0.4196078431, alpha: 1)
                   } else {
                       button.tintColor = #colorLiteral(red: 0.2196957469, green: 0.4681266546, blue: 1, alpha: 1)
                   }
               }
        
        
    }
    
    


    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
      
//        performSegue(withIdentifier: "showTeacherEvidenceVC", sender: self)
        }
        
    }
    


