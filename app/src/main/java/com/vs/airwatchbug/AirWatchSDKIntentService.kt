package com.vs.airwatchbug

import android.content.Context
import android.os.Bundle
import com.airwatch.sdk.AirWatchSDKBaseIntentService
import com.airwatch.sdk.profile.AnchorAppStatus
import com.airwatch.sdk.profile.ApplicationProfile
import com.airwatch.sdk.shareddevice.ClearReasonCode

class AirWatchSDKIntentService : AirWatchSDKBaseIntentService() {
    override fun onApplicationConfigurationChange(applicationConfiguration: Bundle) {}

    override fun onApplicationProfileReceived(
        context: Context,
        profileId: String,
        appProfile: ApplicationProfile
    ) {
    }

    override fun onClearAppDataCommandReceived(context: Context, reasonCode: ClearReasonCode) {}

    override fun onAnchorAppStatusReceived(context: Context, appStatus: AnchorAppStatus) {}

    override fun onAnchorAppUpgrade(context: Context, isUpgrade: Boolean) {}
}