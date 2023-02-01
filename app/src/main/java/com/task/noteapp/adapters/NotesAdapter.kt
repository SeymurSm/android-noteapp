package com.task.noteapp.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.data.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NotesAdapter(private val context: Context,
private var list: ArrayList<Note>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: View.OnClickListener? = null

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            if(!model.imageUrl.isNullOrEmpty()) {
                //TODO: Load image
            }
            else
                holder.itemView.giv_note_image.visibility = View.GONE
            holder.itemView.tvTitle.text = model.title
            holder.itemView.tvDescription.text = model.description
            holder.itemView.tvAddDate.text = model.date
            holder.itemView.tvEditStatus.text = model.editStatus

            if(!model.editStatus.isNullOrEmpty()){
                holder.itemView.vEditStatus.setBackgroundColor(Color.parseColor("#F5B041"))
            }


            holder.itemView.setOnClickListener {

//                if (onClickListener != null) {
//                    onClickListener!!.onClick(position, model)
//                }
            }
        }
    }
    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A function to edit the added note detail and pass the existing details through intent.
     */
    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {

    }

    /**
     * A function to delete the added note detail from the local storage.
     */
    fun removeAt(position: Int) {

    }

    /**
     * A function to bind the onclickListener.
     */
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener : View.OnClickListener {
        fun onClick(position: Int, model: Note)
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}