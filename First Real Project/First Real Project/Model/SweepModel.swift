//
//  SweepModel.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 25/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import UIKit
import CoreData

class SweepModel {
    
    var sweepNumber: Int
    
    var students: [Student] = []
    var newTeacher: Teacher? = nil
    var newAssessment: Assessment? = nil
    
    
    var position = ""
    var isEngaged = false
    var teachingStyle = ""
    var teachingType = ""
    
    var moc: NSManagedObjectContext
    
    
    init(sweepNumber: Int) {
        self.sweepNumber = sweepNumber
        self.moc = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    }
    
    
    func  setStudentDetails (position: String) {
        self.position = position
    }
    
    func  setStudentDetails (isEngaged: Bool) {
        self.isEngaged = isEngaged
    }
    
    func  setStudentDetails (teachingStyle: String) {
        self.teachingStyle = teachingStyle
    }
    
    func  setStudentDetails (teachingType: String) {
        self.teachingType = teachingType
    }
    
    
    
    func placeStudentInArray() {
        let student = Student(context: moc)
        
        student.position = position
        student.isEngaged = isEngaged
        student.teachingStyle = teachingStyle
        student.teachingType = teachingType
        
        students.append(student)
        
    }
    
    
    func setTeacher(position: String){
        newTeacher!.location = position
        
    }
    
    func  setStudentDetails(cognitiveLevel: String) {
        
        
    }
    
    func  setStudentDetails(workCompleted: String) {
    }
    
    func isFinishedSweepingAllStudents(withCognitiveLevel: String, andWorkCompletedLevel: String) -> Bool {
        
        students[sweepNumber - 1].cognitiveLevel = withCognitiveLevel
        students[sweepNumber - 1].workCompleted = andWorkCompletedLevel
        
        sweepNumber += 1
        
        
        if sweepNumber > students.count {
            for student in students {
                newAssessment?.addToPartOfAssessment(student)
                
            }
            return true
        } else {
            
            
            return false
        }
    }
    
    
    func saveData() {
        
        do{
            try moc.save()
        } catch {
            print(error)
        }
        
    }
    
}
