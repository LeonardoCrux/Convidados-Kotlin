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
import com.pi.service.contants.DataBaseConstants.GUEST.COLUMNS.ID
import com.pi.convidadoskotlin.model.GuestModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class AllFragment : Fragment(), GuestListener {

    private lateinit var mViewModel: GuestViewModel
    private val list: List<GuestModel> = arrayListOf()
    private val mAdapter = AllAdapter(list, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewModel = ViewModelProviders.of(this).get(GuestViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        root.recyclerw_all.adapter = mAdapter
        root.recyclerw_all.layoutManager = LinearLayoutManager(context)
        mViewModel.load(2)
        observer()

        return root
    }

    private fun observer() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateList(it)
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(2)
        observer()
    }

    override fun onClick(id: Int) {
        val intent = Intent(context, GuestFormActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(ID, id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onDelete(id: Int) {
        mViewModel.delete(id)
        mViewModel.load(2)
    }

}
