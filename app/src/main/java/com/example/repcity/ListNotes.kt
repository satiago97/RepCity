package com.example.repcity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_LIGHT
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.repcity.adapters.INotesRVAdapter
import com.example.repcity.adapters.NotesAdapter
import com.example.repcity.adapters.OnUpdateClickListener

import com.example.repcity.entities.Notes
import com.example.repcity.viewModel.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.android.synthetic.main.activity_add_notes.view.*


class ListNotes : AppCompatActivity(), INotesRVAdapter, OnUpdateClickListener, SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var brightness: Sensor? = null



    lateinit var notesViewModel: NotesViewModel

    private val newNoteActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_notes)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setUpSensorStuff()


        //backbutton
        val backButtonToMain = findViewById<Button>(R.id.backButtonNotes)
        backButtonToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        //recycler
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotesAdapter(this, this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        notesViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotesViewModel::class.java)
        notesViewModel.allNotes.observe(this, Observer{
            notes-> notes?.let { adapter.setNotes(it) }
        })


    //fab button

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, AddNotes::class.java)
            startActivityForResult(intent, newNoteActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == newNoteActivityRequestCode  && resultCode == Activity.RESULT_OK) {
            val ptitle = data?.getStringExtra(AddNotes.EXTRA_REPLY_TITLE)
            val pdescription = data?.getStringExtra(AddNotes.EXTRA_REPLY_DESCRIPTION)


            if(ptitle != null && pdescription != null){
                val note = Notes(title = ptitle, description = pdescription)
                notesViewModel.insert(note)
            }else{
                Toast.makeText(applicationContext, "Note not saved!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClicked(note: Notes) {
        notesViewModel.delete(note)
    }



    override fun onUpdateClick(note: Notes){

        val mDialogView = LayoutInflater.from(this).inflate(R.layout.activity_add_notes, null)

        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle(title)

        val mAlertDialog = mBuilder.show()

        mDialogView.submitNoteTitle.setText(note.title)
         mDialogView.submitNoteDescription.setText(note.description)


            mDialogView.submitNoteButton.setOnClickListener {
                val tituloUpdated = mDialogView.submitNoteTitle.text.toString()
                val descriptionUpdated = mDialogView.submitNoteDescription.text.toString()


                if(tituloUpdated.length == 0){
                    Toast.makeText(applicationContext, "Title can't be empty", Toast.LENGTH_SHORT).show()
                }
                if(descriptionUpdated.length == 0){
                    Toast.makeText(applicationContext, "Description can't be empty", Toast.LENGTH_SHORT).show()
                }
                if(tituloUpdated.length != 0 && descriptionUpdated.length != 0){
                    mAlertDialog.dismiss()

                    note.id?.let { it1 -> notesViewModel.update(it1, tituloUpdated, descriptionUpdated) }
                }
            }
    }


    private fun setUpSensorStuff(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        brightness = sensorManager.getDefaultSensor(TYPE_LIGHT)

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        val layout = findViewById<RelativeLayout>(R.id.relativeListNotes)
        if(event?.sensor?.type == Sensor.TYPE_LIGHT){
            val light = event.values[0]

            if(light <= 11){
                layout.setBackgroundResource(R.color.colorPrimary)
            }else{
                layout.setBackgroundResource(R.color.colorPrimaryDark)

            }
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, brightness, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }



}