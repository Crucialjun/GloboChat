package com.sriyank.globochat

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager


class MainActivity : AppCompatActivity(),SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get NavHost and NavController
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController = navHostFrag.navController

        // Get AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Link ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val autoreplyTime = sharedPreferences.getString("key_auto_reply_time","")
        Log.i("Main Activity", "onCreatePreferences: $autoreplyTime ")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onSharedPreferenceChanged(sharedPreference: SharedPreferences?, key: String?) {
        if(key == "key_status"){
            val newStatus = sharedPreference?.getString(key,"")
            Toast.makeText(this, "New Status : $newStatus", Toast.LENGTH_SHORT).show()
        }

        if(key == "key_auto_reply"){
            val autoreply = sharedPreference?.getBoolean(key,false)

            if(autoreply!! ){
                Toast.makeText(this, "Auto reply : ON", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Auto reply : OFF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }
}
