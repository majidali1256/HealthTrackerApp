import SwiftUI
import Charts

struct TrendsView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    @EnvironmentObject var settingsManager: SettingsManager
    @State private var selectedDay = 6  // Today (Sunday)
    @State private var selectedMetric: MetricType? = nil
    
    private let weekDays = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
    
    private func formatSleepHours(_ hours: Double) -> String {
        let h = Int(hours)
        let m = Int((hours - Double(h)) * 60)
        return "\(h)h \(m)m"
    }
    
    var body: some View {
        NavigationStack {
            ZStack {
                AppTheme.darkNavyBg.ignoresSafeArea()
                
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 24) {
                        // Insight Card (Sleep)
                        sleepInsightCard
                        
                        // Sleep Graph
                        sleepGraphSection
                        
                        // Stats Summary
                        statsSummary
                        
                        // All Trends
                        trendCardsSection
                    }
                    .padding(.bottom, 100)
                }
            }
            .navigationTitle("Trends")
        }
        .preferredColorScheme(settingsManager.colorScheme)
    }
    // MARK: - Sleep Insight Card
    private var sleepInsightCard: some View {
        Button(action: { selectedMetric = .sleep }) {
            VStack(alignment: .leading, spacing: 12) {
                // Header
                HStack {
                    Text("💤")
                    Text("Sleep")
                        .font(.subheadline)
                        .fontWeight(.medium)
                        .foregroundColor(AppTheme.textSecondary)
                }
                
                // Insight Text
                Text("You've slept an average of \(String(format: "%.1f", healthViewModel.averageWeeklySleep)) hours this week.")
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textPrimary)
                    .lineLimit(2)
                    .multilineTextAlignment(.leading)
                
                // Bedtime and Wake up
                HStack(spacing: 40) {
                    VStack(alignment: .leading, spacing: 4) {
                        Text("⏰ Bedtime")
                            .font(.caption)
                            .foregroundColor(AppTheme.textMuted)
                        Text("11:30 PM")
                            .font(.title3)
                            .fontWeight(.bold)
                            .foregroundColor(AppTheme.textPrimary)
                    }
                    
                    VStack(alignment: .leading, spacing: 4) {
                        Text("☀️ Wake up")
                            .font(.caption)
                            .foregroundColor(AppTheme.textMuted)
                        Text("7:30 AM")
                            .font(.title3)
                            .fontWeight(.bold)
                            .foregroundColor(AppTheme.textPrimary)
                    }
                }
            }
            .padding(20)
            .frame(maxWidth: .infinity, alignment: .leading)
            .background(AppTheme.darkNavyCard)
            .clipShape(RoundedRectangle(cornerRadius: 16))
            .padding(.horizontal, 20)
        }
    }
    
    // MARK: - Sleep Graph Section
    private var sleepGraphSection: some View {
        Button(action: { selectedMetric = .sleep }) {
            VStack(alignment: .leading, spacing: 16) {
                Text("Sleep Stages")
                    .font(.headline)
                    .foregroundColor(AppTheme.textPrimary)
                
                // Graph placeholder with bars
                HStack(alignment: .bottom, spacing: 8) {
                    ForEach(0..<7, id: \.self) { index in
                        VStack(spacing: 4) {
                            RoundedRectangle(cornerRadius: 4)
                                .fill(
                                    LinearGradient(
                                        colors: [AppTheme.accentPurple, AppTheme.accentBlue],
                                        startPoint: .top,
                                        endPoint: .bottom
                                    )
                                )
                                .frame(width: 30, height: CGFloat(healthViewModel.weeklySleep[index] * 10))
                            
                            Text(weekDays[index].prefix(1))
                                .font(.caption2)
                                .foregroundColor(AppTheme.textMuted)
                        }
                        .frame(maxWidth: .infinity)
                    }
                }
                .frame(height: 120)
            }
            .padding(20)
            .background(AppTheme.darkNavyCard)
            .clipShape(RoundedRectangle(cornerRadius: 16))
            .padding(.horizontal, 20)
        }
    }
    
    // MARK: - Stats Summary
    private var statsSummary: some View {
        Button(action: { selectedMetric = .sleep }) {
            VStack(spacing: 12) {
                HStack {
                    Image(systemName: "chart.bar.fill")
                        .foregroundColor(AppTheme.accentTeal)
                    Text("Weekly Summary")
                        .font(.subheadline)
                        .foregroundColor(AppTheme.textPrimary)
                    Spacer()
                    Text("7 days")
                        .font(.subheadline)
                        .foregroundColor(AppTheme.textSecondary)
                }
                
                Divider()
                    .background(AppTheme.darkNavyCardLight)
                
                HStack {
                    Image(systemName: "bed.double.fill")
                    
                        .foregroundColor(AppTheme.accentPurple)
                    Text("Average Time in Bed")
                        .font(.subheadline)
                        .foregroundColor(AppTheme.textPrimary)
                    Spacer()
                    Text(formatSleepHours(healthViewModel.averageWeeklySleep))
                        .font(.subheadline)
                        .fontWeight(.semibold)
                        .foregroundColor(AppTheme.textPrimary)
                }
            }
            .padding(16)
            .background(AppTheme.darkNavyCard)
            .clipShape(RoundedRectangle(cornerRadius: 16))
            .padding(.horizontal, 20)
        }
    }
    
    // MARK: - Trend Cards Section
    private var trendCardsSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("All Trends")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary)
                .padding(.horizontal, 20)
            
            VStack(spacing: 12) {
                // Steps Trend
                Button(action: { selectedMetric = .steps }) {
                    TrendRowCard(
                        icon: "figure.walk",
                        title: "Steps",
                        value: "\(healthViewModel.averageWeeklySteps.formatted()) avg",
                        trend: "+12%",
                        isPositive: true,
                        color: AppTheme.accentGreen
                    )
                }
                
                // Heart Rate Trend
                Button(action: { selectedMetric = .heartRate }) {
                    TrendRowCard(
                        icon: "heart.fill",
                        title: "Heart Rate",
                        value: "\(healthViewModel.averageWeeklyHeartRate) BPM",
                        trend: "-2%",
                        isPositive: true,
                        color: AppTheme.accentRed
                    )
                }
                
                // Hydration Trend
                Button(action: { selectedMetric = .water }) {
                    TrendRowCard(
                        icon: "drop.fill",
                        title: "Hydration",
                        value: "\(healthViewModel.todayWaterGlasses) glasses",
                        trend: "+8%",
                        isPositive: true,
                        color: AppTheme.accentBlue
                    )
                }
                
                // Calories Trend
                Button(action: { selectedMetric = .calories }) {
                    TrendRowCard(
                        icon: "flame.fill",
                        title: "Calories",
                        value: "\(healthViewModel.todayCalories.formatted()) kcal",
                        trend: "+5%",
                        isPositive: true,
                        color: AppTheme.accentOrange
                    )
                }
            }
            .padding(.horizontal, 20)
        }
        .sheet(item: $selectedMetric) { metric in
            NavigationStack {
                ZStack {
                    switch metric {
                    case .steps: StepsDetailView()
                    case .sleep: SleepDetailView()
                    case .water: WaterDetailView()
                    case .heartRate: HeartRateDetailView()
                    case .calories: CaloriesDetailView()
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
}




// MARK: - Steps Detail View
struct StepsDetailView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    @State private var selectedTimeRange = 0
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header Stat
                VStack(spacing: 8) {
                    Text("\(healthViewModel.todaySteps.formatted())")
                        .font(.system(size: 48, weight: .bold, design: .rounded))
                        .foregroundColor(AppTheme.textPrimary)
                    Text("Steps Today")
                        .font(.headline)
                        .foregroundColor(AppTheme.textSecondary)
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 20)
                
                // Chart Section
                VStack(alignment: .leading, spacing: 16) {
                    Text("Activity Trend")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    if #available(iOS 16.0, *) {
                        Chart {
                            ForEach(0..<min(healthViewModel.weeklySteps.count, 7), id: \.self) { index in
                                BarMark(
                                    x: .value("Day", ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"][index]),
                                    y: .value("Steps", healthViewModel.weeklySteps[index])
                                )
                                .foregroundStyle(AppTheme.cardTealGradient[0])
                            }
                            
                            RuleMark(y: .value("Goal", 10000))
                                .foregroundStyle(Color.gray.opacity(0.5))
                        }
                        .frame(height: 250)
                    } else {
                        // Fallback for iOS 15
                        HStack(alignment: .bottom, spacing: 12) {
                            ForEach(0..<min(healthViewModel.weeklySteps.count, 7), id: \.self) { index in
                                VStack {
                                    Spacer()
                                    RoundedRectangle(cornerRadius: 4)
                                        .fill(AppTheme.accentTeal)
                                        .frame(height: CGFloat(healthViewModel.weeklySteps[index]) / 15000 * 200)
                                    Text(["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"][index])
                                        .font(.caption)
                                        .foregroundColor(AppTheme.textMuted)
                                }
                            }
                        }
                        .frame(height: 250)
                    }
                }
                .padding()
                .background(AppTheme.darkNavyCard)
                .clipShape(RoundedRectangle(cornerRadius: 16))
                
                // Stats Grid
                LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                    DetailStatCard(title: "Distance", value: "4.2 km", icon: "map.fill", color: AppTheme.accentBlue)
                    DetailStatCard(title: "Calories", value: "320 kcal", icon: "flame.fill", color: AppTheme.accentOrange)
                    DetailStatCard(title: "Walking Time", value: "1h 20m", icon: "figure.walk", color: AppTheme.accentPurple)
                    DetailStatCard(title: "Avg Pace", value: "5.5 km/h", icon: "speedometer", color: AppTheme.accentTeal)
                }
            }
            .padding()
        }
        .background(AppTheme.darkNavyBg.ignoresSafeArea())
    }
}

// MARK: - Sleep Detail View
struct SleepDetailView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header Stat
                VStack(spacing: 8) {
                    Text(String(format: "%.1f h", healthViewModel.todaySleepHours))
                        .font(.system(size: 48, weight: .bold, design: .rounded))
                        .foregroundColor(AppTheme.textPrimary)
                    Text("Sleep Duration")
                        .font(.headline)
                        .foregroundColor(AppTheme.textSecondary)
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 20)
                
                // Sleep Stages Chart
                VStack(alignment: .leading, spacing: 16) {
                    Text("Sleep Stages")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    VStack(spacing: 12) {
                        SleepStageRow(stage: "Deep Sleep", duration: "1h 45m", percent: 0.25, color: .purple)
                        SleepStageRow(stage: "Core Sleep", duration: "4h 10m", percent: 0.55, color: .blue)
                        SleepStageRow(stage: "REM", duration: "1h 30m", percent: 0.20, color: .cyan)
                    }
                }
                .padding()
                .background(AppTheme.darkNavyCard)
                .clipShape(RoundedRectangle(cornerRadius: 16))
                
                // Weekly Trend
                VStack(alignment: .leading, spacing: 16) {
                    Text("Weekly Trend")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    HStack(alignment: .bottom, spacing: 12) {
                        ForEach(0..<7) { i in
                            VStack {
                                Spacer()
                                RoundedRectangle(cornerRadius: 4)
                                    .fill(i == 6 ? AppTheme.accentIndigo : AppTheme.darkNavySurface)
                                    .frame(height: CGFloat([6.5, 7.2, 5.8, 8.1, 7.5, 6.8, 7.5][i]) * 20)
                                Text(["M", "T", "W", "T", "F", "S", "S"][i])
                                    .font(.caption)
                                    .foregroundColor(AppTheme.textMuted)
                            }
                        }
                    }
                    .frame(height: 180)
                }
                .padding()
                .background(AppTheme.darkNavyCard)
                .clipShape(RoundedRectangle(cornerRadius: 16))
            }
            .padding()
        }
        .background(AppTheme.darkNavyBg.ignoresSafeArea())
    }
}

// MARK: - Heart Rate Detail View
struct HeartRateDetailView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header Stat
                VStack(spacing: 8) {
                    Text("\(healthViewModel.lastHeartRate)")
                        .font(.system(size: 48, weight: .bold, design: .rounded))
                        .foregroundColor(AppTheme.accentRed)
                    Text("BPM")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .foregroundColor(AppTheme.textSecondary)
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 20)
                
                // Chart
                VStack(alignment: .leading, spacing: 16) {
                    Text("Today's Range")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    if #available(iOS 16.0, *) {
                        Chart {
                            ForEach(0..<24, id: \.self) { hour in
                                LineMark(
                                    x: .value("Hour", hour),
                                    y: .value("BPM", Int.random(in: 60...90))
                                )
                                .foregroundStyle(AppTheme.accentRed)
                            }
                        }
                        .frame(height: 250)
                       .chartYScale(domain: 50...120)
                    } else {
                        // Fallback
                        Text("Chart unavailable on this iOS version")
                            .foregroundColor(AppTheme.textMuted)
                            .frame(height: 200)
                    }
                }
                .padding()
                .background(AppTheme.darkNavyCard)
                .clipShape(RoundedRectangle(cornerRadius: 16))
                
                // Stats Grid
                LazyVGrid(columns: [GridItem(.flexible()), GridItem(.flexible())], spacing: 16) {
                    DetailStatCard(title: "Resting Rate", value: "62 BPM", icon: "bed.double.fill", color: .purple)
                    DetailStatCard(title: "Walking Avg", value: "95 BPM", icon: "figure.walk", color: .orange)
                    DetailStatCard(title: "Max Rate", value: "115 BPM", icon: "waveform.path.ecg", color: .red)
                    DetailStatCard(title: "Variability", value: "45 ms", icon: "arrow.left.and.right", color: .blue)
                }
            }
            .padding()
        }
        .background(AppTheme.darkNavyBg.ignoresSafeArea())
    }
}

// MARK: - Water Detail View
struct WaterDetailView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    @EnvironmentObject var settingsManager: SettingsManager
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header Stat
                ZStack {
                    Circle()
                        .stroke(AppTheme.darkNavySurface, lineWidth: 20)
                        .frame(width: 200, height: 200)
                    
                    Circle()
                        .trim(from: 0, to: CGFloat(healthViewModel.todayWaterGlasses) / CGFloat(max(settingsManager.dailyWaterGoal, 1)))
                        .stroke(AppTheme.accentBlue, style: StrokeStyle(lineWidth: 20, lineCap: .round))
                        .frame(width: 200, height: 200)
                        .rotationEffect(.degrees(-90))
                    
                    VStack {
                        Text("\(healthViewModel.todayWaterGlasses) / \(settingsManager.dailyWaterGoal)")
                            .font(.system(size: 40, weight: .bold, design: .rounded))
                            .foregroundColor(AppTheme.textPrimary)
                        Text("Glasses")
                            .font(.headline)
                            .foregroundColor(AppTheme.textSecondary)
                    }
                }
                .padding(.vertical, 20)
                
                // Quick Add
                VStack(spacing: 16) {
                    Text("Quick Add")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    HStack(spacing: 20) {
                        Button(action: { healthViewModel.addWater(glasses: 1) }) {
                            VStack {
                                Image(systemName: "drop.fill")
                                    .font(.title)
                                    Text("+1 Cup")
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(AppTheme.darkNavyCard)
                            .cornerRadius(12)
                            .foregroundColor(AppTheme.accentBlue)
                        }
                        
                        Button(action: { healthViewModel.addWater(glasses: 2) }) {
                            VStack {
                                Image(systemName: "water.waves")
                                    .font(.title)
                                    Text("+1 Bottle")
                            }
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(AppTheme.darkNavyCard)
                            .cornerRadius(12)
                            .foregroundColor(AppTheme.accentBlue)
                        }
                    }
                }
                
                // Weekly History
                VStack(alignment: .leading, spacing: 16) {
                    Text("Weekly Hydration")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    HStack(alignment: .bottom, spacing: 12) {
                        ForEach(0..<7) { i in
                            VStack {
                                Spacer()
                                RoundedRectangle(cornerRadius: 4)
                                    .fill(AppTheme.accentBlue.opacity(0.7))
                                    .frame(height: CGFloat(Int.random(in: 4...10)) * 15)
                                Text(["M", "T", "W", "T", "F", "S", "S"][i])
                                    .font(.caption)
                                    .foregroundColor(AppTheme.textMuted)
                            }
                        }
                    }
                    .frame(height: 180)
                }
                .padding()
                .background(AppTheme.darkNavyCard)
                .clipShape(RoundedRectangle(cornerRadius: 16))
            }
            .padding()
        }
        .background(AppTheme.darkNavyBg.ignoresSafeArea())
    }
}

// MARK: - Calories Detail View
struct CaloriesDetailView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    
    var body: some View {
        ScrollView {
            VStack(spacing: 24) {
                // Header Stat
                VStack(spacing: 8) {
                    Text("\(healthViewModel.todayCalories.formatted())")
                        .font(.system(size: 48, weight: .bold, design: .rounded))
                        .foregroundColor(AppTheme.accentOrange)
                    Text("Calories Burned")
                        .font(.headline)
                        .foregroundColor(AppTheme.textSecondary)
                }
                
                // Chart Section
                VStack(alignment: .leading, spacing: 16) {
                    Text("Weekly Activity")
                        .font(.headline)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    if #available(iOS 16.0, *) {
                        Chart {
                            ForEach(0..<7, id: \.self) { index in
                                BarMark(
                                    x: .value("Day", ["M", "T", "W", "T", "F", "S", "S"][index]),
                                    y: .value("Calories", [2100, 2450, 2150, 2600, 2300, 2800, 2200][index])
                                )
                                .foregroundStyle(AppTheme.accentOrange.gradient)
                            }
                            
                            RuleMark(y: .value("Goal", 2200))
                                .foregroundStyle(AppTheme.textSecondary)
                        }
                        .frame(height: 250)
                    } else {
                        // Fallback for iOS 15
                        HStack(alignment: .bottom, spacing: 12) {
                            ForEach(0..<7, id: \.self) { index in
                                VStack {
                                    Spacer()
                                    RoundedRectangle(cornerRadius: 4)
                                        .fill(AppTheme.accentOrange)
                                        .frame(height: CGFloat([2100, 2450, 2150, 2600, 2300, 2800, 2200][index]) / 2800 * 200)
                                    Text(["M", "T", "W", "T", "F", "S", "S"][index])
                                        .font(.caption)
                                        .foregroundColor(AppTheme.textMuted)
                                }
                            }
                        }
                        .frame(height: 250)
                    }
                }
                .padding()
                .background(AppTheme.darkNavyCard)
                .clipShape(RoundedRectangle(cornerRadius: 16))
                
                // Quick Info
                HStack(spacing: 12) {
                    DetailStatItem(title: "Active", value: "850", unit: "kcal", color: AppTheme.accentOrange)
                    DetailStatItem(title: "Resting", value: "1,450", unit: "kcal", color: AppTheme.accentPurple)
                }
                
                Spacer()
            }
            .padding()
        }
        .background(AppTheme.darkNavyBg.ignoresSafeArea())
    }
}

// MARK: - Components

struct DetailStatCard: View {
    let title: String
    let value: String
    let icon: String
    let color: Color
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            HStack {
                Image(systemName: icon)
                    .foregroundColor(color)
                Spacer()
            }
            
            VStack(alignment: .leading, spacing: 4) {
                Text(value)
                    .font(.title3)
                    .fontWeight(.bold)
                    .foregroundColor(AppTheme.textPrimary)
                Text(title)
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
}

struct SleepStageRow: View {
    let stage: String
    let duration: String
    let percent: CGFloat
    let color: Color
    
    var body: some View {
        VStack(spacing: 8) {
            HStack {
                Text(stage)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textPrimary)
                Spacer()
                Text(duration)
                    .font(.subheadline)
                    .foregroundColor(AppTheme.textSecondary)
            }
            
            GeometryReader { geometry in
                ZStack(alignment: .leading) {
                    Capsule()
                        .fill(AppTheme.darkNavySurface)
                        .frame(height: 8)
                    
                    Capsule()
                        .fill(color)
                        .frame(width: geometry.size.width * percent, height: 8)
                }
            }
            .frame(height: 8)
        }
    }
}

struct DetailStatItem: View {
    let title: String
    let value: String
    let unit: String
    let color: Color
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 4) {
                Text(value)
                    .font(.title2)
                    .fontWeight(.bold)
                    .foregroundColor(color)
                Text("\(title) (\(unit))")
                    .font(.caption)
                    .foregroundColor(AppTheme.textMuted)
            }
            Spacer()
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
    }
}

// MARK: - Trend Row Card Component
struct TrendRowCard: View {
    let icon: String
    let title: String
    let value: String
    let trend: String
    let isPositive: Bool
    let color: Color
    
    var body: some View {
        HStack(spacing: 12) {
            Circle()
                .fill(color.opacity(0.2))
                .frame(width: 48, height: 48)
                .overlay(
                    Image(systemName: icon)
                        .font(.title3)
                        .foregroundColor(color)
                )
            
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .font(.subheadline)
                    .fontWeight(.medium)
                    .foregroundColor(AppTheme.textPrimary)
                Text(value)
                    .font(.caption)
                    .foregroundColor(AppTheme.textSecondary)
            }
            
            Spacer()
            
            Text(trend)
                .font(.subheadline)
                .fontWeight(.bold)
                .foregroundColor(isPositive ? AppTheme.accentGreen : AppTheme.accentRed)
                .padding(.horizontal, 12)
                .padding(.vertical, 6)
                .background((isPositive ? AppTheme.accentGreen : AppTheme.accentRed).opacity(0.15))
                .clipShape(Capsule())
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}

#Preview {
    TrendsView()
        .environmentObject(HealthViewModel.shared)
        .environmentObject(SettingsManager.shared)
}
