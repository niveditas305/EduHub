package snow.app.eduhub.network


import Constants.Companion.ABOUT
import Constants.Companion.ADMIN_DETAILS
import Constants.Companion.CHANGE_PASSWORD
import Constants.Companion.CHAPTER_RELATED_VIDEO
import Constants.Companion.CONTACT_US
import Constants.Companion.FAV_TEACHER_LIST
import Constants.Companion.FAV_UNFAV_TEACHER
import Constants.Companion.FETCH_ALL_NOTIFICATIONS
import Constants.Companion.FORGOT_PASSWORD
import Constants.Companion.GET_ALL_CHAT_MESSAGES
import Constants.Companion.GET_ALL_NOTIFICATIONS
import Constants.Companion.GET_CHAPTERS
import Constants.Companion.GET_CONVERSATION_ID
import Constants.Companion.GET_NOTIFICATION_STATUS
import Constants.Companion.GET_NOTI_COUNT
import Constants.Companion.GET_STARTED_URL
import Constants.Companion.GET_SUBJECT
import Constants.Companion.GET_SUBJECTS_BY_ID
 import Constants.Companion.GET_TOP_PICK
import Constants.Companion.GET_TOP_TEACHER
import Constants.Companion.GET_UNIQUE_ID
import Constants.Companion.GRADES_URL
import Constants.Companion.HOME_BANNER
import Constants.Companion.HOME_DATA_API
import Constants.Companion.LESSON_LIST
import Constants.Companion.LOGIN
import Constants.Companion.NOTIFICATION
import Constants.Companion.PAST_QUESTION_PPRS
import Constants.Companion.PAST_QUE_CATEGORY
import Constants.Companion.PRIVACY_POLICY
import Constants.Companion.RATING
import Constants.Companion.RECEIVE_MESSAGE
import Constants.Companion.RECENT_CONTINUE_TOPIC
import Constants.Companion.REGISTER
import Constants.Companion.RESET_FORGOT_PASSWORD
import Constants.Companion.SEARCH_API
import Constants.Companion.SEND_MESSAGE
import Constants.Companion.SEND_OTP
import Constants.Companion.SOCIAL_LOGIN
import Constants.Companion.SUBJECT_LIST
import Constants.Companion.SUBMIT_ANS
import Constants.Companion.TEACHER_PROFILE
import Constants.Companion.TERMS_AND_CONDITIONS
import Constants.Companion.TEST_LIST
import Constants.Companion.TEST_QUESTION_LIST
import Constants.Companion.TEST_SCORE
import Constants.Companion.TEST_SUMMARY
import Constants.Companion.TOPIC_DETAILS
import Constants.Companion.TOPIC_DETAILS_LIST
import Constants.Companion.TOPIC_LIST
import Constants.Companion.UPDATE_PROFILE
import Constants.Companion.USER_DETAIL
import Constants.Companion.VERIFY_OTP
import Constants.Companion.WELCOME_URL
import Constants.Companion.WORKSHEET_LIST
import app.sixdegree.network.responses.sendmessage.SendMessageRes
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
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
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
import snow.app.eduhub.ui.network.responses.getallchats.GetAllChatsRes
import snow.app.eduhub.ui.network.responses.getconversationid.GetConverstaionIdRes
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

interface ApiService {

    @GET(WELCOME_URL)
    fun fetchWelcomeData(): Observable<WelcomeData>

    @GET(GET_STARTED_URL)

    fun getStartedData(): Observable<GetStartedData>

    @POST(REGISTER)
    fun register(@Body params: HashMap<String, String>): Observable<SignupRes>


    @GET(GRADES_URL)
    fun fetchGrades(): Observable<GradesData>

    @POST(VERIFY_OTP)
    fun verifyOtp(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<VerifyOTP>

    @POST(LOGIN)
    fun login(@Body params: HashMap<String, String>): Observable<SignupRes>

    @POST(CHANGE_PASSWORD)
    fun changePassword(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<CommonResponse>

    @GET(ABOUT)
    fun fetchAboutData(): Observable<AboutData>

    @GET(ADMIN_DETAILS)
    fun fetchAdminDetails(@Header("Authorization") token: String): Observable<FetchAdminDetails>
    @GET(FETCH_ALL_NOTIFICATIONS)
    fun fetchAllNOtifications(@Header("Authorization") token: String
                             ): Observable<FetchNotificationListRes>
    @GET(SUBJECT_LIST)
    fun getSubjectList(@Header("Authorization") token: String): Observable<SubjectList>

    @GET(GET_NOTIFICATION_STATUS)
    fun getNotificationStatus(@Header("Authorization")  token:String ): Observable<GetNotificationStatus>

    @POST(CONTACT_US)
    fun fetchContactUsData(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<CommonResponse>


    @GET(PRIVACY_POLICY)
    fun fetchPrivacyPolicy(): Observable<PrivacyPolicyResponse>

    @POST(SEND_OTP)
    fun sendOtp(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<BaseRes>

    @GET(TERMS_AND_CONDITIONS)
    fun fetchTermsAndConditions(): Observable<TermsAndConditionsResponse>

    @GET(HOME_BANNER)
    fun fetchHomeBanners(): Observable<HomeBannerData>
    @GET(GET_NOTI_COUNT)
    fun getNotificationsCount(@Header("Authorization") token: String ): Observable<NotificationCountRes>
    @POST(NOTIFICATION)
    fun changeNotificationStatus(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<SetNotificationStatus>
    @POST(RECENT_CONTINUE_TOPIC)
    fun recentContinueTopic(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<BaseRes>

    @GET(GET_SUBJECT)
    fun fetchSubjectByGrade(@Header("Authorization") token: String): Observable<GetSubjectByGrade>


    @GET(HOME_DATA_API)
    fun fetchHomeDataApi(@Header("Authorization") token: String): Observable<HomeDataRes>

    @GET(GET_TOP_PICK)
    fun fetchTopPicks(@Header("Authorization") token: String): Observable<TopPicks>

    @GET(FAV_TEACHER_LIST)
    fun fetchFavTeachers(@Header("Authorization") token: String): Observable<FetchFavTeacherRes>

    @GET(PAST_QUE_CATEGORY)
    fun fetchQuepprCat(@Header("Authorization") token: String): Observable<FetchQuestionPprCategoryRes>

    @POST(GET_TOP_TEACHER)
    fun fetchTopTeachers(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<FetchTeachersRes>



    @POST(PAST_QUESTION_PPRS)
    fun fetchPastQuestionpprs(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<PastQuestionPpr>

    @POST(SEARCH_API)
    fun searchApi(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<SearchRes>

    @GET(USER_DETAIL)
    fun fetchUserDetail(@Header("Authorization") token: String): Observable<FetchProfile>


    @GET(GET_ALL_NOTIFICATIONS)
    fun getAllNotifications(@Header("Authorization") token: String): Observable<FetchProfile>

    @POST(FORGOT_PASSWORD)
    fun forgotPassword(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<ForgotPasswordResponse>

    @POST(TEST_SCORE)
    fun testScore(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<ScoreRes>


    @POST(TEST_SUMMARY)
    fun testSummary(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<TestSummaryRes>


    @POST(GET_CONVERSATION_ID)
    fun getConversationId(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<GetConverstaionIdRes>


    @POST(GET_UNIQUE_ID)
    fun getUniqueId(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<GetUniqueId>

    @POST(LESSON_LIST)
    fun getLessonList(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<LessonListRes>



    @POST(WORKSHEET_LIST)
    fun getWorksheetList(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<WorksheetListRes>

    @POST(GET_CHAPTERS)
    fun getChapters(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<GetChaptersResponse>

    @POST(TOPIC_DETAILS_LIST)
    fun getTopicDetails(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<GetTopicDetailsRes>

    @POST(GET_SUBJECTS_BY_ID)
    fun getSubjectsById(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<GetSubjectListById>
  @POST(TEST_LIST)
    fun testList(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<TestListRes>
 @POST(TEST_QUESTION_LIST)
    fun testQuestionsList(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<TestQuestionsRes>
 @POST(SUBMIT_ANS)
    fun submitAns(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<SubmitAnsRes>


    @POST(RATING)
    fun giveRating(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<BaseRes>

    @POST(FAV_UNFAV_TEACHER)
    fun favUbfavTeachers(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<BaseRes>


    @POST(CHAPTER_RELATED_VIDEO)
    fun getChapterVideos(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<ChapterVideos>


    @POST(TOPIC_LIST)
    fun getTopicList(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<TopicListRes>

    //forgot rset pass
    @POST(RESET_FORGOT_PASSWORD)
    fun resetPass(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<BaseRes>


    //teacher profile
    @POST(TEACHER_PROFILE)
    fun teacherProfile(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<TeachersProfileRes>


    //FETCH TOPIC DETAILS WITH PDF
    @POST(TOPIC_DETAILS)
    fun fetchTopicDetails(
        @Header("Authorization") token: String,
        @Body params: HashMap<String, String>
    ): Observable<TopicDetailsRes>

    //social login
    @POST(SOCIAL_LOGIN)
    fun socialLogin(
        @Body params: HashMap<String, String>
    ): Observable<SignupRes>

    @Multipart
    @POST(UPDATE_PROFILE)
    fun updateProfile(


        @Header("Authorization") token: String,
        @Part("first_name") first_name: RequestBody,
        @Part("last_name") last_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("student_mobile") student_mobile: RequestBody,
        @Part("student_school") student_school: RequestBody,
        @Part("school_class_id") school_class_id: RequestBody,
        @Part("student_address") student_address: RequestBody,
        @Part("country_code") country_code: RequestBody,

        @Part profile_photo: MultipartBody.Part?
    ): Observable<SignupRes> //image

    // get all chat messages
    @GET(GET_ALL_CHAT_MESSAGES)
    fun getAllChatMessages(@Header("Authorization") token: String?,
                           @QueryMap hashMap: java.util.HashMap<String?, String?>?): Observable<GetAllChatsRes?>?


    //send message
    @POST(SEND_MESSAGE)
    @FormUrlEncoded
    fun sendMessage(@Header("Authorization") token: String?,
                    @FieldMap app: java.util.HashMap<String?, String?>?): Observable<SendMessageRes?>?

    //send message
    @GET(RECEIVE_MESSAGE)
    fun receiveMsg(@Header("Authorization") token:
                   String?, @QueryMap app: java.util.HashMap<String?, String?>?): Observable<GetAllChatsRes?>?


}