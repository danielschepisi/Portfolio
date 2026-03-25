//
//  MasterViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 17/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit
import CoreData

class MasterViewController: UITableViewController, NSFetchedResultsControllerDelegate {
    
    var detailViewController: DetailViewController? = nil
    var managedObjectContext: NSManagedObjectContext? = nil
    let tableModel = MainTableModel()
    var teachers =  [Teacher]()

    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        configureView()
        setDetailViewController()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    
    func configureView() {
        tableView.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
        title = "Main"
        
        guard let navBar = navigationController?.navigationBar
            else { fatalError("Navigation Controller doesn't exist dude.") }
        navBar.barTintColor = #colorLiteral(red: 0.3277348876, green: 0.7131237388, blue: 0.2882333994, alpha: 1)
        navBar.isTranslucent = true
    }
    func setDetailViewController() {
        //dubious about this. Ok if spilt screen is always on, which it is
        if let split = splitViewController {
                   let controllers = split.viewControllers
                   detailViewController = (controllers[controllers.count-1] as! UINavigationController).topViewController as? DetailViewController
               }
    }
    
    

    

    
    // MARK: - Table View
    
    override func numberOfSections(in tableView: UITableView) -> Int {
        return tableModel.tableData.count
    }
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
       return  tableModel.numberOfRowsForSection(section)
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath)
        cell.textLabel?.text = tableModel.tableData[indexPath.section][indexPath.row]
        
        if cell.textLabel?.text == "" {
            cell.backgroundColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
            cell.isUserInteractionEnabled = false
        } else {
//            cell.backgroundColor = #colorLiteral(red: 1, green: 1, blue: 1, alpha: 1)
//            cell.textLabel?.textColor = #colorLiteral(red: 0, green: 0, blue: 0, alpha: 1)
        }
        
    
        return cell
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
       
            if tableModel.tableData[indexPath.section][indexPath.row] == "Create New" {
            performSegue(withIdentifier: "newMaster", sender: self)
        }
         tableView.deselectRow(at: indexPath, animated: true)
    }
    
    func configureCell(_ cell: UITableViewCell, withEvent event: Event) {
        cell.textLabel!.text = event.timestamp!.description
    }
    
    // MARK: - Segues
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showDetail" {
            if let indexPath = tableView.indexPathForSelectedRow {
                let object = fetchedResultsController.object(at: indexPath)
                let controller = (segue.destination as! UINavigationController).topViewController as! DetailViewController
                controller.detailItem = object
                controller.navigationItem.leftBarButtonItem = splitViewController?.displayModeButtonItem
                controller.navigationItem.leftItemsSupplementBackButton = true
                detailViewController = controller
            }
        }
        
        if segue.identifier == "newMaster" {
            
            if let nextController =   segue.destination as? CreateTeacher {
                
                nextController.managedObjectContext = managedObjectContext
                
//                if let moc = managedObjectContext {
//
//
//                          print("got this far")
//                      let teacher = NSEntityDescription.entity(forEntityName: "Teacher", in: moc)
                
//                let teach = Teacher(context: managedObjectContext)
//                             print("got this far2")
//                          let newTeacher = NSManagedObject(entity: teacher!, insertInto: moc)
//
//                             print("got this far3")
//                          newTeacher.setValue("Dr Teach", forKey: "name")
//
//                          do {
//                                 print("got this far4")
//                             try moc.save()
//                            } catch {
//                             print("Failed saving")
//                                 print("got this far5")
//                          }
//
//                      }
                
//                let request = NSFetchRequest<NSFetchRequestResult>(entityName: "Teacher")
                      //request.predicate = NSPredicate(format: "age = %@", "12")
                
                let request : NSFetchRequest<Teacher> = Teacher.fetchRequest()
                
                      request.returnsObjectsAsFaults = false
                      do {
                         
                          if let moc = managedObjectContext {
                          let result = try moc.fetch(request)
                            
                            nextController.teachers = result
                          }
                            
                      } catch {
                           
                          print("Failed")
                      }
                
            }
        }
    }
    
 //MARK: - Core Data
    
    @objc
    func insertNewObject(_ sender: Any) {
        let context = self.fetchedResultsController.managedObjectContext
        let newEvent = Event(context: context)
        
        // If appropriate, configure the new managed object.
        newEvent.timestamp = Date()
        
        // Save the context.
        do {
            try context.save()
        } catch {
            let nserror = error as NSError
            fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
        }
    }
    
    // MARK: - Fetched results controller
    
    var fetchedResultsController: NSFetchedResultsController<Event> {
        if _fetchedResultsController != nil {
            return _fetchedResultsController!
        }
        
        let fetchRequest: NSFetchRequest<Event> = Event.fetchRequest()
        
        // Set the batch size to a suitable number.
        fetchRequest.fetchBatchSize = 20
        
        // Edit the sort key as appropriate.
        let sortDescriptor = NSSortDescriptor(key: "timestamp", ascending: false)
        
        fetchRequest.sortDescriptors = [sortDescriptor]
        
        // Edit the section name key path and cache name if appropriate.
        // nil for section name key path means "no sections".
        let aFetchedResultsController = NSFetchedResultsController(fetchRequest: fetchRequest, managedObjectContext: self.managedObjectContext!, sectionNameKeyPath: nil, cacheName: "Master")
        aFetchedResultsController.delegate = self
        _fetchedResultsController = aFetchedResultsController
        
        do {
            try _fetchedResultsController!.performFetch()
        } catch {
            // Replace this implementation with code to handle the error appropriately.
            // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            let nserror = error as NSError
            fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
        }
        
        return _fetchedResultsController!
    }
    var _fetchedResultsController: NSFetchedResultsController<Event>? = nil
    
    func controllerWillChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.beginUpdates()
    }
    
    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange sectionInfo: NSFetchedResultsSectionInfo, atSectionIndex sectionIndex: Int, for type: NSFetchedResultsChangeType) {
        switch type {
        case .insert:
            tableView.insertSections(IndexSet(integer: sectionIndex), with: .fade)
        case .delete:
            tableView.deleteSections(IndexSet(integer: sectionIndex), with: .fade)
        default:
            return
        }
    }
    
    func controller(_ controller: NSFetchedResultsController<NSFetchRequestResult>, didChange anObject: Any, at indexPath: IndexPath?, for type: NSFetchedResultsChangeType, newIndexPath: IndexPath?) {
        switch type {
        case .insert:
            tableView.insertRows(at: [newIndexPath!], with: .fade)
        case .delete:
            tableView.deleteRows(at: [indexPath!], with: .fade)
        case .update:
            configureCell(tableView.cellForRow(at: indexPath!)!, withEvent: anObject as! Event)
        case .move:
            configureCell(tableView.cellForRow(at: indexPath!)!, withEvent: anObject as! Event)
            tableView.moveRow(at: indexPath!, to: newIndexPath!)
        default:
            return
        }
    }
    
    func controllerDidChangeContent(_ controller: NSFetchedResultsController<NSFetchRequestResult>) {
        tableView.endUpdates()
    }
    
    /*
     // Implementing the above methods to update the table view in response to individual changes may have performance implications if a large number of changes are made simultaneously. If this proves to be an issue, you can instead just implement controllerDidChangeContent: which notifies the delegate that all section and object changes have been processed.
     
     func controllerDidChangeContent(controller: NSFetchedResultsController) {
     // In the simplest, most efficient, case, reload the table view.
     tableView.reloadData()
     }
     */
    
}

