import SwiftUI

struct ToolsView: View {
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
                            Text("Tools")
                                .font(.title2)
                                .fontWeight(.bold)
                                .foregroundColor(.white)
                        }
                        .padding(.top, 60)
                        .padding(.bottom, 30)
                    }
                    .frame(height: 120)
                    
                    // Emergency SOS Card
                    Button(action: { /* Emergency SOS */ }) {
                        HStack {
                            Circle()
                                .fill(.white.opacity(0.2))
                                .frame(width: 56, height: 56)
                                .overlay(
                                    Image(systemName: "exclamationmark.triangle.fill")
                                        .font(.title2)
                                        .foregroundColor(.white)
                                )
                            
                            VStack(alignment: .leading, spacing: 4) {
                                Text("Emergency SOS")
                                    .font(.headline)
                                    .foregroundColor(.white)
                                Text("Tap to share GPS with contacts")
                                    .font(.caption)
                                    .foregroundColor(.white.opacity(0.8))
                            }
                            
                            Spacer()
                        }
                        .padding()
                        .background(
                            LinearGradient(colors: [Color.red, Color.pink], startPoint: .topLeading, endPoint: .bottomTrailing)
                        )
                        .cornerRadius(20)
                        .shadow(color: .red.opacity(0.4), radius: 10, y: 5)
                    }
                    .padding()
                    .offset(y: -30)
                    
                    // Tools Grid
                    Text("Health Tools")
                        .font(.headline)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(.horizontal)
                        .padding(.top, -10)
                    
                    LazyVGrid(columns: [
                        GridItem(.flexible()),
                        GridItem(.flexible())
                    ], spacing: 16) {
                        ToolCard(icon: "doc.text.fill", title: "Documents", gradient: [.purple, .indigo])
                        ToolCard(icon: "stethoscope", title: "Symptoms", gradient: [.cyan, .blue])
                        ToolCard(icon: "fork.knife", title: "Food Log", gradient: [.green, .teal])
                        ToolCard(icon: "pills.fill", title: "Medications", gradient: [.orange, .red])
                        ToolCard(icon: "chart.line.uptrend.xyaxis", title: "Trends", gradient: [.blue, .indigo])
                        ToolCard(icon: "square.and.arrow.up", title: "Export", gradient: [.gray, .black])
                    }
                    .padding()
                }
            }
            .background(Color(.systemGroupedBackground))
            .ignoresSafeArea(edges: .top)
        }
    }
}

struct ToolCard: View {
    let icon: String
    let title: String
    let gradient: [Color]
    
    var body: some View {
        Button(action: {}) {
            VStack(spacing: 12) {
                Circle()
                    .fill(.white.opacity(0.2))
                    .frame(width: 48, height: 48)
                    .overlay(
                        Image(systemName: icon)
                            .font(.title2)
                            .foregroundColor(.white)
                    )
                
                Text(title)
                    .font(.subheadline)
                    .fontWeight(.semibold)
                    .foregroundColor(.white)
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 24)
            .background(
                LinearGradient(colors: gradient, startPoint: .topLeading, endPoint: .bottomTrailing)
            )
            .cornerRadius(20)
            .shadow(color: gradient.first?.opacity(0.3) ?? .black.opacity(0.2), radius: 8, y: 4)
        }
    }
}

#Preview {
    ToolsView()
}
