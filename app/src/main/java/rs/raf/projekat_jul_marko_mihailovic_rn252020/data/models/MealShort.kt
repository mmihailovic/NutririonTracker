package rs.raf.projekat_jul_marko_mihailovic_rn252020.data.models

data class MealShort (
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
) : Comparable<MealShort> {
    override fun compareTo(other: MealShort): Int {
        return strMeal.compareTo(other.strMeal)
    }
}
