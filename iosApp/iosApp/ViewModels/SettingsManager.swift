import SwiftUI
import Combine

/// Manages app settings with persistence using @AppStorage
class SettingsManager: ObservableObject {
    
    // MARK: - Theme Settings
    @AppStorage("isDarkMode") var isDarkMode: Bool = true
    @AppStorage("followSystemTheme") var followSystemTheme: Bool = false
    
    // MARK: - Notification Settings
    @AppStorage("notificationsEnabled") var notificationsEnabled: Bool = true
    @AppStorage("waterReminders") var waterReminders: Bool = true
    @AppStorage("medicationReminders") var medicationReminders: Bool = true
    @AppStorage("sleepReminders") var sleepReminders: Bool = false
    @AppStorage("stepGoalReminders") var stepGoalReminders: Bool = true
    
    // MARK: - Privacy Settings
    @AppStorage("shareWithDoctor") var shareWithDoctor: Bool = false
    @AppStorage("analyticsEnabled") var analyticsEnabled: Bool = true
    
    // MARK: - User Goals
    @AppStorage("dailyStepsGoal") var dailyStepsGoal: Int = 10000
    @AppStorage("dailyWaterGoal") var dailyWaterGoal: Int = 8
    @AppStorage("sleepGoalHours") var sleepGoalHours: Double = 8.0
    
    // MARK: - User Profile
    @AppStorage("userName") var userName: String = "User"
    @AppStorage("userEmail") var userEmail: String = ""
    @AppStorage("isPremiumUser") var isPremiumUser: Bool = false
    
    // MARK: - Auth State
    @AppStorage("isLoggedIn") var isLoggedIn: Bool = true  // Default true for demo
    
    // MARK: - Singleton
    static let shared = SettingsManager()
    
    private init() {}
    
    // MARK: - Sign Out
    func signOut() {
        isLoggedIn = false
        userName = "User"
        userEmail = ""
        objectWillChange.send()
    }
    
    // MARK: - Sign In (for demo)
    func signIn(name: String, email: String) {
        userName = name
        userEmail = email
        isLoggedIn = true
        objectWillChange.send()
    }
    
    // MARK: - Color Scheme Preference
    var colorScheme: ColorScheme? {
        if followSystemTheme {
            return nil // Follow system
        }
        return isDarkMode ? .dark : .light
    }
    
    // MARK: - Toggle Theme
    func toggleDarkMode() {
        isDarkMode.toggle()
        followSystemTheme = false
        objectWillChange.send()
    }
    
    // MARK: - Reset to Defaults
    func resetToDefaults() {
        isDarkMode = true
        followSystemTheme = false
        notificationsEnabled = true
        waterReminders = true
        medicationReminders = true
        sleepReminders = false
        stepGoalReminders = true
        shareWithDoctor = false
        analyticsEnabled = true
        dailyStepsGoal = 10000
        dailyWaterGoal = 8
        sleepGoalHours = 8.0
    }
}
