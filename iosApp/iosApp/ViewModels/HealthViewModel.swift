import SwiftUI
import Combine

/// Main ViewModel for health data management
class HealthViewModel: ObservableObject {
    
    // MARK: - Published Health Metrics
    @Published var healthScore: Int = 89
    @Published var todaySteps: Int = 8245
    @Published var todaySleepHours: Double = 7.5
    @Published var sleepScore: Int = 85
    @Published var todayWaterGlasses: Int = 6
    @Published var todayCalories: Int = 1850
    @Published var lastHeartRate: Int = 72
    @Published var heartRateStatus: String = "Normal"
    
    // MARK: - Weekly Trends
    @Published var weeklySteps: [Int] = [8500, 7200, 9100, 8245, 6800, 10200, 7800]
    @Published var weeklySleep: [Double] = [8.0, 7.1, 5.0, 8.2, 7.0, 9.0, 6.6]
    @Published var weeklyHeartRate: [Int] = [68, 72, 75, 70, 73, 69, 72]
    
    // MARK: - Recent Logs
    @Published var recentLogs: [HealthLog] = []
    
    // MARK: - Goals (from SettingsManager)
    private let settingsManager = SettingsManager.shared
    
    // MARK: - Singleton
    static let shared = HealthViewModel()
    
    private init() {
        loadSampleData()
        calculateHealthScore()
    }
    
    // MARK: - Health Score Calculation
    func calculateHealthScore() {
        var score = 0
        
        // Steps (25 points max)
        let stepsGoal = settingsManager.dailyStepsGoal
        let stepsRatio = min(Double(todaySteps) / Double(stepsGoal), 1.0)
        score += Int(stepsRatio * 25)
        
        // Sleep (25 points max)
        let sleepGoal = settingsManager.sleepGoalHours
        let sleepRatio = min(todaySleepHours / sleepGoal, 1.0)
        score += Int(sleepRatio * 25)
        
        // Hydration (25 points max)
        let waterGoal = settingsManager.dailyWaterGoal
        let waterRatio = min(Double(todayWaterGlasses) / Double(waterGoal), 1.0)
        score += Int(waterRatio * 25)
        
        // Heart Rate (25 points max - in normal range 60-100)
        if lastHeartRate >= 60 && lastHeartRate <= 100 {
            score += 25
            heartRateStatus = "Normal"
        } else if lastHeartRate > 100 {
            score += 10
            heartRateStatus = "Elevated"
        } else {
            score += 10
            heartRateStatus = "Low"
        }
        
        healthScore = score
    }
    
    // MARK: - Add Water
    func addWater(glasses: Int = 1) {
        todayWaterGlasses += glasses
        addLog(type: .water, value: "\(glasses) glass\(glasses > 1 ? "es" : "")")
        calculateHealthScore()
    }
    
    // MARK: - Log Steps
    func addSteps(count: Int) {
        todaySteps += count
        addLog(type: .steps, value: "\(count.formatted()) steps")
        calculateHealthScore()
    }
    
    // MARK: - Log Sleep
    func logSleep(hours: Double) {
        todaySleepHours = hours
        addLog(type: .sleep, value: String(format: "%.1f hours", hours))
        calculateHealthScore()
    }
    
    // MARK: - Log Heart Rate
    func logHeartRate(bpm: Int) {
        lastHeartRate = bpm
        addLog(type: .heartRate, value: "\(bpm) BPM")
        calculateHealthScore()
    }
    
    // MARK: - Add Log Entry
    private func addLog(type: HealthLogType, value: String) {
        let log = HealthLog(
            id: UUID(),
            type: type,
            value: value,
            timestamp: Date()
        )
        recentLogs.insert(log, at: 0)
        
        // Keep only last 20 logs
        if recentLogs.count > 20 {
            recentLogs = Array(recentLogs.prefix(20))
        }
    }
    
    // MARK: - Load Sample Data
    private func loadSampleData() {
        recentLogs = [
            HealthLog(id: UUID(), type: .heartRate, value: "72 BPM", timestamp: Date().addingTimeInterval(-120)),
            HealthLog(id: UUID(), type: .steps, value: "1,234 steps", timestamp: Date().addingTimeInterval(-3600)),
            HealthLog(id: UUID(), type: .water, value: "1 glass", timestamp: Date().addingTimeInterval(-7200)),
            HealthLog(id: UUID(), type: .sleep, value: "7.5 hours", timestamp: Date().addingTimeInterval(-28800))
        ]
    }
    
    // MARK: - Weekly Averages
    var averageWeeklySteps: Int {
        weeklySteps.reduce(0, +) / weeklySteps.count
    }
    
    var averageWeeklySleep: Double {
        weeklySleep.reduce(0, +) / Double(weeklySleep.count)
    }
    
    var averageWeeklyHeartRate: Int {
        weeklyHeartRate.reduce(0, +) / weeklyHeartRate.count
    }
}

// MARK: - Health Log Model
struct HealthLog: Identifiable {
    let id: UUID
    let type: HealthLogType
    let value: String
    let timestamp: Date
    
    var timeAgo: String {
        let interval = Date().timeIntervalSince(timestamp)
        
        if interval < 60 {
            return "Just now"
        } else if interval < 3600 {
            let minutes = Int(interval / 60)
            return "\(minutes) min ago"
        } else if interval < 86400 {
            let hours = Int(interval / 3600)
            return "\(hours) hour\(hours > 1 ? "s" : "") ago"
        } else {
            let days = Int(interval / 86400)
            return "\(days) day\(days > 1 ? "s" : "") ago"
        }
    }
}

// MARK: - Health Log Type
enum HealthLogType {
    case heartRate
    case steps
    case water
    case sleep
    case food
    case medication
    case vitals
    
    var icon: String {
        switch self {
        case .heartRate: return "heart.fill"
        case .steps: return "figure.walk"
        case .water: return "drop.fill"
        case .sleep: return "moon.fill"
        case .food: return "fork.knife"
        case .medication: return "pills.fill"
        case .vitals: return "waveform.path.ecg"
        }
    }
    
    var title: String {
        switch self {
        case .heartRate: return "Heart Rate"
        case .steps: return "Walking"
        case .water: return "Water"
        case .sleep: return "Sleep"
        case .food: return "Food"
        case .medication: return "Medication"
        case .vitals: return "Vitals"
        }
    }
    
    var color: Color {
        switch self {
        case .heartRate: return AppTheme.accentRed
        case .steps: return AppTheme.accentGreen
        case .water: return AppTheme.accentBlue
        case .sleep: return AppTheme.accentPurple
        case .food: return AppTheme.accentOrange
        case .medication: return AppTheme.accentPurple
        case .vitals: return AppTheme.accentTeal
        }
    }
}

// MARK: - Metric Type
enum MetricType: String, Identifiable {
    case steps, sleep, water, heartRate, calories
    
    var id: String { self.rawValue }
    
    var title: String {
        switch self {
        case .steps: return "Steps"
        case .sleep: return "Sleep Analysis"
        case .water: return "Hydration"
        case .heartRate: return "Heart Rate"
        case .calories: return "Calories"
        }
    }
}
