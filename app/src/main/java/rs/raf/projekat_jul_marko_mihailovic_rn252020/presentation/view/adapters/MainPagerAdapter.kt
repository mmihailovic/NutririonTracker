package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.MealNameFragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.CategoryFragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.IngredientFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 3
        const val FRAGMENT_1 = 0
        const val FRAGMENT_2 = 1
        const val FRAGMENT_3 = 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            FRAGMENT_1 -> CategoryFragment()
            FRAGMENT_2 -> MealNameFragment()
            else -> IngredientFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            FRAGMENT_1 -> context.getString(R.string.meal_categories)
            FRAGMENT_2 -> context.getString(R.string.searchByMealName)
            else -> context.getString(R.string.searchByIngredientName)
        }
    }

}