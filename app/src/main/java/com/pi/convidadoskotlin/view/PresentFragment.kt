package com.pi.convidadoskotlin.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pi.convidadoskotlin.R
import com.pi.convidadoskotlin.view.adapter.AllAdapter
import com.pi.convidadoskotlin.view.interfaces.GuestListener
import com.pi.convidadoskotlin.viewmodel.GuestViewModel
import com.pi.service.contants.DataBaseConstants
import com.pi.convidadoskotlin.model.GuestModel
import kotlinx.android.synthetic.main.fragment_present.view.*

class PresentFragment : Fragment(), GuestListener {

    private lateinit var viewModel: GuestViewModel
    private val list: List<GuestModel> = arrayListOf()
    private val mAdapter = AllAdapter(list,this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(GuestViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_present, container, false)
        root.recycler_present.adapter = mAdapter
        root.recycler_present.layoutManager = LinearLayoutManager(context)
observer()
        return root
    }

    private fun observer() {
        viewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateList(it)

        })
    }

    override fun onClick(id: Int) {
        val intent = Intent(context, GuestFormActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(DataBaseConstants.GUEST.COLUMNS.ID, id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDelete(id: Int) {
        viewModel.delete(id)
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(1)
        observer()
    }
}
