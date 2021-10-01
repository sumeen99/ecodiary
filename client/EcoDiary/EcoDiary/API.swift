//
//  API.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/09/13.
//

import Foundation

class API {
    
    func updateMissionId(userId: String){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/"+userId+"/missionId")
        guard let url = components?.url else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"

        session.dataTask(with: request){ data, response, error in
            print("update :",(response as! HTTPURLResponse).statusCode)
                
        }.resume()
        session.finishTasksAndInvalidate()
    }
    
    func requestMission() -> String {
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/1/mission")
        
        guard let url = components?.url else {
            return ""
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"

        
        var mission = ""
        session.dataTask(with: request){ data, response, error in
            print((response as! HTTPURLResponse).statusCode)
            if let hasData = data {
                    mission = String(data: hasData, encoding: .utf8)!
                    print(mission)
                
            }
                
        }.resume()
        session.finishTasksAndInvalidate()
        
        return mission
    }
    
    func finishMission(userId: String, completion: @escaping (Int?)-> Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/"+userId+"/missionCheck")
        
        guard let url = components?.url else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "PUT"
        
        
        session.dataTask(with: request){ data, response, error in
            print("finish :",(response as! HTTPURLResponse).statusCode)
            completion((response as! HTTPURLResponse).statusCode)
            return
        }.resume()
        
        session.finishTasksAndInvalidate()
        completion(nil)
    }
}
