package com.example.app7

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.app7.databinding.ActivityGameBinding
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    private var currentIndex = 0
    private var optionList = mutableListOf<TextView>()
    private val resulTVList = mutableListOf<TextView>()
    private val userAnswerList = mutableListOf<UserAnswer>()
    private var coins = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvCoins.text = coins.toString()
        binding.submitBtn.visibility = View.GONE


        animationImages()

        setQuestions()

        letterPlacement()


    }
    private fun animationImages() {
        binding.iv11.setOnClickListener {
            binding.ivBigImage.setImageResource(QuestionList.getQuestions()[currentIndex % QuestionList.getQuestions().size].pictureList[0])
            binding.ivBigImage.visibility = View.VISIBLE
            binding.ivBigImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up_1))

            binding.ivBigImage.setOnClickListener {
                binding.ivBigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this,
                        R.anim.scale_down_1
                    )
                )
                binding.ivBigImage.visibility = View.GONE
            }
        }

        binding.iv12.setOnClickListener {
            binding.ivBigImage.setImageResource(QuestionList.getQuestions()[currentIndex % QuestionList.getQuestions().size].pictureList[1])
            binding.ivBigImage.visibility = View.VISIBLE
            binding.ivBigImage.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up_2))

            binding.ivBigImage.setOnClickListener {
                binding.ivBigImage.startAnimation(
                    AnimationUtils.loadAnimation(
                        this,
                        R.anim.scale_down_2
                    )
                )
                binding.ivBigImage.visibility = View.GONE
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setQuestions() {
        val question = QuestionList.getQuestions()[currentIndex % QuestionList.getQuestions().size]
        binding.tvLevel.text = (currentIndex+ 1).toString()
        binding.iv11.setImageResource(question.pictureList[0])
        binding.iv12.setImageResource(question.pictureList[1])


        val answer = question.answer
        val answerOptions = answer.toCharArray().toMutableList()

        repeat(12 - answer.length) {
            answerOptions.add(Random.nextInt(65, 91).toChar())
        }

        answerOptions.shuffle()

        fillOptionList()

        for (i in optionList.indices) {
            optionList[i].text = answerOptions[i].toString()
        }

        fillResultTVList()
        for (i in 0..7) {
            if (i < answer.length) {
                resulTVList[i].visibility = View.VISIBLE
            } else resulTVList[i].visibility = View.GONE
            binding.submitBtn.visibility = View.GONE

        }

    }

    private fun fillOptionList() {
        optionList.add(binding.tvEnter01)
        optionList.add(binding.tvEnter02)
        optionList.add(binding.tvEnter03)
        optionList.add(binding.tvEnter04)
        optionList.add(binding.tvEnter05)
        optionList.add(binding.tvEnter06)
        optionList.add(binding.tvEnter07)
        optionList.add(binding.tvEnter08)
        optionList.add(binding.tvEnter09)
        optionList.add(binding.tvEnter10)
        optionList.add(binding.tvEnter11)
        optionList.add(binding.tvEnter12)
    }

    private fun fillResultTVList() {
        resulTVList.add(binding.tvResult01)
        resulTVList.add(binding.tvResult02)
        resulTVList.add(binding.tvResult03)
        resulTVList.add(binding.tvResult04)
        resulTVList.add(binding.tvResult05)
        resulTVList.add(binding.tvResult06)
        resulTVList.add(binding.tvResult07)
        resulTVList.add(binding.tvResult08)
    }

    private fun letterPlacement() {
        fillUserAnswerList()
        optionList.forEach { tv ->
            tv.setOnClickListener {
                for (index in resulTVList.indices) {
                    if (resulTVList[index].text.isEmpty() && index < QuestionList.getQuestions()[currentIndex % QuestionList.getQuestions().size].answer.length) {
                        userAnswerList[index] = UserAnswer(tv.text.toString(), tv)
                        resulTVList[index].text = tv.text.toString()
                        resulTVList[index].startAnimation(
                            AnimationUtils.loadAnimation(
                                this,
                                R.anim.letters_up
                            )
                        )
                        tv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.letters_down))
                        tv.visibility = View.INVISIBLE
                        checkAnswer()
                        break
                    }
                }
            }
        }

        resulTVList.forEach { rtv ->
            rtv.setOnClickListener {
                userAnswerList[resulTVList.indexOf(rtv)].optionTV.visibility = View.VISIBLE
                userAnswerList[resulTVList.indexOf(rtv)].optionTV.startAnimation(
                    AnimationUtils.loadAnimation(
                        this,
                        R.anim.letters_up
                    )
                )
                rtv.text = ""
            }
        }

    }

    private fun fillUserAnswerList() {
        repeat(8) {
            userAnswerList.add(UserAnswer("", binding.tvLevel))
        }
    }
    private fun checkAnswer() {
        val answer = QuestionList.getQuestions()[currentIndex % QuestionList.getQuestions().size].answer
        var userAnswer = ""
        repeat(answer.length) {
            userAnswer += resulTVList[it].text
        }

        if (userAnswer.length == answer.length){
            binding.submitBtn.visibility = View.VISIBLE
            if (userAnswer == answer) {
                binding.llResult.visibility = View.VISIBLE
                try {
                    animationCircle()
                } catch (e: Exception) {
                    Toast.makeText(this, "animationCircle", Toast.LENGTH_SHORT).show()
                }
                binding.submitBtn.setOnClickListener {
                    forNextQuestion()
                    currentIndex++
                    setQuestions()
                    calculateCoins()
                    binding.animationCircle.visibility = View.GONE
                }
            } else {
                binding.submitBtn.visibility = View.GONE
                binding.llResult.startAnimation(
                    AnimationUtils.loadAnimation(
                        this,
                        R.anim.shake_animation
                    )
                )
            }
        }
    }

    private fun forNextQuestion() {
        optionList.forEach {
            it.visibility = View.VISIBLE
        }
        repeat(12) {
            optionList.removeAt(0)
        }
        resulTVList.forEach {
            it.text = ""
        }
        repeat(8) {
            userAnswerList.removeAt(0)
        }
        fillUserAnswerList()
    }

    private fun calculateCoins() {
        coins += 10

        binding.tvCoins.text = coins.toString()
    }

    private fun animationCircle(){
        binding.animationCircle.visibility = View.VISIBLE
        binding.animationCircle.repeatCount = 5
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
