package com.kyonggi.cellification.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

fun getConnectivityStatus(context:Context):Boolean{
        val mgr:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if(mgr.isDefaultNetworkActive){
            true
        }else{
            Toast.makeText(context,"인터넷 연결 안됨, 인터넷 연결 상태를 확인해주세요",Toast.LENGTH_SHORT).show()
            false
        }
}
