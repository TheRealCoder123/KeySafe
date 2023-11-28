package com.nextsolutions.keyysafe.app

import android.R
import android.app.assist.AssistStructure
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.nextsolutions.keyysafe.db.database.DataAccessObject
import com.nextsolutions.keyysafe.entry.domain.enums.EntryDataFieldType
import com.nextsolutions.keyysafe.entry.domain.model.EntryDataField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class AutofillService : AutofillService() {

    @Inject
    lateinit var dao: DataAccessObject

    override fun onFillRequest(fillReq: FillRequest, cancellationSignal: CancellationSignal, fillCallback: FillCallback) {
        val context: List<FillContext> = fillReq.fillContexts
        val structure: AssistStructure = context[context.size - 1].structure
        CoroutineScope(Dispatchers.IO).launch {
            dao.getAllEntriesWithoutFlow().forEach {  entry ->
                val emails = entry.fields.filter { it.type == EntryDataFieldType.Email }
                val email: EntryDataField? = if (emails.isNotEmpty()) emails[0] else null
                val passwords = entry.fields.filter { it.type == EntryDataFieldType.Email }
                val password: EntryDataField? = if (passwords.isNotEmpty()) passwords[0] else null

                val st = traverseStructure(structure)

                val emailView = RemoteViews(packageName, R.layout.simple_list_item_1)
                emailView.setTextViewText(android.R.id.text1, email?.value ?: "Email")
                val passwordView = RemoteViews(packageName, R.layout.simple_list_item_1)
                passwordView.setTextViewText(android.R.id.text1, password?.value ?: "Password")


                if (email != null && password != null){
                    st.forEach {

                        val viewNode: AssistStructure.ViewNode = it.rootViewNode

                        val fillResponse: FillResponse = FillResponse.Builder()
                            .addDataset(
                                Dataset.Builder()
                                    .setValue(
                                        viewNode.autofillId!!,
                                        AutofillValue.forText(email.value),
                                        emailView
                                    )
                                    .setValue(
                                        viewNode.autofillId!!,
                                        AutofillValue.forText(password.value),
                                        passwordView
                                    )
                                    .build()
                            ).build()
                        fillCallback.onSuccess(fillResponse)
                    }
                }

            }



        }

    }

    fun traverseStructure(structure: AssistStructure): List<AssistStructure.WindowNode> {
        val windowNodes: List<AssistStructure.WindowNode> =
            structure.run {
                (0 until windowNodeCount).map { getWindowNodeAt(it) }
            }

        windowNodes.forEach { windowNode: AssistStructure.WindowNode ->
            val viewNode: AssistStructure.ViewNode? = windowNode.rootViewNode
            traverseNode(viewNode)
        }

        return windowNodes
    }

    private fun traverseNode(viewNode: AssistStructure.ViewNode?) {
//        if (viewNode?.autofillHints?.isNotEmpty() == true) {
//            // If the client app provides autofill hints, you can obtain them using
//            // viewNode.getAutofillHints();
//        } else {
//            // Or use your own heuristics to describe the contents of a view
//            // using methods such as getText() or getHint()
//        }

        val children: List<AssistStructure.ViewNode>? =
            viewNode?.run {
                (0 until childCount).map { getChildAt(it) }
            }

        children?.forEach { childNode: AssistStructure.ViewNode ->
            traverseNode(childNode)
        }
    }


    override fun onSaveRequest(saveRequest: SaveRequest, saveCallback: SaveCallback) {

    }

}


data class ParsedStructure(var usernameId: AutofillId, var passwordId: AutofillId)