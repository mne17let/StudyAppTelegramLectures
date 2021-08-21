package com.example.studyapp

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var inputLayout: TextInputLayout
    private lateinit var editText: TextInputEditText
    private lateinit var buttonCheckEmail: Button
    private lateinit var checkBox: CheckBox
    private lateinit var progressBar: ProgressBar
    private lateinit var linearWithContent: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        setEditText()
        setButton()
        setCheckbox()
    }

    fun init(){
        inputLayout = findViewById(R.id.id_input_layout)
        editText = inputLayout.editText as TextInputEditText
        buttonCheckEmail = findViewById(R.id.id_button_check_email)
        checkBox = findViewById(R.id.id_checkbox)
        buttonCheckEmail.isEnabled = false
        progressBar = findViewById(R.id.id_progressbar)
        linearWithContent = findViewById(R.id.id_linear_with_content)
    }

    fun setEditText(){
        /*editText.addTextChangedListener(object : AbstractTextWatcher(){
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().endsWith("@g")){
                    s?.append("mail.com")
                }

                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                inputLayout.isErrorEnabled = !valid
                val errorText = if (valid) "" else "Введите корректный почтовый адрес"
                inputLayout.error = errorText
                if (valid){
                 Toast.makeText(this@MainActivity, "Почта введена верно", Toast.LENGTH_SHORT).show()
                }
            }
        })*/

        editText.listenTextChanges {
            textEditableType ->
            if (textEditableType.toString().endsWith("@g")){
                textEditableType?.append("mail.com")
            }

            val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(textEditableType.toString()).matches()
            inputLayout.isErrorEnabled = !valid
            val errorText = if (valid) "" else "Введите корректный почтовый адрес"
            inputLayout.error = errorText
            if (valid){
                Toast.makeText(this@MainActivity, "Почта введена верно", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setButton(){
        buttonCheckEmail.setOnClickListener{
            if (EMAIL_ADDRESS.matcher(editText.text.toString()).matches()){
                Snackbar.make(buttonCheckEmail, "Успешный вход", Snackbar.LENGTH_SHORT).show()
                linearWithContent.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                startHandler()
            } else {
                inputLayout.isErrorEnabled = true
                inputLayout.error = "Некорректная почта"
            }

            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(buttonCheckEmail.windowToken, 0)

        }
    }

    fun setCheckbox(){
        checkBox.setOnCheckedChangeListener{ first, isChecked ->
            buttonCheckEmail.isEnabled = isChecked
        }
    }

    fun startHandler(){

        val myRunnable: Runnable = object : Runnable{
            override fun run() {
                linearWithContent.visibility = View.VISIBLE
                progressBar.visibility = View.GONE

                val dialog: Dialog = BottomSheetDialog(this@MainActivity)
                val viewForDialog = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.dialog, linearWithContent, false)

                dialog.setCancelable(false)

                val closeDialogButton = viewForDialog.findViewById<ImageButton>(R.id.id_button_close_dialog)

                closeDialogButton.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.setContentView(viewForDialog)
                dialog.show()
            }

        }

        val handler: Handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(myRunnable, 2000)
    }
}


fun TextInputEditText.listenTextChanges(block: (text: Editable?) -> Unit){
    this.addTextChangedListener(object : AbstractTextWatcher(){
        override fun afterTextChanged(s: Editable?) {
            block.invoke(s)
        }
    })
}

