//
//  CreateTeacher.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 20/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit
import CoreData

class CreateTeacher: MasterViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        

        
        
        //        let teach = Teacher(context: managedObjectContext!)
        //        teach.name = "Boss"
        //
        //        teachers.append(teach)
        //
        //        do{
        //        try managedObjectContext?.save()
        //        } catch {
        //            print("savign in createteacher gone bad: \(error)")
        //        }
        //
    }
    
    override func configureView() {
        super.configureView()
        title = "Teachers"
    }
    
    
    
    
    // MARK: - Table view data source
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return 4
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 3 {
            return teachers.count
        } else {
            return 1
        }
    }
    
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        
      
        
        
        switch indexPath.section {
        case 0:
            cell.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
            cell.isUserInteractionEnabled = false
        case 1:
            cell.textLabel?.text = "New Instance"
            cell.textLabel?.textColor = #colorLiteral(red: 0.9298406243, green: 0.6020182371, blue: 0.2669149041, alpha: 1)
        case 2:
            cell.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
            cell.isUserInteractionEnabled = false
        default:
            cell.textLabel?.text = (teachers[indexPath.row].value(forKey: "name") as! String)
            
            
        }
        
        cell.textLabel?.font = UIFont.boldSystemFont(ofSize: 20)
        
        return cell
    }
    
    
    
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        
        if indexPath.section == 3 {
              if editingStyle == .delete {
                   let teacherAttachedToDelete = teachers[indexPath.row]
                
                      teachers.remove(at: indexPath.row)
                      tableView.deleteRows(at: [indexPath], with: .fade)
                      
                if let moc = managedObjectContext {
                    
                    let assessmentToDelete = teacherAttachedToDelete.assessmentsDone?.first(where: { (any) -> Bool in
                        return true
                    })
                    
                    
                    moc.delete(assessmentToDelete as! NSManagedObject)
                    moc.delete(teacherAttachedToDelete)

                    do {
                        try moc.save()
                    } catch  {
                        print("Error saving context when deleting from swipable row \(error)")
                        print(error)
                    }
                    
                    
                    
                }
                      
                      
                  } else if editingStyle == .insert {
                      // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
                  }
        }
        
        
      
    }
    
    
    
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if tableView.cellForRow(at: indexPath)?.textLabel?.text == "New Instance"{
            
            //Grab Master View NavCtlr
            if let split = splitViewController {
                guard let masterNavController = split.viewControllers.first as? UINavigationController
                    else {
                        fatalError("Missing MasterViewController")
                }
                
                detailViewController?.performSegue(withIdentifier: "embeddedSegue", sender: self)
                
                masterNavController.pushViewController(NewTeacherInfo().self, animated: true)
            }
        }
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
    
}
