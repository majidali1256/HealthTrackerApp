import SwiftUI

struct ProfileView: View {
    @EnvironmentObject var settingsManager: SettingsManager
    @EnvironmentObject var healthViewModel: HealthViewModel
    @State private var showingSignOutAlert = false
    @State private var showingEditProfile = false
    @State private var showingMedicalID = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                // Dark Navy Background
                AppTheme.darkNavyBg
                    .ignoresSafeArea()
                
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 0) {
                        // MARK: - Profile Header
                        profileHeader
                        
                        // MARK: - Stats Cards
                        statsSection
                            .padding(.top, 24)
                        
                        // MARK: - Settings Sections
                        settingsSection
                            .padding(.top, 24)
                        
                        // MARK: - Sign Out Button
                        signOutButton
                            .padding(.top, 24)
                        
                        // Version
                        Text("Health Tracker v1.0")
                            .font(.caption)
                            .foregroundColor(AppTheme.textMuted)
                            .padding(.top, 20)
                            .padding(.bottom, 100)
                    }
                }
            }
        }
        .preferredColorScheme(settingsManager.colorScheme)
        .alert("Sign Out", isPresented: $showingSignOutAlert) {
            Button("Cancel", role: .cancel) {}
            Button("Sign Out", role: .destructive) {
                // Handle sign out
            }
        } message: {
            Text("Are you sure you want to sign out?")
        }
        .sheet(isPresented: $showingEditProfile) {
            EditProfileSheet()
        }
        .sheet(isPresented: $showingMedicalID) {
            MedicalIDSheet()
        }
    }
    
    // MARK: - Profile Header
    private var profileHeader: some View {
        VStack(spacing: 16) {
            // Profile Picture
            ZStack {
                Circle()
                    .fill(
                        LinearGradient(
                            colors: AppTheme.cardPurpleGradient,
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                    )
                    .frame(width: 100, height: 100)
                
                Image(systemName: "person.fill")
                    .font(.system(size: 40))
                    .foregroundColor(.white)
            }
            .overlay(
                Circle()
                    .stroke(AppTheme.accentTeal, lineWidth: 3)
            )
            .shadow(color: AppTheme.accentPurple.opacity(0.5), radius: 15)
            
            // Name and Email
            VStack(spacing: 4) {
                Text(settingsManager.userName)
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(AppTheme.textPrimary)
                
                Text(settingsManager.userEmail.isEmpty ? "user@email.com" : settingsManager.userEmail)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textSecondary)
            }
            
            // Member Badge
            if settingsManager.isPremiumUser {
                HStack(spacing: 6) {
                    Image(systemName: "crown.fill")
                        .font(.caption)
                        .foregroundColor(AppTheme.accentOrange)
                    Text("Premium Member")
                        .font(.caption)
                        .fontWeight(.medium)
                        .foregroundColor(AppTheme.accentOrange)
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 8)
                .background(AppTheme.accentOrange.opacity(0.15))
                .clipShape(Capsule())
            }
        }
        .padding(.top, 60)
        .padding(.bottom, 24)
    }
    
    // MARK: - Stats Section
    private var statsSection: some View {
        HStack(spacing: 12) {
            StatCardDark(
                value: "\(healthViewModel.averageWeeklyHeartRate)",
                label: "Avg Heart",
                icon: "heart.fill",
                color: AppTheme.accentRed
            )
            StatCardDark(
                value: "\(String(format: "%.1f", Double(healthViewModel.averageWeeklySteps) / 1000))K",
                label: "Avg Steps",
                icon: "figure.walk",
                color: AppTheme.accentGreen
            )
            StatCardDark(
                value: "\(String(format: "%.1f", healthViewModel.averageWeeklySleep))h",
                label: "Avg Sleep",
                icon: "moon.fill",
                color: AppTheme.accentPurple
            )
        }
        .padding(.horizontal, 20)
    }
    
    // MARK: - Settings Section
    private var settingsSection: some View {
        VStack(spacing: 20) {
            // Account Section
            SettingsSectionDark(title: "Account") {
                SettingsRowDark(icon: "person.fill", title: "Edit Profile", color: AppTheme.accentBlue) {
                    showingEditProfile = true
                }
                SettingsRowDark(icon: "heart.text.square.fill", title: "Medical ID", color: AppTheme.accentGreen) {
                    showingMedicalID = true
                }
                SettingsRowDark(icon: "phone.fill", title: "Emergency Contacts", color: AppTheme.accentRed) {
                    // Navigate to emergency contacts
                }
            }
            
            // Preferences Section
            SettingsSectionDark(title: "Preferences") {
                SettingsToggleRowDark(
                    icon: "bell.fill",
                    title: "Notifications",
                    color: AppTheme.accentPurple,
                    isOn: Binding(
                        get: { settingsManager.notificationsEnabled },
                        set: { settingsManager.notificationsEnabled = $0 }
                    )
                )
                SettingsToggleRowDark(
                    icon: "moon.fill",
                    title: "Dark Mode",
                    color: AppTheme.accentBlue,
                    isOn: Binding(
                        get: { settingsManager.isDarkMode },
                        set: { newValue in
                            settingsManager.isDarkMode = newValue
                            settingsManager.followSystemTheme = false
                        }
                    )
                )
                SettingsRowDark(icon: "lock.fill", title: "Privacy Settings", color: AppTheme.accentTeal) {
                    // Navigate to privacy
                }
            }
            
            // Goals Section
            SettingsSectionDark(title: "Health Goals") {
                GoalRowDark(
                    icon: "figure.walk",
                    title: "Daily Steps Goal",
                    value: "\(settingsManager.dailyStepsGoal.formatted())",
                    color: AppTheme.accentGreen
                )
                GoalRowDark(
                    icon: "drop.fill",
                    title: "Daily Water Goal",
                    value: "\(settingsManager.dailyWaterGoal) glasses",
                    color: AppTheme.accentBlue
                )
                GoalRowDark(
                    icon: "moon.fill",
                    title: "Sleep Goal",
                    value: "\(Int(settingsManager.sleepGoalHours)) hours",
                    color: AppTheme.accentPurple
                )
            }
            
            // Support Section
            SettingsSectionDark(title: "Support") {
                SettingsRowDark(icon: "questionmark.circle.fill", title: "Help & Support", color: AppTheme.accentGreen) {}
                SettingsRowDark(icon: "star.fill", title: "Rate App", color: AppTheme.accentOrange) {}
                SettingsRowDark(icon: "square.and.arrow.up", title: "Share App", color: AppTheme.accentBlue) {}
            }
        }
    }
    
    // MARK: - Sign Out Button
    private var signOutButton: some View {
        Button(action: { showingSignOutAlert = true }) {
            HStack {
                Spacer()
                Image(systemName: "rectangle.portrait.and.arrow.right")
                Text("Sign Out")
                    .fontWeight(.semibold)
                Spacer()
            }
            .foregroundColor(AppTheme.accentRed)
            .padding()
            .background(AppTheme.accentRed.opacity(0.1))
            .clipShape(RoundedRectangle(cornerRadius: 12))
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(AppTheme.accentRed.opacity(0.3), lineWidth: 1)
            )
        }
        .padding(.horizontal, 20)
    }
}

// MARK: - Edit Profile Sheet
struct EditProfileSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var settingsManager = SettingsManager.shared
    @State private var name: String = ""
    @State private var email: String = ""
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 24) {
                    // Profile Picture
                    ZStack {
                        Circle()
                            .fill(AppTheme.cardPurpleGradient[0])
                            .frame(width: 100, height: 100)
                        Image(systemName: "person.fill")
                            .font(.system(size: 40))
                            .foregroundColor(.white)
                    }
                    
                    VStack(spacing: 16) {
                        TextField("Name", text: $name)
                            .textFieldStyle(.roundedBorder)
                        
                        TextField("Email", text: $email)
                            .textFieldStyle(.roundedBorder)
                            .keyboardType(.emailAddress)
                    }
                    .padding(.horizontal, 20)
                    
                    Button(action: saveProfile) {
                        Text("Save Changes")
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
            .navigationTitle("Edit Profile")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }
                        .foregroundColor(AppTheme.accentTeal)
                }
            }
            .onAppear {
                name = settingsManager.userName
                email = settingsManager.userEmail
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func saveProfile() {
        settingsManager.userName = name
        settingsManager.userEmail = email
        dismiss()
    }
}

// MARK: - Medical ID Sheet
struct MedicalIDSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = MedicalIDViewModel.shared
    @State private var showingAddAllergy = false
    @State private var showingAddCondition = false
    @State private var showingAddContact = false
    @State private var newAllergy = ""
    @State private var newCondition = ""
    @State private var newContactName = ""
    @State private var newContactPhone = ""
    @State private var newContactRelation = ""
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                ScrollView {
                    VStack(spacing: 20) {
                        // Blood Type
                        VStack(alignment: .leading, spacing: 8) {
                            Text("Blood Type")
                                .font(.caption)
                                .foregroundColor(AppTheme.textMuted)
                            Picker("Blood Type", selection: $viewModel.medicalID.bloodType) {
                                Text("Not Set").tag("")
                                ForEach(MedicalID.bloodTypes, id: \.self) { type in
                                    Text(type).tag(type)
                                }
                            }
                            .pickerStyle(.menu)
                            .onChange(of: viewModel.medicalID.bloodType) { _, _ in viewModel.save() }
                        }
                        .padding()
                        .background(AppTheme.darkNavyCard)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        
                        // Organ Donor
                        Toggle(isOn: $viewModel.medicalID.organDonor) {
                            Label("Organ Donor", systemImage: "heart.fill")
                                .foregroundColor(AppTheme.textPrimary)
                        }
                        .tint(AppTheme.accentTeal)
                        .padding()
                        .background(AppTheme.darkNavyCard)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                        .onChange(of: viewModel.medicalID.organDonor) { _, _ in viewModel.save() }
                        
                        // Allergies
                        sectionView(title: "Allergies", items: viewModel.medicalID.allergies, icon: "exclamationmark.triangle.fill", color: AppTheme.accentOrange) {
                            showingAddAllergy = true
                        }
                        
                        // Medical Conditions
                        sectionView(title: "Medical Conditions", items: viewModel.medicalID.conditions, icon: "stethoscope", color: AppTheme.accentPurple) {
                            showingAddCondition = true
                        }
                        
                        // Emergency Contacts
                        VStack(alignment: .leading, spacing: 12) {
                            HStack {
                                Text("Emergency Contacts")
                                    .font(.headline)
                                    .foregroundColor(AppTheme.textPrimary)
                                Spacer()
                                Button(action: { showingAddContact = true }) {
                                    Image(systemName: "plus.circle.fill")
                                        .foregroundColor(AppTheme.accentTeal)
                                }
                            }
                            
                            if viewModel.medicalID.emergencyContacts.isEmpty {
                                Text("No emergency contacts")
                                    .font(.caption)
                                    .foregroundColor(AppTheme.textMuted)
                            } else {
                                ForEach(viewModel.medicalID.emergencyContacts) { contact in
                                    HStack {
                                        VStack(alignment: .leading) {
                                            Text(contact.name)
                                                .foregroundColor(AppTheme.textPrimary)
                                            Text("\(contact.relationship) • \(contact.phone)")
                                                .font(.caption)
                                                .foregroundColor(AppTheme.textMuted)
                                        }
                                        Spacer()
                                        Button(action: { viewModel.removeEmergencyContact(contact) }) {
                                            Image(systemName: "trash")
                                                .foregroundColor(AppTheme.accentRed)
                                        }
                                    }
                                    .padding(.vertical, 4)
                                }
                            }
                        }
                        .padding()
                        .background(AppTheme.darkNavyCard)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .padding()
                }
            }
            .navigationTitle("Medical ID")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Done") { dismiss() }
                        .foregroundColor(AppTheme.accentTeal)
                }
            }
            .alert("Add Allergy", isPresented: $showingAddAllergy) {
                TextField("Allergy", text: $newAllergy)
                Button("Cancel", role: .cancel) { newAllergy = "" }
                Button("Add") { viewModel.addAllergy(newAllergy); newAllergy = "" }
            }
            .alert("Add Condition", isPresented: $showingAddCondition) {
                TextField("Condition", text: $newCondition)
                Button("Cancel", role: .cancel) { newCondition = "" }
                Button("Add") { viewModel.addCondition(newCondition); newCondition = "" }
            }
            .alert("Add Emergency Contact", isPresented: $showingAddContact) {
                TextField("Name", text: $newContactName)
                TextField("Phone", text: $newContactPhone)
                TextField("Relationship", text: $newContactRelation)
                Button("Cancel", role: .cancel) { clearContactFields() }
                Button("Add") {
                    let contact = EmergencyContact(name: newContactName, phone: newContactPhone, relationship: newContactRelation)
                    viewModel.addEmergencyContact(contact)
                    clearContactFields()
                }
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func sectionView(title: String, items: [String], icon: String, color: Color, onAdd: @escaping () -> Void) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Label(title, systemImage: icon)
                    .font(.headline)
                    .foregroundColor(AppTheme.textPrimary)
                Spacer()
                Button(action: onAdd) {
                    Image(systemName: "plus.circle.fill")
                        .foregroundColor(AppTheme.accentTeal)
                }
            }
            
            if items.isEmpty {
                Text("None added")
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            } else {
                FlowLayout(items: items, color: color)
            }
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
    
    private func clearContactFields() {
        newContactName = ""
        newContactPhone = ""
        newContactRelation = ""
    }
}

struct FlowLayout: View {
    let items: [String]
    let color: Color
    
    var body: some View {
        LazyVGrid(columns: [GridItem(.adaptive(minimum: 80))], spacing: 8) {
            ForEach(items, id: \.self) { item in
                Text(item)
                    .font(.caption)
                    .foregroundColor(.white)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 6)
                    .background(color.opacity(0.8))
                    .clipShape(Capsule())
            }
        }
    }
}

// MARK: - Dark Theme Stat Card
struct StatCardDark: View {
    let value: String
    let label: String
    let icon: String
    let color: Color
    
    var body: some View {
        VStack(spacing: 8) {
            Image(systemName: icon)
                .font(.title2)
                .foregroundColor(color)
            
            Text(value)
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(AppTheme.textPrimary)
            
            Text(label)
                .font(.caption)
                .foregroundColor(AppTheme.textMuted)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 20)
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}

// MARK: - Dark Theme Settings Section
struct SettingsSectionDark<Content: View>: View {
    let title: String
    @ViewBuilder let content: () -> Content
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.subheadline)
                .fontWeight(.semibold)
                .foregroundColor(AppTheme.textMuted)
                .padding(.horizontal, 20)
            
            VStack(spacing: 1) {
                content()
            }
            .background(AppTheme.darkNavyCard)
            .clipShape(RoundedRectangle(cornerRadius: 12))
            .padding(.horizontal, 20)
        }
    }
}

// MARK: - Dark Theme Settings Row
struct SettingsRowDark: View {
    let icon: String
    let title: String
    let color: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            HStack(spacing: 12) {
                Circle()
                    .fill(color.opacity(0.2))
                    .frame(width: 40, height: 40)
                    .overlay(
                        Image(systemName: icon)
                            .foregroundColor(color)
                    )
                
                Text(title)
                    .foregroundColor(AppTheme.textPrimary)
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            .padding()
        }
    }
}

// MARK: - Dark Theme Settings Toggle Row
struct SettingsToggleRowDark: View {
    let icon: String
    let title: String
    let color: Color
    @Binding var isOn: Bool
    
    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(color.opacity(0.2))
                .frame(width: 40, height: 40)
                .overlay(
                    Image(systemName: icon)
                        .foregroundColor(color)
                )
            
            Text(title)
                .foregroundColor(AppTheme.textPrimary)
            
            Spacer()
            
            Toggle("", isOn: $isOn)
                .labelsHidden()
                .tint(AppTheme.accentTeal)
        }
        .padding()
    }
}

// MARK: - Goal Row Dark
struct GoalRowDark: View {
    let icon: String
    let title: String
    let value: String
    let color: Color
    
    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(color.opacity(0.2))
                .frame(width: 40, height: 40)
                .overlay(
                    Image(systemName: icon)
                        .foregroundColor(color)
                )
            
            Text(title)
                .foregroundColor(AppTheme.textPrimary)
            
            Spacer()
            
            Text(value)
                .font(.subheadline)
                .fontWeight(.medium)
                .foregroundColor(AppTheme.accentTeal)
        }
        .padding()
    }
}

#Preview {
    ProfileView()
        .environmentObject(SettingsManager.shared)
        .environmentObject(HealthViewModel.shared)
}
