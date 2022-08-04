package com.example.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.example.start.databinding.ActivityMainBinding
import com.example.start.databinding.CardLayoutBinding

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var title = arrayOf("Mjeledi", "mchimbi", "ass ya abi")
    private var details = arrayOf("stamina", "penetration", "spank hiyo kitu")
    private var images = intArrayOf(R.drawable.flame,R.drawable.flame,R.drawable.flame)


     inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
         var  image: ImageView
         var Title : TextView
         var Detail: TextView

         init {
             image = itemView.findViewById(R.id.image)
             Title = itemView.findViewById(R.id.Name)
             Detail = itemView.findViewById(R.id.status)


             itemView.setOnClickListener{
                 val position : Int = adapterPosition

                 Toast.makeText(itemView.context, "clicked on${title[position]}", Toast.LENGTH_LONG).show()
             }
         }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Title.text = title[position]
        holder.Detail.text = details[position]
        holder.image.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return title.size
    }


}



/*
   //TODO : BINDING
   inner class ViewHolder(val itemBinding: CardLayoutBinding):RecyclerView.ViewHolder(itemBinding.root) {

       // TODO: hapa nimecheza kaende

       lateinit var itemIMage :ImageView
       lateinit var title : TextView
       lateinit var detail : TextView

       fun bindItem(mess: mess){
           itemBinding.name.text = title[position]
           itemBinding.status.text = detail.toString()
           itemBinding.image.setImageResource(image[position])
       }


   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       return ViewHolder(CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent,false))


   }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val mess =
   }

   override fun getItemCount(): Int {
       TODO("Not yet implemented")
   }
*/