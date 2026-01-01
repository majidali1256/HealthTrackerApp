import SwiftUI

struct ContentView: View {
    @StateObject private var settingsManager = SettingsManager.shared
    @StateObject private var healthViewModel = HealthViewModel.shared
    @State private var selectedTab = 0
    
    init() {
        // Configure dark tab bar appearance
        configureTabBarAppearance()
    }
    
    var body: some View {
        Group {
            if settingsManager.isOnboardingCompleted {
                mainTabView
            } else {
                OnboardingView()
                    .environmentObject(settingsManager)
            }
        }
        .preferredColorScheme(settingsManager.colorScheme)
        .onAppear {
            // Ensure proper initial state
            if !settingsManager.isOnboardingCompleted {
                // Any specific reset logic if needed
            }
        }
    }
    
    private var mainTabView: some View {
        TabView(selection: $selectedTab) {
            DashboardView()
                .environmentObject(healthViewModel)
                .environmentObject(settingsManager)
                .tabItem {
                    Image(systemName: selectedTab == 0 ? "house.fill" : "house")
                    Text("Home")
                }
                .tag(0)
            
            TrendsView()
                .environmentObject(healthViewModel)
                .environmentObject(settingsManager)
                .tabItem {
                    Image(systemName: selectedTab == 1 ? "chart.xyaxis.line" : "chart.line.uptrend.xyaxis")
                    Text("Summary")
                }
                .tag(1)
            
            ToolsView()
                .environmentObject(healthViewModel)
                .environmentObject(settingsManager)
                .tabItem {
                    Image(systemName: selectedTab == 2 ? "wrench.and.screwdriver.fill" : "wrench.and.screwdriver")
                    Text("Tools")
                }
                .tag(2)
            
            ProfileView()
                .environmentObject(healthViewModel)
                .environmentObject(settingsManager)
                .tabItem {
                    Image(systemName: selectedTab == 3 ? "person.fill" : "person")
                    Text("Profile")
                }
                .tag(3)
        }
        .tint(AppTheme.accentTeal)
    }
    
    private func configureTabBarAppearance() {
        let appearance = UITabBarAppearance()
        appearance.configureWithOpaqueBackground()
        appearance.backgroundColor = UIColor(AppTheme.darkNavyCard)
        
        appearance.stackedLayoutAppearance.selected.iconColor = UIColor(AppTheme.accentTeal)
        appearance.stackedLayoutAppearance.selected.titleTextAttributes = [.foregroundColor: UIColor(AppTheme.accentTeal)]
        
        appearance.stackedLayoutAppearance.normal.iconColor = UIColor(AppTheme.textMuted)
        appearance.stackedLayoutAppearance.normal.titleTextAttributes = [.foregroundColor: UIColor(AppTheme.textMuted)]
        
        UITabBar.appearance().standardAppearance = appearance
        UITabBar.appearance().scrollEdgeAppearance = appearance
    }
}

// MARK: - Onboarding View
struct OnboardingView: View {
    @EnvironmentObject var settingsManager: SettingsManager
    @State private var name = ""
    @State private var age = ""
    @State private var weight = ""
    @State private var height = ""
    @State private var currentStep = 0
    
    var body: some View {
        ZStack {
            AppTheme.darkNavyBg.ignoresSafeArea()
            
            VStack(spacing: 30) {
                Spacer()
                
                // Header
                VStack(spacing: 16) {
                    ZStack {
                        Circle()
                            .fill(LinearGradient(colors: AppTheme.cardTealGradient, startPoint: .topLeading, endPoint: .bottomTrailing))
                            .frame(width: 120, height: 120)
                        Image(systemName: "heart.text.square.fill")
                            .font(.system(size: 60))
                            .foregroundColor(.white)
                    }
                    .shadow(color: AppTheme.accentTeal.opacity(0.5), radius: 20)
                    
                    Text("Welcome to\nHealth Tracker")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .foregroundColor(AppTheme.textPrimary)
                    
                    Text("Let's set up your profile to personalize your health journey.")
                        .font(.body)
                        .multilineTextAlignment(.center)
                        .foregroundColor(AppTheme.textSecondary)
                        .padding(.horizontal, 40)
                }
                
                // Form Fields
                VStack(spacing: 20) {
                    CustomTextField(icon: "person.fill", placeholder: "Name", text: $name)
                    
                    HStack(spacing: 16) {
                        CustomTextField(icon: "calendar", placeholder: "Age", text: $age, keyboardType: .numberPad)
                        CustomTextField(icon: "scalemass.fill", placeholder: "Weight (kg)", text: $weight, keyboardType: .decimalPad)
                    }
                    
                    CustomTextField(icon: "ruler.fill", placeholder: "Height (cm)", text: $height, keyboardType: .decimalPad)
                }
                .padding(.horizontal, 32)
                .padding(.top, 20)
                
                Spacer()
                
                // Action Button
                Button(action: completeSetup) {
                    HStack {
                        Text("Get Started")
                            .font(.headline)
                        Image(systemName: "arrow.right")
                    }
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(isFormValid ? AppTheme.accentTeal : AppTheme.darkNavyCardLight)
                    .clipShape(RoundedRectangle(cornerRadius: 16))
                }
                .disabled(!isFormValid)
                .padding(.horizontal, 32)
                .padding(.bottom, 50)
            }
        }
        .preferredColorScheme(.dark)
    }
    
    var isFormValid: Bool {
        !name.isEmpty && !age.isEmpty && !weight.isEmpty && !height.isEmpty
    }
    
    func completeSetup() {
        withAnimation {
            settingsManager.completeOnboarding(name: name, age: age, weight: weight, height: height)
        }
    }
}

struct CustomTextField: View {
    let icon: String
    let placeholder: String
    @Binding var text: String
    var keyboardType: UIKeyboardType = .default
    
    var body: some View {
        HStack {
            Image(systemName: icon)
                .foregroundColor(AppTheme.accentTeal)
                .frame(width: 24)
            
            TextField(placeholder, text: $text)
                .foregroundColor(AppTheme.textPrimary)
                .keyboardType(keyboardType)
        }
        .padding()
        .background(AppTheme.darkNavyCard)
        .clipShape(RoundedRectangle(cornerRadius: 12))
        .overlay(
            RoundedRectangle(cornerRadius: 12)
                .stroke(AppTheme.darkNavyCardLight, lineWidth: 1)
        )
    }
}

#Preview {
    ContentView()
}
