import SwiftUI

struct TrendsView: View {
    @State private var selectedPeriod = 0
    
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
                        
                        VStack {
                            Text("Health Trends")
                                .font(.title2)
                                .fontWeight(.bold)
                                .foregroundColor(.white)
                        }
                        .padding(.top, 60)
                        .padding(.bottom, 30)
                    }
                    .frame(height: 120)
                    
                    // Period Picker
                    Picker("Period", selection: $selectedPeriod) {
                        Text("This Week").tag(0)
                        Text("This Month").tag(1)
                    }
                    .pickerStyle(.segmented)
                    .padding()
                    
                    // Trend Cards
                    VStack(spacing: 16) {
                        TrendCard(
                            title: "Average Steps",
                            value: "8,245",
                            trend: "+12%",
                            isPositive: true,
                            gradient: [Color.green, Color.teal]
                        )
                        
                        TrendCard(
                            title: "Average Sleep",
                            value: "7h 32m",
                            trend: "+5%",
                            isPositive: true,
                            gradient: [Color.purple, Color.indigo]
                        )
                        
                        TrendCard(
                            title: "Average Heart Rate",
                            value: "72 BPM",
                            trend: "-2%",
                            isPositive: true,
                            gradient: [Color.red, Color.pink]
                        )
                        
                        TrendCard(
                            title: "Average Hydration",
                            value: "6 glasses",
                            trend: "+8%",
                            isPositive: true,
                            gradient: [Color.cyan, Color.blue]
                        )
                    }
                    .padding()
                }
            }
            .background(Color(.systemGroupedBackground))
            .ignoresSafeArea(edges: .top)
        }
    }
}

struct TrendCard: View {
    let title: String
    let value: String
    let trend: String
    let isPositive: Bool
    let gradient: [Color]
    
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 20)
                .fill(LinearGradient(colors: gradient, startPoint: .topLeading, endPoint: .bottomTrailing))
                .shadow(color: gradient.first?.opacity(0.3) ?? .black.opacity(0.2), radius: 10, y: 5)
            
            HStack {
                VStack(alignment: .leading, spacing: 8) {
                    Text(title)
                        .font(.subheadline)
                        .foregroundColor(.white.opacity(0.8))
                    Text(value)
                        .font(.system(size: 36, weight: .bold))
                        .foregroundColor(.white)
                }
                
                Spacer()
                
                Text(trend)
                    .font(.headline)
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                    .padding(.horizontal, 12)
                    .padding(.vertical, 6)
                    .background(.white.opacity(0.2))
                    .cornerRadius(12)
            }
            .padding()
        }
        .frame(height: 100)
    }
}

#Preview {
    TrendsView()
}
