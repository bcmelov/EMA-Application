package com.example.emaapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.emaapp.R
import com.example.emaapp.data.DataSource
import com.example.emaapp.data.User

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {

    //receiving data from the bundle
    private lateinit var bundle : Bundle
    private var name : String? = null
    private val students : List<User> = DataSource.users
    private lateinit var student: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = arguments ?: throw IllegalStateException("View is null.")
        name = bundle.getString("name")
        student = students.find {getString(it.displayName) == name} ?: throw java.lang.IllegalStateException("Student is null.")
        //pomocny log do konzole = bundle funguje
        Log.d("TAG", student.toString())
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recyclerView)
        fillHeader()
        fillSkills()
        fillHomework()
    }

    private fun fillHeader() {
        student.let { view?.findViewById<ImageView>(R.id.user_icon)?.setImageResource(it.displayIcon) }
        student.let { view?.findViewById<TextView>(R.id.user_name)?.setText(it.displayName) }
        setText()
        view?.findViewById<ImageButton>(R.id.slack_icon)?.setOnClickListener {
            val url = Uri.parse(student.slack)
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }

        view?.findViewById<ImageButton>(R.id.email_icon)?.setOnClickListener {
            val mailTo = Uri.parse(student.email)
            val intent = Intent(Intent.ACTION_SEND, mailTo)
            startActivity(Intent.createChooser(intent, "Choose email client to use:"))
        }
        view?.findViewById<ImageButton>(R.id.linkedin_icon)?.setOnClickListener {
            val url = Uri.parse(student.linkedIn)
            val intent = Intent(Intent.ACTION_VIEW, url)
            startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fillSkills() {

        view?.findViewById<ProgressBar>(R.id.progressBarAndroid)?.progress = student.android_skills * 10
        view?.findViewById<TextView>(R.id.progress_android)?.text = "${student.android_skills}/10"
        view?.findViewById<ProgressBar>(R.id.progressBarKotlin)?.progress =
            student.kotlin_skills * 10
        view?.findViewById<TextView>(R.id.progress_kotlin)?.text = "${student.kotlin_skills}/10"
        view?.findViewById<ProgressBar>(R.id.progressBariOS)?.progress = student.iOs_skills * 10
        view?.findViewById<TextView>(R.id.progress_iOS)?.text = "${student.iOs_skills}/10"
        view?.findViewById<ProgressBar>(R.id.progressBarSwift)?.progress = student.swift_skills * 10
        view?.findViewById<TextView>(R.id.progress_swift)?.text = "${student.swift_skills}/10"
    }

    private fun fillHomework() {

        //1. homework
        student.let { view?.findViewById<TextView>(R.id.title_push_1)?.setState(it.push1) }
        student.let { view?.findViewById<TextView>(R.id.title_review_1)?.setState(it.review1) }
        student.let { view?.findViewById<TextView>(R.id.title_acceptance_1)?.setState(it.accepted1) }

        //2. homework
        student.let { view?.findViewById<TextView>(R.id.title_push_2)?.setState(it.push2) }
        student.let { view?.findViewById<TextView>(R.id.title_review_2)?.setState(it.review2) }
        student.let { view?.findViewById<TextView>(R.id.title_acceptance_2)?.setState(it.accepted2) }

        //3. homework
        student.let { view?.findViewById<TextView>(R.id.title_push_3)?.setState(it.push3) }
        student.let { view?.findViewById<TextView>(R.id.title_review_3)?.setState(it.review3) }
        student.let { view?.findViewById<TextView>(R.id.title_acceptance_3)?.setState(it.accepted3) }

        //4. homework
        student.let { view?.findViewById<TextView>(R.id.title_push_4)?.setState(it.push4) }
        student.let { view?.findViewById<TextView>(R.id.title_review_4)?.setState(it.review4) }
        student.let { view?.findViewById<TextView>(R.id.title_acceptance_4)?.setState(it.accepted4) }

        //5. homework
        student.let { view?.findViewById<TextView>(R.id.title_push_5)?.setState(it.push5) }
        student.let { view?.findViewById<TextView>(R.id.title_review_5)?.setState(it.review5) }
        student.let { view?.findViewById<TextView>(R.id.title_acceptance_5)?.setState(it.accepted5) }

        //6. homework
        student.let { view?.findViewById<TextView>(R.id.title_push_6)?.setState(it.push6) }
        student.let { view?.findViewById<TextView>(R.id.title_review_6)?.setState(it.review6) }
        student.let { view?.findViewById<TextView>(R.id.title_acceptance_6)?.setState(it.accepted6) }

    }
}

private fun setText() {
}

private fun TextView.setState(done: Boolean) {
    val id = if (done) R.drawable.ic_done else R.drawable.ic_waiting
    setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
}
