package com.example.datetime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datetime.ui.theme.DatetimeTheme
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DatetimeTheme {
                DateTimePickerExample()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerExample() {
    val datePickerState = rememberDatePickerState()
    val context = LocalContext.current
    val selectedDate = datePickerState.selectedDateMillis
    val formattedDate = remember(selectedDate) {
        selectedDate?.let {
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(it))
        } ?: "No date selected"
    }

    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(0) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text(text = "Select Date", style = MaterialTheme.typography.titleMedium)
        DatePicker(state = datePickerState)

        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Selected Date: $formattedDate")

        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Mock Time Picker", style = MaterialTheme.typography.titleMedium)

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(text = "Hour: ")
            Slider(
                value = selectedHour.toFloat(),
                onValueChange = { selectedHour = it.toInt() },
                valueRange = 0f..23f,
                steps = 23
            )
            Text(text = selectedHour.toString())
        }

        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text(text = "Minute: ")
            Slider(
                value = selectedMinute.toFloat(),
                onValueChange = { selectedMinute = it.toInt() },
                valueRange = 0f..59f,
                steps = 59
            )
            Text(text = selectedMinute.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Selected Time: %02d:%02d".format(selectedHour, selectedMinute))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDateTimePickerExample() {
    DatetimeTheme {
        DateTimePickerExample()
    }
}
