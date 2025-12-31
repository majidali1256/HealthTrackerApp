import SwiftUI

struct TrendsView: View {
    @EnvironmentObject var healthViewModel: HealthViewModel
    @EnvironmentObject var settingsManager: SettingsManager
    @State private var selectedDay = 6  // Today (Sunday)
    @State private var selectedPeriod = 0  // 0 = Week, 1 = Month
    
    // Week days
    let weekDays = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
    
    var body: some View {
        NavigationStack {
            ZStack {
                // Dark Navy Background
                AppTheme.darkNavyBg
                    .ignoresSafeArea()
                
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 0) {
                        // MARK: - Header
                        headerSection
                        
                        // MARK: - Period Picker
                        periodPicker
                            .padding(.top, 20)
                        
                        // MARK: - Week Day Selector
                        weekDaySelector
                            .padding(.top, 24)
                        
                        // MARK: - Sleep Insight Card
                        sleepInsightCard
                            .padding(.top, 24)
                        
                        // MARK: - Sleep Graph
                        sleepGraphSection
                            .padding(.top, 24)
                        
                        // MARK: - Stats Summary
                        statsSummary
                            .padding(.top, 24)
                        
                        // MARK: - Trend Cards
                        trendCardsSection
                            .padding(.top, 28)
                            .padding(.bottom, 100)
                    }
                }
            }
        }
        .preferredColorScheme(settingsManager.colorScheme)
    }
    
    // MARK: - Period Picker
    private var periodPicker: some View {
        Picker("Period", selection: $selectedPeriod) {
            Text("This Week").tag(0)
            Text("This Month").tag(1)
        }
        .pickerStyle(.segmented)
        .padding(.horizontal, 20)
    }
    
    // MARK: - Header Section
    private var headerSection: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text("Sleep Duration")
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(AppTheme.textPrimary)
            
            Text(currentDateRange)
                .font(.subheadline)
                .foregroundColor(AppTheme.textSecondary)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(.horizontal, 20)
        .padding(.top, 60)
    }
    
    private var currentDateRange: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM d"
        
        let calendar = Calendar.current
        let today = Date()
        let startOfWeek = calendar.date(byAdding: .day, value: -6, to: today)!
        
        return "\(formatter.string(from: startOfWeek)) - \(formatter.string(from: today)), \(calendar.component(.year, from: today))"
    }
    
    // MARK: - Week Day Selector
    private var weekDaySelector: some View {
        HStack(spacing: 8) {
            ForEach(0..<7, id: \.self) { index in
                let sleepHours = healthViewModel.weeklySleep[index]
                let barHeight = CGFloat(sleepHours / 10.0) * 50
                
                VStack(spacing: 8) {
                    Text(weekDays[index])
                        .font(.caption)
                        .fontWeight(.medium)
                        .foregroundColor(selectedDay == index ? AppTheme.textPrimary : AppTheme.textMuted)
                    
                    Text(formatSleepHours(sleepHours))
                        .font(.caption2)
                        .foregroundColor(selectedDay == index ? AppTheme.accentTeal : AppTheme.textMuted)
                    
                    // Sleep bar
                    RoundedRectangle(cornerRadius: 4)
                        .fill(selectedDay == index ? AppTheme.accentTeal : AppTheme.darkNavyCardLight)
                        .frame(width: 8, height: barHeight)
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 12)
                .background(selectedDay == index ? AppTheme.darkNavyCard : Color.clear)
                .clipShape(RoundedRectangle(cornerRadius: 12))
                .onTapGesture {
                    withAnimation(.easeInOut(duration: 0.2)) {
                        selectedDay = index
                    }
                }
            }
        }
        .padding(.horizontal, 16)
    }
    
    private func formatSleepHours(_ hours: Double) -> String {
        let h = Int(hours)
        let m = Int((hours - Double(h)) * 60)
        return "\(h)h \(m)m"
    }
    
    // MARK: - Sleep Insight Card
    private var sleepInsightCard: some View {
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
    
    // MARK: - Sleep Graph Section
    private var sleepGraphSection: some View {
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
    
    // MARK: - Stats Summary
    private var statsSummary: some View {
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
    
    // MARK: - Trend Cards Section
    private var trendCardsSection: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("All Trends")
                .font(.headline)
                .foregroundColor(AppTheme.textPrimary)
                .padding(.horizontal, 20)
            
            VStack(spacing: 12) {
                TrendRowCard(
                    icon: "figure.walk",
                    title: "Steps",
                    value: "\(healthViewModel.averageWeeklySteps.formatted()) avg",
                    trend: "+12%",
                    isPositive: true,
                    color: AppTheme.accentGreen
                )
                
                TrendRowCard(
                    icon: "heart.fill",
                    title: "Heart Rate",
                    value: "\(healthViewModel.averageWeeklyHeartRate) BPM",
                    trend: "-2%",
                    isPositive: true,
                    color: AppTheme.accentRed
                )
                
                TrendRowCard(
                    icon: "drop.fill",
                    title: "Hydration",
                    value: "\(healthViewModel.todayWaterGlasses) glasses",
                    trend: "+8%",
                    isPositive: true,
                    color: AppTheme.accentBlue
                )
                
                TrendRowCard(
                    icon: "flame.fill",
                    title: "Calories",
                    value: "\(healthViewModel.todayCalories.formatted()) kcal",
                    trend: "+5%",
                    isPositive: true,
                    color: AppTheme.accentOrange
                )
            }
            .padding(.horizontal, 20)
        }
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
