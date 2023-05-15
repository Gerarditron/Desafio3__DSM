package com.example.carsmotos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carsmotos.R
import com.example.carsmotos.classes.UsuarioModel

class UsuariosAdapter : RecyclerView.Adapter<UsuariosAdapter.userViewHolder>() {

    private var usrList: ArrayList<UsuarioModel> = ArrayList()
    private var onClickItem : ((UsuarioModel) -> Unit)? = null
    private var onClickDeleteItem : ((UsuarioModel) -> Unit)? = null
    fun addItems(items: ArrayList<UsuarioModel>){
        this.usrList = items
        notifyDataSetChanged()
    }

    //Al darle click a un valor en la lista
    fun setOnClickItem(callback: (UsuarioModel) -> Unit){
        this.onClickItem = callback
    }

    //Al darle click al boton eliminar
    fun setOnClickDeleteItem(callback: (UsuarioModel) -> Unit){
        this.onClickDeleteItem = callback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = userViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_layout,parent,false)
    )

    override fun getItemCount(): Int {
        return usrList.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val usr = usrList[position]
        holder.bindView(usr)
        holder.itemView.setOnClickListener{onClickItem?.invoke(usr)}
        holder.btnDeleteUser.setOnClickListener { onClickDeleteItem?.invoke(usr) }

    }

    class userViewHolder(var view: View): RecyclerView.ViewHolder(view){
        //Declarando las variables de "user_layout"

        private var txtUserNombre = view.findViewById<TextView>(R.id.txtUserNombre)
        private var txtUserApellido = view.findViewById<TextView>(R.id.txtUserApellido)
        private var txtUserEmail = view.findViewById<TextView>(R.id.txtUserEmail)
        private var txtUserUser = view.findViewById<TextView>(R.id.txtUserUser)
        private var txtUserTipo = view.findViewById<TextView>(R.id.txtUserTipo)
        var btnDeleteUser = view.findViewById<Button>(R.id.btnDeleteUser)

        fun bindView(usr: UsuarioModel){
            //Agregando al texto
            txtUserNombre.text = usr.nombres
            txtUserApellido.text = usr.apellidos
            txtUserEmail.text = usr.email
            txtUserUser.text = usr.user
            txtUserTipo.text = usr.tipo
        }
    }



}