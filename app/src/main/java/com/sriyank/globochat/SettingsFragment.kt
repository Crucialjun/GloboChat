package com.sriyank.globochat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import androidx.preference.SwitchPreferenceCompat


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings,rootKey)

        val accSettingsPref = findPreference<Preference>("key_account_settings")

        accSettingsPref?.setOnPreferenceClickListener {
            val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)
            true
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val autoreplyTime = sharedPreferences.getString("key_auto_reply_time","")
        Log.i("Settings Fragment", "onCreatePreferences: $autoreplyTime ")

        val statusPref = findPreference<EditTextPreference>("key_status")

        statusPref?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                Log.i("TAG", "onPreferenceChange: New Status is $newValue ")

                true
            }

        val notificationPref = findPreference<SwitchPreferenceCompat>("key_new_msg_notif")
        notificationPref?.summaryProvider =
            Preference.SummaryProvider<SwitchPreferenceCompat> { switch ->
                if(switch.isChecked){
                    "Status On"
                }else{
                    "Status Off"
                }
            }
    }


}