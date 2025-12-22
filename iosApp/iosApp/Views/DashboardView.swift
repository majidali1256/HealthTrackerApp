import SwiftUI

struct DashboardView: View {
    @State private var heartRate = 72
    @State private var steps = 8245
    @State private var sleepHours = 7.5
    @State private var hydrationGlasses = 4
    
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 0) {
                    // Gradient Header
                    ZStack {
                        LinearGradient(
                            colors: [Color.purple, Color.indigo],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                        
                        VStack(alignment: .leading, spacing: 8) {
                            HStack {
                                VStack(alignment: .leading) {
                                    Text("Good Morning")
                                        .font(.subheadline)
                                        .foregroundColor(.white.opacity(0.8))
                                    Text("John")
                                        .font(.title)
                                        .fontWeight(.bold)
                                        .foregroundColor(.white)
                                }
                                Spacer()
                                Circle()
                                    .fill(.white.opacity(0.2))
                                    .frame(width: 48, height: 48)
                                    .overlay(
                                        Image(systemName: "person.fill")
                                            .foregroundColor(.white)
                                    )
                            }
                            .padding(.horizontal)
                            .padding(.top, 60)
                            .padding(.bottom, 40)
                        }
                    }
                    .frame(height: 180)
                    
                    // Metrics Grid
                    LazyVGrid(columns: [
                        GridItem(.flexible()),
                        GridItem(.flexible())
                    ], spacing: 16) {
                        // Heart Rate Card
                        MetricCard(
                            title: "Heart Rate",
                            value: "\(heartRate)",
                            unit: "BPM",
                            icon: "heart.fill",
                            gradient: [Color.red, Color.pink]
                        )
                        
                        // Steps Card
                        MetricCard(
                            title: "Steps",
                            value: "\(steps)",
                            unit: "steps",
                            icon: "figure.walk",
                            gradient: [Color.green, Color.teal]
                        )
                        
                        // Sleep Card
                        MetricCard(
                            title: "Sleep",
                            value: String(format: "%.1f", sleepHours),
                            unit: "hours",
                            icon: "moon.fill",
                            gradient: [Color.purple, Color.indigo]
                        )
                        
                        // Hydration Card
                        MetricCard(
                            title: "Hydration",
                            value: "\(hydrationGlasses)",
                            unit: "glasses",
                            icon: "drop.fill",
                            gradient: [Color.cyan, Color.blue]
                        )
                    }
                    .padding()
                    .offset(y: -30)
                    
                    // Recent Activity Section
                    VStack(alignment: .leading, spacing: 12) {
                        HStack {
                            Text("Recent Activity")
                                .font(.headline)
                            Spacer()
                            Text("View All")
                                .font(.subheadline)
                                .foregroundColor(.purple)
                        }
                        .padding(.horizontal)
                        
                        VStack(spacing: 8) {
                            ActivityRow(icon: "heart.fill", title: "Heart Rate", value: "72 BPM", time: "2 min ago", color: .red)
                            ActivityRow(icon: "figure.walk", title: "Walking", value: "1,234 steps", time: "1 hour ago", color: .green)
                            ActivityRow(icon: "drop.fill", title: "Water", value: "1 glass", time: "2 hours ago", color: .blue)
                        }
                        .padding(.horizontal)
                    }
                    .padding(.top, -20)
                }
            }
            .background(Color(.systemGroupedBackground))
            .ignoresSafeArea(edges: .top)
        }
    }
}

struct MetricCard: View {
    let title: String
    let value: String
    let unit: String
    let icon: String
    let gradient: [Color]
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .fill(LinearGradient(colors: gradient, startPoint: .topLeading, endPoint: .bottomTrailing))
                .shadow(color: gradient.first?.opacity(0.4) ?? .black.opacity(0.2), radius: 10, y: 5)
            
            VStack(alignment: .leading, spacing: 12) {
                HStack {
                    Image(systemName: icon)
                        .font(.title2)
                        .foregroundColor(.white.opacity(0.9))
                    Spacer()
                }
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(value)
                        .font(.system(size: 32, weight: .bold))
                        .foregroundColor(.white)
                    Text(unit)
                        .font(.caption)
                        .foregroundColor(.white.opacity(0.8))
                }
                
                Text(title)
                    .font(.subheadline)
                    .foregroundColor(.white.opacity(0.9))
            }
            .padding()
        }
        .frame(height: 150)
    }
}

struct ActivityRow: View {
    let icon: String
    let title: String
    let value: String
    let time: String
    let color: Color
    
    var body: some View {
        HStack {
            Circle()
                .fill(color.opacity(0.2))
                .frame(width: 44, height: 44)
                .overlay(
                    Image(systemName: icon)
                        .foregroundColor(color)
                )
            
            VStack(alignment: .leading) {
                Text(title)
                    .font(.subheadline)
                    .fontWeight(.medium)
                Text(time)
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            
            Spacer()
            
            Text(value)
                .font(.subheadline)
                .fontWeight(.semibold)
        }
        .padding()
        .background(Color(.systemBackground))
        .cornerRadius(12)
    }
}

#Preview {
    DashboardView()
}
