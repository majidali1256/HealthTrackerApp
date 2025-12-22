package com.healthtracker.shared

import platform.Foundation.*

actual fun getPlatformName(): String = "iOS"

actual fun generateUUID(): String = NSUUID().UUIDString()

actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun formatDate(timestamp: Long, pattern: String): String {
    val date = NSDate.dateWithTimeIntervalSince1970(timestamp / 1000.0)
    val formatter = NSDateFormatter()
    formatter.dateFormat = pattern
    return formatter.stringFromDate(date)
}
