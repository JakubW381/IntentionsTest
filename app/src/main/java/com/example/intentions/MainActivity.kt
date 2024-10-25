package com.example.intentions

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.intentions.ui.theme.IntentionsTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentionsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(Modifier.padding(innerPadding))
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreen(modifier: Modifier = Modifier){
        var firstValue by rememberSaveable { mutableStateOf("") }
        var secondValue by rememberSaveable { mutableStateOf("") }
        Column(modifier = modifier.fillMaxSize()) {
            TextField(value = firstValue, onValueChange = {firstValue = it},modifier = Modifier.fillMaxWidth())
            TextField(value = secondValue, onValueChange = {secondValue= it},modifier = Modifier.fillMaxWidth())
            Button(onClick = {SMS(firstValue,secondValue)}, modifier = Modifier.fillMaxWidth()) {
                Text("SMS")
            }
            Button(onClick = {GEO(firstValue)}, modifier = Modifier.fillMaxWidth()) {
                Text("MAP")
            }
            Button(onClick = {DOC()}, modifier = Modifier.fillMaxWidth()) {
                Text("DOC")
            }
            Button(onClick = {MAIL(firstValue,secondValue)}, modifier = Modifier.fillMaxWidth()) {
                Text("MAIL")
            }
            Button(onClick = {DIAL(secondValue)}, modifier = Modifier.fillMaxWidth()) {
                Text("CALL")
            }

            Button(onClick = {MUSIC()}, modifier = Modifier.fillMaxWidth()) {
                Text("MUSIC")
            }
            Button(onClick = {openSecondActivity(firstValue,secondValue)}, modifier = Modifier.fillMaxWidth()) {
                Text("CONTACT LIST")
            }
            Button(onClick = {CALENDAR(firstValue, secondValue)}, modifier = Modifier.fillMaxWidth()) {
                Text("CALENDAR")
            }
            Button(onClick = {CLOSE()}, modifier = Modifier.fillMaxWidth()) {
                Text("CLOSE APP")
            }

        }
    }
    private fun SMS(number : String, body : String){
        val intent = Intent(Intent.ACTION_SENDTO).apply{
            data = Uri.parse("smsto:$number")
            putExtra("sms_body", body)
        }
        startActivity(intent)
    }
    private fun CALENDAR(dateTime : String = "01-06-2024 20:30", title : String){

        // Parsowanie daty i godziny z formatu "dd-mm-yyyy HH:MM"
        val parts = dateTime.split(" ")
        val dateParts = parts[0].split("-")
        val timeParts = parts[1].split(":")

        val startTime = Calendar.getInstance().apply {
            set(
                dateParts[0].toInt(),
                dateParts[1].toInt(),
                dateParts[2].toInt(),
                timeParts[0].toInt(),
                timeParts[1].toInt()
            )
        }
        val inMilis = startTime.timeInMillis;

        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, inMilis)
        }
        startActivity(intent)
    }
    private fun GEO(location : String){
        val intent = Intent(Intent.ACTION_VIEW).apply{
            data = Uri.parse("geo:0,0?q=$location")
        }
        startActivity(intent)
    }
    private fun openSecondActivity(number1: String, number2 : String){
        val intent = Intent(this,SecondActivity::class.java).apply {
            putExtra("number1",number1)
            putExtra("number2",number2)
        }
        startActivity(intent)
    }
    private fun DOC() {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            type = "application/msword"
            addCategory(Intent.CATEGORY_DEFAULT)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "Brak aplikacji do tworzenia dokumentów.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun MAIL(email : String, title : String){
        val intent = Intent(Intent.ACTION_SENDTO).apply{
            data = Uri.parse("mailto:$email")
            putExtra(Intent.EXTRA_SUBJECT, title)
        }
        startActivity(intent)
    }
    private fun DIAL(number: String){
        val intent = Intent(Intent.ACTION_DIAL).apply{
            data = Uri.parse("tel:$number")
        }
        startActivity(intent)
    }
    private fun MUSIC(){
        val intent = Intent(Intent.ACTION_VIEW).apply{
            //addCategory(Intent.CATEGORY_APP_MUSIC)
            data = Uri.parse("https://music.youtube.com")
        }
        startActivity(intent)
    }
    private fun CLOSE(){
        finishAffinity()
    }






}


