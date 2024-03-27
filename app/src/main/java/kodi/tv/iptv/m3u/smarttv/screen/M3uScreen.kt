package kodi.tv.iptv.m3u.smarttv.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kodi.tv.iptv.m3u.smarttv.model.M3uModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3uScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    androidx.compose.material3.Text(
                        "M3U URL",
                        maxLines = 1,
                        color = Color.Blue,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        },

        ) { pa ->
        Box(modifier = Modifier.padding(pa)) {
            val itemList = observeItemsFromFirebase()
            var selectedIndex = 0

            LazyColumn {
                items(itemList.value) { item ->
                    // Display each item in the list
                    ItemRow(item = item)
                }
            }

        }
    }
}


    @Composable
    fun ItemRow(item: M3uModel) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Item ID: ${item.name}")
                Text(text = "Item Name: ${item.name}")
            }
        }
    }

    @Composable
    fun observeItemsFromFirebase(): MutableLiveData<List<M3uModel>> {
        val itemList = MutableLiveData<List<M3uModel>>()

        // Set up Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("mm")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = mutableListOf<M3uModel>()
                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(M3uModel::class.java)
                    item?.let {
                        items.add(it)
                    }
                }
                itemList.value = items
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        return itemList
    }
