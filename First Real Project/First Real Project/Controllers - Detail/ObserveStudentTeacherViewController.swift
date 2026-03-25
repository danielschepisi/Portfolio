//
//  ObserveStudentTeacherViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 23/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit

class ObserveStudentTeacherViewController: UIViewController, UIPopoverControllerDelegate {
    
    let teacherObservations = TeacherObservations()
    var sweepModel: SweepModel? 
    
    var selectedTeachingStyle: String? = ""
    var selectedTeachingType: String? = ""
    var studentMode = true {
        didSet{
            if studentMode == true {
                setUpStudentScreen()
            }
            if studentMode == false {
                setUpTeacherScreen()
            }
        }
    }
    
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var timerLabel: UILabel!
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var yesButton: UIButton!
    @IBOutlet weak var noButton: UIButton!
    @IBOutlet weak var clickToContinueLabel: UILabel!
    
    @IBAction func testingContinue(_ sender: UIButton) {
        
        
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        timerLabel.layer.cornerRadius = 7.0
        timerLabel.clipsToBounds = true
        timerLabel.text = ""
        navigationItem.hidesBackButton = true
        title = "Student \(sweepModel!.sweepNumber)"
        
//        timerLabel.text = "30"
        questionLabel.isHidden = true
        yesButton.isHidden = true
        noButton.isHidden = true
        clickToContinueLabel.isHidden = true
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        displayCountDownFrom(seconds: 2)
    }
    
    
    func setUpStudentScreen() {
        
        self.titleLabel.text = "Observe Student"
        self.hideItemsOnBottomHalfOfView(true)
        self.yesButton.setTitle("Yes", for: .normal)
        self.noButton.setTitle("No", for: .normal)
        self.questionLabel.text = "Was the student engaged?"
        displayCountDownFrom(seconds: 2)
        self.clickToContinueLabel.isHidden = true
        navigationItem.rightBarButtonItem = nil
        
        
    }
    
    func setUpTeacherScreen() {
        print("Teacher Screen")
    }
    
    
    
    @IBAction func answerButtonPressed(_ sender: UIButton) {
        if sender.titleLabel?.text == "YES" || sender.titleLabel?.text == "NO" {
           
           //set isEngaged prop on Student
            if sender.titleLabel?.text == "YES" {
             sweepModel?.setStudentDetails(isEngaged: true)
            } else {
                sweepModel?.setStudentDetails(isEngaged: false)
            }
            
            
            // save title to property somewhere then change screen
            self.titleLabel.text = "Observe Teacher"
            self.hideItemsOnBottomHalfOfView(true)
            self.yesButton.setTitle("Style", for: .normal)
            self.noButton.setTitle("Type", for: .normal)
            self.questionLabel.text = "Record Teaching Style and Interaction Type:"
            displayCountDownFrom(seconds: 2)

            
        } else   if sender.accessibilityIdentifier == "leftButton" {
            self.selectedTeachingStyle = nil
            createAndPresentPopover(withArray: teacherObservations.styles, sender: sender)
        } else   if sender.accessibilityIdentifier == "rightButton" {
            self.selectedTeachingType = nil
            self.createAndPresentPopover(withArray: teacherObservations.types, sender: sender)
        }
        
        
        
        
    }
    
    
    func setTeachingQualities(selectedRowText: String) {
        
        if selectedTeachingStyle == nil {
            self.selectedTeachingStyle = selectedRowText
            self.yesButton.setTitle("Style: \(selectedRowText)", for: .normal)
            sweepModel!.setStudentDetails(teachingStyle: selectedRowText)
        } else if selectedTeachingType == nil {
            self.selectedTeachingType = selectedRowText
            self.noButton.setTitle("Type: \(selectedRowText)", for: .normal)
            sweepModel!.setStudentDetails(teachingType: selectedRowText)
        }
        
        if selectedTeachingType != "" && selectedTeachingStyle != "" {
            clickToContinueLabel.isHidden = false
            
            navigationItem.rightBarButtonItem =  UIBarButtonItem(title: "Continue", style: .plain, target: self, action: #selector(continueButtonPressed))
            
        }
        
    }
    
    @objc func continueButtonPressed(){
        
        sweepModel?.placeStudentInArray()
        
        if sweepModel!.sweepNumber < 2 {
            //go to next sweep
            
            sweepModel!.sweepNumber += 1
            
            
            ((splitViewController?.viewControllers.first as? UINavigationController)?.topViewController as? SweepTableViewController)?.tableView.reloadData()
            
            
            (splitViewController?.viewControllers.last as? UINavigationController)?.popViewController(animated: true)
            
            
        } else {
            
            let numVCs = navigationController?.viewControllers.count
            
            let currentVC = navigationController?.viewControllers[numVCs! - 2] as! NineStudentsViewController
            
            currentVC.studentMode = false
            
            navigationController?.popViewController(animated: true)
            
            // commented out from here
            
//            sweepModel!.saveData()
//            //to check coreData
//          //  performSegue(withIdentifier: "summaryOfCoreData", sender: self)
//
//            performSegue(withIdentifier: "showCognitiveOutOfFive", sender: self)
            
            
        }
        
        
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        
        //for using coreData check table
        //(segue.destination as! SummaryTableViewController).moc = sweepModel!.moc
        
        
        
    }
    
    
    func createAndPresentPopover(withArray array: [String], sender: UIButton) {
        let tableViewController = PopOverTableViewController(optionsArray: array, parentViewController: self)
        tableViewController.modalPresentationStyle = UIModalPresentationStyle.popover
        
        present(tableViewController, animated: true, completion: nil)
        
        let popoverPresentationController = tableViewController.popoverPresentationController
        popoverPresentationController?.sourceView = sender
        popoverPresentationController?.sourceRect = CGRect(x: 0, y: 0, width: sender.frame.size.width, height: sender.frame.size.height)
    }
    
    
    //MARK: - Managing the View
    func hideItemsOnBottomHalfOfView(_ bool: Bool) {
        self.yesButton.isHidden = bool
        self.noButton.isHidden = bool
        self.questionLabel.isHidden = bool
    }
    func displayCountDownFrom(seconds: Int) {
        //initiates onscreen count down and unhides UI elements at end of count down
        var time = seconds
        timerLabel.text = String(time)
        Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { timer in
                   self.timerLabel.text = String(time)
                   time -= 1
            self.timerLabel.text = String(time)
            
            if time == 0 {
                timer.invalidate()
                self.hideItemsOnBottomHalfOfView(false)
            }
        }
    }
    
}
