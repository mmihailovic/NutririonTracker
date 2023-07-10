package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.SavedMeal
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentSaveMealBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.SaveMealContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.AddMealState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.SavedMealViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class FragmentEditMeal (
    private val meal: SavedMeal
): Fragment(R.layout.fragment_save_meal) {

    private lateinit var datePickerDialog: DatePickerDialog
    private val mainViewModel: SaveMealContract.ViewModel by sharedViewModel<SavedMealViewModel>()
    private var _binding: FragmentSaveMealBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    companion object {
        const val DORUCAK = "DORUCAK"
        const val RUCAK = "RUCAK"
        const val UZINA = "UZINA"
        const val VECERA = "VECERA"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSaveMealBinding.inflate(inflater, container, false)
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
        initData()
        initDatePicker()
    }

    private fun initDatePicker() {
        datePickerDialog = DatePickerDialog(requireContext(),{view,year,month,dayOfMonth ->
            this.year = year
            this.month = month
            this.day = dayOfMonth
            binding.buttonDatePicker.text = String.format("%d.%d.%d", dayOfMonth, month, year)
        },year,month,day)
    }

    private fun initData() {
        val dateTime = Instant.ofEpochSecond(meal.date)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        day = dateTime.dayOfMonth
        month = dateTime.monthValue
        year = dateTime.year
        Picasso.get().load(meal.strMealThumb).into(binding.imageView3)
        binding.mealName.text = meal.strMeal
        binding.buttonDatePicker.text = String.format("%d.%d.%d", day, month, year)
        binding.dorucak.isSelected = true
    }

    private fun initListeners() {
//        binding.buttonSaveMeal.setOnClickListener {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView, SaveMealFragment(mealDetails))
//                .addToBackStack(null)
//                .commit()
//        }
        binding.buttonDatePicker.setOnClickListener {
            datePickerDialog.show()
        }
        binding.buttonSaveMeal.setOnClickListener {
            val obrok: String
            if(binding.dorucak.isChecked)
                obrok = DORUCAK
            else if(binding.rucak.isChecked)
                obrok = RUCAK
            else if(binding.uzina.isChecked)
                obrok = UZINA
            else obrok = VECERA

            val savedMeal = SavedMeal(
                meal.idMeal,
                meal.strMeal,
                meal.strCategory,
                meal.strInstructions,
                meal.strMealThumb,
                meal.strYoutube,
                obrok,
                LocalDate.of(year,month,day).atStartOfDay(ZoneId.systemDefault()).toEpochSecond(),
                meal.strIngredient1,
                meal.strIngredient2,
                meal.strIngredient3,
                meal.strIngredient4,
                meal.strIngredient5,
                meal.strIngredient6,
                meal.strIngredient7,
                meal.strIngredient8,
                meal.strIngredient9,
                meal.strIngredient10,
                meal.strIngredient11,
                meal.strIngredient12,
                meal.strIngredient13,
                meal.strIngredient14,
                meal.strIngredient15,
                meal.strIngredient16,
                meal.strIngredient17,
                meal.strIngredient18,
                meal.strIngredient19,
                meal.strIngredient20,
                meal.strMeasure1,
                meal.strMeasure2,
                meal.strMeasure3,
                meal.strMeasure4,
                meal.strMeasure5,
                meal.strMeasure6,
                meal.strMeasure7,
                meal.strMeasure8,
                meal.strMeasure9,
                meal.strMeasure10,
                meal.strMeasure11,
                meal.strMeasure12,
                meal.strMeasure13,
                meal.strMeasure14,
                meal.strMeasure15,
                meal.strMeasure16,
                meal.strMeasure17,
                meal.strMeasure18,
                meal.strMeasure19,
                meal.strMeasure20
            )
            mainViewModel.update(savedMeal)
        }
    }

    private fun initObservers() {
//        mainViewModel.saveMealState.observe(viewLifecycleOwner, Observer {
//            Timber.e(it.toString())
//            renderState(it)
//        })
    }

    private fun renderState(state: AddMealState) {
        when (state) {
            is AddMealState.Success -> {
                showLoadingState(false)
                Toast.makeText(context, "Jelo uspesno sacuvano u meni!", Toast.LENGTH_LONG).show()
            }
            is AddMealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
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