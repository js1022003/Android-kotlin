package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val push_up = ExerciseModel(0,
            "push up",
            R.drawable.ic_push_up,
        )
        exerciseList.add(push_up)

        val squat = ExerciseModel(1,
            "squat",
            R.drawable.ic_squat,
        )
        exerciseList.add(squat)

        return exerciseList
    }
}