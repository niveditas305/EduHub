package snow.app.eduhub.util

import Constants.Companion.TYPE_USER
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.google.gson.Gson
import snow.app.eduhub.ui.LoginActivity
import snow.app.eduhub.ui.network.responses.signup.SignupRes


class AppSession(context: Context) {

    var pref: SharedPreferences? = null

    // Editor for Shared preferences
    var editor: SharedPreferences.Editor? = null

    // Context
    var _context: Context? = null

    // Shared pref mode
    var PRIVATE_MODE = 0

    init {
        pref = context.getSharedPreferences("MyPref", 0) // 0 - for private mode
this._context=context
        editor = pref?.edit()
    }
    fun saveEntryType(type:String){
        editor?.putString(TYPE_USER,type)
        editor?.commit()
    }
    fun saveSession(data: SignupRes){

        val gson= Gson()
        val sData:String=gson.toJson(data,SignupRes::class.java)
        editor?.putString("EduHub",sData)
        editor?.commit()

    }
//
//    fun saveEntryType(type:String){
//        editor?.putString(TYPE_USER,type)
//        editor?.commit()
//    }
//
//    fun getEntryType():String{
//        return pref!!.getString(TYPE_USER,"").toString()
//    }
//
    fun logout() {
        editor?.clear()
        editor?.commit()
        val intent = Intent(_context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        _context!!.startActivity(intent)
    }
//
    fun getAppData():SignupRes?{

       return  Gson().fromJson(pref?.getString("EduHub",""),SignupRes::class.java)


    }

    fun logoutUser() {
        editor!!.clear()
        editor!!.commit()
    }

}