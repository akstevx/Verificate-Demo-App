package com.verificate.verificate.model.response.states.lga

data class StatesLGAItem(
    val alias: String,
    val lgas: List<String>,
    val state: String
)