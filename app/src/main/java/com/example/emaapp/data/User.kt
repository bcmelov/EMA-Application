package com.example.emaapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.emaapp.R

//Users with mapping [icon] and [name]

enum class User(
    //zavinace = anotace --> informuji kompilator, ze ocekavana hodnota je urcity resource
    @DrawableRes val displayIcon: Int,
    @DrawableRes val displayPlatform: Int,
    @StringRes val displayName: Int,
    val type: PlatformType,
    val email: String,
    val linkedIn: String,
    val slack: String,
    val push1: Boolean,
    val review1: Boolean,
    val accepted1: Boolean,
    val android_skills: Int,
    val kotlin_skills: Int,
    val iOs_skills: Int,
    val swift_skills: Int
) {
    //list of users (test one, the final one has to be completed!
    BaraCmelova(
        R.drawable.me,
        R.drawable.ic_android,
        R.string.bara_cmelova,
        PlatformType.Android,
        "cmelova.b@gmail.com",
        "https://cdn.optinmonster.com/wp-content/uploads/2018/06/android-404-845x504.png",
        "https://www.meme-arsenal.com/memes/561f3e801377a4e022d1fed504bf06ae.jpg",
        true,
        false,
        false,
        2,
        1,
        0,
        0
    ),
    KevinVojco(
        R.drawable.man10,
        R.drawable.ic_apple,
        R.string.kevin_vojco,
        PlatformType.iOS,
        "kevin.vojco@seznam.cz",
        "",
        "",
        true,
        true,
        true, 2,
        4,
        3,
        6
    ),
    LucieZvorilova(
        R.drawable.girl1,
        R.drawable.ic_android,
        R.string.lucie_zvorilova,
        PlatformType.Android,
        "lucie.zvorilova@seznam.cz",
        "",
        "",
        true,
        true,
        true,
        5,
        1,
        1,
        2
    ),
    PatrikMokry(
        R.drawable.man11,
        R.drawable.ic_android,
        R.string.patrik_mokry,
        PlatformType.Android,
        "patrik.mokry@gmail.com",
        "",
        "",
        true,
        true,
        true,
        1,
        1,
        0,
        0
    ),
    PavelStanek(
        R.drawable.man12,
        R.drawable.ic_android,
        R.string.pavel_stanek,
        PlatformType.Android,
        "pavel.stanek@gmail.com",
        "",
        "",
        true,
        true,
        true,
        5,
        1,
        1,
        2
    ),
    LenkaVesela(
        R.drawable.girl2,
        R.drawable.ic_apple,
        R.string.lenka_veselá,
        PlatformType.iOS,
        "lenka.vesela@gmail.cz",
        "",
        "",
        true,
        true,
        true,
        0,
        0,
        0,
        0
    ),
    PavlaVelka(
        R.drawable.girl3,
        R.drawable.ic_android,
        R.string.bara_cmelova,
        PlatformType.Android,
        "cmelova.b@gmail.com",
        "https://cdn.optinmonster.com/wp-content/uploads/2018/06/android-404-845x504.png",
        "",
        true,
        true,
        true,
        2,
        4,
        3,
        6
    ),
    RomanMaly(
        R.drawable.man8,
        R.drawable.ic_apple,
        R.string.kevin_vojco,
        PlatformType.iOS,
        "kevin.vojco@seznam.cz",
        "",
        "",
        true,
        false,
        false,
        5,
        1,
        1,
        2
    ),
    IgorStredni(
        R.drawable.girl3,
        R.drawable.ic_android,
        R.string.lucie_zvorilova,
        PlatformType.Android,
        "lucie.zvorilova@seznam.cz",
        "",
        "",
        true,
        true,
        true,
        1,
        1,
        0,
        0
    ),
    PetraNovakova(
        R.drawable.girl4,
        R.drawable.ic_android,
        R.string.patrik_mokry,
        PlatformType.Android,
        "patrik.mokry@gmail.com",
        "",
        "",
        true,
        true,
        true,
        2,
        4,
        3,
        6
    ),
    RomanTrudic(
        R.drawable.man1,
        R.drawable.ic_android,
        R.string.pavel_stanek,
        PlatformType.Android,
        "pavel.stanek@gmail.com",
        "",
        "",
        true,
        true,
        true,
        4,
        8,
        3,
        2
    ),
    KarelVlasak(
        R.drawable.man2,
        R.drawable.ic_apple,
        R.string.lenka_veselá,
        PlatformType.iOS,
        "lenka.vesela@gmail.cz",
        "",
        "",
        true,
        true,
        true,
        5,
        1,
        1,
        2
    ),
}