package com.pi.convidadoskotlin.view.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pi.convidadoskotlin.R
import com.pi.convidadoskotlin.view.interfaces.GuestListener
import com.pi.convidadoskotlin.model.GuestModel
import kotlinx.android.synthetic.main.lista_convidados.view.*

class AllAdapter(var mGuestList: List<GuestModel>, val clickListener: GuestListener) :
    RecyclerView.Adapter<AllAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_convidados, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mGuestList.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(mGuestList[position])
        holder.itemView.setOnClickListener({
            clickListener.onClick(mGuestList[position].id)
        })
        holder.itemView.setOnLongClickListener({
            AlertDialog.Builder(it.context)
                .setTitle(R.string.remover_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) { dialog, which -> clickListener.onDelete(mGuestList[position].id) }
                .setNegativeButton(R.string.cancelar) { dialog, which ->  null}.show()
            true
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(guest: GuestModel) {
            itemView.list_name.text = guest.name
            val presence = guest.presence
            if (presence) {
                itemView.image_presence.setImageResource(R.drawable.ic_check)
            } else itemView.image_presence.setImageResource(R.drawable.ic_close)
        }
    }

    fun updateList(list: List<GuestModel>) {
        mGuestList = list
        notifyDataSetChanged()
    }
}