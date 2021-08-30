//
//  ViewController.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/08/29.
//

import UIKit

class ViewController: UIViewController {
    
    let now = Date()
    var cal = Calendar.current
    let dateFormatter = DateFormatter()
    var components = DateComponents()
    var weeks: [String] = ["월","화", "수", "목", "금", "토", "일"]
    var days:[String] = []
    var daysCountInMonth = 0
    var weekdayAdding = 0

    @IBOutlet weak var yearAndMonthLabel: UILabel!
    @IBOutlet weak var calendarCollectionView: UICollectionView!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
        
        let layout = UICollectionViewFlowLayout()
        layout.itemSize = CGSize(width: UIScreen.main.bounds.width/7, height: 80)
        layout.minimumInteritemSpacing = 0
        layout.minimumLineSpacing = 0
        
        calendarCollectionView.collectionViewLayout = layout
        
        calendarCollectionView.dataSource = self
    
        self.initView()
    }
    
    private func initView(){
        dateFormatter.dateFormat = "yyyy년MM월"
        components.year = cal.component(.year, from: now)
        components.month = cal.component(.month, from: now)
        components.day = 1
        
        self.calculation()
    }
    
    private func calculation(){
        let firstDayMonth = cal.date(from: components)
        let firstWeekday = cal.component(.weekday, from: firstDayMonth!)
        daysCountInMonth = cal.range(of: .day, in: .month, for: firstDayMonth!)!.count
        
        weekdayAdding = 2-firstWeekday
        yearAndMonthLabel.text = dateFormatter.string(from: firstDayMonth!)
        
        
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
    
}
extension ViewController: UICollectionViewDataSource{
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.days.count

    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "DateCell", for: indexPath) as! DateCell
        
        cell.setDate(indexPath: indexPath, weeks: weeks, days: days)
        
        return cell
    }
    
    
}
