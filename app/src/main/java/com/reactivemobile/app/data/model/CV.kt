package com.reactivemobile.app.data.model

data class CV(val basics: Basics, val work: List<Work>, val education: List<Education>, val skills: List<Skill>)

data class Basics(val name: String, val label: String, val email: String, val phone: String, val summary: String)

data class Work(
    val company: String,
    val position: String,
    val website: String,
    val startDate: String,
    val endDate: String,
    val summary: String
)

data class Education(
    val institution: String,
    val area: String,
    val studyType: String,
    val startDate: String,
    val endDate: String
)

data class Skill(
    val name: String,
    val level: String,
    val keywords: List<String>
)