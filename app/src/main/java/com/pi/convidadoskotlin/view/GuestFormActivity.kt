package com.pi.convidadoskotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pi.convidadoskotlin.R
import com.pi.convidadoskotlin.viewmodel.GuestFormViewModel
import com.pi.service.contants.DataBaseConstants.GUEST.COLUMNS.ID
import kotlinx.android.synthetic.main.activity_guest_form.*

class GuestFormActivity : AppCompatActivity() {

    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)
        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)
        setListeners()
        observe()
        loadData()

        radio_presence.isChecked = true
    }

    private fun setListeners() {
        button_save.setOnClickListener(View.OnClickListener {
            val name = edit_name.text.toString()
            val presence = radio_presence.isChecked
            mViewModel.save(mGuestId, name, presence)
        })
    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, Observer { it ->
            if (it) {
                Toast.makeText(applicationContext, "Convidado adicionado", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(applicationContext, "Falha ao adicionar o convidado", Toast.LENGTH_SHORT).show()
            finish()

        })

        mViewModel.guest.observe(this,  Observer {
            edit_name.setText(it.name)
            if(it.presence){
                radio_presence.isChecked = true
            } else radio_absent.isChecked = true
        })
    }

    private fun loadData(){
        val bundle = intent.extras
        if(bundle!= null){
            mGuestId = bundle.getInt(ID)
            mViewModel.loadData(mGuestId)
        }
    }
}
