//
//  NineStudentsViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 23/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit

class NineStudentsViewController: UIViewController {
    
    @IBOutlet var studentLocationButtons: [UIButton]!
    
    @IBOutlet weak var headingLabel: UILabel!
    
    
    var selectedPosition: String?
    
    var studentMode = true
    
    var newTeacher: Teacher? = nil
    var newAssessment: Assessment? = nil
    
    var sweepModel: SweepModel? = nil
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        if let masterNavController = splitViewController?.viewControllers.first as? UINavigationController {
            
            if  let topViewIsSweepTableVC = masterNavController.topViewController as? SweepTableViewController {
                
                sweepModel = topViewIsSweepTableVC.sweepModel
                
                sweepModel?.newTeacher = newTeacher
                sweepModel?.newAssessment = newAssessment
            }
        }
        
        selectedPosition = nil
        
        navigationItem.hidesBackButton = true
        title = "Student \(sweepModel!.sweepNumber)"
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Continue", style: .plain, target: self, action: #selector(continueButtonPressed))
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        if studentMode == true {
        
                title = "Student \(sweepModel!.sweepNumber)"
        } else {
            
            title = "Teacher"
            headingLabel.text = "Select Teacher Position"
        }
            
    }
    
    
    
    @objc func continueButtonPressed() {
        
        if studentMode == true {
            if selectedPosition == nil {
                
                let alert = UIAlertController(title: "Error:", message: "Please select a student position before continuing.", preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
                
                self.present(alert, animated: true, completion: nil)
                
            }else {
                
               
                sweepModel!.setStudentDetails(position: selectedPosition!)
                performSegue(withIdentifier: "observeStudentTeacher", sender: self)
            }
        } else {
            
            
            sweepModel!.setTeacher(position: selectedPosition!)
             self.performSegue(withIdentifier: "showCognitiveOutOfFive", sender: self)
        }
    }
    
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        if segue.identifier == "observeStudentTeacher"{
            if let destinationVC = segue.destination as? ObserveStudentTeacherViewController {
                destinationVC.sweepModel = sweepModel
            }
        }
        
        if segue.identifier == "showCognitiveOutOfFive"{
            
            if let destinationVC = segue.destination as? CognitiveOutOfFiveViewController {
                
                destinationVC.sweepModel = sweepModel!
                destinationVC.sweepModel?.sweepNumber = 1
            }
            
             }
        
        
    }
    
    
    
    
    
    @IBAction func studentLocationButtonsPressed(_ sender: UIButton) {
        
        var index = 0
        
        for button in studentLocationButtons {
            
            if button == sender {
                
                //could have just used button here instead of grabbing index
                let student = studentLocationButtons[index]
                student.tintColor = #colorLiteral(red: 0, green: 0.6901960784, blue: 0.4196078431, alpha: 1)
                selectedPosition = student.titleLabel!.text
                
                
                
            } else {
                studentLocationButtons[index].tintColor = #colorLiteral(red: 0.2196957469, green: 0.4681266546, blue: 1, alpha: 1)
            }
            index += 1
        }
    }
    
}
