package com.example.repcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.repcity.api.Address
import com.example.repcity.api.EndPoints
import com.example.repcity.api.ServiceBuilder
import com.example.repcity.storage.SharedPrefManager


import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var adr: List<Address>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val fablogout = findViewById<FloatingActionButton>(R.id.fablogout)
        fablogout.setOnClickListener {
            SharedPrefManager.getInstance(applicationContext).clear()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        val fabfab = findViewById<FloatingActionButton>(R.id.filterfab)

        fabfab.setOnClickListener {

            mMap.clear()

            val dialog = AlertDialog.Builder(this)
            val bNames = arrayOf<CharSequence>("Accident", "Fire", "Constructions", "Other")
            dialog.setTitle("SELECT THE TYPE")
            dialog.setItems(bNames) { _, which ->
                when (which) {
                    0 -> filterAccident()
                    1 -> filterfire()
                    2 -> filterConstruction()
                    3 -> filterOther()
                }
            }.create()

            dialog.setNegativeButton("Cancel") { _, _ -> }
            dialog.show()
        }

        val insertFab = findViewById<FloatingActionButton>(R.id.insertfab)
        insertFab.setOnClickListener {


        }

        val request = ServiceBuilder.buildService(EndPoints::class.java)

        val call = request.getIncidentes()

        var position: LatLng

        call.enqueue(object: Callback<List<Address>>{
            override fun onResponse(call: Call<List<Address>>, response: Response<List<Address>>) {
                if (response.isSuccessful) {
                    adr = response.body()!!
                    for (address in adr) {
                        position = LatLng(
                            address.lat.toDouble(),
                            address.lng.toDouble()
                        )
                        if(address.tipo=="Acidente"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        }
                        else if(address.tipo=="Incendio"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        }else if(address.tipo=="Outro"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        }
                        else if(address.tipo=="Construction"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<Address>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        /*  val viana = LatLng(41.6946, -8.83016)
          mMap.addMarker(MarkerOptions().position(viana).title("Marker in Sydney"))
          mMap.moveCamera(CameraUpdateFactory.newLatLng(viana))*/
    }


    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        }
    }


    //filtro para acidentes
    fun filterAccident(){

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        var position: LatLng
        var call = request.getIncidenteAcidente()


        call.enqueue(object: Callback<List<Address>>{
            override fun onResponse(call: Call<List<Address>>, response: Response<List<Address>>) {
                if (response.isSuccessful) {
                    adr = response.body()!!
                    for (address in adr) {
                        position = LatLng(
                            address.lat.toDouble(),
                            address.lng.toDouble()
                        )
                        if(address.tipo=="Acidente"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        }
                        else if(address.tipo=="Incendio"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        }else if(address.tipo=="Outro"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        }
                        else if(address.tipo=="Construction"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<Address>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    //filtro para incendios
    fun filterfire(){

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        var position: LatLng
        var call = request.getIncidenteIncendio()


        call.enqueue(object: Callback<List<Address>>{
            override fun onResponse(call: Call<List<Address>>, response: Response<List<Address>>) {
                if (response.isSuccessful) {
                    adr = response.body()!!
                    for (address in adr) {
                        position = LatLng(
                            address.lat.toDouble(),
                            address.lng.toDouble()
                        )
                        if(address.tipo=="Acidente"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        }
                        else if(address.tipo=="Incendio"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        }else if(address.tipo=="Outro"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        }
                        else if(address.tipo=="Construction"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<Address>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }


    //filtro para construçoes
    fun filterConstruction(){

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        var position: LatLng
        var call = request.getIncidenteObras()


        call.enqueue(object: Callback<List<Address>>{
            override fun onResponse(call: Call<List<Address>>, response: Response<List<Address>>) {
                if (response.isSuccessful) {
                    adr = response.body()!!
                    for (address in adr) {
                        position = LatLng(
                            address.lat.toDouble(),
                            address.lng.toDouble()
                        )
                        if(address.tipo=="Acidente"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        }
                        else if(address.tipo=="Incendio"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        }else if(address.tipo=="Outro"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        }
                        else if(address.tipo=="Construction"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<Address>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }


    //filtro para outros

    fun filterOther(){

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        var position: LatLng
        var call = request.getIncidenteOutros()


        call.enqueue(object: Callback<List<Address>>{
            override fun onResponse(call: Call<List<Address>>, response: Response<List<Address>>) {
                if (response.isSuccessful) {
                    adr = response.body()!!
                    for (address in adr) {
                        position = LatLng(
                            address.lat.toDouble(),
                            address.lng.toDouble()
                        )
                        if(address.tipo=="Acidente"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        }
                        else if(address.tipo=="Incendio"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
                        }else if(address.tipo=="Outro"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        }
                        else if(address.tipo=="Construction"){
                            mMap.addMarker(MarkerOptions().position(position).title(address.descricao)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))

                        }

                    }
                }
            }

            override fun onFailure(call: Call<List<Address>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }



}