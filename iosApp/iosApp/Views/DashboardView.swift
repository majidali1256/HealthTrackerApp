import SwiftUI

struct DashboardView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    @EnvironmentObject var settingsManager: SettingsManager
    @State private var selectedMetric: MetricType? = nil
    @State private var showingAddWaterSheet = false
    @State private var showingLogVitalsSheet = false
    @State private var showingProfileSheet = false
    
    var body: some View {
        NavigationStack {
            ZStack {
                // Dark Navy Background
                AppTheme.darkNavyBg
                    .ignoresSafeArea()
                
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 0) {
                        // MARK: - Header Section
                        headerSection
                        
                        // MARK: - Health Score Ring
                        healthScoreRing
                            .padding(.top, 24)
                        
                        // MARK: - Favorite Section
                        favoriteSection
                            .padding(.top, 32)
                        
                        // MARK: - All Health Data Section
                        allHealthDataSection
                            .padding(.top, 28)
                        
                        // MARK: - Recent Logs Section
                        recentLogsSection
                            .padding(.top, 28)
                            .padding(.bottom, 100)
                    }
                }
                
                // FAB
                VStack {
                    Spacer()
                    HStack {
                        Spacer()
                        Button(action: { showingLogVitalsSheet = true }) {
                            Image(systemName: "plus")
                                .font(.title2)
                                .fontWeight(.bold)
                                .foregroundColor(.white)
                                .frame(width: 56, height: 56)
                                .background(AppTheme.accentTeal)
                                .clipShape(Circle())
                                .shadow(color: AppTheme.accentTeal.opacity(0.5), radius: 10, y: 5)
                        }
                        .padding(.trailing, 20)
                        .padding(.bottom, 90)
                    }
                }
            }
        }
        .preferredColorScheme(settingsManager.colorScheme)
        .sheet(isPresented: $showingAddWaterSheet) {
            AddWaterSheet()
        }
        .sheet(isPresented: $showingLogVitalsSheet) {
            LogVitalsSheet()
        }
        .sheet(isPresented: $showingProfileSheet) {
            ProfileView()
        }
    }
    
    // MARK: - Header Section
    private var headerSection: some View {
        HStack(alignment: .top) {
            VStack(alignment: .leading, spacing: 4) {
                Text(greeting)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textSecondary)
                Text(settingsManager.userName)
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(AppTheme.textPrimary)
            }
            
            Spacer()
            
            HStack(spacing: 12) {
                // Search Button
                Button(action: {}) {
                    Image(systemName: "magnifyingglass")
                        .font(.title3)
                        .foregroundColor(AppTheme.textSecondary)
                }
                
                // Profile Picture
                Button(action: { showingProfileSheet = true }) {
                    Circle()
                        .fill(AppTheme.darkNavyCard)
                        .frame(width: 48, height: 48)
                        .overlay(
                            Image(systemName: "person.fill")
                                .foregroundColor(AppTheme.textSecondary)
                        )
                }
            }
        }
        .padding(.horizontal, 20)
        .padding(.top, 60)
    }
    
    private var greeting: String {
        let hour = Calendar.current.component(.hour, from: Date())
        if hour < 12 {
            return "Good Morning,"
        } else if hour < 17 {
            return "Good Afternoon,"
        } else {
            return "Good Evening,"
        }
    }
    
    // MARK: - Health Score Ring
    private var healthScoreRing: some View {
        ZStack {
            // Background Ring
            Circle()
                .stroke(AppTheme.scoreRingTrack, lineWidth: 16)
                .frame(width: 200, height: 200)
            
            // Progress Ring
            Circle()
                .trim(from: 0, to: CGFloat(healthViewModel.healthScore) / 100)
                .stroke(
                    AngularGradient(
                        colors: [AppTheme.accentBlue, AppTheme.accentTeal, AppTheme.accentBlue],
                        center: .center
                    ),
                    style: StrokeStyle(lineWidth: 16, lineCap: .round)
                )
                .frame(width: 200, height: 200)
                .rotationEffect(.degrees(-90))
                .animation(.easeInOut(duration: 1.0), value: healthViewModel.healthScore)
            
            // Decorative dots around ring
            ForEach(0..<36, id: \.self) { index in
                Circle()
                    .fill(index < Int(Double(healthViewModel.healthScore) / 100 * 36) ? AppTheme.accentBlue : AppTheme.scoreRingTrack)
                    .frame(width: 6, height: 6)
                    .offset(y: -115)
                    .rotationEffect(.degrees(Double(index) * 10))
            }
            
            // Center Content
            VStack(spacing: 4) {
                Text("\(healthViewModel.healthScore)")
                    .font(.system(size: 56, weight: .bold, design: .rounded))
                    .foregroundColor(AppTheme.textPrimary)
                Text("out of 100")
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textSecondary)
                Text("Health Score")
                    .font(.headline)
                    .foregroundColor(AppTheme.accentTeal)
                    .padding(.top, 8)
            }
        }
        .padding(.horizontal, 40)
    }
    
    // MARK: - Favorite Section
    private var favoriteSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Header
            HStack {
                HStack(spacing: 6) {
                    Text("★")
                        .foregroundColor(AppTheme.accentTeal)
                    Text("Favorite")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                }
                
                Spacer()
                
                Button(action: {}) {
                    Text("Edit ›")
                        .font(.subheadline)
                        .foregroundColor(AppTheme.accentTeal)
                }
            }
            .padding(.horizontal, 20)
            
            // Horizontal Scrolling Cards
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    // Steps Card - Tappable
                    Button(action: { selectedMetric = .steps }) {
                        FavoriteMetricCard(
                            emoji: "👟",
                            title: "Steps",
                            subtitle: "Today",
                            value1: "\(healthViewModel.todaySteps.formatted())",
                            unit1: "steps",
                            value2: "280",
                            unit2: "kcal",
                            gradient: AppTheme.cardTealGradient
                        )
                    }
                    
                    // Sleep Card - Tappable
                    Button(action: { selectedMetric = .sleep }) {
                        FavoriteMetricCard(
                            emoji: "💤",
                            title: "Sleep",
                            subtitle: "Last night",
                            value1: String(format: "%.1f", healthViewModel.todaySleepHours),
                            unit1: "hours",
                            value2: "\(healthViewModel.sleepScore)",
                            unit2: "score",
                            gradient: AppTheme.cardIndigoGradient
                        )
                    }
                    
                    // Water Card - Tappable
                    Button(action: { selectedMetric = .water }) {
                        FavoriteMetricCard(
                            emoji: "💧",
                            title: "Water",
                            subtitle: "Today",
                            value1: "\(healthViewModel.todayWaterGlasses)",
                            unit1: "glasses",
                            value2: "\(Int(Double(healthViewModel.todayWaterGlasses) / Double(settingsManager.dailyWaterGoal) * 100))%",
                            unit2: "goal",
                            gradient: AppTheme.cardGreenGradient
                        )
                    }
                    
                    // Heart Rate Card - Tappable
                    Button(action: { selectedMetric = .heartRate }) {
                        FavoriteMetricCard(
                            emoji: "❤️",
                            title: "Heart",
                            subtitle: "Last reading",
                            value1: "\(healthViewModel.lastHeartRate)",
                            unit1: "BPM",
                            value2: healthViewModel.heartRateStatus,
                            unit2: "status",
                            gradient: AppTheme.cardRedGradient
                        )
                    }
                }
                .padding(.horizontal, 16)
            }
        }
        .sheet(item: $selectedMetric) { metric in
            NavigationStack {
                ZStack {
                    switch metric {
                    case .steps: StepsDetailView()
                    case .sleep: SleepDetailView()
                    case .water: WaterDetailView()
                    case .heartRate: HeartRateDetailView()
                    }
                }
                .navigationTitle(metric.title)
                .navigationBarTitleDisplayMode(.inline)
                .toolbar {
                    ToolbarItem(placement: .topBarTrailing) {
                        Button("Done") { selectedMetric = nil }
                            .foregroundColor(AppTheme.accentTeal)
                    }
                }
            }
            .presentationDetents([.medium, .large])
            .preferredColorScheme(settingsManager.colorScheme)
        }
    }
    
    // MARK: - All Health Data Section
    private var allHealthDataSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            // Header
            HStack {
                HStack(spacing: 6) {
                    Text("♥")
                        .foregroundColor(AppTheme.accentRed)
                    Text("Quick Actions")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                }
                
                Spacer()
            }
            .padding(.horizontal, 20)
            
            // Activity Pills
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 12) {
                    Button(action: { showingLogVitalsSheet = true }) {
                        ActivityPill(emoji: "❤️", title: "Log Vitals")
                    }
                    Button(action: { showingAddWaterSheet = true }) {
                        ActivityPill(emoji: "💧", title: "Add Water")
                    }
                    ActivityPill(emoji: "🧘", title: "Meditation")
                    ActivityPill(emoji: "🏃", title: "Running")
                    ActivityPill(emoji: "🧘‍♀️", title: "Yoga")
                    ActivityPill(emoji: "🏋️", title: "Strength")
                }
                .padding(.horizontal, 16)
            }
        }
    }
    
    // MARK: - Recent Logs Section
    private var recentLogsSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Recent Logs")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary)
                .padding(.horizontal, 20)
            
            VStack(spacing: 8) {
                ForEach(healthViewModel.recentLogs.prefix(4)) { log in
                    RecentLogRow(
                        icon: log.type.icon,
                        title: log.type.title,
                        value: log.value,
                        time: log.timeAgo,
                        color: log.type.color
                    )
                }
            }
            .padding(.horizontal, 20)
        }
    }
}

// MARK: - Add Water Sheet
struct AddWaterSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var healthViewModel = HealthViewModel.shared
    @State private var glasses = 1
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 30) {
                    Text("💧")
                        .font(.system(size: 80))
                    
                    Text("Add Water")
                        .font(.title)
                        .fontWeight(.bold)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    HStack(spacing: 40) {
                        Button(action: { if glasses > 1 { glasses -= 1 } }) {
                            Image(systemName: "minus.circle.fill")
                                .font(.system(size: 44))
                                .foregroundColor(AppTheme.accentBlue)
                        }
                        
                        Text("\(glasses)")
                            .font(.system(size: 64, weight: .bold, design: .rounded))
                            .foregroundColor(AppTheme.textPrimary)
                            .frame(width: 100)
                        
                        Button(action: { glasses += 1 }) {
                            Image(systemName: "plus.circle.fill")
                                .font(.system(size: 44))
                                .foregroundColor(AppTheme.accentBlue)
                        }
                    }
                    
                    Text("glass\(glasses > 1 ? "es" : "")")
                        .font(.headline)
                        .foregroundColor(AppTheme.textSecondary)
                    
                    Button(action: {
                        healthViewModel.addWater(glasses: glasses)
                        dismiss()
                    }) {
                        Text("Add")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(AppTheme.accentTeal)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .padding(.horizontal, 40)
                    .padding(.top, 20)
                }
            }
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }
                        .foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
}

// MARK: - Log Vitals Sheet
struct LogVitalsSheet: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var healthViewModel = HealthViewModel.shared
    @State private var heartRate: String = ""
    @State private var selectedType = 0
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                VStack(spacing: 24) {
                    Picker("Type", selection: $selectedType) {
                        Text("Heart Rate").tag(0)
                        Text("Steps").tag(1)
                        Text("Sleep").tag(2)
                    }
                    .pickerStyle(.segmented)
                    .padding(.horizontal, 20)
                    
                    VStack(spacing: 16) {
                        Text(selectedType == 0 ? "❤️" : selectedType == 1 ? "👟" : "💤")
                            .font(.system(size: 60))
                        
                        TextField(
                            selectedType == 0 ? "Heart Rate (BPM)" : selectedType == 1 ? "Steps" : "Hours",
                            text: $heartRate
                        )
                        .textFieldStyle(.roundedBorder)
                        .keyboardType(.numberPad)
                        .padding(.horizontal, 40)
                    }
                    
                    Button(action: saveVital) {
                        Text("Save")
                            .font(.headline)
                            .foregroundColor(.white)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(AppTheme.accentTeal)
                            .clipShape(RoundedRectangle(cornerRadius: 12))
                    }
                    .padding(.horizontal, 40)
                    
                    Spacer()
                }
                .padding(.top, 20)
            }
            .navigationTitle("Log Vitals")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarTrailing) {
                    Button("Cancel") { dismiss() }
                        .foregroundColor(AppTheme.accentTeal)
                }
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func saveVital() {
        guard let value = Int(heartRate) else { return }
        
        switch selectedType {
        case 0: healthViewModel.logHeartRate(bpm: value)
        case 1: healthViewModel.addSteps(count: value)
        case 2: healthViewModel.logSleep(hours: Double(value))
        default: break
        }
        
        dismiss()
    }
}

// MARK: - Favorite Metric Card Component
struct FavoriteMetricCard: View {
    let emoji: String
    let title: String
    let subtitle: String
    let value1: String
    let unit1: String
    let value2: String
    let unit2: String
    let gradient: [Color]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            // Header
            VStack(alignment: .leading, spacing: 2) {
                Text("\(emoji) \(title)")
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textSecondary)
                Text(subtitle)
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            
            Spacer()
            
            // Values
            HStack(spacing: 16) {
                VStack(alignment: .leading, spacing: 2) {
                    Text(value1)
                        .font(.title2)
                        .fontWeight(.bold)
                        .foregroundColor(AppTheme.textPrimary)
                    Text(unit1)
                        .font(.caption)
                        .foregroundColor(AppTheme.textMuted)
                }
                
                VStack(alignment: .leading, spacing: 2) {
                    Text(value2)
                        .font(.title2)
                        .fontWeight(.bold)
                        .foregroundColor(AppTheme.textPrimary)
                    Text(unit2)
                        .font(.caption)
                        .foregroundColor(AppTheme.textMuted)
                }
            }
        }
        .padding(16)
        .frame(width: 160, height: 140)
        .background(
            LinearGradient(colors: gradient, startPoint: .topLeading, endPoint: .bottomTrailing)
        )
        .clipShape(RoundedRectangle(cornerRadius: 20))
    }
}

// MARK: - Activity Pill Component
struct ActivityPill: View {
    let emoji: String
    let title: String
    
    var body: some View {
        HStack(spacing: 6) {
            Text(emoji)
            Text(title)
                .font(.subheadline)
                .foregroundColor(AppTheme.textPrimary)
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 12)
        .background(AppTheme.darkNavySurface)
        .clipShape(Capsule())
        .overlay(
            Capsule()
                .stroke(AppTheme.darkNavyCardLight, lineWidth: 1)
        )
    }
}

// MARK: - Recent Log Row Component
struct RecentLogRow: View {
    let icon: String
    let title: String
    let value: String
    let time: String
    let color: Color
    
    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(color.opacity(0.2))
                .frame(width: 44, height: 44)
                .overlay(
                    Image(systemName: icon)
                        .foregroundColor(color)
                )
            
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .font(.subheadline)
                    .fontWeight(.medium)
                    .foregroundColor(AppTheme.textPrimary)
                Text(time)
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            
            Spacer()
            
            Text(value)
                .font(.subheadline)
                .fontWeight(.semibold)
                .foregroundColor(AppTheme.textPrimary)
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
}

#Preview {
    DashboardView()
        .environmentObject(HealthViewModel.shared)
        .environmentObject(SettingsManager.shared)
}
