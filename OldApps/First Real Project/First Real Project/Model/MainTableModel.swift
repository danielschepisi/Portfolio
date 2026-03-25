//
//  MainTableModel.swift
//  First Real Project
//
//  Created by Daniel Schepisi on 23/4/20.
//  Copyright © 2020 Daniel Schepisi. All rights reserved.
//

import Foundation

struct MainTableModel {
    
    let tableData = [[""],
                      ["Create New", "Edit Recent", "View Previous"],
                      [""],
                      ["Export Data", "Manage Teachers"]]
  
    func numberOfRowsForSection(_ section: Int) -> Int {
        return tableData[section].count
    }
}
