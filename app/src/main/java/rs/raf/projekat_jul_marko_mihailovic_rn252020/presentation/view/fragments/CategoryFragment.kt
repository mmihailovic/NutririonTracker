package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.MealCategoryAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsCateogoryState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentListBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.MainContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class CategoryFragment : Fragment(R.layout.fragment_list) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MainViewModel>()

    private var _binding: FragmentListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initListeners() {
        binding.filterStranaDugme.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FilterFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.pregledJela.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AllSavedMealsFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.statistika.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, StatistikaFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.plan.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FormFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun initRecycler() {
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = MealCategoryAdapter(mainViewModel as MainViewModel)
        binding.listRv.adapter = adapter
    }

    private fun initObservers() {
        mainViewModel.mealsCategoryState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mainViewModel.clickedItem.observe(viewLifecycleOwner, Observer { clickedItem ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MealsForCategoryFragment(clickedItem.category))
                .addToBackStack(null)
                .commit()
        })
        mainViewModel.getAllMovies()
        mainViewModel.fetchAllMovies()
    }

    private fun renderState(state: MealsCateogoryState) {
        when (state) {
            is MealsCateogoryState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.movies)
            }
            is MealsCateogoryState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsCateogoryState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsCateogoryState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.listRv.isVisible = !loading
        binding.loadingPb.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}