package com.example.repcity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.repcity.api.ServiceBuilder
import com.example.repcity.storage.SharedPrefManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInButton.setOnClickListener {

            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()


            if(email.isEmpty()){
                editTextEmail.error = "Email required"
                editTextEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                editTextPassword.error = "Password required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

           ServiceBuilder.instance.userLogin(email, password)
               .enqueue(object: Callback<LoginResponse>{
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_SHORT).show()
                }


                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse> ) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()

                        if(!response.body()?.error!!){
                        SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)

                            val intent = Intent(applicationContext, MapActivity::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)

                        }else{
                           Toast.makeText(applicationContext,"${response.body()?.message}" , Toast.LENGTH_LONG).show()
                       }
                    }

                   /* if(!response.body()?.error!!){
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)
                            val intent = Intent(applicationContext, MapActivity::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                    }else{
                        Toast.makeText(applicationContext,"${response.body()?.message}" , Toast.LENGTH_LONG).show()
                    }*/
                }
           })
        }

        val notesButton = findViewById<Button>(R.id.notesButton)
        notesButton.setOnClickListener {
            val intent = Intent(this, ListNotes::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MapActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        }
    }


}