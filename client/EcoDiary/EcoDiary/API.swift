//
//  API.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/09/13.
//

import Foundation

class API {
    
    func getUserId(completion: @escaping (String?)->Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user")
        
        guard let url = components?.url else {
            return
        }
        var request = URLRequest(url:url)
        request.httpMethod = "POST"
        DispatchQueue.main.async {
            session.dataTask(with: request){ data, response, error in
                print("uesr :", (response as! HTTPURLResponse).statusCode)
                if let hasData = data {
                    completion(String(data: hasData, encoding:.utf8)!)
                    return
                }
                
            }.resume()
            session.finishTasksAndInvalidate()
        }
        completion(nil)
    }
    
    func getMission(userId: String, completion: @escaping (MissionModel?) -> Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/"+userId+"/missionId")
        
        guard let url = components?.url else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        session.dataTask(with: request){ data, response, error in
            print("새로운 미션",(response as! HTTPURLResponse).statusCode)
            if let hasData = data {
                DispatchQueue.main.async {
                    do{
                        completion(try JSONDecoder().decode(MissionModel.self, from: hasData))
                        return
                        
                    }catch{
                        print(error)
                    }
                }
            }
        }.resume()
        session.finishTasksAndInvalidate()
        completion(nil)
    }
    
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
    
    func finishMissionAPI(userId: String, completion: @escaping (Int?)-> Void){
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
    
    func checkDateOfToday(dateOfToday: String, completion: @escaping (String?)->Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/"+dateOfToday)
        
        guard let url = components?.url else {
            return
        }
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        DispatchQueue.main.async {
            session.dataTask(with: request){ data, response, error in
                print((response as! HTTPURLResponse).statusCode)
                if let hasData = data {
                    completion(String(data: hasData, encoding: .utf8))
                    return
                }
            }.resume()
            session.finishTasksAndInvalidate()
            completion(nil)
        }
        
    }
    
    func getTotalOfToday(completion: @escaping (String?)->Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/user/totalOfToday")
        
        guard let url = components?.url else {
            return
        }
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
            
        session.dataTask(with: request){ data, response, error in
            print("total",(response as! HTTPURLResponse).statusCode)
            if let hasData = data {
                completion(String(data: hasData, encoding: .utf8))
                return
            }
        }.resume()
        session.finishTasksAndInvalidate()
        completion(nil)
    }
    
    func getMissionInfo(userId: String, completion: @escaping (String?)->Void) {
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/"+userId+"/info")
        
        guard let url = components?.url else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        session.dataTask(with: request){ data, response, error in
            print("Info :",(response as! HTTPURLResponse).statusCode)
            if let hasData = data {
                completion(String(data: hasData, encoding: .utf8)!)
                return
            }
        }.resume()
        session.finishTasksAndInvalidate()
        completion(nil)
    }
    
    func getImgURLAPI(userId: String, completion: @escaping (URL?)->Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/"+userId+"/imgurl")
        guard let url = components?.url else {
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        session.dataTask(with: request){data, response, error in
            print("imgURL :", (response as! HTTPURLResponse).statusCode)
            if let hasData = data{
                completion(URL(string: String(data: hasData, encoding: .utf8)!))
                return
            }
        }.resume()
        session.finishTasksAndInvalidate()
        
        completion(nil)
    }
    
    func getQuestion(userId:String, completion: @escaping (String?)->Void){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        let components = URLComponents(string: "http://ec2-3-37-141-187.ap-northeast-2.compute.amazonaws.com:8080/api/v1/posts/"+userId+"/question")
        
        guard let url = components?.url else{
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        
        session.dataTask(with: request){data,response,error in
            print("Qusetion :",(response as! HTTPURLResponse).statusCode)
            if let hasData = data {
                completion(String(data:hasData, encoding: .utf8)!)
                return
            }
        }.resume()
        session.finishTasksAndInvalidate()
        
        completion(nil)
    }
    
}
