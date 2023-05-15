package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.AutomovilModel

class AutomovilAdapter: RecyclerView.Adapter<AutomovilAdapter.autosViewHolder>() {
    private var autList: ArrayList<AutomovilModel> = ArrayList()
    private var onClickItem : ((AutomovilModel) -> Unit)? = null
    private var onClickDeleteItem : ((AutomovilModel) -> Unit)? = null
    fun addItems(items: ArrayList<AutomovilModel>){
        this.autList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (AutomovilModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (AutomovilModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = autosViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.automovil_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return autList.size
    }

    override fun onBindViewHolder(holder: autosViewHolder, position: Int) {
        val aut = autList[position]
        holder.bindView(aut)
        holder.itemView.setOnClickListener{onClickItem?.invoke(aut)}
        holder.btnDeleteAuto.setOnClickListener { onClickDeleteItem?.invoke(aut) }

    }

    class autosViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "automovil_layout"

        private var txtModelLYT = view.findViewById<TextView>(R.id.txtModelLYT)
        private var txtDescripcionLYT = view.findViewById<TextView>(R.id.txtDescripcionLYT)
        private var txtPrecioLYT = view.findViewById<TextView>(R.id.txtPrecioLYT)
        var btnDeleteAuto = view.findViewById<Button>(R.id.btnDeleteAuto)

        fun bindView(aut: AutomovilModel){
            //Agregando al texto
            txtModelLYT.text = aut.modelo
            txtDescripcionLYT.text = aut.descripcion
            txtPrecioLYT.text = aut.precio.toString()
        }
    }




}