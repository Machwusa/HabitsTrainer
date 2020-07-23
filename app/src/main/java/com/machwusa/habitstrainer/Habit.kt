package com.machwusa.habitstrainer

data class Habit(val title: String, val description: String, val image: Int)

fun getSampleHabits(): List<Habit>{
    return listOf(Habit("Go for a walk", "A nice walk to clear the head", R.drawable.walking),
                    Habit("A glass of water", "A refreshing glass of water gets you hydrated", R.drawable.water)
    )
}