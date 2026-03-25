//
//  WeatherManagerDelegate.swift
//  Clima
//
//  Created by Daniel Schepisi on 26/3/20.
//  Copyright © 2020 App Brewery. All rights reserved.
//

import Foundation

protocol WeatherManagerDelegate {
    func didUpdateWeather(weather: WeatherModel)
}
