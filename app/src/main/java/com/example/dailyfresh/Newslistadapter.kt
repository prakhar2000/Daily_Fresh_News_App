package com.example.dailyfresh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Newslistadapter(private val listener:Newsitemclick):RecyclerView.Adapter<newsviewholder>() {

    private val items:ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newsviewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.newsitem,parent,false)

        val viewholder=newsviewholder(view)
        view.setOnClickListener{
            listener.onitemclick(items[viewholder.adapterPosition])
        }
        return viewholder


    }

    override fun onBindViewHolder(holder: newsviewholder, position: Int) {

        val currentitem=items[position]
        holder.titleview.text=currentitem.title
        holder.author.text=currentitem.author
        Glide.with(holder.itemView.context).load(currentitem.imageurl).into(holder.image)
    }

    override fun getItemCount(): Int {

        return items.size
    }

    fun updatenews(updatednews:ArrayList<News>){
        items.clear()
        items.addAll(updatednews)


        notifyDataSetChanged()
    }
}


class newsviewholder(itemView: View) :RecyclerView.ViewHolder(itemView){

    val titleview:TextView=itemView.findViewById(R.id.title)
    val image:ImageView=itemView.findViewById(R.id.image)
    val author:TextView=itemView.findViewById(R.id.author)

}
interface Newsitemclick  {
    fun onitemclick(item: News)
}