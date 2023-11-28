package com.nextsolutions.keyysafe.main.domain.model

data class DashboardEntry(
    val entryId: String,
    val title: String,
    val subTitle: String,
    val timeStamp: Long,
    val color: Int,
    val askForAuth: Boolean
)