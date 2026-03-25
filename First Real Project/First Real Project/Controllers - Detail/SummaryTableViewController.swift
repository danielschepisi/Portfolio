//
//  SummaryTableViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 11/5/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit
import CoreData

class SummaryTableViewController: UITableViewController {

    var moc: NSManagedObjectContext? = nil
    var allAssessments = [Assessment]()
    
    var assessmentDetails = [[String]]()
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
// just when using as initial view viewcontroll
        
         moc = (UIApplication.shared.delegate as? AppDelegate)?.persistentContainer.viewContext
        
        fetchAllData()
        
        sortData()
    
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return assessmentDetails.count
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return assessmentDetails[0].count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "reuseIdentifier", for: indexPath)
        
        cell.textLabel?.text = assessmentDetails[indexPath.section][indexPath.row]
        
        if indexPath.row == 0 {
            cell.backgroundColor = #colorLiteral(red: 1.0, green: 1.0, blue: 1.0, alpha: 1.0)
            cell.textLabel?.textColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
        } else {
            cell.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
            cell.textLabel?.textColor = #colorLiteral(red: 1, green: 1, blue: 1, alpha: 1)
            
        }


        return cell
    }
    
    
    
    func fetchAllData() {
            
        let request : NSFetchRequest<Assessment> = Assessment.fetchRequest()
        //request.predicate = NSPredicate(format: "age = %@", "12")
        request.returnsObjectsAsFaults = false
        do {
            let result = try moc!.fetch(request)
            
            allAssessments = result
            
        } catch {
            print("Failed")
        }
      
    }
    
    
    //i don't think this should belong to the VC but a model somewhere
    func sortData() {
        
        for ass in allAssessments {
            
            var assArray: [String] = [ass.school!, ass.whoAssessed!.name! ]
            
           let students = ass.partOfAssessment!
            let studentArray = Array(students) as! Array<Student>
            
          var  index = 1
            
            for stud in studentArray{
                assArray.append("Student \(index)")
                assArray.append( String(stud.isEngaged))
                assArray.append( stud.position!)
                assArray.append(  stud.teachingStyle!)
                assArray.append(  stud.teachingType!)
                
                index += 1
            }
            
        
            assessmentDetails.append(assArray)
       
        }
        
        print(assessmentDetails)
    }
    
    
    

    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
