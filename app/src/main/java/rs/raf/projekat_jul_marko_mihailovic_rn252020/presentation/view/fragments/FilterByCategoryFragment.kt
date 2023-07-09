package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentFilterBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.FilterContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.MealShortAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FilterViewModel
import timber.log.Timber

class FilterByCategoryFragment : Fragment(R.layout.fragment_filter) {

    private val mainViewModel: FilterContract.ViewModel by sharedViewModel<FilterViewModel>()

    private var _binding: FragmentFilterBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealShortAdapter
    private var page: Int = 1
    private var sort: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
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

    private fun initRecycler() {

        binding.filterMeals.layoutManager = LinearLayoutManager(context)
        adapter = MealShortAdapter(mainViewModel as FilterViewModel)
        binding.filterMeals.adapter = adapter
    }

    private fun initListeners() {
        binding.filter.doAfterTextChanged {
            val filter = it.toString()
            mainViewModel.filterByCategory(filter)
        }
        binding.filterByName.doAfterTextChanged {
            val filter = it.toString()
            mainViewModel.filterByName(filter)
        }
        binding.prevButton.setOnClickListener {
            if(page > 1) {
                page--
                mainViewModel.getAllWithPagination((page - 1) * 10)
            }
        }
        binding.nextButton.setOnClickListener {
            page++
            mainViewModel.getAllWithPagination((page - 1) * 10)
        }
        binding.sortiranje.setOnClickListener {
            sort = !sort
            mainViewModel.getAllWithPagination(pocetak = page)
        }
    }

    private fun initObservers() {
        mainViewModel.mealsShortState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mainViewModel.clickedItem.observe(viewLifecycleOwner, Observer { clickedItem ->
            Toast.makeText(context,clickedItem.idMeal, Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MealDetailsFragment(idMeal = clickedItem.idMeal))
                .addToBackStack(null)
                .commit()
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        mainViewModel.getAllWithPagination(0)
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
    }

    private fun renderState(state: MealsShortState) {
        when (state) {
            is MealsShortState.Success -> {
                showLoadingState(false)
                if(sort)adapter.submitList(state.movies.sorted())
                else adapter.submitList(state.movies)
            }
            is MealsShortState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsShortState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsShortState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.filterMeals.isVisible = !loading
        binding.filter.isVisible = !loading
        binding.loadingAnimation3.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}