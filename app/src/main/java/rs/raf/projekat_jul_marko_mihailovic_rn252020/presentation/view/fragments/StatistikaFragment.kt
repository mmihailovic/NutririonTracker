package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentStatistikaBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.StatistikaContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.CountMealsState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.StatistikaViewModel
import timber.log.Timber
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

class StatistikaFragment: Fragment(R.layout.fragment_statistika) {
    private var _binding: FragmentStatistikaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val calendar = Calendar.getInstance()
    private val mainViewModel: StatistikaContract.ViewModel by sharedViewModel<StatistikaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatistikaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    companion object {
        const val duration = 1000L
        val barSetBaza = mutableListOf<Pair<String, Float>>()
    }

    private fun init() {
        initObservers()
        initData()
    }

    private fun initObservers() {
        mainViewModel.mealsState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: CountMealsState) {
        when (state) {
            is CountMealsState.Success -> {
                calendar.add(Calendar.DAY_OF_MONTH,-7)
                for(n in state.count) {
                    val dateTime = Instant.ofEpochSecond(n.date)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()

                    val day = dateTime.dayOfMonth
                    val month = dateTime.monthValue
                    val year = dateTime.year
                    Timber.e(String.format("%d.%d.%d iz baze izvuceno",day,month,year))
                    val today = Calendar.getInstance()
                    today.set(Calendar.DAY_OF_MONTH,day)
                    today.set(Calendar.MONTH,month - 1)
                    today.set(Calendar.YEAR,year)
                    Timber.e("pocetno vreme kalendara " + calendar.get(Calendar.DAY_OF_MONTH) + "." + calendar.get(Calendar.MONTH))
                    while (calendar.before(today)) {
                        Timber.e("dodat dan")
                        barSetBaza.add(String.format("%d.%d.%d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.YEAR)) to 0F)
                        calendar.add(Calendar.DAY_OF_MONTH,1)
                    }
                    barSetBaza.removeLast()
                    barSetBaza.add(String.format("%d.%d.%d",day,month,year) to n.count.toFloat())
                }
                val today = Calendar.getInstance()
                while (calendar.before(today) || calendar == today) {
                    barSetBaza.add(String.format("%d.%d.%d",calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR)) to 0F)
                    calendar.add(Calendar.DAY_OF_MONTH,1)
                }
                binding.chartHorizontal.animation.duration = duration
                binding.chartHorizontal.animate(barSetBaza)

            }
            is CountMealsState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is CountMealsState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is CountMealsState.Loading -> {
            }
        }
    }

    private fun initData() {
        val today = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH,-6)
        val dates =  mutableListOf<Long>()
        while (calendar.before(today) || calendar == today) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            dates.add(LocalDate.of(year, month, day).atStartOfDay(ZoneId.systemDefault()).toEpochSecond())
            calendar.add(Calendar.DAY_OF_MONTH,1)
        }
        Timber.e("datumi")
        for(date in dates) {
            Timber.e(date.toString())
        }
        mainViewModel.count(dates)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}