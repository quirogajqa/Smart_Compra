package com.example.smartcompra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.smartcompra.ui.theme.SmartCompraTheme
import com.example.smartcompra.view.comparador.ComparadorScreen
import com.example.smartcompra.view.compras.ComprasScreen
import com.example.smartcompra.view.home.HomeScreen
import com.example.smartcompra.view.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartCompraTheme {
                AppNavigation()
            }
        }
    }
}