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
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentPregledJelaBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.SaveMealContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.SavedMealAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.SavedMealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.SavedMealViewModel
import timber.log.Timber

class AllSavedMealsFragment : Fragment(R.layout.fragment_list) {

    // Koristimo by sharedViewModel jer sada view modele instanciramo kroz koin
    private val mainViewModel: SaveMealContract.ViewModel by sharedViewModel<SavedMealViewModel>()

    private var _binding: FragmentPregledJelaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: SavedMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPregledJelaBinding.inflate(inflater, container, false)
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
//        binding.filterStranaDugme.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView, FilterFragment())
//                .addToBackStack(null)
//                .commit()
//        }
    }

    private fun initRecycler() {
        binding.svaJela.layoutManager = LinearLayoutManager(context)
        adapter = SavedMealAdapter(mainViewModel as SavedMealViewModel)
        binding.svaJela.adapter = adapter
    }

    private fun initObservers() {
        mainViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mainViewModel.editMeal.observe(viewLifecycleOwner, Observer { clickedItem ->
            Toast.makeText(context,clickedItem.idMeal, Toast.LENGTH_SHORT).show()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FragmentEditMeal(clickedItem))
                .addToBackStack(null)
                .commit()
        })
        mainViewModel.deleteMeal.observe(viewLifecycleOwner, Observer { clickedItem ->
            Toast.makeText(context,clickedItem.idMeal, Toast.LENGTH_SHORT).show()
            mainViewModel.delete(clickedItem.idMeal)
        })
        mainViewModel.getAll()
    }

    private fun renderState(state: SavedMealsState) {
        when (state) {
            is SavedMealsState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is SavedMealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is SavedMealsState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is SavedMealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.svaJela.isVisible = !loading
        binding.progressBar.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}