package rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import rs.raf.projekat_jul_marko_mihailovic_rn252020.R
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.CategoryMealsFragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.IngredientFragment
import rs.raf.projekat_jul_marko_mihailovic_rn252020.presentation.view.fragments.MealNameFragment

class MealsForCategoryAdapter (
    private val category: String,
    fragmentManager: FragmentManager,
    private val context: Context
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        private const val ITEM_COUNT = 3
        const val FRAGMENT_MEALS_FOR_CATEGORY = 0
        const val FRAGMENT_SEARCH_NAME = 1
        const val FRAGMENT_INGREDIENT = 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            FRAGMENT_MEALS_FOR_CATEGORY -> CategoryMealsFragment(category)
            FRAGMENT_SEARCH_NAME -> MealNameFragment()
            else -> IngredientFragment()
        }
    }

    override fun getCount(): Int {
        return ITEM_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            FRAGMENT_MEALS_FOR_CATEGORY -> context.getString(R.string.meals)
            FRAGMENT_SEARCH_NAME -> context.getString(R.string.searchByMealName)
            else -> context.getString(R.string.searchByIngredientName)
        }
    }
}