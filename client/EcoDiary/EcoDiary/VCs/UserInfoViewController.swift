//
//  UserInfoViewController.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/09/26.
//

import UIKit
import CoreData

protocol UserInfoViewControllerDelegate: AnyObject{
    func setUserNumber() -> String
}

class UserInfoViewController: UIViewController {

    weak var delegate: UserInfoViewControllerDelegate?
    
    @IBOutlet weak var userNumber: UILabel!
    var userId: String?
    override func viewDidLoad() {
        super.viewDidLoad()
        
        userNumber.text = "사용자 번호 : "
        //userId = (delegate?.setUserNumber())!
        userNumber.text! += userId!
    }
    
    @IBAction func back(_ sender: Any) {
        self.dismiss(animated: true, completion: nil)
    }
    
    

}
