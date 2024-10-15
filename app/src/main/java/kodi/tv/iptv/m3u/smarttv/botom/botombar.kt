package kodi.tv.iptv.m3u.smarttv.botom

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun MyBottomNavBar(
    onSelectedItem: (Int) -> Unit
) {
    BottomNavigation() {
        BottomNavigationItem(
            selected = true,
            onClick = { onSelectedItem(0) },
            icon = { Icon(imageVector = Icons.Filled.Person, contentDescription = "Person Icon") },
            enabled = true,
        )
        BottomNavigationItem(
            selected = true,
            onClick = { onSelectedItem(1) },
            icon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone Icon") },
            enabled = true,
        )
        BottomNavigationItem(
            selected = true,
            onClick = { onSelectedItem(2) },
            icon = { Icon(imageVector = Icons.Filled.Place, contentDescription = "Place Icon") },
            enabled = true,
        )

        BottomNavigationItem(
            selected = true,
            onClick = { onSelectedItem(3) },
            icon = { Icon(imageVector = Icons.Filled.Place, contentDescription = "Place Icon") },
            enabled = true,
        )
    }
}
