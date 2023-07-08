package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.activities

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.ActivityMainBinding
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.FirstFragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedPreference: SharedPreferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val splashScreen: SplashScreen = this.installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
            false
        }
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        if(sharedPreference.getString("username",null) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FirstFragment())
                .commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, LoginFragment())
                .commit()
        }
    }

}