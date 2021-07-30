package com.example.studyapp

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTextView()
    }

    /*fun initImageView(){
        imageView = findViewById(R.id.id_imageview_icon)
        imageView.setImageResource(R.drawable.original)
    }*/


    private fun setTextView(){
        textView = findViewById(R.id.textViewAgreement)
        textView.text = "Text from code"
        textView.myFunForSetColor(R.color.black, null)
        setAgreementText()
    }


    fun setAgreementText(){
        val fullText = getString(R.string.full_text)
        val privacyPolicyText = getString(R.string.privacy_policy_text)
        val rulesText = getString(R.string.rules_text)
        val conditionsText = getString(R.string.conditions_text)

        val spannableString = SpannableString(fullText)

        val myClickableSpanConditions = MyClickableSpanConditions()
        val myClickableSpanPrivacyPolicy = MyClickableSpanPrivacyPolicy()
        val myClickableSpanRules = MyClickableSpanRules()

        spannableString.setSpan(myClickableSpanConditions, fullText.indexOf(conditionsText), fullText.indexOf(conditionsText) + conditionsText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(myClickableSpanRules, fullText.indexOf(rulesText), fullText.indexOf(rulesText) + rulesText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(myClickableSpanPrivacyPolicy, fullText.indexOf(privacyPolicyText), fullText.indexOf(privacyPolicyText) + privacyPolicyText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.highlightColor = Color.TRANSPARENT
    }
}

fun TextView.myFunForSetColor(@ColorRes colorIntId: Int, theme: Resources.Theme? = null){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        setTextColor(resources.getColor(R.color.black, null))
    } else {
        setTextColor(resources.getColor(R.color.black))
    }
}

class MyClickableSpanRules : ClickableSpan(){
    override fun onClick(widget: View) {
        Snackbar.make(widget, "Правила", Snackbar.LENGTH_SHORT).show()
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = Color.parseColor("#FF0000")
        ds.isUnderlineText = true
    }
}

class MyClickableSpanPrivacyPolicy : ClickableSpan(){
    override fun onClick(widget: View) {
        Snackbar.make(widget, "Политика конфиденциальности", Snackbar.LENGTH_SHORT).show()
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = Color.parseColor("#FF0000")
        ds.isUnderlineText = true
    }
}

class MyClickableSpanConditions : ClickableSpan(){
    override fun onClick(widget: View) {
        Snackbar.make(widget, "Условия", Snackbar.LENGTH_SHORT).show()
    }

    override fun updateDrawState(ds: TextPaint) {
        //super.updateDrawState(ds)
        ds.color = Color.parseColor("#FF0000")
        ds.isUnderlineText = true
    }
}

