package com.example.gituserlistapp.views

import Utils
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.gituserlistapp.R
import com.example.gituserlistapp.models.UserProfile
import com.example.gituserlistapp.roomdb.Note
import com.example.gituserlistapp.roomdb.NoteViewModel
import com.example.gituserlistapp.viewmodel.ProfileActivityViewModel

class ProfileActivity : AppCompatActivity() {

    lateinit var profileActivityViewModel: ProfileActivityViewModel
    lateinit var noteViewModel: NoteViewModel
    lateinit var userId: String
    lateinit var profile: UserProfile
    lateinit var Profilepic: ImageView
    lateinit var backimage: ImageView
    lateinit var follow_text: TextView
    lateinit var following_text: TextView
    lateinit var Name: TextView
    lateinit var company: TextView
    lateinit var bloodText: TextView
    lateinit var noteswrite: EditText
    lateinit var Save: Button
    lateinit var progressBar: ProgressBar
    lateinit var mNoDataText: TextView
    lateinit var mTitleName: TextView
    private var allNotes: List<Note> = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initWidgets()
        userId = intent.extras?.get("activity") as String
        profile = UserProfile()
        profileActivityViewModel = ViewModelProvider(this).get(ProfileActivityViewModel::class.java)
        noteViewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        getProfileData(userId)
    }

    private fun initWidgets() {
        Profilepic = findViewById(R.id.Profilepic)
        follow_text = findViewById(R.id.follow_text)
        following_text = findViewById(R.id.following_text)
        Name = findViewById(R.id.Name)
        company = findViewById(R.id.textView4)
        mTitleName = findViewById(R.id.textView2)
        bloodText = findViewById(R.id.textView5)
        noteswrite = findViewById(R.id.noteswrite)
        backimage = findViewById(R.id.backimage)
        progressBar = findViewById(R.id.progress_Bar)
        mNoDataText = findViewById(R.id.no_data_text_view)
        backimage.setOnClickListener {
            onBackPressed()
        }
        Save = findViewById(R.id.Save)
        Save.setOnClickListener {
            if (TextUtils.isEmpty(noteswrite.getText().toString())) {
                Toast.makeText(this, "Please fill the field", Toast.LENGTH_SHORT).show()
            } else {
                noteViewModel.insert(
                    Note(
                        profile.login!!,
                        noteswrite.getText().toString(),
                        profile.id!!.toInt()
                    )
                )
                noteswrite.getText().clear()
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
                // onBackPressed()
            }


        }
    }

    private fun getProfileData(userId: String) {
        progressBar.visibility = View.VISIBLE
        if (Utils.networkStatus(this)) {
            profileActivityViewModel.getProfile(userId)!!
                .observe(this, Observer { profileDataGetterSetter ->
                    profile = profileDataGetterSetter
                    progressBar.visibility = View.GONE
                    if (profile != null) {
                        mNoDataText.visibility = View.GONE
                        Glide.with(this).load(profile.avatar_url).into(Profilepic)
                        follow_text.text = profile.followers
                        following_text.text = profile.following
                        Name.text = profile.login
                        company.text = profile.company
                        bloodText.text = profile.blog
                        mTitleName.text=profile.login
                        getOfflineData()
                    }
                })
        } else {
            Toast.makeText(this, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }

    }

    fun getOfflineData() {
        noteViewModel.getUniquNotes(profile.id!!.toInt()).observe(this, Observer {
            Log.e("DataOffline", "$it")
            allNotes = it
            for (note in allNotes) {
                noteswrite.setText(note.description.toString())
            }

        })
    }
}