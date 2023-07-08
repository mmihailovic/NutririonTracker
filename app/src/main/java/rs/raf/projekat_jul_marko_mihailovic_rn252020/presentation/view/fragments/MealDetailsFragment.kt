package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.Meal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentMealDetailsBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.MealDetailsContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.MealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.MealDetailsViewModel
import timber.log.Timber

class MealDetailsFragment(
    private val idMeal: String
) : Fragment(R.layout.fragment_meal_details) {
    private val mainViewModel: MealDetailsContract.ViewModel by sharedViewModel<MealDetailsViewModel>()
    private var _binding: FragmentMealDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealDetailsBinding.inflate(inflater, container, false)
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
        initListeners()
    }

    private fun initListeners() {
    }
    private fun sastojak(stringBuilder: StringBuilder, sastojak: String?, mera: String?) {
        if(sastojak != null) {
            stringBuilder.append(sastojak + " " + mera + "\n")
        }
    }

    private fun loadData(meal: Meal) {
        binding.link.text = meal.strYoutube
        binding.kategorija.text = meal.strCategory
        binding.nazivJela.text = meal.strMeal
        binding.oblast.text = meal.strArea
        binding.priprema.text = meal.strInstructions
        binding.tagovi.text = meal.strTags
        Picasso.get().load(meal.strMealThumb).into(binding.slika)
        val stringBuilder = StringBuilder()
        sastojak(stringBuilder, meal.strIngredient1, meal.strMeasure1)
        sastojak(stringBuilder, meal.strIngredient2, meal.strMeasure2)
        sastojak(stringBuilder, meal.strIngredient3, meal.strMeasure3)
        sastojak(stringBuilder, meal.strIngredient4, meal.strMeasure4)
        sastojak(stringBuilder, meal.strIngredient5, meal.strMeasure5)
        sastojak(stringBuilder, meal.strIngredient6, meal.strMeasure6)
        sastojak(stringBuilder, meal.strIngredient7, meal.strMeasure7)
        sastojak(stringBuilder, meal.strIngredient8, meal.strMeasure8)
        sastojak(stringBuilder, meal.strIngredient9, meal.strMeasure9)
        sastojak(stringBuilder, meal.strIngredient10, meal.strMeasure10)
        sastojak(stringBuilder, meal.strIngredient11, meal.strMeasure11)
        sastojak(stringBuilder, meal.strIngredient12, meal.strMeasure12)
        sastojak(stringBuilder, meal.strIngredient13, meal.strMeasure13)
        sastojak(stringBuilder, meal.strIngredient14, meal.strMeasure14)
        sastojak(stringBuilder, meal.strIngredient15, meal.strMeasure15)
        sastojak(stringBuilder, meal.strIngredient16, meal.strMeasure16)
        sastojak(stringBuilder, meal.strIngredient17, meal.strMeasure17)
        sastojak(stringBuilder, meal.strIngredient18, meal.strMeasure18)
        sastojak(stringBuilder, meal.strIngredient19, meal.strMeasure19)
        sastojak(stringBuilder, meal.strIngredient20, meal.strMeasure20)
        stringBuilder.deleteCharAt(stringBuilder.length - 1)
        binding.sastojci.text = stringBuilder.toString()
    }

    private fun initObservers() {
        mainViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mainViewModel.get(idMeal)
        mainViewModel.fetch(idMeal)
    }

    private fun renderState(state: MealsState) {
        when (state) {
            is MealsState.Success -> {
                showLoadingState(false)
                loadData(state.movies.get(0))
            }
            is MealsState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealsState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealsState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
//        binding.inputEt.isVisible = !loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}