//
//  ViewController.swift
//  SedgwickSwamplands
//
//  Created by Daniel Schepisi on 20/9/2022.
//

import UIKit
import CoreData
import SwipeCellKit

class ViewController: UIViewController, UIPickerViewDelegate, UIPickerViewDataSource {
    
    
    
    
    
    
    @IBOutlet weak var viewForSegmentControl: UIView!
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var segmentedControl: UISegmentedControl!
    @IBOutlet weak var addButton: UIButton!
    
    
    
    
    //set value between 0 and 1
    let tableViewDisplayRatio = 7.0 / 8.0
    let dataNeeded = DataNeeded()
    
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    var paddockArray: [Paddock] = []
    var taskArray: [Task] = []
    var picker = UIPickerView()
    var pickerSet: [String] = []
    var pickerRowSelected = 0
    var sortTableByPaddock = true
    var selectedArray = [String()]
    
    
    
    //MARK: - View Lifecycles
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        dataNeeded.createPaddocks()
        loadPaddocks()
        pickerSet = dataNeeded.paddockNamesArray
        loadTasks()
        
        
        
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        
        displayTableView(withRatio: tableViewDisplayRatio)
        
        segmentedControl.frame.size.height = 60.0
        tableView.tableHeaderView = segmentedControl
        
    }
    
    
    
    
    //MARK: - UI Actions
    
    @IBAction func segmentedControlPressed(_ sender: UISegmentedControl) {
        
        let previousState = sortTableByPaddock
        
        //could sort this by ?title of the segmented control for clearer code. [Paddocks, Tasks] = [0,1]
        
        switch segmentedControl.selectedSegmentIndex {
        case 0:
            sortTableByPaddock = true
        case 1:
            sortTableByPaddock = false
        default:
            sortTableByPaddock = true
        }
        
        if previousState != sortTableByPaddock{
            tableView.reloadData()
        }
        
    }
    
    
    
    
    
    
    
    
    @IBAction func addButtonPressed(_ sender: Any) {
        var textField = UITextField()
        
        pickerRowSelected = 0
        
        //Create alert with add and cancel buttons
        let alert = UIAlertController(title: "Create a New Chore", message: "\n \n Select a paddock and enter a task for the new chore.\n \n \n \n \n \n \n \n \n \n \n \n \n \n \n", preferredStyle: .alert)
        
        let action = UIAlertAction(title: "Add Chore", style: .default) { (action) in
            print(textField.text!)
            
            
            
            
            if let safeText = textField.text {      //don't think I actually need to optional bind
                
                var doesTaskExist = false
                
                //check if task exists
                for task in self.taskArray {
                    
                    //Check if task already exists
                    if task.name == safeText {
                        doesTaskExist = true
                        //add new paddock to existing task
                        
                        print("whoop")
                        
                        //task.taskToPad?.addToPadToTasks(self.paddockArray[self.pickerRowSelected])
                        
                        //self.paddockArray[self.pickerRowSelected]
                    }
                }
                
                if !doesTaskExist{
                    //Create new task
                    let task = Task(context: self.context)
                    task.name = safeText
                    task.taskToPad = self.paddockArray[self.pickerRowSelected]
                }
                
                
                
                
                
                
                
                self.saveContext()
                self.loadTasks()
                self.tableView.reloadData()
            } else {
                //I probably want to do a check for tasks with no characters
                print("Need to fix issue with no text in textfield")
            }
            
            
            
        }
        alert.addAction(action)
        
        let cancelAction = UIAlertAction(title: "Cancel", style: .destructive, handler: nil)
        alert.addAction(cancelAction)
        
        
        //Add textfield to the alert
        alert.addTextField { alertTextField in
            alertTextField.placeholder = "Enter new task"
            textField = alertTextField
        }
        
        
        
        
        
        //Create a frame (placeholder/wrapper) for the picker and then create the picker
        let pickerFrame: CGRect = CGRectMake(35, 150, 200, 140) // CGRectMake(left, top, width, height) - left and top are like margins
        picker = UIPickerView(frame: pickerFrame)
        //            picker.backgroundColor = UIColor(red:0.29, green:0.45, blue:0.74, alpha:1.0)
        picker.backgroundColor = .white
        
        //set the pickers datasource and delegate
        picker.delegate = self
        picker.dataSource = self
        
        
        
        //Add the picker to the alert controller
        alert.view.addSubview(picker)
        
        
        
        
        
        
        
        present(alert, animated: true, completion: nil)
        
        
    }
    
    
    
    
    
    
    
    //MARK: - UIDisplay
    
    func displayTableView(withRatio ratio: Double){
        //Sets the porportion of the screen covered by tableView. And now places the button top right
        
        let safeAreaTop = view.safeAreaInsets.top
        let safeAreaHeight = view.safeAreaLayoutGuide.layoutFrame.height
        let safeAreaWidth = view.safeAreaLayoutGuide.layoutFrame.width
        let heightSegControl = safeAreaHeight * (1-ratio) - 20
        
        
        if ratio <= 1.0 && ratio >= 0.0 {
            //Issues with this for non-portrait orientations
            
            
            viewForSegmentControl.frame = CGRect(x: 0.0, y: safeAreaTop, width: safeAreaWidth, height: heightSegControl)
            
            tableView.frame = CGRect(x: 0.0 , y: safeAreaTop + ((1-ratio) * safeAreaHeight), width: safeAreaWidth, height: ratio * safeAreaHeight)
        } else {
            displayTableView(withRatio: 3.0 / 4.0)
        }
        
        
        addButton.frame = CGRect(x: safeAreaWidth - heightSegControl, y: safeAreaTop, width: heightSegControl, height: heightSegControl)
        
    }
    
    
    
    
    //MARK: - Picker
    
    //let's try to outsouce all of this later
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return pickerSet.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? { return pickerSet[row] }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        pickerRowSelected = row
    }
    
    
    //MARK: - CoreData
    
    func saveContext() {
        (UIApplication.shared.delegate as! AppDelegate).saveContext()
    }
    
    func loadPaddocks(){
        
        let request: NSFetchRequest<Paddock> = Paddock.fetchRequest()
        
        let sortDescriptor = NSSortDescriptor(key: "viewOrder", ascending: true)
        
        request.sortDescriptors = [sortDescriptor]
        do {
            print("loading from context")
            paddockArray = try context.fetch(request)
        } catch {
            print("Error fetching paddock data from context \(error)")
        }
        
        
    }
    
    
    func loadTasks(){
        
        let request: NSFetchRequest<Task> = Task.fetchRequest()
        
        do {
            taskArray = try context.fetch(request)
        } catch {
            print("Error fetch task data from context \(error)")
        }
        
    }
    
    
    
    
    
    
}

//MARK: - TableView Data Source

extension ViewController: UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //        print("why does this print/getcalled so many times?")
        
        if sortTableByPaddock {
            
            if let numOfTasks = paddockArray[section].padToTasks?.count{
                return numOfTasks
            }else{
                return 0
            }
        } else {
            
            if taskArray[section].taskToPad != nil{
                return 1
            }else{
                return 0
            }
        }
        
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40.0
    }
    
    func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        
        if sortTableByPaddock{
            return paddockArray[section].name
        }else{
            return taskArray[section].name
        }
    }
    
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let myLabel = UILabel()
        myLabel.frame = CGRect(x: 20, y: 8, width: 320, height: 30)
        myLabel.font = UIFont.boldSystemFont(ofSize: 28)
        myLabel.text = self.tableView(tableView, titleForHeaderInSection: section)
        
        let headerView = UIView()
        headerView.addSubview(myLabel)
        
        return headerView
    }
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        if sortTableByPaddock{
            return paddockArray.count
        } else {
            return taskArray.count
        }
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! SwipeTableViewCell
        cell.delegate = self
        var content = cell.defaultContentConfiguration()
        
        if sortTableByPaddock{
            
            //could I use indexPath.row here? and section maybe?
            if let currentPaddockTasks = paddockArray[indexPath[0]].padToTasks {
                
                let taskArray = Array(currentPaddockTasks) as! [Task]
                content.text = taskArray[indexPath[1]].name
                
            }
        } else {
            if let currentTaskPaddock = taskArray[indexPath[0]].taskToPad {
                
                content.text = currentTaskPaddock.name
                
            }
            
        }
        
        cell.contentConfiguration = content
        return cell
    }
}

//MARK: - TableView Delegate
extension ViewController: UITableViewDelegate{
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableView.deselectRow(at: indexPath, animated: true)
        
    }
    
    
}

//MARK: - SwipeCell Delegate
extension ViewController: SwipeTableViewCellDelegate {
    
    func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath, for orientation: SwipeCellKit.SwipeActionsOrientation) -> [SwipeCellKit.SwipeAction]? {
        guard orientation == .right else { return nil }
        
        if self.sortTableByPaddock {
            
            let deleteAction = SwipeAction(style: .destructive, title: "Delete") { action, indexPath in
                // handle action by updating model with deletion
                
                
                
                let cellContent = self.tableView.cellForRow(at: indexPath)?.contentConfiguration as! UIListContentConfiguration
                //We now know which task and Paddock was selected
                let selectedTaskName = cellContent.text!
                let selectedPaddock = self.paddockArray[indexPath.section]
                
                let arrayOfTasks = selectedPaddock.padToTasks!.allObjects as! [Task]
                for task in arrayOfTasks {
                    
                    if task.name! == selectedTaskName {
                        print(task.name!)
                        self.context.delete(task)
                    }
                }
                
                var index = 0
                print(self.taskArray)
                for task in self.taskArray {
                    
                    
                    if task.name! == selectedTaskName {
                        print(self.taskArray[index])
                        self.taskArray.remove(at: index)
                    } else {
                        index += 1
                    }
                }
                
                self.saveContext()
                self.tableView.reloadData()
            }
            
            // customize the action appearance
            deleteAction.image = UIImage(named: "delete")
            
            return [deleteAction]
        }else{
            return nil
        }
    }
    
    
    
    
}



//MARK: - random code for later




//
//    private func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {}
//

//
//    func pickerView(pickerView: UIPickerView, attributedTitleForRow row: Int, forComponent component: Int) -> NSAttributedString? {
//        let titleData = pickerSet[row]
//        let myTitle = NSAttributedString(string: titleData, attributes: [NSFontAttributeName:UIFont(name: "Verdana", size: 15.0)!,NSForegroundColorAttributeName:UIColor.whiteColor()])
//        return myTitle
//    }
//
