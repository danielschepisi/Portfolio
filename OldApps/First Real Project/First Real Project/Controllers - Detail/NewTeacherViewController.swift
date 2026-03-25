//
//  NewTeacherViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 20/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit
import CoreData

class NewTeacherViewController: UIViewController {

    
    
    @IBOutlet weak var schoolTextField: UITextField!
    @IBOutlet weak var teacherTextField: UITextField!
    @IBOutlet weak var subjectTextField: UITextField!
    @IBOutlet weak var classBandingTextField: UITextField!
    @IBOutlet weak var yearLevelTextField: UITextField!
    @IBOutlet weak var periodTextField: UITextField!
    @IBOutlet weak var yearsTeachingTextField: UITextField!
    @IBOutlet weak var termTextField: UITextField!
    
    var managedObjectContext: NSManagedObjectContext? = nil
    
    var newTeacher: Teacher? = nil
    var newAssessment: Assessment? = nil
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setFields()
        
        //create new Assessment and Teacher
        if let moc = managedObjectContext {
        newTeacher = Teacher(context: moc)
        newAssessment = Assessment(context: moc)
        }
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Commence", style: .plain, target: self, action: #selector(commenceButtonPressed))
        navigationItem.hidesBackButton = true
    }
    
    func setFields() {
        schoolTextField.placeholder = "Melbourne University"
        schoolTextField.isEnabled = false
        yearLevelTextField.text = "0"
        yearsTeachingTextField.text = "0"
        periodTextField.text = "0"
        termTextField.text = "0"
    }
    
    
    @objc func commenceButtonPressed() {
        if teacherTextField.text == "" {
            let alert = UIAlertController(title: "Error", message: "A School and Teacher must be specified before commencing the observation.", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: nil))
            present(alert, animated: true, completion: nil)
        } else {
            
            passDetailsToAssessment()
            
//            do {
//                try managedObjectContext?.save()
//            } catch {
//                print("Trouble saving MOC in NewTeacherViewContoller: \(error)")
//            }
            
            performSegue(withIdentifier: "show9students", sender: self)
            
            //Get MasterView NavController
            guard let masterTableViewNavigation = splitViewController?.viewControllers.first as? UINavigationController else {
                      fatalError("Done stuffed up getting masterTableView in NineStudentsViewController")
                  }
                  
                  let sweepTableView = SweepTableViewController()
                  sweepTableView.view.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
            
                  
                  masterTableViewNavigation.pushViewController(sweepTableView, animated: true)
            
        }
    }
  
    func passDetailsToAssessment() {
        newTeacher!.name = teacherTextField.text

        newAssessment!.school = schoolTextField.placeholder
        newAssessment!.whoAssessed = newTeacher
        newAssessment!.date = NSDate.now
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let controller = (segue.destination as? NineStudentsViewController) {
            controller.newTeacher = newTeacher
            controller.newAssessment = newAssessment
        }
    }
    

}
