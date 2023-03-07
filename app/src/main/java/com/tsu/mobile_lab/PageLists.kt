package com.tsu.mobile_lab

object PageLists {
    private const val desc = "Quarantine is the perfect time to spend your \n day learning something new, from anywhere!"

    val introSlides = listOf(
        Intro(R.drawable.intro_one, "Learn anytime\nand anywhere", desc),
        Intro(R.drawable.intro_two, "Find a course\nfor you", desc),
        Intro(R.drawable.intro_three, "Improve your skills", desc)
    )
}