package com.example.note.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.note.R
import com.example.note.db.NoteDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        note_recycle_view.setHasFixedSize(true)
        note_recycle_view.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        launch {
            context?.let {
                val notes=NoteDatabase(it).getNoteDao().getAllNotes()
                note_recycle_view.adapter=NotesAdapter(notes)


            }
        }





        button_add.setOnClickListener{

            val action=HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)

        }


    }

}
