package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentMealsForCategoryBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.adapters.FilterAdapter

class FilterFragment : Fragment(R.layout.fragment_meals_for_category) {


    private var _binding: FragmentMealsForCategoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealsForCategoryBinding.inflate(inflater, container, false)
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
        binding.viewPagerForMeals.adapter =
            FilterAdapter(
                parentFragmentManager,
                requireContext()
            )
        binding.tabs.setupWithViewPager(binding.viewPagerForMeals)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}