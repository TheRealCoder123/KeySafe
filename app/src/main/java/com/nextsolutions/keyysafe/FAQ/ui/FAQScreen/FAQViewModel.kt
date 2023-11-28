package com.nextsolutions.keyysafe.FAQ.ui.FAQScreen

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.nextsolutions.keyysafe.FAQ.data.getFAQList
import com.nextsolutions.keyysafe.FAQ.domain.model.FAQuestion
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FAQViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _state = mutableStateOf(FAQScreenState())
    val state : State<FAQScreenState> = _state

    init {
        _state.value = FAQScreenState(getFAQList(app))
    }

    fun clearSearch() {
        _state.value = _state.value.copy(searchTextState = "")
    }

    fun updateSearchTextState(newText: String) {
        _state.value = _state.value.copy(searchTextState = newText)
    }

    fun searchFaqs(query: String) {

        if (query.isBlank()) {
            _state.value = _state.value.copy(faqs = getFAQList(app))
        }else{
            val faqList = getFAQList(app)
            val matchingFAQs = mutableListOf<FAQuestion>()

            for (faq in faqList) {
                if (faq.question.contains(query, true) || faq.answer.contains(query, true)) {
                    matchingFAQs.add(faq)
                }
            }

            _state.value = _state.value.copy(faqs = matchingFAQs)
        }

    }

}