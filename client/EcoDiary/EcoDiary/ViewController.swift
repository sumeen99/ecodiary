//
//  ViewController.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/08/29.
//

import UIKit
import CoreData



class ViewController: UIViewController {
    
    let dateFormatter = DateFormatter()
    var dateOfToday = ""
    
    let now = Date()
    var cal = Calendar.current
    let yearAndMonthFormatter = DateFormatter()
    var components = DateComponents()
    var weeks: [String] = ["월","화", "수", "목", "금", "토", "일"]
    var days:[String] = []
    var daysCountInMonth = 0
    var weekdayAdding = 0

    @IBOutlet weak var yearAndMonthLabel: UILabel?
    @IBOutlet weak var missionLabel: UILabel!
    @IBOutlet weak var calendarCollectionView: UICollectionView!
    @IBOutlet weak var totalOfToday: UILabel!
    @IBOutlet weak var missionCheck: UIImageView!
    @IBOutlet weak var userInfoBtn: UIButton!
    
    let appdelegate = UIApplication.shared.delegate as! AppDelegate
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    var userId: String = ""
    var missionId: String = ""
    var missionModel: MissionModel?

    var api:API = API()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let layout = UICollectionViewFlowLayout()
        layout.itemSize = CGSize(width: UIScreen.main.bounds.width/7, height: 80)
        layout.minimumInteritemSpacing = 0
        layout.minimumLineSpacing = 0
        
        calendarCollectionView.collectionViewLayout = layout
        
        calendarCollectionView.dataSource = self
        calendarCollectionView.delegate = self
        
        initDateOfToday()
        initView()
        
        
        setUserId()
        
    }
    
    @IBAction func goUserInfo(_ sender: Any) {
        let userInfoVC = UserInfoViewController.init(nibName: "UserInfoViewController", bundle: nil)
        userInfoVC.userId = userId
        present(userInfoVC, animated: true, completion: nil)
    }
    
    func setUserId(){
        let fetchRequest: NSFetchRequest<User> = User.fetchRequest()
        DispatchQueue.main.async {
            do{
                let user = try self.context.fetch(fetchRequest)
                if user.count==0{
                    self.getUserId()
                }else{
                    self.userId = user.first!.id!
                    print("userId", self.userId)
                    self.checkMissionDate()
                    self.getMissionCheck()
                }
                
            }catch{
                print(error)
            }
        }
    }
    
    func getUserId(){
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
                    self.userId = String(data: hasData, encoding: .utf8)!
                    print("userid", self.userId)
                    self.saveInitInfo(id: String(data: hasData, encoding: .utf8)!)
                    self.getMissionCheck()
                }
                
            }.resume()
            session.finishTasksAndInvalidate()
        }
    }
    
    func getMissionCheck(){
        let fetchRequest: NSFetchRequest<DiaryList> = DiaryList.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "date = %@", self.dateOfToday)
        do{
            let loadedData = try self.context.fetch(fetchRequest)
            if loadedData.count == 0{
                self.getMission()
            }else{
                self.missionLabel.text = loadedData.first?.mission
                self.missionId = (loadedData.first?.missionId)!
            }
            
        }catch{
            
        }
    }
    
    
    func getMission(){
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
                        self.missionModel = try JSONDecoder().decode(MissionModel.self, from: hasData)
                        self.missionLabel.text = self.missionModel?.mission
                        self.missionId = String((self.missionModel?.missionId)!)
                        self.saveMission()
                        
                    }catch{
                        print(error)
                    }
                }
            }
        }.resume()
        session.finishTasksAndInvalidate()
    }
    
    func saveMission(){
        let entityDescription = NSEntityDescription.entity(forEntityName: "DiaryList", in: context)
        
        guard let object = NSManagedObject(entity: entityDescription!, insertInto: context) as? DiaryList else{
            return
        }
        
        object.date = dateOfToday
        object.mission = missionLabel.text
        object.missionId = missionId
        appdelegate.saveContext()
    }
    
    func saveInitInfo(id:String){
        let entityDescription = NSEntityDescription.entity(forEntityName: "User", in: context)
        
        guard let object = NSManagedObject(entity: entityDescription!, insertInto: context) as? User else{
            return
        }
        
        object.id = id
        object.date = self.dateOfToday
        object.missionCheck = false
        self.appdelegate.saveContext()
        
    }
    
    func initDateOfToday(){
        let session = URLSession(configuration: URLSessionConfiguration.default)
        
        dateFormatter.dateFormat = "yyyyMMdd"
        dateOfToday = dateFormatter.string(from: now)
        
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
                    DispatchQueue.main.async {
                        if(String(data: hasData, encoding: .utf8)!=="false"){
                            let dateAlert = UIAlertController(title: "날짜 확인", message: "날짜가 올바르지 않습니다. 날짜 확인 후 앱을 다시 실행해 주세요.", preferredStyle: UIAlertController.Style.alert)
                            let dateAction = UIAlertAction(title: "종료", style: .destructive) { action in
                                exit(0)
                            }
                            dateAlert.addAction(dateAction)
                            self.present(dateAlert, animated: true, completion: nil)
                        }
                    }
                }
            }.resume()
            session.finishTasksAndInvalidate()
        }
        
    }
    
    func checkMissionDate(){
        let fetchRequest: NSFetchRequest<User> = User.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id = %@", self.userId)
        DispatchQueue.main.async {
            do{
                let loadedData = try self.context.fetch(fetchRequest)
                if ((loadedData.first!.date!)) < self.dateOfToday {
                    loadedData.first!.date = self.dateOfToday
                    loadedData.first?.missionCheck = false
                }else{
                    if loadedData.first?.missionCheck == true {
                        self.missionCheck.isHidden = true
                    }
                }
                
            }catch{
                
            }
        }
        appdelegate.saveContext()
    }

    @objc func checkMission(){
        missionCheck.isHidden = true
        let fetchRequest: NSFetchRequest<User> = User.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id = %@", self.userId)
        do{
            let loadedData = try self.context.fetch(fetchRequest)
            loadedData.first?.missionCheck = true
            api.updateMissionId(userId: self.userId)
        } catch{
            
        }
        appdelegate.saveContext()
    }
    
    func getTotalOfToday(){
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
                DispatchQueue.main.async {
                    print("!!!")
                    self.totalOfToday.text = "오늘의 환경 지킴이는 "
                    self.totalOfToday.text! += String(data: hasData, encoding: .utf8)!
                    self.totalOfToday.text! += "명 입니다."
                }
            }
        }.resume()
        session.finishTasksAndInvalidate()
    }
    
    private func initView(){
        let tapRecognizer = UITapGestureRecognizer(target: self, action: #selector(checkMission))
        missionCheck.image = UIImage(systemName: "exclamationmark.bubble.fill")
        missionCheck.tintColor = .systemGreen
        missionCheck.isUserInteractionEnabled = true
        missionCheck.addGestureRecognizer(tapRecognizer)
        
        yearAndMonthFormatter.dateFormat = "yyyy년 MM월"
        components.year = cal.component(.year, from: now)
        components.month = cal.component(.month, from: now)
        components.day = 1
        
        userInfoBtn.tintColor = .systemGreen
        
        getTotalOfToday()
        
        self.calculation()
    }
    
    private func calculation(){
        let firstDayMonth = cal.date(from: components)
        let firstWeekday = cal.component(.weekday, from: firstDayMonth!)
        daysCountInMonth = cal.range(of: .day, in: .month, for: firstDayMonth!)!.count
        
        weekdayAdding = 2-firstWeekday
        yearAndMonthLabel?.text = yearAndMonthFormatter.string(from: firstDayMonth!)
        
        self.days.removeAll()
        for day in weekdayAdding...daysCountInMonth{
            if day < 1{
                self.days.append("")
            }else{
                self.days.append(String(day))
            }
        }
    }
    
    @IBAction func goPrevMonth(_ sender: UIButton) {
        components.month = components.month!-1
        calculation()
        calendarCollectionView.reloadData()
    }
    
    @IBAction func goNextMonth(_ sender: UIButton) {
        components.month = components.month!+1
        calculation()
        calendarCollectionView.reloadData()
    }
    
    func isAlreadyWritten(date: String)->Bool {
        let fetchRequest: NSFetchRequest<DiaryList> = DiaryList.fetchRequest()
        fetchRequest.predicate = NSPredicate(format:"date = %@", date)
        
        do{
            let loadedData = try context.fetch(fetchRequest)
            if loadedData.first == nil{
                return false
            }else{
                if loadedData.first?.diary == nil{
                    return false
                }else{
                    return true
                }
            }
        }catch{
            
        }
        return false
    }
    
}

extension ViewController: UICollectionViewDataSource{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.days.count

    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "DateCell", for: indexPath) as! DateCell
        
        var yearAndMonth = String(components.year!)
        if String(components.month!).count==1{
            yearAndMonth += "0"+String(components.month!)
        }else{
            yearAndMonth += String(components.month!)
        }
        
        cell.setDate(indexPath: indexPath, days: days, dateOfToday: dateOfToday, yearAndMonth: yearAndMonth)
        
        return cell
    }
    
}

extension ViewController: UICollectionViewDelegate{
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let cell = collectionView.cellForItem(at: indexPath) as! DateCell
        var dateOfCell = String(components.year!)
        
        if String(components.month!).count==1{
            dateOfCell += "0"+String(components.month!)
        }else{
            dateOfCell += String(components.month!)
        }
        
        if cell.dateLabel.text?.count==1{
            dateOfCell += "0"+cell.dateLabel.text!
        }else{
            dateOfCell += cell.dateLabel.text!
        }
        
        if cell.dateLabel.text=="" || dateOfCell > dateOfToday  {
            return
        }else if dateOfCell == dateOfToday && !(isAlreadyWritten(date: dateOfToday)){
            if missionCheck.isHidden == false{
                checkMission()
            }
            let diaryEditVC = DiaryEditViewController.init(nibName: "DiaryEditViewController", bundle: nil)
            diaryEditVC.delegate = self
            present(diaryEditVC, animated: true, completion: nil)
            diaryEditVC.dateLabel?.text = yearAndMonthLabel!.text!+" "+cell.dateLabel.text!+"일"
            
            
        }else if dateOfCell <= dateOfToday && isAlreadyWritten(date: dateOfCell){
            let diaryVC = DiaryViewController.init(nibName: "DiaryViewController", bundle: nil)
            present(diaryVC, animated: true, completion: nil)
            diaryVC.date = dateOfCell
            diaryVC.setDiaryInfo()
        }
    }

}


extension ViewController: EditDelegate{
    func didFinishSaveDiary() {
        getTotalOfToday()
        calculation()
        calendarCollectionView.reloadData()
    }
    
    func getDateOfToday() -> String {
        return dateOfToday
    }
    
    func getMissionModel() -> MissionModel {
        if missionModel == nil{
            missionModel = MissionModel(missionId: Int(missionId)!, mission: missionLabel.text!)
        }
        return missionModel!
    }
    
    func getId() -> String {
        return userId
    }
}

extension ViewController:UserInfoViewControllerDelegate{
    func setUserNumber() -> String {
        return userId
    }
    
    
}
