package com.example.note.ui


import android.app.AlertDialog
import android.app.LauncherActivity
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.note.R
import com.example.note.db.NoteDatabase
import com.example.note.db.note
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddNoteFragment : BaseFragment() {

    private var note: note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {

            note = AddNoteFragmentArgs.fromBundle(it).note
            edit_text_title.setText(note?.title)
            edit_text_note.setText(note?.note)

        }


        button_save.setOnClickListener { view ->
            val title = edit_text_title.text.toString().trim()
            val noteBody = edit_text_note.text.toString().trim()


            if (title.isEmpty()) {
                edit_text_title.setError("required title")
                edit_text_title.requestFocus()
                return@setOnClickListener
            }


            if (noteBody.isEmpty()) {
                edit_text_note.setError("required note")
                edit_text_note.requestFocus()
                return@setOnClickListener
            }

            launch {


                context?.let {
                    val mnote = note(title, noteBody)

                    if (note == null) {

                        NoteDatabase(it).getNoteDao().addNote(mnote)
                        it.toast("Saved Note")
                    } else {
                        mnote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mnote)
                        it.toast("Note Update")

                    }


                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)

                }
            }


        }

    }
        private fun deleteNote(){

            AlertDialog.Builder(context).apply {
                setTitle("are you sure?")
                setMessage("you can not undo this operation")
                setPositiveButton("Yes"){_,_->

                    launch {
                        NoteDatabase(context).getNoteDao().deleteNote(note!!)
                        val action = AddNoteFragmentDirections.actionSaveNote()
                        Navigation.findNavController(view!!).navigate(action)

                    }

                }
                setNegativeButton("No"){_,_->

                }
            }.create().show()
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_note -> if (note != null) deleteNote() else context?.toast("Cannot Deleted")

        }
    return  super.onOptionsItemSelected(item)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu,menu)

    }

}
