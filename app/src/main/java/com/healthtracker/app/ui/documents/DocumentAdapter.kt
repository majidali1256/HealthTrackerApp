package com.healthtracker.app.ui.documents

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthtracker.app.data.local.entities.Document
import com.healthtracker.app.data.local.entities.DocumentCategory
import com.healthtracker.app.databinding.ItemDocumentBinding
import java.text.SimpleDateFormat
import java.util.*

class DocumentAdapter(
    private val onDocumentClick: (Document) -> Unit,
    private val onDeleteClick: (Document) -> Unit
) : ListAdapter<Document, DocumentAdapter.DocumentViewHolder>(DocumentDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        val binding = ItemDocumentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DocumentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        holder.bind(getItem(position), onDocumentClick, onDeleteClick)
    }

    class DocumentViewHolder(
        private val binding: ItemDocumentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        fun bind(
            document: Document,
            onDocumentClick: (Document) -> Unit,
            onDeleteClick: (Document) -> Unit
        ) {
            binding.tvDocumentName.text = document.title
            binding.tvDocumentDate.text = dateFormatter.format(document.uploadedAt)
            
            // Set icon based on category - using string comparison
            val iconRes = when (document.category) {
                DocumentCategory.LAB_RESULT -> android.R.drawable.ic_menu_view
                DocumentCategory.PRESCRIPTION -> android.R.drawable.ic_menu_agenda
                DocumentCategory.IMAGING -> android.R.drawable.ic_menu_gallery
                DocumentCategory.INSURANCE -> android.R.drawable.ic_menu_info_details
                else -> android.R.drawable.ic_menu_save
            }
            binding.ivDocumentIcon.setImageResource(iconRes)
            
            // Sync status
            binding.ivSyncStatus.setImageResource(
                if (document.isSynced) android.R.drawable.ic_menu_upload
                else android.R.drawable.ic_popup_sync
            )
            
            binding.root.setOnClickListener { onDocumentClick(document) }
            binding.btnDelete.setOnClickListener { onDeleteClick(document) }
        }
    }
}

class DocumentDiffCallback : DiffUtil.ItemCallback<Document>() {
    override fun areItemsTheSame(oldItem: Document, newItem: Document) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Document, newItem: Document) = oldItem == newItem
}


