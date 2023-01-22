package com.example.cafever2.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cafever2.R
import com.example.cafever2.databinding.FragmentDashboardBinding
import com.example.cafever2.ui.home.Cafe
import com.example.cafever2.ui.home.CafeActivity
import com.google.firebase.database.*

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    private lateinit var dbref : DatabaseReference
    private lateinit var foodRecyclerview : RecyclerView
    private lateinit var foodArrayList : ArrayList<Food>

    lateinit var editText: EditText
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        foodRecyclerview = root.findViewById(R.id.FoodrecyclerView) as RecyclerView
        foodRecyclerview.layoutManager = LinearLayoutManager(root.context)
        foodRecyclerview.setHasFixedSize(true)
        foodArrayList = arrayListOf<Food>()
        getUserData()
        editText = root.findViewById(R.id.editText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }
        })
        val btsearch = root.findViewById<Button>(R.id.btsearch)
        btsearch.setOnClickListener{
            search(editText.text.toString())
        }


        return root
    }

    private fun filter(s: String) {
        val filteredItem = ArrayList<Food>()
        // looping through the array list to obtain the required value
        for (item in foodArrayList) {
            if (item.foodName!!.toLowerCase().contains(s.toLowerCase())) {
                filteredItem.add(item)
            }
        }
        //adding the filted value to adapter
        FoodAdapter(filteredItem)
    }

    private fun search(toString: String) {
        dbref = FirebaseDatabase.getInstance().getReference("FoodModel")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    foodArrayList.clear()
                    for(userSnapshot in snapshot.children){
                        val food = userSnapshot.getValue(Food::class.java)
                        if (food!!.foodName == toString || food!!.cafeName == toString || food!!.price == toString || food!!.p == toString)
                            foodArrayList.add(food)
                    }
                    foodRecyclerview.adapter = FoodAdapter(foodArrayList)

                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("FoodModel")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val food = userSnapshot.getValue(Food::class.java)
                        foodArrayList.add(food!!)

                    }

                    foodRecyclerview.adapter = FoodAdapter(foodArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    inner class FoodAdapter(private val foodList: ArrayList<Food>) : RecyclerView.Adapter<FoodAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.modelfood,
                parent,false)
            return MyViewHolder(itemView)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val currentitem = foodList[position]
            holder.foodName.text = currentitem.foodName
            holder.cafeName.text = currentitem.cafeName
            holder.price.text = currentitem.price
            Glide.with(holder.itemView)
                .load(currentitem.imagemodel)
                .into(holder.imagemodel)

            holder.itemView.setOnClickListener{
                val mIntent = Intent(context, FoodActivity::class.java)
                mIntent.putExtra("title_food", currentitem.foodName)
                mIntent.putExtra("imaf1",currentitem.imagemodel)
                mIntent.putExtra("imaf2",currentitem.image1)
                mIntent.putExtra("imaf3",currentitem.image2)
                mIntent.putExtra("imaf4",currentitem.image3)
                mIntent.putExtra("cafename",currentitem.cafeName)
                mIntent.putExtra("price",currentitem.price)
                context?.startActivity(mIntent)
            }

        }

        override fun getItemCount(): Int {
            return foodList.size
        }

        inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

            val foodName : TextView = itemView.findViewById(R.id.tagefoodname)
            val cafeName : TextView = itemView.findViewById(R.id.tagecafe)
            val price : TextView = itemView.findViewById(R.id.tageprice)
            val imagemodel : ImageView = itemView.findViewById(R.id.imgmodel)

        }

    }

}