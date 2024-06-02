package com.putragandad.noteschallenge4.ui.fragments.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.putragandad.noteschallenge4.NotesApplication
import com.putragandad.noteschallenge4.R
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.databinding.FragmentAddNotesBinding
import com.putragandad.noteschallenge4.ui.viewmodels.NotesViewModel
import com.putragandad.noteschallenge4.ui.viewmodels.NotesViewModelFactory
import com.putragandad.noteschallenge4.ui.viewmodels.UserViewModel

class AddNotesFragment : Fragment() {
    private var _binding: FragmentAddNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabSaveNotes.setOnClickListener {
            val notesTitle = binding.tfNotesTitle.text.toString()
            val notesContent = binding.tfNotesContent.text.toString()
            if(notesTitle.isNotEmpty() && notesContent.isNotEmpty()) { // check condition if notes is not empty then save the notes
                val notes = Notes(0, notesTitle, notesContent, userViewModel.getEmail())
                notesViewModel.insert(notes)
                findNavController().popBackStack() // back to notes list
                Snackbar.make(it, "Notes successfully added!", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                Snackbar.make(it, "Notes cannot be empty!", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}