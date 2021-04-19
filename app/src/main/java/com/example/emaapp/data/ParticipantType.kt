package com.example.emaapp.data

import com.google.gson.annotations.SerializedName

enum class ParticipantType {
    @SerializedName("androidMentor")
    ANDROID_MENTOR,
    @SerializedName("androidStudent")
    ANDROID_STUDENT,
    @SerializedName("iosMentor")
    IOS_MENTOR,
    @SerializedName("iosStudent")
    IOS_STUDENT,
    @SerializedName("watcher")
    WATCHER
}