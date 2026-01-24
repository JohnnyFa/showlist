package com.fagundes.myshowlist.feat.home.vm

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel(
    private val auth: FirebaseAuth
) : ViewModel() {

    fun logout() {
        auth.signOut()
    }
}