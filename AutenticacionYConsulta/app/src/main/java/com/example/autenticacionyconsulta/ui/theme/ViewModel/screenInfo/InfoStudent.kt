package com.example.autenticacionyconsulta.ui.theme.ViewModel.screenInfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.ui.theme.AutenticacionYConsultaTheme
import com.example.autenticacionyconsulta.ui.theme.ViewModel.ViewModelCargaAcademica
import com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal.MenuGlobal

class InfoStudent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun dataStudent(
    navController: NavController,
    text:String?,
    viewModelAcademic: ViewModelCargaAcademica = viewModel(factory = ViewModelCargaAcademica.Factory)
){
    val alumnoInfo=text?.split("(",")")?.get(1)?.split(",")
    Log.d("info",""+alumnoInfo)


    Scaffold (
        topBar = {
            //navController.popBackStack()
            MenuGlobal(navController, viewModelAcademic)
        },
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(7.dp)
        ){
            Card (
                modifier = Modifier.fillMaxSize()
            ){

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight(.91f)
                        .fillMaxWidth()
                        .padding(3.dp)
                ) {
                    Text(
                        text = " DATOS DEL ALUMNO ",
                        fontSize = 19.sp)
                    Text(
                        text = ""+alumnoInfo?.get(13)?.split("=")?.get(1),
                        fontSize = 21.sp)
                    Icon(imageVector = Icons.Filled.Person,
                        contentDescription = "",
                        modifier = Modifier.size(120.dp))
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Fecha de reinscripción: "+alumnoInfo?.get(0)?.split("=")?.get(1))
                        Text(text = "Mod. Educativo: "+alumnoInfo?.get(1)?.split("=")?.get(1))
                        Text(text = "Adeudo: "+ validarCampos(alumnoInfo?.get(2)?.split("=")?.get(1)))
                        Text(text = "Adeudo descripción: "+ validarCampos(alumnoInfo?.get(4)?.split("=")?.get(1)))
                        Text(text = "Inscrito: "+ validarCampos(alumnoInfo?.get(5)?.split("=")?.get(1)))
                        Text(text = "Estatus: "+alumnoInfo?.get(6)?.split("=")?.get(1))
                        Text(text = "Semestre actual: "+alumnoInfo?.get(7)?.split("=")?.get(1))
                        Text(text = "Creditos acumulados: "+alumnoInfo?.get(8)?.split("=")?.get(1))
                        Text(text = "Creditos actuales: "+alumnoInfo?.get(9)?.split("=")?.get(1))
                        Text(text = "Carrera: "+alumnoInfo?.get(11)?.split("=")?.get(1))
                        Text(text = "Matricula: "+alumnoInfo?.get(14)?.split("=")?.get(1))
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Especialidad: ",
                            fontSize = 17.sp)
                        Text(text = ""+alumnoInfo?.get(10)?.split("=")?.get(1),
                            fontSize = 13.sp,
                            fontFamily = FontFamily.SansSerif)
                    }
                }

            }
        }
    }

}

fun validarCampos(dato:String?):String?{
    if(dato.equals("")){
        return "Ninguno"
    }else if(dato.equals("true")){
        return "Si"
    }else if(dato.equals("false")){
        return "No"
    }else{
        return dato
    }
}

