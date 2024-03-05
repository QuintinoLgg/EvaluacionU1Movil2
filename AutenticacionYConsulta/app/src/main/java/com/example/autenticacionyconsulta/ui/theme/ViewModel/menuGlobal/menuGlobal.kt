package com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AssuredWorkload
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.HistoryEdu
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.navigation.AppScreens
import com.example.autenticacionyconsulta.ui.theme.ViewModel.cargaAcademica.ViewModelCargaAcademica
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
                        label = { Text("Carga Académica") },
                        icon = { Icon(Icons.Outlined.Schedule, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCargaAcademica(viewModelAcademic, navController)
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Cardex") },
                        icon = { Icon(Icons.Outlined.HistoryEdu, null) },
                        selected = false,
                        onClick = { navController.navigate(AppScreens.Kardex.route) }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones por unidad") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = {
                            scope.launch {
                                obtenerCalificaciones(viewModelAcademic, navController)
                            }
                            //navController.navigate(AppScreens.UnitsCalifScreen.route)
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Calificaciones finales") },
                        icon = { Icon(Icons.Outlined.School, null) },
                        selected = false,
                        onClick = { navController.navigate(AppScreens.CalFinal.route) }
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

suspend fun obtenerCargaAcademica(viewModel: ViewModelCargaAcademica, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerCargaAcademica")
    var schedule = viewModel.getAcademicSchedule()
    var encodedInfo = Uri.encode(schedule)
    navController.navigate(AppScreens.CargAcad.route + encodedInfo)
}

suspend fun obtenerCalificaciones(viewModel: ViewModelCargaAcademica, navController: NavController){
    val TAG = "HOME SCREEN"
    Log.d(TAG, "Invocando obtenerCalififcaciones")
    var unidades = viewModel.getCalifByUnidad()
    var encodedInfo = Uri.encode(unidades)
    navController.navigate(AppScreens.CalUnidad.route + encodedInfo)
}