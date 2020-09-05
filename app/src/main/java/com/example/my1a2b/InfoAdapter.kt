package com.example.my1a2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.info.view.*


class InfoAdapter(
    private var info: MutableList<Info>
): RecyclerView.Adapter<InfoAdapter.InfoViewHolder>() {
    class InfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.info, parent, false)
        return InfoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.itemView.apply{
            round.text = info[position].round
            input.text = info[position].input;
            result.text = info[position].result
        }
    }
}