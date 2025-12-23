package com.healthtracker.app.utils

import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.core.content.FileProvider
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility class for generating and sharing PDF health reports.
 * Creates professional-looking health summaries for users.
 */
@Singleton
class PdfGenerator @Inject constructor(
    private val context: Context
) {
    
    private val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    private val primaryColor = DeviceRgb(79, 70, 229) // Primary purple
    
    /**
     * Generate a weekly health report PDF
     */
    fun generateWeeklyReport(
        userName: String,
        avgHeartRate: Int,
        avgSteps: Int,
        avgSleepHours: Float,
        avgHydration: Int,
        avgCalories: Int,
        startDate: Date,
        endDate: Date
    ): File? {
        return try {
            val fileName = "HealthReport_${System.currentTimeMillis()}.pdf"
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                fileName
            )
            
            val writer = PdfWriter(file)
            val pdf = PdfDocument(writer)
            val document = Document(pdf)
            
            // Title
            document.add(
                Paragraph("Health Tracker Report")
                    .setFontSize(24f)
                    .setBold()
                    .setFontColor(primaryColor)
                    .setTextAlignment(TextAlignment.CENTER)
            )
            
            // Subtitle with date range
            document.add(
                Paragraph("${dateFormat.format(startDate)} - ${dateFormat.format(endDate)}")
                    .setFontSize(12f)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f)
            )
            
            // User name
            document.add(
                Paragraph("Report for: $userName")
                    .setFontSize(14f)
                    .setBold()
                    .setMarginBottom(15f)
            )
            
            // Summary section
            document.add(
                Paragraph("Weekly Summary")
                    .setFontSize(16f)
                    .setBold()
                    .setFontColor(primaryColor)
                    .setMarginBottom(10f)
            )
            
            // Create summary table
            val table = Table(UnitValue.createPercentArray(floatArrayOf(50f, 50f)))
                .useAllAvailableWidth()
            
            // Add rows
            addTableRow(table, "❤️ Average Heart Rate", "$avgHeartRate BPM")
            addTableRow(table, "👟 Average Daily Steps", formatNumber(avgSteps))
            addTableRow(table, "💤 Average Sleep", String.format("%.1f hours", avgSleepHours))
            addTableRow(table, "💧 Average Hydration", "$avgHydration glasses/day")
            addTableRow(table, "🔥 Calories Burned", formatNumber(avgCalories))
            
            document.add(table)
            
            // Health tips section
            document.add(
                Paragraph("\nHealth Insights")
                    .setFontSize(16f)
                    .setBold()
                    .setFontColor(primaryColor)
                    .setMarginTop(20f)
                    .setMarginBottom(10f)
            )
            
            // Generate insights based on data
            val insights = mutableListOf<String>()
            
            if (avgHeartRate in 60..100) {
                insights.add("✅ Your heart rate is within the normal range.")
            } else if (avgHeartRate > 100) {
                insights.add("⚠️ Your heart rate is elevated. Consider consulting a doctor.")
            }
            
            if (avgSteps >= 10000) {
                insights.add("✅ Excellent! You're meeting the 10,000 steps goal.")
            } else if (avgSteps >= 7000) {
                insights.add("👍 Good activity level. Try to reach 10,000 steps.")
            } else {
                insights.add("📈 Try to increase your daily activity.")
            }
            
            if (avgSleepHours >= 7f) {
                insights.add("✅ Great sleep duration. Keep it up!")
            } else {
                insights.add("💤 Consider getting more sleep (7-9 hours recommended).")
            }
            
            if (avgHydration >= 8) {
                insights.add("✅ Excellent hydration! You're meeting your water goals.")
            } else {
                insights.add("💧 Try to drink at least 8 glasses of water daily.")
            }
            
            for (insight in insights) {
                document.add(
                    Paragraph(insight)
                        .setFontSize(11f)
                        .setMarginLeft(10f)
                )
            }
            
            // Footer
            document.add(
                Paragraph("\nGenerated by Health Tracker App on ${dateFormat.format(Date())}")
                    .setFontSize(9f)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30f)
            )
            
            document.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    /**
     * Share a PDF file using Android share intent
     */
    fun sharePdfReport(file: File): Intent {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        
        return Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "My Health Report")
            putExtra(Intent.EXTRA_TEXT, "Here's my weekly health report from Health Tracker app.")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }
    
    /**
     * Generate and share a weekly health report in one step
     */
    fun generateAndShare(
        userName: String,
        avgHeartRate: Int,
        avgSteps: Int,
        avgSleepHours: Float,
        avgHydration: Int,
        avgCalories: Int,
        startDate: Date,
        endDate: Date
    ): Intent? {
        val file = generateWeeklyReport(
            userName, avgHeartRate, avgSteps, avgSleepHours, 
            avgHydration, avgCalories, startDate, endDate
        )
        
        return file?.let { sharePdfReport(it) }
    }
    
    private fun addTableRow(table: Table, label: String, value: String) {
        table.addCell(
            Cell().add(Paragraph(label).setFontSize(12f))
                .setBorder(null)
                .setPaddingBottom(8f)
        )
        table.addCell(
            Cell().add(Paragraph(value).setFontSize(12f).setBold())
                .setBorder(null)
                .setPaddingBottom(8f)
                .setTextAlignment(TextAlignment.RIGHT)
        )
    }
    
    private fun formatNumber(number: Int): String {
        return String.format("%,d", number)
    }
}
