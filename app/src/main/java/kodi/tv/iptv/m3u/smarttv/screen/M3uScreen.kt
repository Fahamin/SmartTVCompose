package kodi.tv.iptv.m3u.smarttv.screen

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.model.M3uModel
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.utils.M3UParserurl
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3uScreen(navController: NavHostController) {
    var itemList by remember { mutableStateOf(emptyList<M3uModel>()) }
    val handler: Handler? = null
    val executor: ExecutorService? = null
    LaunchedEffect(Unit) {

        var list: MutableList<M3uModel> = ArrayList()

        // Set up Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("mm")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(M3uModel::class.java)
                    item?.let {
                        list.add(it)
                    }
                }
                itemList = list

            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("fahamin", error.toString())

            }
        })
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "M3U URL",
                        maxLines = 1,
                        color = Color.Blue,
                        overflow = TextOverflow.Ellipsis
                    )
                },
            )
        },

        ) { pa ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pa)
        ) {
            var selectedIndex = 0
            //  Log.e("fahamin", itemList[0].name.toString())
            if (itemList.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn {
                    itemsIndexed(items = itemList) { index, item ->
                        ListItemView(
                            m3uModel = item, index, selectedIndex
                        ) { i ->
                            selectedIndex = i

                            Log.e("fahamin", itemList[i].name.toString())
                            Log.e("fahamin", itemList[i].link.toString())

                            val bundle = bundleOf(
                                "name" to itemList[i].name,
                                "link" to itemList[i].name,
                            )

                            val executor = Executors.newSingleThreadExecutor()
                            val handler = Handler(Looper.getMainLooper())


                            executor.execute(Runnable {
                                val parse: List<ChannelModel> =
                                    M3UParserurl().parse(itemList[i].link)
                                Log.e("fahamin", parse.toString())

                                handler.post(Runnable {

                                    navController.navigate(Routes.play) {
                                        launchSingleTop
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            inclusive = true
                                        }
                                    }
                                })
                            })
                        }
                    }
                }

            }

        }
    }
}

@Composable
fun ListItemView(
    m3uModel: M3uModel, index: Int, selectedIndex: Int, onClick: (Int) -> Unit
) {

    val backgroundColor = if (index == selectedIndex) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.background
    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth()
        .clickable { onClick(index) }
        .height(50.dp), shape = RoundedCornerShape(8.dp)) {
        Surface(color = backgroundColor) {

            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
            ) {

                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = m3uModel.name!!,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )


            }
        }
    }

}

