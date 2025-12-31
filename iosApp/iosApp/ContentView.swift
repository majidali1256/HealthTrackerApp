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
            if settingsManager.isLoggedIn {
                mainTabView
            } else {
                LoginView()
                    .environmentObject(settingsManager)
            }
        }
        .preferredColorScheme(settingsManager.colorScheme)
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

// MARK: - Login View
struct LoginView: View {
    @EnvironmentObject var settingsManager: SettingsManager
    @State private var email = ""
    @State private var password = ""
    @State private var name = ""
    @State private var isSignUp = false
    
    var body: some View {
        ZStack {
            AppTheme.darkNavyBg.ignoresSafeArea()
            
            VStack(spacing: 32) {
                Spacer()
                
                // Logo
                ZStack {
                    Circle()
                        .fill(LinearGradient(colors: AppTheme.cardTealGradient, startPoint: .topLeading, endPoint: .bottomTrailing))
                        .frame(width: 100, height: 100)
                    Image(systemName: "heart.fill")
                        .font(.system(size: 40))
                        .foregroundColor(.white)
                }
                .shadow(color: AppTheme.accentTeal.opacity(0.5), radius: 20)
                
                VStack(spacing: 8) {
                    Text("Health Tracker")
                        .font(.largeTitle)
                        .fontWeight(.bold)
                        .foregroundColor(AppTheme.textPrimary)
                    Text(isSignUp ? "Create your account" : "Welcome back")
                        .font(.subheadline)
                        .foregroundColor(AppTheme.textSecondary)
                }
                
                VStack(spacing: 16) {
                    if isSignUp {
                        TextField("Name", text: $name)
                            .textFieldStyle(.roundedBorder)
                            .textContentType(.name)
                    }
                    
                    TextField("Email", text: $email)
                        .textFieldStyle(.roundedBorder)
                        .textContentType(.emailAddress)
                        .keyboardType(.emailAddress)
                        .autocapitalization(.none)
                    
                    SecureField("Password", text: $password)
                        .textFieldStyle(.roundedBorder)
                        .textContentType(isSignUp ? .newPassword : .password)
                }
                .padding(.horizontal, 32)
                
                Button(action: handleAuth) {
                    Text(isSignUp ? "Sign Up" : "Sign In")
                        .font(.headline)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(AppTheme.accentTeal)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                .padding(.horizontal, 32)
                
                Button(action: { isSignUp.toggle() }) {
                    Text(isSignUp ? "Already have an account? Sign In" : "Don't have an account? Sign Up")
                        .font(.subheadline)
                        .foregroundColor(AppTheme.accentTeal)
                }
                
                Spacer()
            }
        }
        .preferredColorScheme(.dark)
    }
    
    private func handleAuth() {
        let userName = isSignUp ? name : (email.components(separatedBy: "@").first ?? "User")
        settingsManager.signIn(name: userName, email: email)
    }
}

#Preview {
    ContentView()
}
