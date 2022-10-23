package com.example.todoapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.fragments.SharedViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private val adapter: ListAdapter by lazy { ListAdapter() }

    lateinit var view: FragmentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        view = FragmentListBinding.inflate(inflater,container,false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }



        //Set Menu
        setHasOptionsMenu(true)



        return view.root
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if(emptyDatabase){
            view?.noDataImageView?.visibility = View.VISIBLE
            view?.noDataTextView?.visibility = View.VISIBLE
        }else{
            view?.noDataImageView?.visibility = View.INVISIBLE
            view?.noDataTextView?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all){
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // Show alert dialogue to confirm removal of all items from database table
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No"){_,_ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }
}