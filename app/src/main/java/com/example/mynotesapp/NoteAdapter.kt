package com.example.mynotesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.databinding.ItemNoteBinding
import com.example.mynotesapp.entity.Note

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    // getter untuk arraylist-nya
    var listNotes = ArrayList<Note>()  // objek linkedlist bernama listNotes
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
    override fun getItemCount(): Int = this.listNotes.size

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(note: Note) {
            binding.tvItemTitle.text = note.title
            binding.tvItemDate.text = note.date
            binding.tvItemDescription.text = note.description
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemClicked(note, adapterPosition)
            }
        }
    }

    // setiap CardView bisa diklik untuk mengarah ke halaman NoteAddUpdateActivity
    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int?)
    }

    //CRUD
    fun addItem(note: Note) {
        this.listNotes.add(note)
        // animasi jika ada data baru
        notifyItemInserted(this.listNotes.size - 1)
    }
    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        // animasi jika ada data baru
        notifyItemChanged(position, note)
    }
    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }
}