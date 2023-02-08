package com.example.projmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projmanager.R
import com.example.projmanager.models.Board

open class BoardsItemAdapter (private val context: Context,
        private var list : ArrayList<Board>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var onClickListener :OnClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.board_item,parent,false))
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val model = list[position]
                if(holder is MyViewHolder) {
                        Glide
                                .with(context)
                                .load(model.image)
                                .centerCrop()
                                .placeholder(R.drawable.profile)
                                .into(holder.itemView.findViewById(R.id.board_image))

                        holder.itemView.findViewById<TextView>(R.id.board_name).text= model.name

                        holder.itemView.findViewById<TextView>(R.id.board_created).text=
                                "by:${model.createdBy}"

                        holder.itemView.setOnClickListener {
                                if (onClickListener!=null)
                                {
                                        onClickListener!!.onClick(position,model)
                                }
                        }
                }
        }

        interface OnClickListener{
                fun onClick(position: Int,model: Board)
        }

        fun setOnClickListener(onClickListener: OnClickListener){
                this.onClickListener=onClickListener
        }

        override fun getItemCount(): Int {
                return list.size
        }

        private  class MyViewHolder(view: View):RecyclerView.ViewHolder(view)


}