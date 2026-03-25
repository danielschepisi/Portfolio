//
//  PopOverTableViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 24/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit

class PopOverTableViewController: UITableViewController {
    
    let optionsArray: [String]
    let immediateParentViewController: ObserveStudentTeacherViewController
    
    
    
    
    init(optionsArray: [String], parentViewController: ObserveStudentTeacherViewController) {
        self.optionsArray = optionsArray
        self.immediateParentViewController = parentViewController
        super.init(style: .plain)
    }
    
    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "reuseIdentifier")
        
        let sectionHeight = tableView.rect(forSection: 0).height
        
        self.preferredContentSize = CGSize(width: 200, height: sectionHeight)
        
    }
    
    
    
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return optionsArray.count
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)
        
        cell.textLabel?.text = optionsArray[indexPath.row]
        
        return cell
    }
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.cellForRow(at: indexPath)?.accessoryType = UITableViewCell.AccessoryType.checkmark
        
        if let txtLbl = tableView.cellForRow(at: indexPath)?.textLabel {
            
            if let text = txtLbl.text {
            immediateParentViewController.setTeachingQualities(selectedRowText: text)
            }
            
            dismiss(animated: true, completion: nil)
        }
        
        
        
        
        
        
    }
    
    
    
    
}
