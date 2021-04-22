package com.example.repcity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.repcity.api.EndPoints
import com.example.repcity.api.ServiceBuilder
import com.example.repcity.api.User
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

            val emailPesquisa = findViewById<EditText>(R.id.editTextEmail)
            val passwordPesquisa = findViewById<EditText>(R.id.editTextPassword)


            if(emailPesquisa.text.isNullOrEmpty() || passwordPesquisa.text.isNullOrEmpty()){
                emailPesquisa.error= "E-mail required"
                passwordPesquisa.error= "Password required"
            }


            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.userLogin(emailPesquisa.text.toString(), passwordPesquisa.text.toString())
            val intent = Intent(this, MapsActivity::class.java)


            call.enqueue(object: Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if(response.isSuccessful){
                        val c: User = response.body()!!
                        if(emailPesquisa.text.toString().equals(c.email) && passwordPesquisa.text.toString().equals(c.password)){
                            SharedPrefManager.getInstance(applicationContext).saveUser(c)
                            Toast.makeText(this@MainActivity, c.email, Toast.LENGTH_SHORT).show()
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }else{
                            Toast.makeText(this@MainActivity, "Invalid data", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

           /*ServiceBuilder.instance.userLogin(email, password)
               .enqueue(object: Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(applicationContext, "erro", Toast.LENGTH_SHORT).show()
                }

                   override fun onResponse(call: Call<User>, response: Response<User> ) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()

                    }*/

                   /* if(!response.body()?.error!!){
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.user!!)
                            val intent = Intent(applicationContext, MapActivity::class.java )
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)

                    }else{
                        Toast.makeText(applicationContext,"${response.body()?.message}" , Toast.LENGTH_LONG).show()
                    }
                }
           })*/
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
            val intent = Intent(applicationContext, MapsActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        }
    }


}