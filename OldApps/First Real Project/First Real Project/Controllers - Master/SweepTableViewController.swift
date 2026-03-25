//
//  SweepTableViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 25/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit

class SweepTableViewController: UITableViewController {
    
    var sweepModel = SweepModel(sweepNumber: 1)
 
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "reuseIdentifier")
        
        tableView.separatorStyle = .none
        print(sweepModel)
        

    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        
        navigationItem.hidesBackButton = true
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(cancelButtonPressed))
        
    }
    
    
    @objc func cancelButtonPressed() {
         
         let alert = UIAlertController(title: "Warning:", message: "This will terminate the obsevation and all data will be lost.", preferredStyle: .alert)
         
         alert.addAction(UIAlertAction(title: "OK", style: .destructive, handler: { (action) in
             self.cancelNewTeacher()
         }))
         
         alert.addAction(UIAlertAction(title: "Resume", style: .cancel, handler: nil))
         
         self.present(alert, animated: true)
        
     }
    
    func cancelNewTeacher() {
        
        //Change Detail View back to Logo
        if let split = splitViewController {
            if let navController = split.viewControllers.last as? UINavigationController {
                navController.popToViewController(navController.viewControllers.first!, animated: true)
            }
        }
        
        //Change Master View back to Create Teacher View
        if let viewController = (navigationController?.viewControllers[1] as? CreateTeacher ) {
            navigationController?.popToViewController(viewController, animated: true)
        }
        
       
        
    }
    
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return 2
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
//        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)
        
        // do we have any issue not putting indexPath in below?
        
        
        let cell2 = UITableViewCell(style: .value1, reuseIdentifier: "reuseIdentifier2")
        
        if indexPath.row == 0 {
            cell2.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
        } else {
                        
            cell2.textLabel?.text = "Sweep \(sweepModel.sweepNumber)"
            cell2.detailTextLabel?.text = "In progress"
         
        }
        return cell2
    }

    
    
    
}
