package com.example.intentions

import android.content.Intent
import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.intentions.ui.theme.IntentionsTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var numberReceived : String
        var numberReceived2 : String
        if (intent.getStringExtra("number1").isNullOrBlank()){
            numberReceived = "empty"
        }
        else{
            numberReceived = intent.getStringExtra("number1").toString()
        }
        if (intent.getStringExtra("number2").isNullOrBlank()){
            numberReceived2 = "empty"
        }
        else{
            numberReceived2 = intent.getStringExtra("number2").toString()
        }

        setContent {
            IntentionsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SecondScreen(numberReceived,numberReceived2,Modifier.padding(innerPadding))
                }
            }
        }
    }


    @Composable
    fun SecondScreen(numberReceived1: String,numberReceived2 : String, modifier: Modifier = Modifier){
        Column (Modifier){
            Text("Contact1 $numberReceived1", modifier = modifier)
            Text("Contact2 $numberReceived2 ",modifier = modifier)
            Button(onClick = {finish()}) { Text("Close B") }
            Button(onClick = {openMain()}) { Text("Go to A") }
        }
    }
    private fun openMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
