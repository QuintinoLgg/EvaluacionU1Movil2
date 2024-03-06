package com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.HotelClass
import androidx.compose.material.icons.outlined.Notes
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.navigation.AppScreens
import com.example.autenticacionyconsulta.ui.theme.ViewModel.ViewModelCargaAcademica
import com.example.autenticacionyconsulta.ui.theme.ViewModel.kardex.Kardex
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuGlobal(
    navController: NavController,
    viewModelAcademic: ViewModelCargaAcademica = viewModel(factory = ViewModelCargaAcademica.Factory)
    ){

    var openCloseSesion by remember {
        mutableStateOf(false)
    }

    if(openCloseSesion){
        LaunchedEffect(openCloseSesion){
            delay(3000)
            openCloseSesion = false
            navController.popBackStack()
        }
        DialogCloseSesion()
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Menu",
                        fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                    val scope = rememberCoroutineScope()
                    NavigationDrawerItem(
                        label = { Text("Perfil") },
                        icon = { Icon(Icons.Outlined.AccountCircle, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerPerfil(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Carga Académica") },
                        icon = { Icon(Icons.Outlined.EditNote, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCargaAcademica(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Kardex") },
                        icon = { Icon(Icons.Outlined.HotelClass, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerKardex(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones por unidad") },
                        icon = { Icon(Icons.Outlined.Notes, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCalificacionesPorUnidad(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones finales") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCalifFinales(viewModelAcademic, navController)
                            }
                        }
                    )
                }
            }
        }
    ){
                TopAppBar(
                    navigationIcon = {
                        Button(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if(isClosed) open()
                                        else close()
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    title = {
                        Text(
                            text = "Bienvenido",
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor =MaterialTheme.colorScheme.primary
                    )
                )

    }
}




@Composable
fun DialogCloseSesion() {
    Dialog(onDismissRequest = { }) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(16.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(
                    text = "Cerrando sesion...",
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

suspend fun obtenerPerfil(viewModel: ViewModelCargaAcademica, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerPerfil")
    var schedule = viewModel.getPerfil()
    var encodedInfo = Uri.encode(schedule)
    navController.navigate(AppScreens.Info.route + encodedInfo)
}

suspend fun obtenerCargaAcademica(viewModel: ViewModelCargaAcademica, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerCargaAcademica")
    var schedule = viewModel.getCargaAcademica()
    var encodedInfo = Uri.encode(schedule)
    navController.navigate(AppScreens.CargAcad.route + encodedInfo)
}

suspend fun obtenerCalificacionesPorUnidad(viewModel: ViewModelCargaAcademica, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerCalififcaciones")
    var unidades = viewModel.getCalifUnidad()
    var encodedInfo = Uri.encode(unidades)
    navController.navigate(AppScreens.CalUnidad.route + encodedInfo)
}

suspend fun obtenerCalifFinales(viewModel: ViewModelCargaAcademica, navController: NavController){
    var unidades = viewModel.getCalifFinal()
    var encodedInfo = Uri.encode(unidades)
    navController.navigate(AppScreens.CalFinal.route + encodedInfo)
}

suspend fun obtenerKardex(viewModel: ViewModelCargaAcademica, navController: NavController){
    var cardex = viewModel.getKardexByAlumno()
    var encodedInfo = Uri.encode(cardex)
    navController.navigate(AppScreens.Kardex.route + encodedInfo)
}