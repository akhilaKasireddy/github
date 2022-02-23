package com.example.practiseDemo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.practiseDemo.R
import com.example.practiseDemo.adapter.UserAdapter
import com.example.practiseDemo.apiData.Item
import com.example.practiseDemo.database.RoomDbApp
import com.example.practiseDemo.databinding.FragmentStartBinding
import com.example.practiseDemo.retrofit.Repository
import com.example.practiseDemo.viewModel.MyViewModel
import com.example.practiseDemo.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

private lateinit var binding: FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var viewModel: MyViewModel //to get live data
    private lateinit var dbApp: RoomDbApp //to get room data
    private val adapter: UserAdapter by lazy { UserAdapter(this::onClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        dbApp = RoomDbApp.getInstance(requireContext())
        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                Repository(dbApp),
                requireActivity().application
            )
        ).get(MyViewModel::class.java)
        binding.item = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = resources.getString(R.string.StartFragment_text)

        lifecycleScope.launch {
            viewModel.itemsLiveData.observe(viewLifecycleOwner) {
                adapter.submitData(lifecycle, it)
                binding.recyclerview.adapter = adapter
            }
        }
    }

    private fun onClick(item: Item) {
        val action = StartFragmentDirections.actionStartFragmentToSecondFragment(
            item.login, item.avatar_url, item.html_url
        )
        findNavController().navigate(action)
    }


}


