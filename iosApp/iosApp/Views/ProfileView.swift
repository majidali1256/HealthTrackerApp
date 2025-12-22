import SwiftUI

struct ProfileView: View {
    var body: some View {
        NavigationStack {
            ScrollView {
                VStack(spacing: 0) {
                    // Gradient Header with Profile
                    ZStack {
                        LinearGradient(
                            colors: [Color.purple, Color.indigo],
                            startPoint: .topLeading,
                            endPoint: .bottomTrailing
                        )
                        
                        VStack(spacing: 16) {
                            // Profile Picture
                            Circle()
                                .fill(.white)
                                .frame(width: 100, height: 100)
                                .overlay(
                                    Image(systemName: "person.fill")
                                        .font(.system(size: 40))
                                        .foregroundColor(.purple)
                                )
                                .shadow(radius: 10)
                            
                            VStack(spacing: 4) {
                                Text("John Doe")
                                    .font(.title2)
                                    .fontWeight(.bold)
                                    .foregroundColor(.white)
                                Text("john.doe@email.com")
                                    .font(.subheadline)
                                    .foregroundColor(.white.opacity(0.8))
                            }
                        }
                        .padding(.top, 60)
                        .padding(.bottom, 40)
                    }
                    .frame(height: 280)
                    
                    // Stats Cards
                    HStack(spacing: 12) {
                        StatCard(value: "72", label: "Avg Heart", color: .red)
                        StatCard(value: "8.2K", label: "Avg Steps", color: .green)
                        StatCard(value: "7.5h", label: "Avg Sleep", color: .purple)
                    }
                    .padding(.horizontal)
                    .offset(y: -30)
                    
                    // Settings Sections
                    VStack(spacing: 16) {
                        // Account Section
                        SettingsSection(title: "Account") {
                            SettingsRow(icon: "person.fill", title: "Edit Profile", color: .blue)
                            SettingsRow(icon: "heart.text.square.fill", title: "Medical ID", color: .green)
                            SettingsRow(icon: "phone.fill", title: "Emergency Contacts", color: .red)
                        }
                        
                        // Preferences Section
                        SettingsSection(title: "Preferences") {
                            SettingsToggleRow(icon: "bell.fill", title: "Notifications", color: .purple, isOn: .constant(true))
                            SettingsRow(icon: "lock.fill", title: "Privacy Settings", color: .cyan)
                            SettingsRow(icon: "questionmark.circle.fill", title: "Help & Support", color: .green)
                        }
                        
                        // Sign Out Button
                        Button(action: { /* Sign Out */ }) {
                            HStack {
                                Spacer()
                                Text("Sign Out")
                                    .fontWeight(.semibold)
                                    .foregroundColor(.red)
                                Spacer()
                            }
                            .padding()
                            .background(Color(.systemBackground))
                            .cornerRadius(12)
                            .overlay(
                                RoundedRectangle(cornerRadius: 12)
                                    .stroke(Color.red, lineWidth: 1)
                            )
                        }
                        .padding(.horizontal)
                        
                        Text("Health Tracker v1.0")
                            .font(.caption)
                            .foregroundColor(.secondary)
                            .padding(.top, 8)
                    }
                    .padding(.top, -10)
                    .padding(.bottom, 100)
                }
            }
            .background(Color(.systemGroupedBackground))
            .ignoresSafeArea(edges: .top)
        }
    }
}

struct StatCard: View {
    let value: String
    let label: String
    let color: Color
    
    var body: some View {
        VStack(spacing: 4) {
            Text(value)
                .font(.title2)
                .fontWeight(.bold)
                .foregroundColor(color)
            Text(label)
                .font(.caption)
                .foregroundColor(.secondary)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 16)
        .background(Color(.systemBackground))
        .cornerRadius(16)
        .shadow(color: .black.opacity(0.1), radius: 5, y: 2)
    }
}

struct SettingsSection<Content: View>: View {
    let title: String
    @ViewBuilder let content: () -> Content
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.subheadline)
                .fontWeight(.semibold)
                .foregroundColor(.secondary)
                .padding(.horizontal)
            
            VStack(spacing: 1) {
                content()
            }
            .background(Color(.systemBackground))
            .cornerRadius(12)
            .padding(.horizontal)
        }
    }
}

struct SettingsRow: View {
    let icon: String
    let title: String
    let color: Color
    
    var body: some View {
        Button(action: {}) {
            HStack {
                Circle()
                    .fill(color.opacity(0.2))
                    .frame(width: 40, height: 40)
                    .overlay(
                        Image(systemName: icon)
                            .foregroundColor(color)
                    )
                
                Text(title)
                    .foregroundColor(.primary)
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            .padding()
        }
    }
}

struct SettingsToggleRow: View {
    let icon: String
    let title: String
    let color: Color
    @Binding var isOn: Bool
    
    var body: some View {
        HStack {
            Circle()
                .fill(color.opacity(0.2))
                .frame(width: 40, height: 40)
                .overlay(
                    Image(systemName: icon)
                        .foregroundColor(color)
                )
            
            Text(title)
            
            Spacer()
            
            Toggle("", isOn: $isOn)
                .labelsHidden()
        }
        .padding()
    }
}

#Preview {
    ProfileView()
}
