import SwiftUI

// MARK: - Health Tracker Dark Theme
// Premium dark navy color palette matching the reference design

struct AppTheme {
    // MARK: - Primary Colors
    static let darkNavyBg = Color(hex: "0A1628")
    static let darkNavyCard = Color(hex: "0F2744")
    static let darkNavyCardLight = Color(hex: "163A5F")
    static let darkNavySurface = Color(hex: "1A3A5C")
    
    // MARK: - Text Colors
    static let textPrimary = Color.white
    static let textSecondary = Color(hex: "B0BEC5")
    static let textMuted = Color(hex: "607D8B")
    
    // MARK: - Accent Colors
    static let accentTeal = Color(hex: "00BCD4")
    static let accentPurple = Color(hex: "7C4DFF")
    static let accentBlue = Color(hex: "64B5F6")
    static let accentGreen = Color(hex: "4CAF50")
    static let accentOrange = Color(hex: "FF9800")
    static let accentRed = Color(hex: "EF5350")
    
    // MARK: - Card Gradients
    static let cardTealGradient = [Color(hex: "0D3B66"), Color(hex: "1A4D7C")]
    static let cardIndigoGradient = [Color(hex: "1A237E"), Color(hex: "283593")]
    static let cardGreenGradient = [Color(hex: "004D40"), Color(hex: "00695C")]
    static let cardPurpleGradient = [Color(hex: "4A148C"), Color(hex: "6A1B9A")]
    static let cardRedGradient = [Color(hex: "B71C1C"), Color(hex: "C62828")]
    
    // MARK: - Score Ring
    static let scoreRingBg = Color(hex: "1A3A5C")
    static let scoreRingTrack = Color(hex: "0D3B66")
    static let scoreRingProgress = Color(hex: "64B5F6")
}

// MARK: - Color Hex Extension
extension Color {
    init(hex: String) {
        let hex = hex.trimmingCharacters(in: CharacterSet.alphanumerics.inverted)
        var int: UInt64 = 0
        Scanner(string: hex).scanHexInt64(&int)
        let a, r, g, b: UInt64
        switch hex.count {
        case 3: // RGB (12-bit)
            (a, r, g, b) = (255, (int >> 8) * 17, (int >> 4 & 0xF) * 17, (int & 0xF) * 17)
        case 6: // RGB (24-bit)
            (a, r, g, b) = (255, int >> 16, int >> 8 & 0xFF, int & 0xFF)
        case 8: // ARGB (32-bit)
            (a, r, g, b) = (int >> 24, int >> 16 & 0xFF, int >> 8 & 0xFF, int & 0xFF)
        default:
            (a, r, g, b) = (1, 1, 1, 0)
        }
        self.init(
            .sRGB,
            red: Double(r) / 255,
            green: Double(g) / 255,
            blue:  Double(b) / 255,
            opacity: Double(a) / 255
        )
    }
}

// MARK: - Gradient Modifiers
extension View {
    func darkCardStyle(gradient: [Color]) -> some View {
        self
            .background(
                LinearGradient(
                    colors: gradient,
                    startPoint: .topLeading,
                    endPoint: .bottomTrailing
                )
            )
            .clipShape(RoundedRectangle(cornerRadius: 20))
    }
    
    func glassCardStyle() -> some View {
        self
            .background(
                AppTheme.darkNavyCard.opacity(0.8)
            )
            .clipShape(RoundedRectangle(cornerRadius: 16))
    }
}
