import SwiftUI
import Charts

// MARK: - Metric Detail Views Container
enum MetricType: String, Identifiable {
    case steps, sleep, water, heartRate
    
    var id: String { self.rawValue }
    
    var title: String {
        switch self {
        case .steps: return "Steps"
        case .sleep: return "Sleep Analysis"
        case .water: return "Hydration"
        case .heartRate: return "Heart Rate"
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
                            ForEach(healthViewModel.weeklySteps, id: \.day) { day in
                                BarMark(
                                    x: .value("Day", day.day),
                                    y: .value("Steps", day.steps)
                                )
                                .foregroundStyle(AppTheme.cardTealGradient[0])
                                .cornerRadius(4)
                            }
                            
                            RuleMark(y: .value("Goal", 10000))
                                .lineStyle(StrokeStyle(lineWidth: 2, dash: [5, 5]))
                                .foregroundStyle(.gray.opacity(0.5))
                                .annotation(position: .leading) {
                                    Text("Goal")
                                        .font(.caption)
                                        .foregroundColor(.gray)
                                }
                        }
                        .frame(height: 250)
                    } else {
                        // Fallback for iOS 15
                        HStack(alignment: .bottom, spacing: 12) {
                            ForEach(healthViewModel.weeklySteps, id: \.day) { day in
                                VStack {
                                    Spacer()
                                    RoundedRectangle(cornerRadius: 4)
                                        .fill(AppTheme.accentTeal)
                                        .frame(height: CGFloat(day.steps) / 15000 * 200)
                                    Text(day.day)
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
                                .interpolationMethod(.catmullRom)
                                .foregroundStyle(AppTheme.accentRed)
                                .symbol(.circle)
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
                        .stroke(AppTheme.scoreRingTrack, lineWidth: 20)
                        .frame(width: 200, height: 200)
                    
                    Circle()
                        .trim(from: 0, to: CGFloat(healthViewModel.todayWaterGlasses) / CGFloat(settingsManager.dailyWaterGoal))
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
                        .frame(maxWidth: .leading)
                    
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
