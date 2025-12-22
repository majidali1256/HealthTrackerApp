package com.healthtracker.shared

/**
 * Platform-specific functionality using expect/actual pattern
 */

/**
 * Get current platform name
 */
expect fun getPlatformName(): String

/**
 * Generate unique ID
 */
expect fun generateUUID(): String

/**
 * Get current timestamp in milliseconds
 */
expect fun currentTimeMillis(): Long

/**
 * Format date for display
 */
expect fun formatDate(timestamp: Long, pattern: String): String
