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
        .preferredColorScheme(settingsManager.colorScheme)
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

#Preview {
    ContentView()
}
