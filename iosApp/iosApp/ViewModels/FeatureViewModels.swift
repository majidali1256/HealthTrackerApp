import SwiftUI
import Combine

/// ViewModel for managing medications with UserDefaults persistence
class MedicationsViewModel: ObservableObject {
    
    @Published var medications: [Medication] = []
    @Published var isLoading: Bool = false
    
    private let storageKey = "medications_data"
    
    static let shared = MedicationsViewModel()
    
    private init() {
        loadMedications()
    }
    
    // MARK: - CRUD Operations
    
    func addMedication(_ medication: Medication) {
        medications.append(medication)
        saveMedications()
    }
    
    func updateMedication(_ medication: Medication) {
        if let index = medications.firstIndex(where: { $0.id == medication.id }) {
            medications[index] = medication
            saveMedications()
        }
    }
    
    func deleteMedication(_ medication: Medication) {
        medications.removeAll { $0.id == medication.id }
        saveMedications()
    }
    
    func markAsTaken(_ medication: Medication) {
        if let index = medications.firstIndex(where: { $0.id == medication.id }) {
            medications[index].lastTakenAt = Date()
            saveMedications()
        }
    }
    
    // MARK: - Computed Properties
    
    var activeMedications: [Medication] {
        medications.filter { $0.isActive }
    }
    
    var nextDoseText: String {
        guard let nextMed = activeMedications.filter({ !$0.isTakenToday }).sorted(by: { 
            ($0.reminderHour * 60 + $0.reminderMinute) < ($1.reminderHour * 60 + $1.reminderMinute) 
        }).first else {
            return "No upcoming doses"
        }
        return "\(nextMed.name) at \(nextMed.reminderTimeFormatted)"
    }
    
    var medicationsTakenToday: Int {
        activeMedications.filter { $0.isTakenToday }.count
    }
    
    var totalActiveMedications: Int {
        activeMedications.count
    }
    
    // MARK: - Persistence
    
    private func saveMedications() {
        if let encoded = try? JSONEncoder().encode(medications) {
            UserDefaults.standard.set(encoded, forKey: storageKey)
        }
    }
    
    private func loadMedications() {
        if let data = UserDefaults.standard.data(forKey: storageKey),
           let decoded = try? JSONDecoder().decode([Medication].self, from: data) {
            medications = decoded
        }
    }
}

/// ViewModel for managing food log with UserDefaults persistence
class FoodLogViewModel: ObservableObject {
    
    @Published var entries: [FoodEntry] = []
    @Published var isLoading: Bool = false
    
    private let storageKey = "food_entries_data"
    
    static let shared = FoodLogViewModel()
    
    private init() {
        loadEntries()
    }
    
    // MARK: - CRUD Operations
    
    func addEntry(_ entry: FoodEntry) {
        entries.insert(entry, at: 0) // Most recent first
        saveEntries()
    }
    
    func deleteEntry(_ entry: FoodEntry) {
        entries.removeAll { $0.id == entry.id }
        saveEntries()
    }
    
    // MARK: - Computed Properties
    
    var todayEntries: [FoodEntry] {
        entries.filter { Calendar.current.isDateInToday($0.timestamp) }
    }
    
    var todayCalories: Int {
        todayEntries.reduce(0) { $0 + $1.calories }
    }
    
    var caloriesByMeal: [MealType: Int] {
        var result: [MealType: Int] = [:]
        for meal in MealType.allCases {
            result[meal] = todayEntries.filter { $0.mealType == meal }.reduce(0) { $0 + $1.calories }
        }
        return result
    }
    
    var weeklyAverage: Int {
        let calendar = Calendar.current
        let weekAgo = calendar.date(byAdding: .day, value: -7, to: Date())!
        let weekEntries = entries.filter { $0.timestamp >= weekAgo }
        guard !weekEntries.isEmpty else { return 0 }
        
        // Group by day and calculate average
        var dailyTotals: [Date: Int] = [:]
        for entry in weekEntries {
            let day = calendar.startOfDay(for: entry.timestamp)
            dailyTotals[day, default: 0] += entry.calories
        }
        
        guard !dailyTotals.isEmpty else { return 0 }
        return dailyTotals.values.reduce(0, +) / dailyTotals.count
    }
    
    // MARK: - Persistence
    
    private func saveEntries() {
        if let encoded = try? JSONEncoder().encode(entries) {
            UserDefaults.standard.set(encoded, forKey: storageKey)
        }
    }
    
    private func loadEntries() {
        if let data = UserDefaults.standard.data(forKey: storageKey),
           let decoded = try? JSONDecoder().decode([FoodEntry].self, from: data) {
            entries = decoded
        }
    }
}

/// ViewModel for managing symptoms with UserDefaults persistence
class SymptomsViewModel: ObservableObject {
    
    @Published var symptoms: [Symptom] = []
    @Published var isLoading: Bool = false
    
    private let storageKey = "symptoms_data"
    
    static let shared = SymptomsViewModel()
    
    private init() {
        loadSymptoms()
    }
    
    // MARK: - CRUD Operations
    
    func addSymptom(_ symptom: Symptom) {
        symptoms.insert(symptom, at: 0)
        saveSymptoms()
    }
    
    func deleteSymptom(_ symptom: Symptom) {
        symptoms.removeAll { $0.id == symptom.id }
        saveSymptoms()
    }
    
    // MARK: - Computed Properties
    
    var recentSymptoms: [Symptom] {
        Array(symptoms.prefix(10))
    }
    
    var todaySymptoms: [Symptom] {
        symptoms.filter { Calendar.current.isDateInToday($0.timestamp) }
    }
    
    // MARK: - Common Symptoms for Quick Selection
    
    static let commonSymptoms = [
        "Headache", "Fatigue", "Nausea", "Dizziness", "Chest Pain",
        "Shortness of Breath", "Back Pain", "Joint Pain", "Fever",
        "Cough", "Sore Throat", "Congestion", "Stomach Ache", "Anxiety"
    ]
    
    // MARK: - Persistence
    
    private func saveSymptoms() {
        if let encoded = try? JSONEncoder().encode(symptoms) {
            UserDefaults.standard.set(encoded, forKey: storageKey)
        }
    }
    
    private func loadSymptoms() {
        if let data = UserDefaults.standard.data(forKey: storageKey),
           let decoded = try? JSONDecoder().decode([Symptom].self, from: data) {
            symptoms = decoded
        }
    }
}

/// ViewModel for Medical ID with UserDefaults persistence
class MedicalIDViewModel: ObservableObject {
    
    @Published var medicalID: MedicalID = MedicalID()
    
    private let storageKey = "medical_id_data"
    
    static let shared = MedicalIDViewModel()
    
    private init() {
        loadMedicalID()
    }
    
    func save() {
        if let encoded = try? JSONEncoder().encode(medicalID) {
            UserDefaults.standard.set(encoded, forKey: storageKey)
        }
    }
    
    private func loadMedicalID() {
        if let data = UserDefaults.standard.data(forKey: storageKey),
           let decoded = try? JSONDecoder().decode(MedicalID.self, from: data) {
            medicalID = decoded
        }
    }
    
    func addEmergencyContact(_ contact: EmergencyContact) {
        medicalID.emergencyContacts.append(contact)
        save()
    }
    
    func removeEmergencyContact(_ contact: EmergencyContact) {
        medicalID.emergencyContacts.removeAll { $0.id == contact.id }
        save()
    }
    
    func addAllergy(_ allergy: String) {
        guard !allergy.isEmpty else { return }
        medicalID.allergies.append(allergy)
        save()
    }
    
    func addCondition(_ condition: String) {
        guard !condition.isEmpty else { return }
        medicalID.conditions.append(condition)
        save()
    }
}

/// ViewModel for Documents with UserDefaults persistence
class DocumentsViewModel: ObservableObject {
    
    @Published var documents: [HealthDocument] = []
    
    private let storageKey = "health_documents_data"
    
    static let shared = DocumentsViewModel()
    
    private init() {
        loadDocuments()
    }
    
    func addDocument(_ document: HealthDocument) {
        documents.insert(document, at: 0)
        saveDocuments()
    }
    
    func deleteDocument(_ document: HealthDocument) {
        documents.removeAll { $0.id == document.id }
        saveDocuments()
    }
    
    var documentsByType: [DocumentType: [HealthDocument]] {
        Dictionary(grouping: documents) { $0.type }
    }
    
    private func saveDocuments() {
        if let encoded = try? JSONEncoder().encode(documents) {
            UserDefaults.standard.set(encoded, forKey: storageKey)
        }
    }
    
    private func loadDocuments() {
        if let data = UserDefaults.standard.data(forKey: storageKey),
           let decoded = try? JSONDecoder().decode([HealthDocument].self, from: data) {
            documents = decoded
        }
    }
}
