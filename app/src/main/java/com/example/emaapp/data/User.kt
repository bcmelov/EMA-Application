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
    val hw1_progress: HomeworkProgress,
    val hw2_progress: HomeworkProgress,
    val hw3_progress: HomeworkProgress,
    val hw4_progress: HomeworkProgress,
    val hw5_progress: HomeworkProgress,
    val hw6_progress: HomeworkProgress,
    val android_skills: Int,
    val kotlin_skills: Int,
    val iOs_skills: Int,
    val apple_skills: Int
) {
    //list of users (test one, the final one has to be completed!
    BaraCmelova(R.drawable.me, R.drawable.ic_android,R.string.bara_cmelova, PlatformType.Android, "cmelova.b@gmail.com", "https://cdn.optinmonster.com/wp-content/uploads/2018/06/android-404-845x504.png", "", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.waiting, HomeworkProgress.waiting, 1,1,0,0),
    KevinVojco(R.drawable.ic_apple, R.drawable.ic_apple, R.string.kevin_vojco, PlatformType.iOS, "kevin.vojco@seznam.cz", "","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.submit, HomeworkProgress.waiting,2,4,3,6),
    LucieZvorilova(R.drawable.ic_emoji, R.drawable.ic_android, R.string.lucie_zvorilova, PlatformType.Android, "lucie.zvorilova@seznam.cz","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.waiting, HomeworkProgress.waiting, 5,1,1,2),
    PatrikMokry(R.drawable.ic_swift, R.drawable.ic_android, R.string.patrik_mokry, PlatformType.Android,"patrik.mokry@gmail.com","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.waiting, HomeworkProgress.waiting, HomeworkProgress.waiting,1,1,0,0),
    PavelStanek(R.drawable.ic_kotlin, R.drawable.ic_android, R.string.pavel_stanek, PlatformType.Android,"pavel.stanek@gmail.com","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.waiting, HomeworkProgress.waiting, HomeworkProgress.waiting,5,1,1,2),
    LenkaVesela(R.drawable.ic_emoji, R.drawable.ic_apple, R.string.lenka_veselá, PlatformType.iOS, "lenka.vesela@gmail.cz","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.waiting, HomeworkProgress.waiting,0,0,0,0),
    PavlaVelka(R.drawable.me, R.drawable.ic_android,R.string.bara_cmelova, PlatformType.Android, "cmelova.b@gmail.com", "https://cdn.optinmonster.com/wp-content/uploads/2018/06/android-404-845x504.png", "", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.waiting, HomeworkProgress.waiting,2,4,3,6),
    RomanMaly(R.drawable.ic_apple, R.drawable.ic_apple, R.string.kevin_vojco, PlatformType.iOS, "kevin.vojco@seznam.cz", "","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.submit, HomeworkProgress.waiting,5,1,1,2),
    IgorStredni(R.drawable.ic_emoji, R.drawable.ic_android, R.string.lucie_zvorilova, PlatformType.Android, "lucie.zvorilova@seznam.cz","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.waiting, HomeworkProgress.waiting,1,1,0,0),
    PetraNovakova(R.drawable.ic_swift, R.drawable.ic_android, R.string.patrik_mokry, PlatformType.Android,"patrik.mokry@gmail.com","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.waiting, HomeworkProgress.waiting, HomeworkProgress.waiting,2,4,3,6),
    RomanTrudic(R.drawable.ic_kotlin, R.drawable.ic_android, R.string.pavel_stanek, PlatformType.Android,"pavel.stanek@gmail.com","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.waiting, HomeworkProgress.waiting, HomeworkProgress.waiting,4,8,3,2),
    KarelVlasak(R.drawable.ic_emoji, R.drawable.ic_apple, R.string.lenka_veselá, PlatformType.iOS, "lenka.vesela@gmail.cz","","", HomeworkProgress.merge, HomeworkProgress.merge, HomeworkProgress.submit, HomeworkProgress.review, HomeworkProgress.waiting, HomeworkProgress.waiting,5,1,1,2),
}