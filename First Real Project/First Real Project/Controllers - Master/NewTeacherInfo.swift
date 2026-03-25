//
//  NewTeacherInfo.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 21/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit

class NewTeacherInfo: MasterViewController {
    
    let teacherInfo = TeacherInfoModel().teacherInfo

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "cell")
        navigationItem.hidesBackButton = true
        
        tableView.separatorStyle = .none
        
        navigationItem.rightBarButtonItem = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(cancelButtonPressed))
    }
    
    override func configureView() {
        super.configureView()
        title = "New"
    }
    
    
    
    

    @objc func cancelButtonPressed() {
        
        let alert = UIAlertController(title: "Warning:", message: "This will terminate the obsevatoin and all data will be lost.", preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "OK", style: .destructive, handler: { (action) in
            self.cancelNewTeacher()
        }))
        
        alert.addAction(UIAlertAction(title: "Resume", style: .cancel, handler: nil))
        
        self.present(alert, animated: true)
       
    }
    
    
   func cancelNewTeacher() {
        navigationController?.popViewController(animated: true)
        
        if let split = splitViewController {
            if let detailView = split.viewControllers.last as? UINavigationController {
                detailView.popViewController(animated: true)
            }
        }
    }
        

    
    
    
    
    
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 2
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return 1
        }else {
        return teacherInfo.count
        }
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
         cell.isUserInteractionEnabled = false

        if indexPath.section == 1 {
        cell.textLabel?.text = teacherInfo[indexPath.row]
        cell.textLabel?.font = UIFont.boldSystemFont(ofSize: 20.0)
        } else {
            cell.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
            cell.isUserInteractionEnabled = false
        }
        return cell
    }
    
    
    
   

 



}
