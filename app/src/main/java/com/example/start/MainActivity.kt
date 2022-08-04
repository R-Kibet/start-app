package com.example.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.start.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var fb : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        layoutManager = LinearLayoutManager(this)
        binding.recview.layoutManager = layoutManager

        adapter = RecyclerAdapter()
        binding.recview.adapter = adapter

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fb = FirebaseAuth.getInstance()


        binding.btnLogout.setOnClickListener {
            fb.signOut()
            startActivity(Intent(this , Sign_inActivity ::class.java))
            finish()
        }
    }
}