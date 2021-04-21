package com.example.emaapp.data

enum class FilterType(val types: Array<ParticipantType>) {
    ALL(ParticipantType.values()),
    ANDROID(arrayOf(ParticipantType.ANDROID_STUDENT, ParticipantType.ANDROID_STUDENT)),
    IOS(arrayOf(ParticipantType.IOS_STUDENT, ParticipantType.IOS_MENTOR))
}