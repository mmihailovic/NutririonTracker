package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.FragmentLoginBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.contract.UserContract
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.states.UsersState
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.viewmodel.UserViewModel
import timber.log.Timber

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val mainViewModel: UserContract.ViewModel by sharedViewModel<UserViewModel>()
    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val sharedPreference: SharedPreferences by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        mainViewModel.usersState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        binding.buttonLogin.setOnClickListener {
            val username: String = binding.usernameEditText.text.toString()
            val password: String = binding.passwordEditText.text.toString()
            mainViewModel.get(username, password)
        }
    }

    private fun renderState(state: UsersState) {
        when (state) {
            is UsersState.Success -> {
                sharedPreference.edit().putString("username",state.user.username).apply()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, FirstFragment())
                    .addToBackStack(null)
                    .commit()
            }
            is UsersState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                Timber.e("Greska pri ucitavanju podataka")
            }
            is UsersState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is UsersState.Loading -> {
                Timber.e("ucitavaju se podaci")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}