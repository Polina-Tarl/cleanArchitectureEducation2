package ru.tarlycheva.navigation.extension

import androidx.navigation.NavController
import ru.tarlycheva.navigation.actions.BaseAction

fun NavController.navigateGlobal(action: BaseAction) {
    this.navigate(action.id)
}