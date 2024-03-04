package com.example.autenticacionyconsulta.ui.theme.ViewModel.menuGlobal

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AssuredWorkload
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.autenticacionyconsulta.navigation.AppScreens


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MenuGlobal(navController: NavController){
    TopAppBar(
        title = { Text(text = "") },
        actions = {
            Botones(navController = navController)
        }
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun Botones(navController: NavController){
    val navControllerList: List<AppScreens> = listOf(
        AppScreens.Login,
        AppScreens.Info,
        AppScreens.CargAcad,
        AppScreens.Kardex,
        AppScreens.CalUnidad,
        AppScreens.CalFinal

    )
    val iconList: List<ImageVector> = listOf(
        Icons.Filled.ExitToApp,
        Icons.Filled.Person,
        Icons.Filled.Newspaper,
        Icons.Filled.AssuredWorkload,
        Icons.Filled.Addchart,
        Icons.Filled.Analytics,
    )
    val DescriptionList: List<String> = listOf(
        "Salir al login",
        "Informacion personal",
        "Carga academica",
        "Kardex",
        "Calificaciones por unidad",
        "Califaciones finales"
    )

        Row {
            repeat(iconList.size){ index ->
                IconButton(onClick = { navController.navigate(navControllerList[index].route)}) {
                    Icon(imageVector = iconList[index], contentDescription = DescriptionList[index])
                }
                Spacer(modifier = Modifier.width(20.dp))
            }
        }

}