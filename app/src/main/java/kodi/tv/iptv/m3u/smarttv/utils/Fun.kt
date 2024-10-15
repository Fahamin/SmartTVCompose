package kodi.tv.iptv.m3u.smarttv.utils

import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Fun(var context: Context) {

    companion object {
        var sc = "0";
        var div = 4;
        var nc = 15;
        var count = 0;

    }

    var referenceadmob: DatabaseReference = FirebaseDatabase.getInstance().getReference("adid")

    init {
        getSc()
        getDIV()
        getNc()
    }

    private fun getSc() {

        referenceadmob.child("scs").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                sc = snapshot.value.toString()

               // Log.e("fahamin", sc.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun getNc() {

        referenceadmob.child("nc").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                var value = snapshot.value

                try {
                    nc = value.toString().toInt()
                } catch (e: Exception) {

                }
              //  Log.e("fahamin", nc.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun getDIV() {

        referenceadmob.child("div").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var value = snapshot.value

                try {
                    div = value.toString().toInt()
                } catch (e: Exception) {

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


}

