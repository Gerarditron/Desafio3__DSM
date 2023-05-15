package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.TipoAutoActivity
import com.example.carsmotos.classes.TipoAutomovilModel

class TipoAutoAdapter: RecyclerView.Adapter<TipoAutoAdapter.tipoautoViewHolder>() {

    private var tipoautoList: ArrayList<TipoAutomovilModel> = ArrayList()
    private var onClickItem : ((TipoAutomovilModel) -> Unit)? = null
    private var onClickDeleteItem : ((TipoAutomovilModel) -> Unit)? = null
    fun addItems(items: ArrayList<TipoAutomovilModel>){
        this.tipoautoList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (TipoAutomovilModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (TipoAutomovilModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = tipoautoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.tipoauto_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return tipoautoList.size
    }

    override fun onBindViewHolder(holder: tipoautoViewHolder, position: Int) {
        val tpa = tipoautoList[position]
        holder.bindView(tpa)
        holder.itemView.setOnClickListener{onClickItem?.invoke(tpa)}
        holder.btnDeleteTipoAuto.setOnClickListener { onClickDeleteItem?.invoke(tpa) }

    }

    class tipoautoViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "tipoauto_layout"
        private var txtTipoAuto = view.findViewById<TextView>(R.id.txtTipoAuto)
        var btnDeleteTipoAuto = view.findViewById<Button>(R.id.btnDeleteTipoAuto)

        fun bindView(tpa: TipoAutomovilModel){
            //Agregando al texto
            txtTipoAuto.text = tpa.descripcion
        }
    }



}