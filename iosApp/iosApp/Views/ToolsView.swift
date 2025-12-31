import SwiftUI

struct ToolsView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    @EnvironmentObject var settingsManager: SettingsManager
    @StateObject private var medicationsVM = MedicationsViewModel.shared
    @StateObject private var foodLogVM = FoodLogViewModel.shared
    @StateObject private var symptomsVM = SymptomsViewModel.shared
    @StateObject private var documentsVM = DocumentsViewModel.shared
    
    @State private var showingEmergencySOS = false
    @State private var showingMedications = false
    @State private var showingFoodLog = false
    @State private var showingSymptoms = false
    @State private var showingDocuments = false
    @State private var showingExportSheet = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 0) {
                        headerSection
                        emergencySOSCard.padding(.top, 24)
                        quickActionsSection.padding(.top, 24)
                        healthToolsSection.padding(.top, 28)
                        exportSection.padding(.top, 28).padding(.bottom, 100)
                    }
                }
            }
        }
        .preferredColorScheme(settingsManager.colorScheme)
        .alert("Emergency SOS", isPresented: $showingEmergencySOS) {
            Button("Cancel", role: .cancel) {}
            Button("Call Emergency", role: .destructive) { callEmergency() }
        } message: {
            Text("This will share your location with emergency contacts and may call emergency services. Continue?")
        }
        .sheet(isPresented: $showingMedications) { MedicationsFullSheet() }
        .sheet(isPresented: $showingFoodLog) { FoodLogFullSheet() }
        .sheet(isPresented: $showingSymptoms) { SymptomsFullSheet() }
        .sheet(isPresented: $showingDocuments) { DocumentsFullSheet() }
        .sheet(isPresented: $showingExportSheet) { ExportSheet() }
    }
    
    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Tools")
                .font(.largeTitle)
                .fontWeight(.bold)
                .foregroundColor(AppTheme.textPrimary)
            Text("Health management tools")
                .font(.subheadline)
                .foregroundColor(AppTheme.textSecondary)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.horizontal, 20)
        .padding(.top, 60)
    }
    
    private var emergencySOSCard: some View {
        Button(action: { showingEmergencySOS = true }) {
            HStack(spacing: 16) {
                ZStack {
                    Circle()
                        .fill(.white.opacity(0.2))
                        .frame(width: 56, height: 56)
                    Image(systemName: "exclamationmark.triangle.fill")
                        .font(.title2)
                        .foregroundColor(.white)
                }
                VStack(alignment: .leading, spacing: 4) {
                    Text("Emergency SOS")
                        .font(.headline)
                        .fontWeight(.bold)
                        .foregroundColor(.white)
                    Text("Tap to share GPS with emergency contacts")
                        .font(.caption)
                        .foregroundColor(.white.opacity(0.8))
                }
                Spacer()
                Image(systemName: "chevron.right")
                    .foregroundColor(.white.opacity(0.6))
            }
            .padding(20)
            .background(LinearGradient(colors: [Color(hex: "B71C1C"), Color(hex: "E53935")], startPoint: .leading, endPoint: .trailing))
            .clipShape(RoundedRectangle(cornerRadius: 20))
            .shadow(color: AppTheme.accentRed.opacity(0.4), radius: 15, y: 8)
        }
        .padding(.horizontal, 20)
    }
    
    private func callEmergency() {
        print("Emergency SOS activated!")
    }
    
    private var quickActionsSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Quick Actions")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary)
                .padding(.horizontal, 20)
            
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    QuickActionButton(icon: "heart.fill", title: "Log Vitals", color: AppTheme.accentRed) {}
                    QuickActionButton(icon: "drop.fill", title: "Add Water", color: AppTheme.accentBlue) {
                        healthViewModel.addWater()
                    }
                    QuickActionButton(icon: "moon.fill", title: "Log Sleep", color: AppTheme.accentPurple) {}
                    QuickActionButton(icon: "fork.knife", title: "Log Food", color: AppTheme.accentGreen) {
                        showingFoodLog = true
                    }
                }
                .padding(.horizontal, 20)
            }
        }
    }
    
    private var healthToolsSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Health Tools")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary)
                .padding(.horizontal, 20)
            
            LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                ToolCardDark(icon: "doc.text.fill", title: "Documents", subtitle: "\(documentsVM.documents.count) saved", gradient: AppTheme.cardPurpleGradient) {
                    showingDocuments = true
                }
                ToolCardDark(icon: "stethoscope", title: "Symptoms", subtitle: "\(symptomsVM.todaySymptoms.count) today", gradient: AppTheme.cardTealGradient) {
                    showingSymptoms = true
                }
                ToolCardDark(icon: "fork.knife", title: "Food Log", subtitle: "\(foodLogVM.todayCalories) kcal", gradient: AppTheme.cardGreenGradient) {
                    showingFoodLog = true
                }
                ToolCardDark(icon: "pills.fill", title: "Medications", subtitle: "\(medicationsVM.medicationsTakenToday)/\(medicationsVM.totalActiveMedications) taken", gradient: AppTheme.cardRedGradient) {
                    showingMedications = true
                }
            }
            .padding(.horizontal, 20)
        }
    }
    
    private var exportSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("Export & Share")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary)
                .padding(.horizontal, 20)
            
            VStack(spacing: 12) {
                ExportRowCard(icon: "doc.richtext", title: "Generate PDF Report", subtitle: "Weekly health summary", color: AppTheme.accentPurple) {
                    showingExportSheet = true
                }
                ExportRowCard(icon: "square.and.arrow.up", title: "Share Health Data", subtitle: "Export to files or email", color: AppTheme.accentBlue) {
                    shareHealthData()
                }
            }
            .padding(.horizontal, 20)
        }
    }
    
    private func shareHealthData() {
        let summary = """
        Health Tracker Summary
        
        Steps Today: \(healthViewModel.todaySteps.formatted())
        Sleep: \(String(format: "%.1f", healthViewModel.todaySleepHours)) hours
        Water: \(healthViewModel.todayWaterGlasses) glasses
        Heart Rate: \(healthViewModel.lastHeartRate) BPM
        Health Score: \(healthViewModel.healthScore)/100
        """
        
        let activityVC = UIActivityViewController(activityItems: [summary], applicationActivities: nil)
        if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
           let window = windowScene.windows.first,
           let rootVC = window.rootViewController {
            rootVC.present(activityVC, animated: true)
        }
    }
}

// MARK: - Medications Full Sheet
struct MedicationsFullSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = MedicationsViewModel.shared
    @State private var showingAddSheet = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                if viewModel.medications.isEmpty {
                    VStack(spacing: 16) {
                        Image(systemName: "pills.fill")
                            .font(.system(size: 50))
                            .foregroundColor(AppTheme.textMuted)
                        Text("No medications")
                            .font(.headline)
                            .foregroundColor(AppTheme.textSecondary)
                        Text("Tap + to add your first medication")
                            .font(.subheadline)
                            .foregroundColor(AppTheme.textMuted)
                    }
                } else {
                    ScrollView {
                        LazyVStack(spacing: 12) {
                            ForEach(viewModel.medications) { med in
                                MedicationRow(medication: med, onTaken: {
                                    viewModel.markAsTaken(med)
                                })
                            }
                        }
                        .padding()
                    }
                }
            }
            .navigationTitle("Medications")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button("Done") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
                ToolbarItem(placement: .topBarTrailing) {
                    Button(action: { showingAddSheet = true }) {
                        Image(systemName: "plus")
                    }.foregroundColor(AppTheme.accentTeal)
                }
            }
            .sheet(isPresented: $showingAddSheet) { AddMedicationSheet() }
        }
        .preferredColorScheme(.dark)
    }
}

struct MedicationRow: View {
    let medication: Medication
    let onTaken: () -> Void
    
    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(medication.isTakenToday ? AppTheme.accentGreen.opacity(0.2) : AppTheme.accentPurple.opacity(0.2))
                .frame(width: 44, height: 44)
                .overlay(
                    Image(systemName: medication.isTakenToday ? "checkmark" : "pills.fill")
                        .foregroundColor(medication.isTakenToday ? AppTheme.accentGreen : AppTheme.accentPurple)
                )
            
            VStack(alignment: .leading, spacing: 2) {
                Text(medication.name)
                    .font(.subheadline)
                    .fontWeight(.medium)
                    .foregroundColor(AppTheme.textPrimary)
                Text("\(medication.dosage) - \(medication.reminderTimeFormatted)")
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            
            Spacer()
            
            if !medication.isTakenToday {
                Button(action: onTaken) {
                    Text("Take")
                        .font(.caption)
                        .fontWeight(.semibold)
                        .foregroundColor(.white)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 8)
                        .background(AppTheme.accentTeal)
                        .clipShape(Capsule())
                }
            }
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
}

struct AddMedicationSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = MedicationsViewModel.shared
    
    @State private var name = ""
    @State private var dosage = ""
    @State private var frequency: MedicationFrequency = .onceDaily
    @State private var reminderTime = Date()
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 20) {
                    TextField("Medication name", text: $name)
                        .textFieldStyle(.roundedBorder)
                    
                    TextField("Dosage (e.g., 10mg)", text: $dosage)
                        .textFieldStyle(.roundedBorder)
                    
                    Picker("Frequency", selection: $frequency) {
                        ForEach(MedicationFrequency.allCases, id: \.self) { freq in
                            Text(freq.rawValue).tag(freq)
                        }
                    }
                    .pickerStyle(.segmented)
                    
                    DatePicker("Reminder Time", selection: $reminderTime, displayedComponents: .hourAndMinute)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    Button(action: addMedication) {
                        Text("Add Medication")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(name.isEmpty ? Color.gray : AppTheme.accentTeal)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .disabled(name.isEmpty)
                    
                    Spacer()
                }
                .padding(20)
            }
            .navigationTitle("Add Medication")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func addMedication() {
        let calendar = Calendar.current
        let medication = Medication(
            name: name,
            dosage: dosage,
            frequency: frequency,
            reminderHour: calendar.component(.hour, from: reminderTime),
            reminderMinute: calendar.component(.minute, from: reminderTime)
        )
        viewModel.addMedication(medication)
        dismiss()
    }
}

// MARK: - Food Log Full Sheet
struct FoodLogFullSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = FoodLogViewModel.shared
    @State private var showingAddSheet = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 0) {
                    // Today's summary
                    HStack {
                        VStack(alignment: .leading) {
                            Text("Today")
                                .font(.headline)
                                .foregroundColor(AppTheme.textPrimary)
                            Text("\(viewModel.todayCalories) calories")
                                .font(.title)
                                .fontWeight(.bold)
                                .foregroundColor(AppTheme.accentGreen)
                        }
                        Spacer()
                        VStack(alignment: .trailing) {
                            Text("Week avg")
                                .font(.caption)
                                .foregroundColor(AppTheme.textMuted)
                            Text("\(viewModel.weeklyAverage)")
                                .font(.headline)
                                .foregroundColor(AppTheme.textSecondary)
                        }
                    }
                    .padding()
                    .background(AppTheme.darkNavyCard)
                    
                    if viewModel.todayEntries.isEmpty {
                        Spacer()
                        VStack(spacing: 16) {
                            Image(systemName: "fork.knife")
                                .font(.system(size: 50))
                                .foregroundColor(AppTheme.textMuted)
                            Text("No food logged today")
                                .foregroundColor(AppTheme.textSecondary)
                        }
                        Spacer()
                    } else {
                        ScrollView {
                            LazyVStack(spacing: 8) {
                                ForEach(viewModel.todayEntries) { entry in
                                    FoodEntryRow(entry: entry)
                                }
                            }
                            .padding()
                        }
                    }
                }
            }
            .navigationTitle("Food Log")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button("Done") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
                ToolbarItem(placement: .topBarTrailing) {
                    Button(action: { showingAddSheet = true }) {
                        Image(systemName: "plus")
                    }.foregroundColor(AppTheme.accentTeal)
                }
            }
            .sheet(isPresented: $showingAddSheet) { AddFoodEntrySheet() }
        }
        .preferredColorScheme(.dark)
    }
}

struct FoodEntryRow: View {
    let entry: FoodEntry
    
    var body: some View {
        HStack(spacing: 12) {
            Text(entry.mealType.icon)
                .font(.title2)
            
            VStack(alignment: .leading, spacing: 2) {
                Text(entry.name)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textPrimary)
                Text("\(entry.mealType.rawValue) • \(entry.timeFormatted)")
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            
            Spacer()
            
            Text("\(entry.calories)")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary) + Text(" kcal")
                .font(.caption)
                .foregroundColor(AppTheme.textMuted)
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
}

struct AddFoodEntrySheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = FoodLogViewModel.shared
    
    @State private var name = ""
    @State private var calories = ""
    @State private var mealType: MealType = .snack
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 20) {
                    TextField("Food name", text: $name)
                        .textFieldStyle(.roundedBorder)
                    
                    TextField("Calories", text: $calories)
                        .textFieldStyle(.roundedBorder)
                        .keyboardType(.numberPad)
                    
                    Picker("Meal", selection: $mealType) {
                        ForEach(MealType.allCases, id: \.self) { meal in
                            Text("\(meal.icon) \(meal.rawValue)").tag(meal)
                        }
                    }
                    .pickerStyle(.segmented)
                    
                    Button(action: addEntry) {
                        Text("Add Food")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(name.isEmpty ? Color.gray : AppTheme.accentGreen)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .disabled(name.isEmpty)
                    
                    Spacer()
                }
                .padding(20)
            }
            .navigationTitle("Add Food")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func addEntry() {
        let entry = FoodEntry(
            name: name,
            calories: Int(calories) ?? 0,
            mealType: mealType
        )
        viewModel.addEntry(entry)
        dismiss()
    }
}

// MARK: - Symptoms Full Sheet
struct SymptomsFullSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = SymptomsViewModel.shared
    @State private var showingAddSheet = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                if viewModel.symptoms.isEmpty {
                    VStack(spacing: 16) {
                        Image(systemName: "stethoscope")
                            .font(.system(size: 50))
                            .foregroundColor(AppTheme.textMuted)
                        Text("No symptoms logged")
                            .foregroundColor(AppTheme.textSecondary)
                    }
                } else {
                    ScrollView {
                        LazyVStack(spacing: 8) {
                            ForEach(viewModel.symptoms) { symptom in
                                SymptomRow(symptom: symptom)
                            }
                        }
                        .padding()
                    }
                }
            }
            .navigationTitle("Symptoms")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button("Done") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
                ToolbarItem(placement: .topBarTrailing) {
                    Button(action: { showingAddSheet = true }) {
                        Image(systemName: "plus")
                    }.foregroundColor(AppTheme.accentTeal)
                }
            }
            .sheet(isPresented: $showingAddSheet) { AddSymptomSheet() }
        }
        .preferredColorScheme(.dark)
    }
}

struct SymptomRow: View {
    let symptom: Symptom
    
    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(severityColor.opacity(0.2))
                .frame(width: 44, height: 44)
                .overlay(
                    Text("\(symptom.severity)")
                        .font(.headline)
                        .foregroundColor(severityColor)
                )
            
            VStack(alignment: .leading, spacing: 2) {
                Text(symptom.name)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textPrimary)
                Text("\(symptom.severityText) • \(formattedDate)")
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            
            Spacer()
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
    
    private var severityColor: Color {
        switch symptom.severity {
        case 1...3: return AppTheme.accentGreen
        case 4...6: return AppTheme.accentOrange
        default: return AppTheme.accentRed
        }
    }
    
    private var formattedDate: String {
        let formatter = DateFormatter()
        formatter.dateStyle = .short
        formatter.timeStyle = .short
        return formatter.string(from: symptom.timestamp)
    }
}

struct AddSymptomSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = SymptomsViewModel.shared
    
    @State private var selectedSymptom = ""
    @State private var severity: Double = 5
    @State private var notes = ""
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                ScrollView {
                    VStack(spacing: 20) {
                        Text("Select Symptom")
                            .font(.headline)
                            .foregroundColor(AppTheme.textPrimary)
                            .frame(maxWidth: .infinity, alignment: .leading)
                        
                        LazyVGrid(columns: [GridItem(.adaptive(minimum: 100))], spacing: 8) {
                            ForEach(SymptomsViewModel.commonSymptoms, id: \.self) { symptom in
                                Button(action: { selectedSymptom = symptom }) {
                                    Text(symptom)
                                        .font(.caption)
                                        .foregroundColor(selectedSymptom == symptom ? .white : AppTheme.textPrimary)
                                        .padding(.horizontal, 12)
                                        .padding(.vertical, 8)
                                        .background(selectedSymptom == symptom ? AppTheme.accentTeal : AppTheme.darkNavyCard)
                                        .clipShape(Capsule())
                                }
                            }
                        }
                        
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Severity: \(Int(severity))")
                                .font(.headline)
                                .foregroundColor(AppTheme.textPrimary)
                            Slider(value: $severity, in: 1...10, step: 1)
                                .tint(severityColor)
                        }
                        
                        TextField("Additional notes", text: $notes)
                            .textFieldStyle(.roundedBorder)
                        
                        Button(action: addSymptom) {
                            Text("Log Symptom")
                                .font(.headline)
                                .foregroundColor(.white)
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(selectedSymptom.isEmpty ? Color.gray : AppTheme.accentTeal)
                                .clipShape(RoundedRectangle(cornerRadius: 12))
                        }
                        .disabled(selectedSymptom.isEmpty)
                    }
                    .padding(20)
                }
            }
            .navigationTitle("Log Symptom")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private var severityColor: Color {
        switch Int(severity) {
        case 1...3: return AppTheme.accentGreen
        case 4...6: return AppTheme.accentOrange
        default: return AppTheme.accentRed
        }
    }
    
    private func addSymptom() {
        let symptom = Symptom(name: selectedSymptom, severity: Int(severity), notes: notes)
        viewModel.addSymptom(symptom)
        dismiss()
    }
}

// MARK: - Documents Full Sheet
struct DocumentsFullSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = DocumentsViewModel.shared
    @State private var showingAddSheet = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                if viewModel.documents.isEmpty {
                    VStack(spacing: 16) {
                        Image(systemName: "doc.text.fill")
                            .font(.system(size: 50))
                            .foregroundColor(AppTheme.textMuted)
                        Text("No documents")
                            .foregroundColor(AppTheme.textSecondary)
                    }
                } else {
                    ScrollView {
                        LazyVStack(spacing: 8) {
                            ForEach(viewModel.documents) { doc in
                                DocumentRow(document: doc)
                            }
                        }
                        .padding()
                    }
                }
            }
            .navigationTitle("Documents")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button("Done") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
                ToolbarItem(placement: .topBarTrailing) {
                    Button(action: { showingAddSheet = true }) {
                        Image(systemName: "plus")
                    }.foregroundColor(AppTheme.accentTeal)
                }
            }
            .sheet(isPresented: $showingAddSheet) { AddDocumentSheet() }
        }
        .preferredColorScheme(.dark)
    }
}

struct DocumentRow: View {
    let document: HealthDocument
    
    var body: some View {
        HStack(spacing: 12) {
            Text(document.type.icon)
                .font(.title2)
            
            VStack(alignment: .leading, spacing: 2) {
                Text(document.title)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textPrimary)
                Text(document.type.rawValue)
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            
            Spacer()
            
            Image(systemName: "chevron.right")
                .foregroundColor(AppTheme.textMuted)
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
}

struct AddDocumentSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = DocumentsViewModel.shared
    
    @State private var title = ""
    @State private var type: DocumentType = .other
    @State private var notes = ""
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 20) {
                    TextField("Document title", text: $title)
                        .textFieldStyle(.roundedBorder)
                    
                    Picker("Type", selection: $type) {
                        ForEach(DocumentType.allCases, id: \.self) { t in
                            Text("\(t.icon) \(t.rawValue)").tag(t)
                        }
                    }
                    .pickerStyle(.menu)
                    
                    TextField("Notes", text: $notes)
                        .textFieldStyle(.roundedBorder)
                    
                    Button(action: addDocument) {
                        Text("Save Document")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(title.isEmpty ? Color.gray : AppTheme.accentPurple)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .disabled(title.isEmpty)
                    
                    Spacer()
                }
                .padding(20)
            }
            .navigationTitle("Add Document")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func addDocument() {
        let doc = HealthDocument(title: title, type: type, notes: notes)
        viewModel.addDocument(doc)
        dismiss()
    }
}

// MARK: - Export Sheet (moved from original)
struct ExportSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var healthViewModel = HealthViewModel.shared
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 24) {
                    Image(systemName: "doc.richtext")
                        .font(.system(size: 60))
                        .foregroundColor(AppTheme.accentPurple)
                    
                    Text("Export Health Report")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    VStack(alignment: .leading, spacing: 12) {
                        ExportPreviewRow(label: "Steps (avg)", value: "\(healthViewModel.averageWeeklySteps.formatted())")
                        ExportPreviewRow(label: "Sleep (avg)", value: "\(String(format: "%.1f", healthViewModel.averageWeeklySleep)) hrs")
                        ExportPreviewRow(label: "Heart Rate (avg)", value: "\(healthViewModel.averageWeeklyHeartRate) BPM")
                        ExportPreviewRow(label: "Health Score", value: "\(healthViewModel.healthScore)/100")
                    }
                    .padding()
                    .background(AppTheme.darkNavyCard)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
                    .padding(.horizontal, 20)
                    
                    Button(action: {
                        let report = """
                        📊 Weekly Health Report
                        
                        Average Steps: \(healthViewModel.averageWeeklySteps.formatted())
                        Average Sleep: \(String(format: "%.1f", healthViewModel.averageWeeklySleep)) hours
                        Average Heart Rate: \(healthViewModel.averageWeeklyHeartRate) BPM
                        Health Score: \(healthViewModel.healthScore)/100
                        
                        Generated by Health Tracker
                        """
                        
                        let activityVC = UIActivityViewController(activityItems: [report], applicationActivities: nil)
                        if let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
                           let window = windowScene.windows.first,
                           let rootVC = window.rootViewController {
                            rootVC.present(activityVC, animated: true)
                        }
                        dismiss()
                    }) {
                        HStack {
                            Image(systemName: "square.and.arrow.up")
                            Text("Share Report")
                        }
                        .font(.headline)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(AppTheme.accentTeal)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .padding(.horizontal, 20)
                    
                    Spacer()
                }
                .padding(.top, 40)
            }
            .navigationTitle("Export")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Done") { dismiss() }.foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
}

struct ExportPreviewRow: View {
    let label: String
    let value: String
    
    var body: some View {
        HStack {
            Text(label).foregroundColor(AppTheme.textSecondary)
            Spacer()
            Text(value).fontWeight(.semibold).foregroundColor(AppTheme.textPrimary)
        }
    }
}

// MARK: - Reusable Components
struct QuickActionButton: View {
    let icon: String
    let title: String
    let color: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            VStack(spacing: 8) {
                Circle()
                    .fill(color.opacity(0.2))
                    .frame(width: 56, height: 56)
                    .overlay(Image(systemName: icon).font(.title2).foregroundColor(color))
                Text(title).font(.caption).fontWeight(.medium).foregroundColor(AppTheme.textPrimary)
            }
            .frame(width: 80)
        }
    }
}

struct ToolCardDark: View {
    let icon: String
    let title: String
    let subtitle: String
    let gradient: [Color]
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            VStack(alignment: .leading, spacing: 12) {
                Circle().fill(.white.opacity(0.2)).frame(width: 48, height: 48)
                    .overlay(Image(systemName: icon).font(.title2).foregroundColor(.white))
                Spacer()
                VStack(alignment: .leading, spacing: 2) {
                    Text(title).font(.subheadline).fontWeight(.bold).foregroundColor(.white)
                    Text(subtitle).font(.caption).foregroundColor(.white.opacity(0.7))
                }
            }
            .padding(16)
            .frame(maxWidth: .infinity, alignment: .leading)
            .frame(height: 140)
            .background(LinearGradient(colors: gradient, startPoint: .topLeading, endPoint: .bottomTrailing))
            .clipShape(RoundedRectangle(cornerRadius: 20))
        }
    }
}

struct ExportRowCard: View {
    let icon: String
    let title: String
    let subtitle: String
    let color: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Circle().fill(color.opacity(0.2)).frame(width: 48, height: 48)
                    .overlay(Image(systemName: icon).font(.title3).foregroundColor(color))
                VStack(alignment: .leading, spacing: 2) {
                    Text(title).font(.subheadline).fontWeight(.medium).foregroundColor(AppTheme.textPrimary)
                    Text(subtitle).font(.caption).foregroundColor(AppTheme.textMuted)
                }
                Spacer()
                Image(systemName: "chevron.right").font(.caption).foregroundColor(AppTheme.textMuted)
            }
            .padding()
            .background(AppTheme.darkNavyCard)
            .clipShape(RoundedRectangle(cornerRadius: 16))
        }
    }
}

#Preview {
    ToolsView()
        .environmentObject(HealthViewModel.shared)
        .environmentObject(SettingsManager.shared)
}
