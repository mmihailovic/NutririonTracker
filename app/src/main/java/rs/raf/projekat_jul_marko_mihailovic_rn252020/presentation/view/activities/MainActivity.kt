package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.adapters.MainPagerAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
        println("dsa")
    }

    private fun initUi() {
        binding.viewPager.adapter =
            MainPagerAdapter(
                supportFragmentManager,
                this
            )
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

}