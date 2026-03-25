//
//  DataNeeded.swift
//  SedgwickSwamplands
//
//  Created by Daniel Schepisi on 21/9/2022.
//

import UIKit
import CoreData

struct DataNeeded {
    
    let paddockNamesArray = ["House", "Out Back", "Front Paddocks ", "Shed Side", "Dam Paddock", "Faraway Paddocks", "Oh No Zone"]
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    func createPaddocks()  {
        
        var paddocks: [Paddock] = []
        
        let request: NSFetchRequest<Paddock> = Paddock.fetchRequest()
        
        do {
          let  paddockArray = try context.fetch(request)
            
            print("fetching paddocks")
            
            if paddockArray.count == 0 {
                
                
                print(paddockArray.count)
                print("blah blah blah")
                
                var positionNumber = 0.0
                for name in paddockNamesArray {
                    
                    let newPaddock = Paddock(context: context)
                    newPaddock.name = name
                    newPaddock.viewOrder = positionNumber
                    paddocks.append(newPaddock)
                    
                    positionNumber += 1
                }
                
            }else{
                print("alrady have paddocks")
            }
            
            
        } catch {
            print("Error fetching data from context \(error)")
        }
        
        
      
        
        if context.hasChanges {
            
            print("context has changed")
            do {
                try context.save()
            } catch {
                // Replace this implementation with code to handle the error appropriately.
                // fatalError() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
        
    }
    
    
    
    
  
}
