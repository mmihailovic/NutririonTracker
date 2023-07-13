package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentBiranjeBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.FormContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.SavedMealAdapterForPlan
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.SavedMealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FormViewModel
import timber.log.Timber


class KreiranjePlanaBazaFragment : Fragment(R.layout.fragment_biranje) {

    private val viewModel: FormContract.ViewModel by sharedViewModel<FormViewModel>()

    private var _binding: FragmentBiranjeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: SavedMealAdapterForPlan
    private lateinit var dan: String

    companion object {
        const val prvi_dan = "PRVI DAN"
        const val drugi_dan = "DRUGI DAN"
        const val treci_dan = "TRECI DAN"
        const val cetvrti_dan = "CETVRTI DAN"
        const val peti_dan = "PETI DAN"
        const val sesti_dan = "SESTI DAN"
        const val sedmi_dan = "SEDMI DAN"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBiranjeBinding.inflate(inflater, container, false)
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
        initSpinner()
        initRecycler()
        initListeners()
    }

    private fun initSpinner() {
        val boje = arrayOf(prvi_dan, drugi_dan, treci_dan, cetvrti_dan, peti_dan, sesti_dan, sedmi_dan)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, boje)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                dan = parent.getItemAtPosition(position) as String
                // Ovdje možete izvršiti željene radnje na temelju odabrane stavke
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Poziva se kada nijedna stavka nije odabrana
            }
        }
    }

    private fun initRecycler() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SavedMealAdapterForPlan(viewModel = viewModel as FormViewModel)
        binding.recyclerView.adapter = adapter
    }

    private fun initListeners() {
        binding.button.setOnClickListener {
            val obrok: String
            if(binding.dorucak.isChecked)
                obrok = "DORUCAK"
            else if(binding.uzina.isChecked)
                obrok = "UZINA"
            else if(binding.rucak.isChecked)
                obrok = "RUCAK"
            else obrok = "VECERA"
            viewModel.addMeal(dan,obrok)
        }
    }

    private fun initObservers() {
        viewModel.savedMealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
        // koji zadovoljavaju query
        viewModel.getAllFromDatabase()
        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
        // preko metode getAllMovies()
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
//        binding.ingredientNameEditText.isVisible = !loading
//        binding.recyclerViewIngredientName.isVisible = !loading
//        binding.loadingAnimation1.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}