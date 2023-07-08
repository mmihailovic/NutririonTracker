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
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentCategoryMealsBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.MealsForCategoryContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.MealShortAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsShortState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.MealsForCategoryViewModel
import timber.log.Timber

class CategoryMealsFragment(
    private val category: String
): Fragment(R.layout.fragment_category_meals) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: MealsForCategoryContract.ViewModel by sharedViewModel<MealsForCategoryViewModel>()

    private var _binding: FragmentCategoryMealsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealShortAdapter
    private var page: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryMealsBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        Timber.e(category)
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()
    }

    private fun initRecycler() {
        binding.mealsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MealShortAdapter(mainViewModel as MealsForCategoryViewModel)
        binding.mealsRecyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.prev.setOnClickListener {
            if(page > 1) {
                page--
                mainViewModel.getAllMealsWithPagination((page - 1) * 10)
                binding.strana.text = page.toString()
            }
        }
        binding.next.setOnClickListener {
            page++
            mainViewModel.getAllMealsWithPagination((page - 1) * 10)
            binding.strana.text = page.toString()
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
        mainViewModel.getAllMealsWithPagination(1)
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
        mainViewModel.fetchAllMeals(category)
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
        binding.mealsRecyclerView.isVisible = !loading
        binding.loadingAnimation2.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}