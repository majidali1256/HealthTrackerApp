import Foundation

// MARK: - Medication Model
struct Medication: Identifiable, Codable {
    let id: UUID
    var name: String
    var dosage: String
    var frequency: MedicationFrequency
    var reminderHour: Int
    var reminderMinute: Int
    var isActive: Bool
    var createdAt: Date
    var lastTakenAt: Date?
    
    init(
        id: UUID = UUID(),
        name: String,
        dosage: String,
        frequency: MedicationFrequency = .onceDaily,
        reminderHour: Int = 9,
        reminderMinute: Int = 0,
        isActive: Bool = true,
        createdAt: Date = Date(),
        lastTakenAt: Date? = nil
    ) {
        self.id = id
        self.name = name
        self.dosage = dosage
        self.frequency = frequency
        self.reminderHour = reminderHour
        self.reminderMinute = reminderMinute
        self.isActive = isActive
        self.createdAt = createdAt
        self.lastTakenAt = lastTakenAt
    }
    
    var reminderTimeFormatted: String {
        let hour12 = reminderHour > 12 ? reminderHour - 12 : (reminderHour == 0 ? 12 : reminderHour)
        let amPm = reminderHour >= 12 ? "PM" : "AM"
        return String(format: "%d:%02d %@", hour12, reminderMinute, amPm)
    }
    
    var isTakenToday: Bool {
        guard let lastTaken = lastTakenAt else { return false }
        return Calendar.current.isDateInToday(lastTaken)
    }
}

enum MedicationFrequency: String, Codable, CaseIterable {
    case onceDaily = "Once daily"
    case twiceDaily = "Twice daily"
    case threeTimesDaily = "Three times daily"
    case asNeeded = "As needed"
}

// MARK: - Food Entry Model
struct FoodEntry: Identifiable, Codable {
    let id: UUID
    var name: String
    var calories: Int
    var mealType: MealType
    var servingSize: String
    var timestamp: Date
    
    init(
        id: UUID = UUID(),
        name: String,
        calories: Int,
        mealType: MealType = .snack,
        servingSize: String = "1 serving",
        timestamp: Date = Date()
    ) {
        self.id = id
        self.name = name
        self.calories = calories
        self.mealType = mealType
        self.servingSize = servingSize
        self.timestamp = timestamp
    }
    
    var timeFormatted: String {
        let formatter = DateFormatter()
        formatter.timeStyle = .short
        return formatter.string(from: timestamp)
    }
}

enum MealType: String, Codable, CaseIterable {
    case breakfast = "Breakfast"
    case lunch = "Lunch"
    case dinner = "Dinner"
    case snack = "Snack"
    
    var icon: String {
        switch self {
        case .breakfast: return "🌅"
        case .lunch: return "☀️"
        case .dinner: return "🌙"
        case .snack: return "🍎"
        }
    }
}

// MARK: - Symptom Model
struct Symptom: Identifiable, Codable {
    let id: UUID
    var name: String
    var severity: Int // 1-10
    var notes: String
    var timestamp: Date
    
    init(
        id: UUID = UUID(),
        name: String,
        severity: Int = 5,
        notes: String = "",
        timestamp: Date = Date()
    ) {
        self.id = id
        self.name = name
        self.severity = min(10, max(1, severity))
        self.notes = notes
        self.timestamp = timestamp
    }
    
    var severityText: String {
        switch severity {
        case 1...3: return "Mild"
        case 4...6: return "Moderate"
        case 7...9: return "Severe"
        case 10: return "Critical"
        default: return "Unknown"
        }
    }
    
    var severityColor: String {
        switch severity {
        case 1...3: return "green"
        case 4...6: return "orange"
        case 7...9: return "red"
        case 10: return "purple"
        default: return "gray"
        }
    }
}

// MARK: - Medical ID Model
struct MedicalID: Codable {
    var bloodType: String
    var allergies: [String]
    var conditions: [String]
    var emergencyContacts: [EmergencyContact]
    var organDonor: Bool
    var notes: String
    
    init(
        bloodType: String = "",
        allergies: [String] = [],
        conditions: [String] = [],
        emergencyContacts: [EmergencyContact] = [],
        organDonor: Bool = false,
        notes: String = ""
    ) {
        self.bloodType = bloodType
        self.allergies = allergies
        self.conditions = conditions
        self.emergencyContacts = emergencyContacts
        self.organDonor = organDonor
        self.notes = notes
    }
    
    static let bloodTypes = ["A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"]
}

struct EmergencyContact: Identifiable, Codable {
    let id: UUID
    var name: String
    var phone: String
    var relationship: String
    
    init(id: UUID = UUID(), name: String, phone: String, relationship: String) {
        self.id = id
        self.name = name
        self.phone = phone
        self.relationship = relationship
    }
}

// MARK: - Document Model
struct HealthDocument: Identifiable, Codable {
    let id: UUID
    var title: String
    var type: DocumentType
    var notes: String
    var createdAt: Date
    
    init(
        id: UUID = UUID(),
        title: String,
        type: DocumentType = .other,
        notes: String = "",
        createdAt: Date = Date()
    ) {
        self.id = id
        self.title = title
        self.type = type
        self.notes = notes
        self.createdAt = createdAt
    }
}

enum DocumentType: String, Codable, CaseIterable {
    case prescription = "Prescription"
    case labResult = "Lab Result"
    case vaccination = "Vaccination"
    case insurance = "Insurance"
    case other = "Other"
    
    var icon: String {
        switch self {
        case .prescription: return "💊"
        case .labResult: return "🧪"
        case .vaccination: return "💉"
        case .insurance: return "🏥"
        case .other: return "📄"
        }
    }
}
