package com.example.gituserlistapp.views

import Utils
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserlistapp.R
import com.example.gituserlistapp.adapters.UsersRvAdapter
import com.example.gituserlistapp.models.UserList
import com.example.gituserlistapp.roomdb.Note
import com.example.gituserlistapp.roomdb.NoteViewModel
import com.example.mvvmkotlinexample.viewmodel.MainActivityViewModel


class MainActivity : AppCompatActivity(), UsersRvAdapter.OnClickItemCallBack {

    lateinit var context: Context
    lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var mAdapter: UsersRvAdapter
    private var users_recycler_view: RecyclerView? = null
    private var mSearchView: SearchView? = null
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var mPage: Int = 0
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var VISIBLE_THRESHOLD = 1
    var listUsers = arrayListOf<UserList>()
    lateinit var usersData: UserList
    lateinit var progressBar: ProgressBar
    lateinit var mNoDataText: TextView
    lateinit var noteViewModel: NoteViewModel
    private var allNotes: List<Note> = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this@MainActivity
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        noteViewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        noteViewModel.getAllNotes().observe(this, Observer {
            Log.e("Notes observed", "$it")
            allNotes =it
        })
        usersData = UserList()
        mSearchView = findViewById(R.id.search_view)
        progressBar = findViewById(R.id.progress_Bar)
        mNoDataText = findViewById(R.id.no_data_text_view)
        mAdapter = UsersRvAdapter(this)
        users_recycler_view = findViewById(R.id.recyclerView)
        users_recycler_view?.layoutManager = LinearLayoutManager(this)
        users_recycler_view?.adapter = mAdapter

        if (users_recycler_view != null) {
            mLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            users_recycler_view?.apply {
                layoutManager = mLayoutManager
                adapter = mAdapter
            }

        }
        setNoDataTextVisiblity()
        getUSerList()
        setInfiniteScroller()
        searchList()
    }

    private fun searchList() {
        mSearchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //Performs search when user hit the search button on the keyboard
//                if (listUsers.contains(p0)) {
//                    mAdapter.filterList(p0)
//                } else {
//                    Toast.makeText(this@MainActivity, "No match found", Toast.LENGTH_SHORT).show()
//                }
                return false
            }

            override fun onQueryTextChange(value: String?): Boolean {
                //Start filtering the list as user start entering the characters
                filterData(value)
                return false
            }
        })
    }

    private fun filterData(value: String?) {
        val filterdNames: ArrayList<UserList> = ArrayList()
        for (user in listUsers) {
            //if the existing elements contains the search input
            if (user.login.toLowerCase().contains(value?.toLowerCase()!!)) {
                //adding the element to filtered list
                filterdNames.add(user)
            }
        }
        //calling a method of the adapter class and passing the filtered list
        mAdapter.filterList(filterdNames)
    }


    private fun getUSerList() {

        progressBar.visibility = View.VISIBLE
        if (Utils.networkStatus(this)) {
            mainActivityViewModel.getUser(mPage)!!.observe(this, Observer { serviceSetterGetter ->
                listUsers = serviceSetterGetter as ArrayList<UserList>
                progressBar.visibility = View.GONE
                if (mPage == 0) {
                    mAdapter.update(serviceSetterGetter as ArrayList<UserList>,allNotes)
                } else {
                    mAdapter.addItems(serviceSetterGetter as ArrayList<UserList>,allNotes)
                }
                setNoDataTextVisiblity()
                mPage++
            })
        } else {
            Toast.makeText(this, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setInfiniteScroller() {
        users_recycler_view?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = (mLayoutManager as LinearLayoutManager).getItemCount()
                lastVisibleItem = (mLayoutManager as LinearLayoutManager)
                    .findLastVisibleItemPosition()
                if (!loading && totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    getUSerList()
                    loading = true
                }
            }
        })
    }

    private fun setNoDataTextVisiblity() {
        if (mAdapter != null && mNoDataText != null) {
            mNoDataText.setVisibility(
                if (mAdapter.getItemCount() > 0)
                    View.GONE
                else
                    View.VISIBLE
            )
        }

    }

    override fun onItemClick(position: Int, leeds: UserList) {
        startActivity(
            Intent(this, ProfileActivity::class.java).putExtra(
                "activity",
                "" + leeds.login
            )
        )
    }
}