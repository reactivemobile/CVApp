package com.reactivemobile.app.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.reactivemobile.app.R
import com.reactivemobile.app.data.model.*

class MainAdapter(private val cv: CV) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeBasics = 1
    private val viewTypeHeader = 2
    private val viewTypeWork = 3
    private val viewTypeEducation = 4
    private val viewTypeSkills = 5

    private val constantViewSize = 5 // Headers and "Basics"

    private val basicsHeaderPosition = 0
    private val basicsPosition = 1
    private val workHeaderPosition = 2
    private val educationHeaderPosition = workHeaderPosition + cv.work.size + 1
    private val skillsHeaderPosition = educationHeaderPosition + cv.education.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewTypeBasics -> BasicsViewHolder(inflate(parent, R.layout.basics_list_item))
            viewTypeHeader -> HeaderViewHolder(inflate(parent, R.layout.header_list_item))
            viewTypeWork -> WorkViewHolder(inflate(parent, R.layout.work_list_item))
            viewTypeSkills -> SkillViewHolder(inflate(parent, R.layout.skill_list_item))
            else -> EducationViewHolder(inflate(parent, R.layout.education_list_item))
        }
    }

    private fun inflate(parent: ViewGroup, @LayoutRes id: Int): View {
        return LayoutInflater.from(parent.context).inflate(id, parent, false)
    }

    override fun getItemCount(): Int {
        return constantViewSize + cv.work.size + cv.education.size + cv.skills.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == basicsHeaderPosition
            || position == workHeaderPosition
            || position == educationHeaderPosition
            || position == skillsHeaderPosition
        ) {
            viewTypeHeader
        } else if (position == basicsPosition) {
            viewTypeBasics
        } else if (position < educationHeaderPosition) {
            viewTypeWork
        } else if (position < skillsHeaderPosition) {
            viewTypeEducation
        } else {
            viewTypeSkills
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.setText(
                when (position) {
                    basicsHeaderPosition -> R.string.basics
                    workHeaderPosition -> R.string.work
                    educationHeaderPosition -> R.string.education
                    else -> R.string.skills
                }
            )
            is BasicsViewHolder -> holder.setBasics(cv.basics)
            is WorkViewHolder -> holder.setWork(cv.work[position - (workHeaderPosition + 1)])
            is EducationViewHolder -> holder.setEducation(cv.education[position - (educationHeaderPosition + 1)])
            is SkillViewHolder -> holder.setSkill(cv.skills[position - (skillsHeaderPosition + 1)])
        }
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setText(@StringRes text: Int) {
            (itemView as TextView).setText(text)
        }
    }

    class BasicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setBasics(basics: Basics) {
            itemView.findViewById<TextView>(R.id.name).text = basics.name
            itemView.findViewById<TextView>(R.id.label).text = basics.label
            itemView.findViewById<TextView>(R.id.email).text = basics.email
            itemView.findViewById<TextView>(R.id.phone).text = basics.phone
            itemView.findViewById<TextView>(R.id.summary).text = basics.summary
        }
    }

    class SkillViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setSkill(skill: Skill) {
            itemView.findViewById<TextView>(R.id.name).text = skill.name
            itemView.findViewById<TextView>(R.id.level).text = skill.level
            itemView.findViewById<TextView>(R.id.keywords).text = skill.keywords.joinToString(", ")
        }
    }

    inner class WorkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setWork(work: Work) {
            itemView.findViewById<TextView>(R.id.company).text = work.company
            itemView.findViewById<TextView>(R.id.position).text = work.position
            itemView.findViewById<TextView>(R.id.website).text = work.website
            itemView.findViewById<TextView>(R.id.dates).text = combineDates(work.startDate, work.endDate)
            itemView.findViewById<TextView>(R.id.summary).text = work.summary
        }
    }

    inner class EducationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setEducation(education: Education) {
            itemView.findViewById<TextView>(R.id.institution).text = education.institution
            itemView.findViewById<TextView>(R.id.area).text = education.area
            itemView.findViewById<TextView>(R.id.dates).text = combineDates(education.startDate, education.endDate)
        }
    }

    fun combineDates(s1: String, s2: String): String {
        return "$s1 to $s2"
    }
}