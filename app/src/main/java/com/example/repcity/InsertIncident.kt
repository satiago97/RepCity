package com.example.repcity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import com.example.repcity.api.Address
import com.example.repcity.api.EndPoints
import com.example.repcity.api.ServiceBuilder
import kotlinx.android.synthetic.main.activity_insert_incident.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertIncident : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_incident)



        var tipo = ""


        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            var rb = findViewById<RadioButton>(i)

            tipo = rb.text.toString()

        }


        val descricao = findViewById<EditText>(R.id.descriptionIncident)
        val id_user = getSharedPreferences("my_shared_preff", Context.MODE_PRIVATE).getInt("id", 0)


        val lat = intent.getDoubleExtra("lat", 0.0)
        val lng = intent.getDoubleExtra("lng", 0.0)


        submitIncidentButton.setOnClickListener {
            if(descricao.text.isNullOrEmpty()){
                descricao.error = "Required"
            }else{
                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.addPoint(tipo, descricao.text.toString(), id_user, lat, lng)

                call.enqueue(object: Callback<Address> {
                    override fun onFailure(call: Call<Address>, t: Throwable) {
                        Toast.makeText(this@InsertIncident, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Address>, response: Response<Address>) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext, "Thank you for your report", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MapsActivity::class.java)
                            startActivity(intent)
                        }
                    }

                })
            }
        }
    }
}