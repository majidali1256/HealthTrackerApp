package com.healthtracker.app.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthtracker.app.R
import com.healthtracker.app.data.local.entities.HealthLog
import com.healthtracker.app.data.local.entities.HealthLogType
import com.healthtracker.app.databinding.ItemHealthLogBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * RecyclerView adapter for health logs on the dashboard.
 * Displays recent health entries with icon, title, time, and value.
 */
class HealthLogAdapter : ListAdapter<HealthLog, HealthLogAdapter.HealthLogViewHolder>(HealthLogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthLogViewHolder {
        val binding = ItemHealthLogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HealthLogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HealthLogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class HealthLogViewHolder(
        private val binding: ItemHealthLogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(healthLog: HealthLog) {
            // Set title based on type
            binding.tvLogTitle.text = when (healthLog.type) {
                HealthLogType.NUTRITION -> healthLog.title ?: "Nutrition"
                HealthLogType.HYDRATION -> "Water"
                HealthLogType.SYMPTOM -> healthLog.title ?: "Symptom"
                HealthLogType.EXERCISE -> healthLog.title ?: "Exercise"
                HealthLogType.MEDICATION -> healthLog.title ?: "Medication"
                else -> healthLog.title ?: "Health Log"
            }

            // Set icon based on type
            val iconRes = when (healthLog.type) {
                HealthLogType.NUTRITION -> android.R.drawable.ic_menu_my_calendar
                HealthLogType.HYDRATION -> android.R.drawable.ic_dialog_info
                HealthLogType.SYMPTOM -> android.R.drawable.ic_menu_help
                HealthLogType.EXERCISE -> android.R.drawable.ic_media_play
                HealthLogType.MEDICATION -> android.R.drawable.ic_menu_agenda
                else -> android.R.drawable.ic_menu_info_details
            }
            binding.ivLogIcon.setImageResource(iconRes)

            // Set value with unit
            binding.tvLogValue.text = formatValue(healthLog)

            // Set time (relative)
            binding.tvLogTime.text = getRelativeTime(healthLog.timestamp)

            // Apply lift animation on touch
            binding.cardHealthLog.setOnTouchListener { v, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        v.animate()
                            .scaleX(1.02f)
                            .scaleY(1.02f)
                            .translationZ(4f)
                            .setDuration(100)
                            .start()
                    }
                    android.view.MotionEvent.ACTION_UP,
                    android.view.MotionEvent.ACTION_CANCEL -> {
                        v.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .translationZ(0f)
                            .setDuration(100)
                            .start()
                        v.performClick()
                    }
                }
                true
            }
        }

        private fun formatValue(healthLog: HealthLog): String {
            return when (healthLog.type) {
                HealthLogType.HYDRATION -> "${healthLog.value.toInt()} glasses"
                HealthLogType.NUTRITION -> "${healthLog.value.toInt()} kcal"
                HealthLogType.EXERCISE -> "${healthLog.durationMinutes ?: healthLog.value.toInt()} min"
                else -> "${healthLog.value} ${healthLog.unit ?: ""}"
            }
        }

        private fun getRelativeTime(date: Date): String {
            val now = System.currentTimeMillis()
            val diff = now - date.time

            return when {
                diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
                diff < TimeUnit.HOURS.toMillis(1) -> {
                    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
                    "$minutes min ago"
                }
                diff < TimeUnit.DAYS.toMillis(1) -> {
                    val hours = TimeUnit.MILLISECONDS.toHours(diff)
                    "$hours ${if (hours == 1L) "hour" else "hours"} ago"
                }
                diff < TimeUnit.DAYS.toMillis(2) -> "Yesterday"
                else -> {
                    SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
                }
            }
        }
    }
}

/**
 * DiffUtil callback for efficient list updates
 */
class HealthLogDiffCallback : DiffUtil.ItemCallback<HealthLog>() {
    override fun areItemsTheSame(oldItem: HealthLog, newItem: HealthLog): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HealthLog, newItem: HealthLog): Boolean {
        return oldItem == newItem
    }
}
