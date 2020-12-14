package snow.app.eduhub.util

import snow.app.eduhub.ui.network.responses.testquestionsres.Question

interface GetQueList {
    fun getQueLIst(arrayList: List<Question>)
}