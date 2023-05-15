package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.ColoresModel


class ColoresAdapter : RecyclerView.Adapter<ColoresAdapter.colorViewHolder>(){
    private var clrList: ArrayList<ColoresModel> = ArrayList()
    private var onClickItem : ((ColoresModel) -> Unit)? = null
    private var onClickDeleteItem : ((ColoresModel) -> Unit)? = null
    fun addItems(items: ArrayList<ColoresModel>){
        this.clrList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (ColoresModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (ColoresModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = colorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.colores_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return clrList.size
    }

    override fun onBindViewHolder(holder: colorViewHolder, position: Int) {
        val clr = clrList[position]
        holder.bindView(clr)
        holder.itemView.setOnClickListener{onClickItem?.invoke(clr)}
        holder.btnDeleteColor.setOnClickListener { onClickDeleteItem?.invoke(clr) }

    }

    class colorViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "colores_layout"
        private var txtColor = view.findViewById<TextView>(R.id.txtColor)
        var btnDeleteColor = view.findViewById<Button>(R.id.btnDeleteColor)

        fun bindView(clr: ColoresModel){
            //Agregando al texto
            txtColor.text = clr.descripcion
        }
    }


}