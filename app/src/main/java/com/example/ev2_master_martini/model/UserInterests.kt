package com.example.ev2_master_martini.model

data class UserInterests(
    val businessType: String,
    val businessSize: String,
    val catalogOptions: Set<String>
)
