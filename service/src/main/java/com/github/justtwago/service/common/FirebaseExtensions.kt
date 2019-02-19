package com.github.justtwago.service.common

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

fun DatabaseReference.addValueEventListener(
    onCancelled: ((DatabaseError) -> Unit)? = null,
    onDataChange: ((DataSnapshot) -> Unit)? = null
) {
    addValueEventListener(object : ValueEventListener {
        override fun onCancelled(error: DatabaseError) {
            onCancelled?.invoke(error)
        }

        override fun onDataChange(snapshot: DataSnapshot) {
            onDataChange?.invoke(snapshot)
        }
    })
}