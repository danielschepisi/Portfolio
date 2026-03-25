//
//  WeatherData.swift
//  Clima
//
//  Created by Daniel Schepisi on 25/3/20.
//  Copyright © 2020 App Brewery. All rights reserved.
//

import Foundation


struct WeatherData: Decodable {
    
    let name: String
    let main: Main
    let weather: Array<Weather>
    

//    
//    "coord":{"lon":144.28,
//            "lat":-36.77},
//    "weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}],
//    "base":"stations",
//    "main":{"temp":290.93,"feels_like":286.5,"temp_min":290.93,"temp_max":290.93,"pressure":1022,"humidity":45},
//    "wind":{"speed":4.92,"deg":141,"gust":8.49},
//    "clouds":{"all":59},
//    "dt":1585115667,
//    "sys":{"type":3,"id":2007315,"country":"AU","sunrise":1585081808,"sunset":1585124847},
//
//    "timezone":39600,
//    "id":2176187,
//    "name":"Bendigo",
//    "cod":200
//
    
    
    
}


struct Main: Decodable {
    let temp: Double
}

struct Weather: Decodable {
    let description: String
    let id: Int
}
