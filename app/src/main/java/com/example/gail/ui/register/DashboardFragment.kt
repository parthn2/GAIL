package com.example.gail.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import com.example.gail.databinding.FragmentRegisterBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegisterBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        binding.button.setOnClickListener {

            val firstName = binding.motorId.text.toString()
            val lastName = binding.current.text.toString()
            val age = binding.voltage.text.toString()
            val userName = binding.power.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            val Motor = Motor(firstName,lastName,age,userName)
            database.child(userName).setValue(Motor).addOnSuccessListener {

                binding.motorId.text.clear()
                binding.current.text.clear()
                binding.voltage.text.clear()
                binding.power.text.clear()

                Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }

    }
}
