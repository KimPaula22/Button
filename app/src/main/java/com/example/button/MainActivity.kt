package com.example.button

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.button.ui.theme.ButtonTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButtonTheme {
                MyButtonScreen()
            }
        }
    }
}

@Composable
fun MyButtonScreen() {
    // Variables de estado
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isTextVisible by remember { mutableStateOf(false) }
    var isSwitchChecked by remember { mutableStateOf(false) }
    var selectedRadioButton by remember { mutableStateOf("Opción 1") }
    var currentImageIndex by remember { mutableStateOf(0) }
    val images = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_android_black_24dp),
            contentDescription = "Icono Android",
            modifier = Modifier.size(40.dp)
        )

        Button(onClick = {
            isLoading = true
            message = "Botón presionado"
            currentImageIndex = (currentImageIndex + 1) % images.size
        }) {
            Text("Presionar")
        }

        // CircularProgressIndicator que aparece durante 5 segundos
        if (isLoading) {
            CircularProgressIndicator()
            LaunchedEffect(Unit) {
                delay(5000)  // Esperar 5 segundos
                isLoading = false
            }
        }

        // Checkbox "Activar"
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isTextVisible,
                onCheckedChange = { isTextVisible = it }
            )
            Text("Activar")
        }

        // Campo de texto que muestra el mensaje, inicialmente no visible
        if (isTextVisible) {
            Text(message)
        }

        // Switch para mostrar el grupo de RadioButtons
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = isSwitchChecked,
                onCheckedChange = { isSwitchChecked = it }
            )
            Text("Mostrar grupo de opciones")
        }

        // Grupo de RadioButtons
        if (isSwitchChecked) {
            Column {
                Text("Selecciona una opción:")
                RadioButtonGroup(
                    selectedOption = selectedRadioButton,
                    onOptionSelected = { option ->
                        selectedRadioButton = option
                        message = "Seleccionaste $option"
                    },
                    onImageChange = { index ->
                        currentImageIndex = index // Cambiar el índice de la imagen
                    }
                )
            }
        }

        // Imagen cambiante
        Image(
            painter = painterResource(id = images[currentImageIndex]),
            contentDescription = "Imagen cambiante",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun RadioButtonGroup(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onImageChange: (Int) -> Unit // Nueva función para cambiar la imagen
) {
    val options = listOf("Opción 1", "Opción 2", "Opción 3")
    Column {
        for ((index, option) in options.withIndex()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onOptionSelected(option)
                        onImageChange(index) // Cambia la imagen según la opción seleccionada
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = {
                        onOptionSelected(option)
                        onImageChange(index) // Cambia la imagen según la opción seleccionada
                    }
                )
                Text(text = option)
            }
        }
    }
}