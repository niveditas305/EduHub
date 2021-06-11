package snow.app.eduhub.repo

import android.util.Log
import snow.app.eduhub.network.ApiAdapter
import snow.app.eduhub.network.ApiService
import snow.app.eduhub.network.responses.PrivacyPolicyResponse
import snow.app.eduhub.network.responses.TermsAndConditionsResponse
import snow.app.eduhub.network.responses.about.AboutData
import snow.app.eduhub.network.responses.commonResponse.CommonResponse
import snow.app.eduhub.network.responses.fetchWelcomeData.WelcomeData
import snow.app.eduhub.network.responses.forgotPassword.ForgotPasswordResponse
import snow.app.eduhub.network.responses.getStarted.GetStartedData
import snow.app.eduhub.network.responses.getSubjects.GetSubjectByGrade
import snow.app.eduhub.network.responses.grades.GradesData
import snow.app.eduhub.network.responses.homeBanner.HomeBannerData
import snow.app.eduhub.network.responses.notification.SetNotificationStatus
import snow.app.eduhub.network.responses.otpVerification.VerifyOTP
import snow.app.eduhub.network.responses.topPicks.TopPicks
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import snow.app.beautasapserviceprovider.network.responses.signupres.getnotistatus.GetNotificationStatus
import snow.app.eduhub.ui.network.responses.NotificationCountRes
import snow.app.eduhub.ui.network.responses.admindetailsres.FetchAdminDetails
import snow.app.eduhub.ui.network.responses.baseres.BaseRes
import snow.app.eduhub.ui.network.responses.chapterVideos.ChapterVideos
import snow.app.eduhub.ui.network.responses.fetchfavteachers.FetchFavTeacherRes
import snow.app.eduhub.ui.network.responses.fetchnotficationres.FetchNotificationListRes
import snow.app.eduhub.ui.network.responses.fetchprofile.FetchProfile
import snow.app.eduhub.ui.network.responses.fetchquestionpprcat.FetchQuestionPprCategoryRes
import snow.app.eduhub.ui.network.responses.fetchsubjectlist.SubjectList
import snow.app.eduhub.ui.network.responses.fetchteachers.FetchTeachersRes
import snow.app.eduhub.ui.network.responses.getChapters.GetChaptersResponse
import snow.app.eduhub.ui.network.responses.getconversationid.GetConverstaionIdRes
import snow.app.eduhub.ui.network.responses.getstudyguild.StudyGuideRes
import snow.app.eduhub.ui.network.responses.getsubjectlistbyid.GetSubjectListById
import snow.app.eduhub.ui.network.responses.getuniqueid.GetUniqueId
import snow.app.eduhub.ui.network.responses.homedatares.HomeDataRes
import snow.app.eduhub.ui.network.responses.lessonlistres.LessonListRes
import snow.app.eduhub.ui.network.responses.pastquestions.PastQuestionPpr
import snow.app.eduhub.ui.network.responses.scoreres.ScoreRes
import snow.app.eduhub.ui.network.responses.searchres.SearchRes
import snow.app.eduhub.ui.network.responses.signup.SignupRes
import snow.app.eduhub.ui.network.responses.submitans.SubmitAnsRes
import snow.app.eduhub.ui.network.responses.teachersprofile.TeachersProfileRes
import snow.app.eduhub.ui.network.responses.testlistres.TestListRes
import snow.app.eduhub.ui.network.responses.testquestionsres.TestQuestionsRes
import snow.app.eduhub.ui.network.responses.testsummaryres.TestSummaryRes
import snow.app.eduhub.ui.network.responses.topicdetails.TopicDetailsRes
import snow.app.eduhub.ui.network.responses.topicdetailsres.GetTopicDetailsRes
import snow.app.eduhub.ui.network.responses.topiclistres.TopicListRes
import snow.app.eduhub.ui.network.responses.worksheetlist.WorksheetListRes
import snow.app.eduhub.util.OnTokenExpired


class Repo {
    var apiService = ApiAdapter().getRetrofitInstance()!!.create(ApiService::class.java)

    fun register(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>
    ) {
        apiService.register(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SignupRes> {

                override fun onNext(data: SignupRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }
                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })


    }

    fun sendOtpOnemail(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {


        apiService.sendOtp(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<BaseRes> {

                override fun onNext(data: BaseRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    if (e.message.equals("HTTP 401 Unauthorized")) {
                        // OnTokenExpired.onTokenExpiredListener?.onTokenExpiredListener()
                    } else {
                        onDataReadyCallback.onDataReady(null, true)
                    }

                }
            })


    }

    fun getChapters(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.getChapters(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetChaptersResponse> {

                override fun onNext(data: GetChaptersResponse) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun getTopicDeatails(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.getTopicDetails(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetTopicDetailsRes> {

                override fun onNext(data: GetTopicDetailsRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun giveRating(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.giveRating(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<BaseRes> {

                override fun onNext(data: BaseRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun getSubjectsById(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.getSubjectsById(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetSubjectListById> {

                override fun onNext(data: GetSubjectListById) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun testList(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.testList(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TestListRes> {

                override fun onNext(data: TestListRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun testQuestionsList(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.testQuestionsList(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TestQuestionsRes> {

                override fun onNext(data: TestQuestionsRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }
fun submitAns(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.submitAns(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SubmitAnsRes> {

                override fun onNext(data: SubmitAnsRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }


    fun getChapterRelatedVideos(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.getChapterVideos(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<ChapterVideos> {

                override fun onNext(data: ChapterVideos) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun getTopiclist(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.getTopicList(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TopicListRes> {

                override fun onNext(data: TopicListRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun searchApi(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.searchApi(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SearchRes> {

                override fun onNext(data: SearchRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun teacherProfile(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.teacherProfile(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TeachersProfileRes> {

                override fun onNext(data: TeachersProfileRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun topicDetailsRes(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {

        apiService.fetchTopicDetails(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TopicDetailsRes> {

                override fun onNext(data: TopicDetailsRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in signip--", "--" + e.toString())

                }
            })
    }

    fun welcome(onDataReadyCallback: OnDataReadyCallback) {
        apiService.fetchWelcomeData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<WelcomeData> {

                override fun onNext(data: WelcomeData) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun grades(
        onDataReadyCallback: OnDataReadyCallback
    ) {
        apiService.fetchGrades()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GradesData> {

                override fun onNext(data: GradesData) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })


    }

    fun login(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>
    ) {
        apiService.login(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SignupRes> {

                override fun onNext(data: SignupRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })


    }

    fun verifyOTP(
        onDataReadyCallback: OnDataReadyCallback,
        token: String,
        map: HashMap<String, String>
    ) {
        apiService.verifyOtp(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<VerifyOTP> {

                override fun onNext(data: VerifyOTP) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })


    }


    fun changePassword(
        onDataReadyCallback: OnDataReadyCallback,
        token: String,
        map: HashMap<String, String>
    ) {
        apiService.changePassword(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<CommonResponse> {

                override fun onNext(data: CommonResponse) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })


    }


    fun getStarted(
        onDataReadyCallback: OnDataReadyCallback
    ) {

        apiService.getStartedData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetStartedData> {

                override fun onNext(data: GetStartedData) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }
//forgot password

    fun resetPass(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>, token: String
    ) {


        apiService.resetPass(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<BaseRes> {

                override fun onNext(data: BaseRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    if (e.message.equals("HTTP 401 Unauthorized")) {
                        // OnTokenExpired.onTokenExpiredListener?.onTokenExpiredListener()
                    } else {
                        onDataReadyCallback.onDataReady(null, true)
                    }

                }
            })


    }

    fun getAboutData(
        onDataReadyCallback: OnDataReadyCallback
    ) {

        apiService.fetchAboutData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<AboutData> {

                override fun onNext(data: AboutData) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }
 fun getStudyGuide(
        onDataReadyCallback: OnDataReadyCallback,token: String, map: HashMap<String, String>
    ) {

        apiService.getStudyGuide(token,map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<StudyGuideRes> {

                override fun onNext(data: StudyGuideRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun fetchAdminDetails(
        onDataReadyCallback: OnDataReadyCallback
        , token: String
    ) {

        apiService.fetchAdminDetails(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<FetchAdminDetails> {

                override fun onNext(data: FetchAdminDetails) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun fetchAllNOtifications(
        onDataReadyCallback: OnDataReadyCallback,
        token: String
    ) {


        apiService.fetchAllNOtifications(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<FetchNotificationListRes> {

                override fun onNext(data: FetchNotificationListRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                }
            })

    }

    fun getSubjectList(
        onDataReadyCallback: OnDataReadyCallback
        , token: String
    ) {

        apiService.getSubjectList(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SubjectList> {

                override fun onNext(data: SubjectList) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }
    fun getNotificationStatus(
        onDataReadyCallback: OnDataReadyCallback,
        token: String
    ) {
        apiService.getNotificationStatus(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetNotificationStatus> {

                override fun onNext(data: GetNotificationStatus) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    if (e.message.equals("HTTP 401 Unauthorized")) {
                        OnTokenExpired.onTokenExpiredListener?.onTokenExpiredListener()
                    } else {
                        onDataReadyCallback.onDataReady(null, true)
                    }

                }
            })


    }


    fun getPrivacyPolicyData(
        onDataReadyCallback: OnDataReadyCallback
    ) {

        apiService.fetchPrivacyPolicy()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<PrivacyPolicyResponse> {

                override fun onNext(data: PrivacyPolicyResponse) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun getTermsAndConditions(
        onDataReadyCallback: OnDataReadyCallback
    ) {

        apiService.fetchTermsAndConditions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TermsAndConditionsResponse> {

                override fun onNext(data: TermsAndConditionsResponse) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun fetchQuestionPprCat(
        onDataReadyCallback: OnDataReadyCallback,
        token: String
    ) {

        apiService.fetchQuepprCat(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<FetchQuestionPprCategoryRes> {

                override fun onNext(data: FetchQuestionPprCategoryRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun getBanners(
        onDataReadyCallback: OnDataReadyCallback
    ) {

        apiService.fetchHomeBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<HomeBannerData> {

                override fun onNext(data: HomeBannerData) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun fetchContactUsData(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String

    ) {

        apiService.fetchContactUsData(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<CommonResponse> {

                override fun onNext(data: CommonResponse) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun changeNotificationStatus(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String

    ) {

        apiService.changeNotificationStatus(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SetNotificationStatus> {

                override fun onNext(data: SetNotificationStatus) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun getNotificationsCount(
        onDataReadyCallback: OnDataReadyCallback,

        token: String
    ) {


        apiService.getNotificationsCount(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<NotificationCountRes> {

                override fun onNext(data: NotificationCountRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                }
            })


    }
    fun recentContinueTopic(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String

    ) {

        apiService.recentContinueTopic(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<BaseRes> {

                override fun onNext(data: BaseRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun forgotPassword(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String

    ) {

        apiService.forgotPassword(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<ForgotPasswordResponse> {

                override fun onNext(data: ForgotPasswordResponse) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }
  fun testScore(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String) {
      apiService.testScore(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<ScoreRes> {
                override fun onNext(data: ScoreRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }
 fun testSummary(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String) {
      apiService.testSummary(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TestSummaryRes> {
                override fun onNext(data: TestSummaryRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }
 fun getUniqueId(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String) {
      apiService.getUniqueId(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetUniqueId> {
                override fun onNext(data: GetUniqueId) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

 fun getLessonList(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String) {
      apiService.getLessonList(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<LessonListRes> {
                override fun onNext(data: LessonListRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

 fun getWorksheetList(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String) {
      apiService.getWorksheetList(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<WorksheetListRes> {
                override fun onNext(data: WorksheetListRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun getConversationId(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>,
        token: String) {
      apiService.getConversationId(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetConverstaionIdRes> {
                override fun onNext(data: GetConverstaionIdRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }
                override fun onComplete() {
                }
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun socialLogin(
        onDataReadyCallback: OnDataReadyCallback,
        map: HashMap<String, String>
    ) {


        apiService.socialLogin(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<SignupRes> {

                override fun onNext(data: SignupRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                }
            })


    }

    fun getSubjectByGrade(
        onDataReadyCallback: OnDataReadyCallback,
        token: String

    ) {

        apiService.fetchSubjectByGrade(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<GetSubjectByGrade> {

                override fun onNext(data: GetSubjectByGrade) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun fetchHomeDataApi(
        onDataReadyCallback: OnDataReadyCallback,
        token: String

    ) {

        apiService.fetchHomeDataApi(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<HomeDataRes> {

                override fun onNext(data: HomeDataRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun getTopPicks(
        onDataReadyCallback: OnDataReadyCallback,
        token: String
    ) {

        apiService.fetchTopPicks(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<TopPicks> {

                override fun onNext(data: TopPicks) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun fetchFavTeachers(
        onDataReadyCallback: OnDataReadyCallback,
        token: String
    ) {

        apiService.fetchFavTeachers(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<FetchFavTeacherRes> {

                override fun onNext(data: FetchFavTeacherRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun getTopTeachers(
        onDataReadyCallback: OnDataReadyCallback,
        token: String, map: HashMap<String, String>
    ) {

        apiService.fetchTopTeachers(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<FetchTeachersRes> {

                override fun onNext(data: FetchTeachersRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }


    fun fetchPastQuestionpprs(
        onDataReadyCallback: OnDataReadyCallback,
        token: String, map: HashMap<String, String>
    ) {

        apiService.fetchPastQuestionpprs(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<PastQuestionPpr> {

                override fun onNext(data: PastQuestionPpr) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun favUbfavTeachers(
        onDataReadyCallback: OnDataReadyCallback,
        token: String, map: HashMap<String, String>
    ) {

        apiService.favUbfavTeachers(token, map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<BaseRes> {

                override fun onNext(data: BaseRes) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })

    }

    fun getUserDetail(
        onDataReadyCallback: OnDataReadyCallback,
        token: String
    ) {


        apiService.fetchUserDetail(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : Observer<FetchProfile> {

                override fun onNext(data: FetchProfile) {
                    onDataReadyCallback.onDataReady(data, false)
                }

                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    onDataReadyCallback.onDataReady(null, true)

                    Log.e("error in login--", "--" + e.toString())

                }
            })


    }
}


interface OnDataReadyCallback {
    fun onDataReady(data: Any?, isError: Boolean)
}
