package com.putragandad.noteschallenge4.ui.fragments.notes

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.search.SearchView
import com.google.android.material.snackbar.Snackbar
import com.putragandad.noteschallenge4.NotesApplication
import com.putragandad.noteschallenge4.R
import com.putragandad.noteschallenge4.adapters.NotesListAdapter
import com.putragandad.noteschallenge4.adapters.OnItemClickListener
import com.putragandad.noteschallenge4.data.Notes
import com.putragandad.noteschallenge4.databinding.FragmentNoteListBinding
import com.putragandad.noteschallenge4.ui.viewmodels.NotesViewModel
import com.putragandad.noteschallenge4.ui.viewmodels.NotesViewModelFactory
import com.putragandad.noteschallenge4.ui.viewmodels.UserViewModel
import com.putragandad.noteschallenge4.utils.Constant

class NoteListFragment : Fragment(), OnItemClickListener {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel

    private val notesViewModel : NotesViewModel by activityViewModels {
        NotesViewModelFactory((requireActivity().application as NotesApplication).repository)
    }

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

        setUpNavigationDrawer()
        setUpSearchView()

        binding.fabAddNotes.setOnClickListener {
            findNavController().navigate(R.id.action_noteListFragment_to_addNotesFragment)
        }

        notesViewModel.setUserEmail(userViewModel.getEmail())
        notesViewModel.notesByUser.observe(viewLifecycleOwner, Observer { notes ->
            setUpRecyclerView(notes)
            notesEmpty(notes)
        })
    }

    private fun setUpRecyclerView(dataset: List<Notes>) {
        val adapter = NotesListAdapter(dataset, this)
        val recyclerView : RecyclerView? = view?.findViewById(R.id.rv_notes_list)
        recyclerView?.adapter = adapter
        setRvLayoutMode(recyclerView)

        val searchBar = binding.searchBar
        searchBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.searchbar_layout -> {
                    switchLayoutMode()
                    setRvLayoutMode(recyclerView)
                    true
                }
                else -> false
            }
        }
    }

    private fun setUpRvSearchView(dataset: List<Notes>) {
        val adapter = NotesListAdapter(dataset, this)
        val recyclerView : RecyclerView? = view?.findViewById(R.id.rv_notes_search)
        recyclerView?.adapter = adapter
        setRvLayoutMode(recyclerView)
    }

    private fun setRvLayoutMode(rv: RecyclerView?) {
        val layoutmode = notesViewModel.getLayoutMode()
        val searchBar = binding.searchBar
        if(layoutmode == "column") {
            rv?.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            searchBar.menu.findItem(R.id.searchbar_layout).setIcon(R.drawable.ic_grid_view_24)
        } else {
            rv?.layoutManager = GridLayoutManager(requireActivity(), 1)
            searchBar.menu.findItem(R.id.searchbar_layout).setIcon(R.drawable.ic_list_view)
        }
    }

    private fun switchLayoutMode() {
        val layoutmode = notesViewModel.getLayoutMode()
        val searchBar = binding.searchBar
        if(layoutmode == "column") notesViewModel.setLayoutMode("standard") else notesViewModel.setLayoutMode("column")
    }

    private fun setUpSearchView() {
        val searchBar = binding.searchBar
        val searchView = binding.searchView
        searchView.setupWithSearchBar(searchBar)

        searchView
            .editText.addTextChangedListener { editText ->
                val textQuery = editText.toString().trim()
                if(textQuery.isNotEmpty()) {
                    notesViewModel.searchNotesByUser(textQuery).observe(viewLifecycleOwner, Observer { notes ->
                        setUpRvSearchView(notes)
                    })
                } else {
                    setUpRvSearchView(emptyList())
                }
            }
    }

    private fun setUpNavigationDrawer() {
        val navDrawer = binding.notesDrawerLayout

        val searchBar = binding.searchBar

        // topappbar navdrawer
        searchBar.setNavigationOnClickListener {
            navDrawer.open()
        }
        val headerNavDrawer = binding.navigationView.inflateHeaderView(R.layout.header_navigation_drawer) // inflate navdrawer with heading
        val userNameHeaderNavDrawer = headerNavDrawer.findViewById<TextView>(R.id.header_username_navdrawer)
        val emailHeaderNavDrawer = headerNavDrawer.findViewById<TextView>(R.id.header_email_navdrawer)

        userNameHeaderNavDrawer.text = userViewModel.getUserName()
        emailHeaderNavDrawer.text = userViewModel.getEmail()

        binding.navigationView.menu.findItem(R.id.navdrawer_mainmenu_home).isChecked = true // set home is checked as default
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.navdrawer_mainmenu_home -> {
                    menuItem.isChecked = true
                    navDrawer.close()
                    true
                }
                R.id.navdrawer_mainmenu_logout -> {
                    userViewModel.logout()
                    Snackbar.make(requireView(), "Logged out successfully!", Snackbar.LENGTH_LONG)
                        .show()
                    findNavController().navigate(R.id.action_noteListFragment_to_loginFragment)
                    true
                }
            }
            true
        }
    }

    private fun notesEmpty(notes: List<Notes>) {
        val emptyText = binding.tvNotesEmpty
        if(notes.isNotEmpty()) {
            emptyText.visibility = View.GONE
        } else {
            emptyText.visibility = View.VISIBLE
        }
    }

    override fun onNotesClicked(notes: Notes) {
        val bundle = Bundle().apply {
            putParcelable(Constant.NOTES_BUNDLE, notes)
        }
        findNavController().navigate(R.id.action_noteListFragment_to_editNotesFragment, bundle)
    }

    override fun onDelete(notes: Notes) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete Notes")
            .setMessage("Are you sure to delete this note?")
            .setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Yes") { dialog, which ->
                notesViewModel.delete(notes)
                Snackbar.make(requireView(), "Notes deleted successfully.", Snackbar.LENGTH_LONG)
                    .show()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}