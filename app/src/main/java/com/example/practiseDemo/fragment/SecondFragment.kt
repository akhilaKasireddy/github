package com.example.practiseDemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.practiseDemo.R
import com.example.practiseDemo.databinding.FragmentSecondBinding
import com.example.practiseDemo.extensions.setImageUrl

private lateinit var binding: FragmentSecondBinding

class SecondFragment : Fragment() {
    private val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = resources.getString(R.string.SecondFragment_text)
        binding.toolbar.navigationIcon =
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.textView.text = args.name
        binding.url.text = args.repo
//        setImageUrl(binding.imageView,args.image)
        binding.imageView.setImageUrl(args.image)
        binding.url.setOnClickListener {
            val action = SecondFragmentDirections.actionSecondFragmentToEndFragment(args.repo)
            findNavController().navigate(action)
        }

    }

}