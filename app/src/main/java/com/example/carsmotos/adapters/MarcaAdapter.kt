package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.MarcaModel

class MarcaAdapter : RecyclerView.Adapter<MarcaAdapter.marcaViewHolder>() {
    private var mrcList: ArrayList<MarcaModel> = ArrayList()
    private var onClickItem : ((MarcaModel) -> Unit)? = null
    private var onClickDeleteItem : ((MarcaModel) -> Unit)? = null
    fun addItems(items: ArrayList<MarcaModel>){
        this.mrcList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (MarcaModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (MarcaModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = marcaViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.marcas_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return mrcList.size
    }

    override fun onBindViewHolder(holder: marcaViewHolder, position: Int) {
        val mrc = mrcList[position]
        holder.bindView(mrc)
        holder.itemView.setOnClickListener{onClickItem?.invoke(mrc)}
        holder.btnDeleteMarca.setOnClickListener { onClickDeleteItem?.invoke(mrc) }

    }

    class marcaViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "marcas_layout"
        private var txtName = view.findViewById<TextView>(R.id.txtMarca)
        var btnDeleteMarca = view.findViewById<Button>(R.id.btnDeleteMarca)

        fun bindView(mrc: MarcaModel){
            //Agregando al texto
            txtName.text = mrc.nombre
        }
    }


}