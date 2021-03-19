package com.example.gituserlistapp.roomdb
import android.app.Application
import androidx.lifecycle.LiveData

class NoteRepository(application: Application) {

    private var noteDao: NoteDao
    private var allNotes: LiveData<List<Note>>

   // private var uniqueNotes: LiveData<List<Note>>

    private val database = NoteDatabase.getInstance(application)
     var id : Int ?= null

    init {
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
        //uniqueNotes = noteDao.getIDNotes()
    }

    fun insert(note: Note) {
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        subscribeOnBackground {
            noteDao.insert(note)
        }
    }

    fun update(note: Note) {
        subscribeOnBackground {
            noteDao.update(note)
        }
    }

    fun delete(note: Note) {
        subscribeOnBackground {
            noteDao.delete(note)
        }
    }

    fun deleteAllNotes() {
        subscribeOnBackground {
            noteDao.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }
 fun getIDNotes(id : Int): LiveData<List<Note>> {
        return  noteDao.getIDNotes(id)
    }



}