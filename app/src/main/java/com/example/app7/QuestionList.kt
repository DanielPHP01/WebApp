package com.example.app7

object QuestionList {

    fun getQuestions(): MutableList<Question> {
        val question = mutableListOf<Question>()

        question.add(
            Question(
                id = 0,
                pictureList = mutableListOf(
                    R.drawable.img_1_1,
                    R.drawable.img_1_2
                ),
                answer = "FOOTBALL"
            )
        )

        question.add(
            Question(
                id = 1,
                pictureList = mutableListOf(
                    R.drawable.img_2_1,
                    R.drawable.img_2_2
                ),
                answer = "TRIATLON"
            )
        )

        question.add(
            Question(
                id = 2,
                pictureList = mutableListOf(
                    R.drawable.img_3_1,
                    R.drawable.img_3_2
                ),
                answer = "FORMULA1"
            )
        )

        question.add(
            Question(
                id = 3,
                pictureList = mutableListOf(
                    R.drawable.img_4_1,
                    R.drawable.img_4_2
                ),
                answer = "AIRSOFT"
            )
        )

        question.add(
            Question(
                id = 4,
                pictureList = mutableListOf(
                    R.drawable.img_5_1,
                    R.drawable.img_5_2
                ),
                answer = "TENNIS"
            )
        )
        question.add(
            Question(
                id = 5,
                pictureList = mutableListOf(
                    R.drawable.img_6_1,
                    R.drawable.img_6_2
                ),
                answer = "JUDO"
            )
        )
        question.add(
            Question(
                id = 6,
                pictureList = mutableListOf(
                    R.drawable.img_7_1,
                    R.drawable.img_7_2
                ),
                answer = "KERLING"
            )
        )
        question.add(
            Question(
                id = 7,
                pictureList = mutableListOf(
                    R.drawable.img_8_1,
                    R.drawable.img_8_2
                ),
                answer = "JUMPING"
            )
        )
        question.add(
            Question(
                id = 8,
                pictureList = mutableListOf(
                    R.drawable.img_9_1,
                    R.drawable.img_9_2
                ),
                answer = "POLO"
            )
        )
        question.add(
            Question(
                id = 9,
                pictureList = mutableListOf(
                    R.drawable.img_10_1,
                    R.drawable.img_10_2
                ),
                answer = "DARTS"
            )
        )
        return question
    }
}