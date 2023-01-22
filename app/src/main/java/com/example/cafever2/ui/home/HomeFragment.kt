package com.example.cafever2.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cafever2.R
import com.example.cafever2.databinding.FragmentHomeBinding
import com.google.firebase.database.*
import java.util.Locale.filter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var dbref : DatabaseReference
    private lateinit var cafeRecyclerview : RecyclerView
    private lateinit var cafeArrayList : ArrayList<Cafe>
    lateinit var editText: EditText
//    private lateinit var searchView: SearchView
//    var cafeadapter: CafeAdapter
    private lateinit var cafeadapter : CafeAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        cafeRecyclerview = root.findViewById(R.id.CaferecyclerView) as RecyclerView
        cafeRecyclerview.layoutManager = LinearLayoutManager(root.context)
        cafeRecyclerview.setHasFixedSize(true)
        cafeArrayList = arrayListOf<Cafe>()
//        searchView = root.findViewById(R.id.cafesearch)
        getUserData()
        editText = root.findViewById(R.id.editText)
        editText.addTextChangedListener(object : TextWatcher{
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
        val filteredItem = ArrayList<Cafe>()
        // looping through the array list to obtain the required value
        for (item in cafeArrayList) {
            if (item.titleName!!.toLowerCase().contains(s.toLowerCase())) {
                filteredItem.add(item)
            }
        }
         //adding the filted value to adapter
        CafeAdapter(filteredItem)
    }


    private fun search(toString: String) {
        dbref = FirebaseDatabase.getInstance().getReference("CafeModel")
            dbref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        cafeArrayList.clear()
                        for(userSnapshot in snapshot.children){
                            val cafe = userSnapshot.getValue(Cafe::class.java)
                            if (cafe!!.titleName == toString || cafe!!.detail == toString || cafe!!.food1 == toString
                                || cafe!!.food2 == toString || cafe!!.food3 == toString || cafe!!.food4 == toString)
                            cafeArrayList.add(cafe)
                        }
                        cafeRecyclerview.adapter = CafeAdapter(cafeArrayList)

                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("CafeModel")
        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val cafe = userSnapshot.getValue(Cafe::class.java)
                        cafeArrayList.add(cafe!!)

                    }
                    cafeRecyclerview.adapter = CafeAdapter(cafeArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }
    inner class CafeAdapter(private val cafeList: ArrayList<Cafe>) : RecyclerView.Adapter<CafeAdapter.MyViewHolder>() {

        private lateinit var cafeArray:ArrayList<Cafe>
        var itemList =ArrayList<Cafe>()

        fun setData(itemmodellist:ArrayList<Cafe>){
            this.itemList =itemmodellist
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.modelcafe,
                parent,false)
            return MyViewHolder(itemView)

        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val currentitem = cafeList[position]
            holder.titleName.text = currentitem.titleName
            holder.detail.text = currentitem.detail
            Glide.with(holder.itemView)
                .load(currentitem.image1)
                .into(holder.image1)

            holder.itemView.setOnClickListener{
                val mIntent = Intent(context,CafeActivity::class.java)
                mIntent.putExtra("title_cafe", currentitem.titleName)
                mIntent.putExtra("ima1",currentitem.image1)
                mIntent.putExtra("ima2",currentitem.image2)
                mIntent.putExtra("ima3",currentitem.image3)
                mIntent.putExtra("ima4",currentitem.image4)
                mIntent.putExtra("ima5",currentitem.image5)
                mIntent.putExtra("ima6",currentitem.image6)
                mIntent.putExtra("ima7",currentitem.image7)
                mIntent.putExtra("food1",currentitem.food1)
                mIntent.putExtra("food2",currentitem.food2)
                mIntent.putExtra("food3",currentitem.food3)
                mIntent.putExtra("food4",currentitem.food4)
                mIntent.putExtra("price1",currentitem.price1)
                mIntent.putExtra("price2",currentitem.price2)
                mIntent.putExtra("price3",currentitem.price3)
                mIntent.putExtra("price4",currentitem.price4)
                context?.startActivity(mIntent)
            }

        }

        override fun getItemCount(): Int {
            return cafeList.size
        }

        inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

            val titleName : TextView = itemView.findViewById(R.id.tagefoodname)
            val detail : TextView = itemView.findViewById(R.id.tagecafe)
            val image1 : ImageView = itemView.findViewById(R.id.imgmodel)

        }

    }

}
