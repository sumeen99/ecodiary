//
//  DiaryViewController.swift
//  EcoDiary
//
//  Created by 심주미 on 2021/09/02.
//

import UIKit
import CoreData

protocol EditDelegate: AnyObject {
    func getMissionModel() -> MissionModel
    func getDateOfToday() -> String
    func getId()-> String
    func didFinishSaveDiary()
}


class DiaryEditViewController: UIViewController, UITextViewDelegate{
    weak var delegate: EditDelegate?
    var missionModel: MissionModel?
    var userId: String?
    
    var starNumber: Int = 5
    var currentStar = 0
    var buttons: [UIButton] = []
    
    var api:API = API()
    var imgURL : URL?
    
    lazy var stackView: UIStackView = {
        let view = UIStackView()
        view.axis = .horizontal
        view.spacing = 12
        view.backgroundColor = .white
        
        return view
    }()
    
    var dateOfToday: String!
    
    @IBOutlet weak var dateLabel: UILabel?
    @IBOutlet weak var missionLabel: UILabel!
    @IBOutlet weak var questionLabel: UILabel!
    @IBOutlet weak var questionAnsTF: UITextField!
    
    @IBOutlet weak var todayDiary: UITextView!
    
    let appdelegate = UIApplication.shared.delegate as! AppDelegate
    
    let session = URLSession(configuration: URLSessionConfiguration.default)
    
    var missionInfo:String=""
    override func viewDidLoad() {
        super.viewDidLoad()
        view.addSubview(stackView)
        makeRate()
        
        todayDiary.layer.borderWidth = 1.0
        todayDiary.layer.borderColor = UIColor.black.cgColor
        todayDiary.text = ""
        todayDiary.delegate = self
        
        userId = delegate?.getId()
        dateOfToday = delegate?.getDateOfToday()
        missionModel = delegate?.getMissionModel()
        missionLabel.text = missionModel?.mission
        
        getQuestionAPI()
        getImgURLAPI()
        getMissionInfo()
    }

    
    func makeRate(){
        stackView.translatesAutoresizingMaskIntoConstraints = false
        stackView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 20).isActive = true
        
        stackView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: UIScreen.main.bounds.height*0.8).isActive = true
    
        for i in 0..<5 {
            let button = UIButton()
            button.setImage(UIImage(systemName: "star"), for: .normal)
            button.tintColor = .systemGreen
            button.tag = i
            buttons += [button]
            stackView.addArrangedSubview(button)
            button.addTarget(self, action: #selector(didTapStarButton(sender:)), for: .touchUpInside)
        }
    }
    
    @objc func didTapStarButton(sender: UIButton) {
        let end = sender.tag
        for i in 0...end {
            buttons[i].setImage(UIImage(systemName: "star.fill"), for: .normal)
            buttons[i].tintColor = .systemGreen
        }
        for i in end+1..<starNumber {
            buttons[i].setImage(UIImage(systemName: "star"), for: .normal)
            buttons[i].tintColor = .systemGreen
        }
        currentStar = end + 1
    }
    
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }

    @IBAction func goBack(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
    func getQuestionAPI(){
        api.getQuestion(userId: userId!) { question in
            if question != nil {
                DispatchQueue.main.async {
                    self.questionLabel.text = question
                }
            }
        }
    }
    
    func getImgURLAPI(){
        api.getImgURLAPI(userId: userId!) { url in
            if url != nil {
                DispatchQueue.main.async {
                    self.imgURL = url
                }
            }
        }
    }
    
    func getMissionInfo() {
        api.getMissionInfo(userId: userId!) { info in
            if info != nil {
                self.missionInfo = info!
            }
        }
    }
    
    @IBAction func saveDiary(_ sender: Any) {
        let context = appdelegate.persistentContainer.viewContext
        
        let fetchRequest: NSFetchRequest<DiaryList> = DiaryList.fetchRequest()
        fetchRequest.predicate = NSPredicate(format: "date = %@", dateOfToday)
        
        do{
            let loadedData = try context.fetch(fetchRequest)
            loadedData.first?.question = self.questionLabel.text
            loadedData.first?.questionAns = self.questionAnsTF.text
            loadedData.first?.diary = self.todayDiary.text
            loadedData.first?.grade = Int64(self.currentStar)
            loadedData.first?.missionInfo = self.missionInfo
            loadedData.first?.imgURL = self.imgURL

        } catch {
            
        }
        api.finishMissionAPI(userId: userId!){ statusCode in
            if statusCode == 200 {
                DispatchQueue.main.async {
                    self.appdelegate.saveContext()
                    self.delegate?.didFinishSaveDiary()
                }
            }
        }
        self.dismiss(animated: true, completion: nil)
    }
   
}



