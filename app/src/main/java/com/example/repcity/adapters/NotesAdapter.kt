package com.example.repcity.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.repcity.ListNotes
import com.example.repcity.R
import com.example.repcity.entities.Notes

class NotesAdapter(private val
                   context: Context, private val listener: INotesRVAdapter, private val listener2: OnUpdateClickListener
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Notes>()




    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val titleItemView: TextView = itemView.findViewById(R.id.titleView)
        val descriptionItemView: TextView = itemView.findViewById(R.id.descriptionView)
        val btnDelete: Button = itemView.findViewById(R.id.deleteNoteButton)
        val updateNotesButton: Button = itemView.findViewById(R.id.updateNoteButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val viewHolder = NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false))


    viewHolder.btnDelete.setOnClickListener {

    listener.onItemClicked(notes[viewHolder.adapterPosition])
    }

        viewHolder.updateNotesButton.setOnClickListener {
            listener2.onUpdateClick(notes[viewHolder.adapterPosition])
        }

        return viewHolder
    }



    override fun onBindViewHolder(holder: NotesViewHolder, position: Int){
        val current = notes[position]
        holder.titleItemView.text = current.title
        holder.descriptionItemView.text = current.description



    }

    internal fun setNotes(notes: List<Notes>) {
        this.notes = notes
        notifyDataSetChanged()
    }


    override fun getItemCount() = notes.size

}


interface INotesRVAdapter{
    fun onItemClicked(notes: Notes)
}


interface OnUpdateClickListener{
    fun onUpdateClick(notes: Notes)
}





