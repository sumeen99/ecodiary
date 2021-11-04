//
//  DateCell.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/08/29.
//

import UIKit
import CoreData

class DateCell:UICollectionViewCell {
    
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var stampImg: UIImageView!
    
    func setDate(indexPath: IndexPath, days: [String], dateOfToday: String, yearAndMonth: String){
        self.dateLabel.text = days[indexPath.row]
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyyMMdd"
        
        let date:String!
        if days[indexPath.row].count == 1{
            date = yearAndMonth+"0"+days[indexPath.row]
        }else{
            date = yearAndMonth+days[indexPath.row]
        }
        
        if dateFormatter.string(from: Date()) == date {
            dateLabel.textColor = .systemGreen
            print(dateFormatter.string(from: Date()))
        }else{
            dateLabel.textColor = .black
        }
        
        findDiary(date: date)
    }
    
    func findDiary(date: String){
        let context = (UIApplication.shared.delegate as! AppDelegate).persistentContainer.viewContext
        
        let fetchRequest: NSFetchRequest<DiaryList> = DiaryList.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "date = %@", date)
        do {
            let loadedData = try context.fetch(fetchRequest)
            if loadedData.count != 0{
                if loadedData.first?.imgURL != nil {
                    let imgData = try Data(contentsOf: (loadedData.first?.imgURL)!)
                    self.stampImg.image = UIImage(data: imgData)
                }else{
                    self.stampImg.image = nil
                }
            }else{
                self.stampImg.image = nil
            }
        }catch{
            
        }
    }
    
}



