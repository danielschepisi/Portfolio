//
//  WeatherManager.swift
//  Clima
//
//  Created by Daniel Schepisi on 25/3/20.
//  Copyright © 2020 App Brewery. All rights reserved.
//

import Foundation
import CoreLocation


protocol WeatherManagerDelegate {
    func didUpdateWeather(_ weatherManager: WeatherManager, weather: WeatherModel)
    func didFailWithError(_ error:Error)
}


struct WeatherManager {
    
    var delegate: WeatherManagerDelegate?
    
    let weatherUrl = "https://api.openweathermap.org/data/2.5/weather?appid=6ee8c354e23a5d05724efe4c797041a8&units=metric"
    
    func fetchWeather( lattitude lat: CLLocationDegrees, longitude lon: CLLocationDegrees){
        
        let urlString = "\(weatherUrl)&lat=\(lat)&lon=\(lon)"
        performRequest(with: urlString)
    }
    
    
    
    func fetchWeather(cityName: String){
        let urlString = "\(weatherUrl)&q=\(cityName)"
        
        print(urlString)
        
        performRequest(with: urlString)
    }
    
    func performRequest(with urlString: String){
        if let url = URL(string: urlString){
            let session = URLSession(configuration: .default)
            let task = session.dataTask(with: url) { (data, response, error) in
                if error != nil {
                    print("hello")
                    self.delegate?.didFailWithError(error!)
                    return
                }
                
                if let safeData = data {
                    if let weather =  self.parseJSON(safeData) {
                        self.delegate?.didUpdateWeather(self, weather: weather)
                    }
                }
            }
            task.resume()
        }
    }
    
    
    func parseJSON(_ weatherData: Data) -> WeatherModel?{
        
        let decoder = JSONDecoder()
        
        do {
            let decodedData = try decoder.decode(WeatherData.self, from: weatherData)
            let conditionId = decodedData.weather[0].id
            let cityName = decodedData.name
            let temp = decodedData.main.temp
            
            
            let weather = WeatherModel(conditionId: conditionId, cityName: cityName, temperature: temp)
            
            print(weather.temperatureString)
            return weather
            
        } catch {
            print("fourscore")
            delegate?.didFailWithError(error)
            return nil
        }
    }

    
}

protocol  WeatherManagerProtocol {
    func didUpdateWeather(weather: WeatherModel)
}

