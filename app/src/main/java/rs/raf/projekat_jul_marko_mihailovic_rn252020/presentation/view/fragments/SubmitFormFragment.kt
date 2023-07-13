package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models.MealForPlan
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentFormaBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.FormContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.recycler.adapter.MealForPlanAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.FormViewModel

class SubmitFormFragment: Fragment(R.layout.fragment_forma) {
    private val viewModel: FormContract.ViewModel by sharedViewModel<FormViewModel>()

    private var _binding: FragmentFormaBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var adapter: MealForPlanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormaBinding.inflate(inflater, container, false)
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
        initRecycler()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.meals.observe(viewLifecycleOwner, Observer {
            val allMeals: List<MealForPlan> = it.flatMap { it.second }
            adapter.submitList(allMeals)
        })
    }

    private fun initRecycler() {
        binding.recyclerView2.layoutManager = LinearLayoutManager(context)
        adapter = MealForPlanAdapter()
        binding.recyclerView2.adapter = adapter

    }

    private fun initListeners() {
        binding.submitButton.setOnClickListener {
            val recipientEmail = binding.email.text.toString()
            val subject = "Plan ishrane"
            val body: String
            val stringBuilder = StringBuilder()
            var dan: String = " "
            for(meal in adapter.currentList) {
                if(meal.day != dan) {
                    dan = meal.day
                    stringBuilder.append(dan+"\n")
                }
                stringBuilder.append(meal.meal + " " + meal.mealName + "\t\n")
            }
            stringBuilder.append("Link: http://myappformealplan")
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:"+recipientEmail)
                putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
            }

            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}