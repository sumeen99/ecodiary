//
//  DiaryViewController.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/09/13.
//

import UIKit
import CoreData



class DiaryViewController: UIViewController {
    var date:String!
    
    @IBOutlet weak var gradeLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var missionLabel: UILabel!
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var questionAnsLabel: UILabel!
    @IBOutlet weak var diaryLabel: UILabel!
    @IBOutlet weak var infoLabel: UILabel!
    
    let appdelegate = UIApplication.shared.delegate as! AppDelegate
    let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setDiaryInfo()
    }
    @IBAction func goBack(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func setDiaryInfo(){
        guard date != nil else {
            return
        }
        
        let fetchRequest: NSFetchRequest<DiaryList> = DiaryList.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "date = %@", date)
        do{
            let loadedData = try context.fetch(fetchRequest)
            missionLabel.text = loadedData.first?.mission
            questionLabel.text = loadedData.first?.question
            questionAnsLabel.text = loadedData.first?.questionAns
            diaryLabel.text = loadedData.first?.diary
            infoLabel.text = loadedData.first?.missionInfo
            setGrade(grade: Int(loadedData.first!.grade))
            
            let endYear = date.index(date.startIndex, offsetBy: 4)
            let endMonth = date.index(date.startIndex, offsetBy: 6)
            dateLabel.text = date[...endYear]+"년 "+date[endYear..<endMonth]+"월 "+date[endMonth...]+"일"
        }catch{
            
        }
        
    }
    
    func setGrade(grade:Int){
        var starGrade: String = ""
        for _ in 1...grade{
            starGrade += " ★ "
        }
        for _ in 1...5-grade{
            starGrade += " ☆ "
        }
        gradeLabel.text = starGrade
    }

}
