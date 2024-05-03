package com.putragandad.noteschallenge4.ui.fragments.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.putragandad.noteschallenge4.R
import com.putragandad.noteschallenge4.adapters.NotesListAdapter
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.databinding.FragmentNoteListBinding
import com.putragandad.noteschallenge4.ui.viewmodels.UserViewModel

class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvHelloList.text = userViewModel.getUserName()
        binding.btnLogout.setOnClickListener {
            userViewModel.logout()
            findNavController().navigate(R.id.action_noteListFragment_to_loginFragment)
        }

        val dataset : ArrayList<Notes> = arrayListOf()
        dataset.add(Notes(1, "Notes 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.", "user@email.com"))
        dataset.add(Notes(2, "Notes 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", "user@email.com"))

        val adapter = NotesListAdapter(dataset)
        val recyclerView : RecyclerView = view.findViewById(R.id.rv_notes_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}