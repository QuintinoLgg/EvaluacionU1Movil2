package com.example.autenticacionyconsulta.ui.theme.ViewModel.screenLogin

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.R
import com.example.autenticacionyconsulta.data.Variables
import com.example.autenticacionyconsulta.navigation.AppNavigation
import com.example.autenticacionyconsulta.navigation.AppScreens
import com.example.autenticacionyconsulta.ui.theme.AutenticacionYConsultaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginApp(
    navController: NavController,
    viewmodel: LoginView = viewModel(factory = LoginView.Factory)
){
    val scope= rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(40.dp)
    ) {
        if(viewmodel.errorLogin){
            Spacer(modifier = Modifier.height(45.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Icon(imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = "",
                    modifier=Modifier.size(35.dp),
                    tint = Color(255,124,112))
                Text(
                    text = "Datos incorrectos",
                    fontSize = 14.sp,
                    color=Color(255,158,142))
            }
        }

        Row (
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Matricula",
                fontSize = 20.sp)
        }
        OutlinedTextField(
            value = viewmodel.matricula,
            label = { Text(text = "Ingresa tu matricula") },
            onValueChange = {
                viewmodel.updateMatricula(it)
                viewmodel.updateErrorLogin(false)
            },
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(25.dp))
        Row (
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Contraseña",
                fontSize = 20.sp)
        }
        OutlinedTextField(
            value = viewmodel.password,
            label = { Text(text = "Ingresa tu contraseña") },
            onValueChange = {
                viewmodel.updatePassword(it)
                viewmodel.updateErrorLogin(false)
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = viewmodel.expandido,
                onExpandedChange = {viewmodel.updateExpandido(it)}
            ) {
                OutlinedTextField(
                    value = viewmodel.tipoUsuario,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.tipoUsuario)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                )
                ExposedDropdownMenu(
                    expanded = viewmodel.expandido,
                    onDismissRequest = { viewmodel.expandido = false })
                {
                    var alumno = stringResource(id = R.string.alumno)
                    var docente = stringResource(id = R.string.docente)
                    DropdownMenuItem(
                        text = { Text(alumno) },
                        onClick = {
                            viewmodel.updateTipoUsuario(alumno)
                            viewmodel.expandido=false
                            viewmodel.updateErrorLogin(false)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(docente) },
                        onClick = {
                            viewmodel.updateTipoUsuario(docente)
                            viewmodel.expandido=false
                            viewmodel.updateErrorLogin(false)
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (!viewmodel.matricula.equals("") && !viewmodel.password.equals("")){
                       scope.launch {
                           if(viewmodel.getAccess(viewmodel.matricula,viewmodel.password,viewmodel.tipoUsuario)){
                               viewmodel.updateErrorLogin(false)
                               Variables.matricula=viewmodel.matricula
                               var info=viewmodel.getInfo()
                               var encodedInfo = Uri.encode(info)
                               navController.navigate(route = AppScreens.Info.route+encodedInfo)
                           }else{
                               viewmodel.updateErrorLogin(true)
                           }
                       }
                    }else{
                        viewmodel.updateErrorLogin(true)
                    }
                },

            ) {
                Text(text = "Confirmar",
                    modifier=Modifier.width(120.dp),
                    textAlign = TextAlign.Center)
            }
        }


    }
}


