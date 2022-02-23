package com.example.practiseDemo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.practiseDemo.R
import com.example.practiseDemo.databinding.FragmentEndBinding

private lateinit var binding:FragmentEndBinding
class EndFragment : Fragment() {
    private val args: EndFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_end,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title=resources.getString(R.string.EndFragment_text)
        binding.toolbar.navigationIcon=ContextCompat.getDrawable(requireActivity(),R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.webView.loadUrl(args.url)


    }
}