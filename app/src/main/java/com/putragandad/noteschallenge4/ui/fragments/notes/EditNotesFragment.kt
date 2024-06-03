package com.putragandad.noteschallenge4.ui.fragments.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.putragandad.noteschallenge4.NotesApplication
import com.putragandad.noteschallenge4.R
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.databinding.FragmentEditNotesBinding
import com.putragandad.noteschallenge4.ui.viewmodels.NotesViewModel
import com.putragandad.noteschallenge4.ui.viewmodels.NotesViewModelFactory
import com.putragandad.noteschallenge4.utils.Constant

class EditNotesFragment : Fragment() {
    private var _binding: FragmentEditNotesBinding? = null
    private val binding get() = _binding!!

    private val notesViewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments?.getParcelable<Notes>(Constant.NOTES_BUNDLE)

        if(bundle != null) { // check if bundle isn't null
            val notesTitleFromBundle = bundle.title
            val notesContentFromBundle = bundle.notesContent
            val notesIdFromBundle = bundle.id
            val notesUserEmailFromBundle = bundle.userEmail

            val notesTitleField = binding.tfNotesTitle
            val notesContentField = binding.tfNotesContent

            notesTitleField.setText(notesTitleFromBundle)
            notesContentField.setText(notesContentFromBundle)

            binding.fabSaveNotes.setOnClickListener {
                // get current title and content from the textfield when fab pressed
                val notesTitle = notesTitleField.text.toString()
                val notesContent = notesContentField.text.toString()
                if(notesTitle.isNotEmpty() && notesContent.isNotEmpty()) { // check isNotEmpty
                    val notes = Notes(notesIdFromBundle, notesTitle, notesContent, notesUserEmailFromBundle)
                    notesViewModel.update(notes)
                    Snackbar.make(requireView(), "Notes edited and saved successfully!", Snackbar.LENGTH_LONG).show()
                    findNavController().popBackStack()
                } else {
                    Snackbar.make(it, "Notes cannot be empty!", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }

        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.edit_bottom_delete -> {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Delete Notes")
                        .setMessage("Are you sure to delete this note?")
                        .setNegativeButton("No") { dialog, which ->
                            dialog.cancel()
                        }
                        .setPositiveButton("Yes") { dialog, which ->
                            bundle?.let { notesViewModel.delete(it) }
                            Snackbar.make(requireView(), "Notes deleted successfully.", Snackbar.LENGTH_LONG)
                                .show()
                            findNavController().popBackStack() // pop back stack when notes deleted
                        }
                        .show()
                    true
                }
                else -> false
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