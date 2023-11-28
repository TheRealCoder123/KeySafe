package com.nextsolutions.keyysafe.FAQ.ui.FAQScreen

import com.nextsolutions.keyysafe.FAQ.domain.model.FAQuestion

data class FAQScreenState(
    val faqs: List<FAQuestion> = emptyList(),
    val searchTextState: String = ""
)
