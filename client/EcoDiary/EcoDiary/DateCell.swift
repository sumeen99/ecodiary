//
//  DateCell.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/08/29.
//

import UIKit

class DateCell:UICollectionViewCell {
    
    @IBOutlet weak var dateLabel: UILabel!
    
    func setDate(indexPath: IndexPath, weeks: [String], days: [String]){
        
        /*switch indexPath.section {
        case 0:
            self.dateLabel.text = weeks[indexPath.row]
        default:
            self.dateLabel.text = days[indexPath.row]
        }*/
        
        self.dateLabel.text = days[indexPath.row]
    }
}
