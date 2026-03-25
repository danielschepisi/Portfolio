//
//  DetailViewController.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 17/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit
import CoreData

class DetailViewController: UIViewController {
    
    var managedObjectContext: NSManagedObjectContext? = nil
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        managedObjectContext = (UIApplication.shared.delegate as? AppDelegate)?.persistentContainer.viewContext
        configureView()
    }
    
    var detailItem: Event? {
        didSet {
            // Update the view.
            configureView()
        }
    }
    
    func configureView() {
        
        title = ""
        navigationController?.navigationBar.barTintColor = #colorLiteral(red: 0.9538897872, green: 0.596511364, blue: 0.2432208359, alpha: 1)
        
        let logoutBarButton = UIBarButtonItem(title: "Logout", style: .plain, target: self, action: nil)
        
        navigationItem.rightBarButtonItem = logoutBarButton
        
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        //Pass MOC to pushed View (NewTecheerVC)
        if  let viewController = segue.destination as? NewTeacherViewController {
            viewController.managedObjectContext = managedObjectContext
        }
    }
    
    
    
}
