package com.example.week2_day4

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    lateinit var  editorSharedPreferences: SharedPreferences.Editor

    private var username_CountKey: String? = "users_in"

    private var VALUE_KEY = "user_"

    private var numberOfUsers =  0

    private val MAX_USER_COUNT = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var username: EditText
        var number: EditText
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("com.example.week2_day4", Context.MODE_PRIVATE)
        editorSharedPreferences = sharedPreferences.edit()
        username = username_editText
        number = number_editText
        sharedPreferences.getInt(username_CountKey, 0)

        add_button.setOnClickListener {

            if(username_editText.text.toString() == "" ||
                number_editText.text.toString() == "")
            {
                Toast.makeText(this, "Not so fast! Fill in those fields!", Toast.LENGTH_LONG).show()
            }
            else if (numberOfUsers == MAX_USER_COUNT) {
                Toast.makeText(applicationContext, "No more room!", Toast.LENGTH_LONG).show()
                username.setText("")
                number.setText("")
            }
            else {

                var newUser: String = ("" + username_textView.text + " " + username.text + " " + number_textView.text + " " + number.text)
                editorSharedPreferences.putString(VALUE_KEY + (numberOfUsers), newUser)
                editorSharedPreferences.putInt(username_CountKey, (numberOfUsers + 1)).commit()
                numberOfUsers++

                username_editText.setText("")
                number_editText.setText("")

                if(numberOfUsers > 0)
                {

                    var myUsers = StringBuilder()
                    for(i in 0..numberOfUsers)
                    {
                        var user: String? = sharedPreferences.getString(VALUE_KEY + i, "")
                        myUsers.append(user + "\n")
                    }
                    userinfo_textView.setText(myUsers)
                }
                else {
                    userinfo_textView.setText("No users in!")
                }


                Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onResume() {
        super.onResume()

        if(numberOfUsers > 0)
        {

            var myUsers = StringBuilder()
            for(i in 0..numberOfUsers)
            {
                var user: String? = sharedPreferences.getString(VALUE_KEY + i, "FAILED")
                myUsers.append(user + "\n")
            }
            userinfo_textView.setText(myUsers)
        }
        else {
            userinfo_textView.setText("No users in!")
        }
    }

    override fun onStop() {
        super.onStop()
        userinfo_textView.setText("")
//        editorSharedPreferences.putString(VALUE_KEY, username)
//        editorSharedPreferences.putString(VALUE_KEY, number)

//        editorSharedPreferences.commit()
    }
}
