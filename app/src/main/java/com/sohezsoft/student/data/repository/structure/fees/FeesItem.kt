package com.sohezsoft.student.data.repository.structure.fees

data class FeesItem(
    val due_date: String,
    val fees_type: String,
    val last_update_date: String,
    val paid_amount: Double,
    val pending_amount: Double,
    val program: String,
    val statues: String,
    val total_amount: Double
)