package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentKreiranjePlanaBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.adapters.KreiranjePlanaAdapter

class FormFragment: Fragment(R.layout.fragment_kreiranje_plana) {


    private var _binding: FragmentKreiranjePlanaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKreiranjePlanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        binding.viewPagerKreiranjePlana.adapter =
            KreiranjePlanaAdapter(
                parentFragmentManager,
                requireContext()
            )
        binding.tabLayoutKreiranjePlana.setupWithViewPager(binding.viewPagerKreiranjePlana)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}