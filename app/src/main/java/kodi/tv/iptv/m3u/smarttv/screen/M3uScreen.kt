package kodi.tv.iptv.m3u.smarttv.screen

import android.os.Handler
import android.os.Looper
import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.BundleListRetriever.getList
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kodi.tv.iptv.m3u.smarttv.model.PlayListModel
import kodi.tv.iptv.m3u.smarttv.model.ChannelModel
import kodi.tv.iptv.m3u.smarttv.model.M3uModel
import kodi.tv.iptv.m3u.smarttv.route.Routes
import kodi.tv.iptv.m3u.smarttv.utils.M3UParserurl
import kodi.tv.iptv.m3u.smarttv.viewModel.DbViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.Executors

var referenceBuild = FirebaseDatabase.getInstance().getReference("mm")
var viewModel: DbViewModel? = null

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M3uScreen(navController: NavController) {
    viewModel = hiltViewModel<DbViewModel>()

    LaunchedEffect(Unit) {
        getUsaList()
    }


}



private fun getUkList() {

    referenceBuild.child("v").addListenerForSingleValueEvent(object : ValueEventListener {
        @androidx.annotation.OptIn(UnstableApi::class)
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
        }
    })
}

private fun getGermanList() {
    referenceBuild.child("h").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())


            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getUkList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}

private fun getEngList() {
    referenceBuild.child("d").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getGermanList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}

private fun getArabList() {
    referenceBuild.child("a").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getEngList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}

private fun getBrazilList() {
    referenceBuild.child("b").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getArabList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}

private fun getNewsList() {
    referenceBuild.child("t").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getBrazilList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}

private fun getIndianList() {
    referenceBuild.child("c").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getNewsList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })
}

private fun getUsaList() {
    referenceBuild.child("g").addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val changedPost: M3uModel? = dataSnapshot.getValue(M3uModel::class.java)
            Log.e("fahamin", changedPost.toString())

            if (changedPost != null) {
                parsing(changedPost.link, changedPost.name!!)

            }
            getIndianList()

        }

        override fun onCancelled(databaseError: DatabaseError) {

        }
    })

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

