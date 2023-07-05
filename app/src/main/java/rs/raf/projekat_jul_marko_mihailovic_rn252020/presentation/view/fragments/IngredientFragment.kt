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
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentSearchIngredientBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.IngredientContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.MealShortAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.IngredientViewModel
import timber.log.Timber

class IngredientFragment: Fragment(R.layout.fragment_search_ingredient) {

    private val viewModel: IngredientContract.ViewModel by sharedViewModel<IngredientViewModel>()

    private var _binding: FragmentSearchIngredientBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealShortAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchIngredientBinding.inflate(inflater, container, false)
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

        binding.recyclerViewIngredientName.layoutManager = LinearLayoutManager(context)
        adapter = MealShortAdapter()
        binding.recyclerViewIngredientName.adapter = adapter
    }

    private fun initListeners() {
        binding.ingredientNameEditText.doAfterTextChanged {
            val filter = it.toString()
            viewModel.fetchAllMeals(filter)
            viewModel.getMealsByIngredient(filter)
        }
    }

    private fun initObservers() {
        viewModel.mealsShortState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        viewModel.getAllMeals()
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
    }

    private fun renderState(state: MealsShortState) {
        when (state) {
            is MealsShortState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.movies)
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
        binding.ingredientNameEditText.isVisible = !loading
        binding.recyclerViewIngredientName.isVisible = !loading
        binding.loadingAnimation1.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}