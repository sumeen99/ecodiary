//
//  ViewController.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/08/29.
//

import UIKit
import CoreData


class ViewController: UIViewController {
    @IBOutlet weak var yearAndMonthLabel: UILabel?
    @IBOutlet weak var missionLabel: UILabel!
    @IBOutlet weak var calendarCollectionView: UICollectionView!
    @IBOutlet weak var totalOfToday: UILabel!
    @IBOutlet weak var missionCheck: UIImageView!
    @IBOutlet weak var userInfoBtn: UIButton!
    
    let dateFormatter = DateFormatter()
    var dateOfToday:String?
    
    let now = Date()
    var calendar = Calendar.current
    var dateComponents = DateComponents()
    var weeks: [String] = ["월","화", "수", "목", "금", "토", "일"]
    var days:[String] = []
    var daysCountInMonth = 0
    var weekdayAdding = 0
    
    let appdelegate = UIApplication.shared.delegate as! AppDelegate
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    var userId: String?
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
                    print("userId", self.userId!)
                    self.checkMissionDate()
                }
                
            }catch{
                print(error)
            }
        }
    }
    
    func getUserId(){
        api.getUserId { id in
            if id != nil{
                self.userId = id!
                self.saveInitInfo(id: id!)
            }
        }
    }
    
    /**
     오늘 미션을 안 받아 왔으면 받아오기
     오늘 미션을 받았으면 coredata에서 미션 정보 가져오기
     */
    func getMissionCheck(){
        let fetchRequest: NSFetchRequest<DiaryList> = DiaryList.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "date = %@", self.dateOfToday!)
        do{
            let loadedData = try self.context.fetch(fetchRequest)
            if loadedData.count == 0{
                self.getMission()
            }else{
                missionModel = MissionModel(missionId: Int(loadedData.first!.missionId!)!, mission: loadedData.first!.mission!)
                self.missionLabel.text = loadedData.first?.mission
            }
            
        }catch{
            
        }
    }
    
    func getMission(){
        api.getMission(userId: userId!) { model in
            if model != nil {
                self.missionModel = model
                self.missionLabel.text = self.missionModel?.mission
                self.saveMission()
            }
        }
    }
    
    func saveMission(){
        let entityDescription = NSEntityDescription.entity(forEntityName: "DiaryList", in: context)
        
        guard let object = NSManagedObject(entity: entityDescription!, insertInto: context) as? DiaryList else{
            return
        }
        
        object.date = dateOfToday
        object.mission = missionLabel.text
        object.missionId = String(missionModel!.missionId)
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
        dateFormatter.dateFormat = "yyyyMMdd"
        dateOfToday = dateFormatter.string(from: now)
        
        api.checkDateOfToday(dateOfToday: dateOfToday!) { result in
            if result != nil {
                if result == "false"{
                    DispatchQueue.main.async {
                        let dateAlert = UIAlertController(title: "날짜 확인", message: "날짜가 올바르지 않습니다. 날짜 확인 후 앱을 다시 실행해 주세요.", preferredStyle: UIAlertController.Style.alert)
                        let dateAction = UIAlertAction(title: "종료", style: .destructive) { action in
                            exit(0)
                        }
                        dateAlert.addAction(dateAction)
                        self.present(dateAlert, animated: true, completion: nil)
                    }
                }
            }
        }

    }
    
    /**
     미션 확인한 날이 지난 날이면 오늘 미션 확인은 안 한 것이니 missionCheck = false로!
     미션 확인한 날이 오늘이고 미션을 확인 했다면 확인 버튼을 숨기기
     */
    func checkMissionDate(){
        let fetchRequest: NSFetchRequest<User> = User.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "id = %@", self.userId!)
        DispatchQueue.main.async {
            do{
                let loadedData = try self.context.fetch(fetchRequest)
                if ((loadedData.first!.date!)) < self.dateOfToday! {
                    if loadedData.first!.missionCheck == true{
                        self.api.updateMissionId(userId: self.userId!)
                    }
                    loadedData.first!.date = self.dateOfToday
                    loadedData.first!.missionCheck = false
                    self.appdelegate.saveContext()
                }else{
                    if loadedData.first?.missionCheck == true {
                        self.missionCheck.isHidden = true
                        self.getMissionCheck()
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
        fetchRequest.predicate = NSPredicate(format: "id = %@", self.userId!)
        
        do{
            let loadedData = try self.context.fetch(fetchRequest)
            loadedData.first?.missionCheck = true
            self.getMissionCheck()
        } catch{
            
        }
        appdelegate.saveContext()
    }
    
    func getTotalOfToday(){
        api.getTotalOfToday { total in
            if total != nil {
                DispatchQueue.main.async {
                    self.totalOfToday.text = "오늘의 환경 지킴이는 "+total!+"명 입니다."
                }
            }
        }
    }
    
    private func initView(){
        let tapRecognizer = UITapGestureRecognizer(target: self, action: #selector(checkMission))
        missionCheck.image = UIImage(systemName: "exclamationmark.bubble.fill")
        missionCheck.tintColor = .systemGreen
        missionCheck.isUserInteractionEnabled = true
        missionCheck.addGestureRecognizer(tapRecognizer)
        
        dateComponents.year = calendar.component(.year, from: now)
        dateComponents.month = calendar.component(.month, from: now)
        dateComponents.day = 1
        
        userInfoBtn.tintColor = .systemGreen
        
        getTotalOfToday()
        
        self.calculation()
    }
    
    private func calculation(){
        let firstDayMonth = calendar.date(from: dateComponents)
        let firstWeekday = calendar.component(.weekday, from: firstDayMonth!)
        daysCountInMonth = calendar.range(of: .day, in: .month, for: firstDayMonth!)!.count
        
        weekdayAdding = 2-firstWeekday
        dateFormatter.dateFormat = "yyyy년 MM월"
        yearAndMonthLabel?.text = dateFormatter.string(from: firstDayMonth!)
        
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
        dateComponents.month = dateComponents.month!-1
        calculation()
        calendarCollectionView.reloadData()
    }
    
    @IBAction func goNextMonth(_ sender: UIButton) {
        dateComponents.month = dateComponents.month!+1
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
        
        dateFormatter.dateFormat = "yyyyMM"
        let yearAndMonth = dateFormatter.string(from: calendar.date(from : dateComponents)!)
        
        cell.setDate(indexPath: indexPath, days: days, dateOfToday: dateOfToday!, yearAndMonth: yearAndMonth)
        
        return cell
    }
    
}

extension ViewController: UICollectionViewDelegate{
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let cell = collectionView.cellForItem(at: indexPath) as! DateCell
        dateFormatter.dateFormat = "yyyyMM"
        var dateOfCell = dateFormatter.string(from: calendar.date(from: dateComponents)!)
        
        if cell.dateLabel.text?.count==1{
            dateOfCell += "0"+cell.dateLabel.text!
        }else{
            dateOfCell += cell.dateLabel.text!
        }
        
        if cell.dateLabel.text=="" || dateOfCell > dateOfToday!  {
            return
        }else if dateOfCell == dateOfToday && !(isAlreadyWritten(date: dateOfToday!)){
            if missionCheck.isHidden == false{
                checkMission()
                return
            }
            let diaryEditVC = DiaryEditViewController.init(nibName: "DiaryEditViewController", bundle: nil)
            diaryEditVC.delegate = self
            present(diaryEditVC, animated: true, completion: nil)
            diaryEditVC.dateLabel?.text = yearAndMonthLabel!.text!+" "+cell.dateLabel.text!+"일"
            
            
        }else if dateOfCell <= dateOfToday! && isAlreadyWritten(date: dateOfCell){
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
        return dateOfToday!
    }
    
    func getMissionModel() -> MissionModel {
        return missionModel!
    }
    
    func getId() -> String {
        return userId!
    }
}

extension ViewController:UserInfoViewControllerDelegate{
    func setUserNumber() -> String {
        return userId!
    }
    
    
}
