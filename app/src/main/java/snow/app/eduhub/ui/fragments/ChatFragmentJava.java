package snow.app.eduhub.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.sixdegree.network.responses.sendmessage.SendMessageRes;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import snow.app.eduhub.R;
import snow.app.eduhub.network.ApiAdapter;
import snow.app.eduhub.network.ApiService;
import snow.app.eduhub.ui.adapter.ChatDetailAdapter;
import snow.app.eduhub.ui.network.responses.admindetailsres.FetchAdminDetails;
import snow.app.eduhub.ui.network.responses.getallchats.Datum;
import snow.app.eduhub.ui.network.responses.getallchats.GetAllChatsRes;
import snow.app.eduhub.ui.network.responses.getconversationid.GetConverstaionIdRes;
import snow.app.eduhub.util.AppSession;
import snow.app.eduhub.util.BaseFragment;

public class ChatFragmentJava extends BaseFragment {

    ApiAdapter apiAdapter = new ApiAdapter();
    ApiService apiService = apiAdapter.getRetrofitInstance().create(ApiService.class);

    EditText sendmsg;
    RecyclerView chats_rv;
    ChatDetailAdapter chatDetailAdapter;
    ProgressBar pbar;
    List<Datum> chatUserList = new ArrayList<>();
    Timer timer;
    MyTimerTask myTimerTask;
    TextView txt_top_header;
    String admin_id = "";
    String conversation_id = "0";
    ImageView sendBtn, user_img;
ProgressDialog dialogg;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_java, container, false);
         dialogg = new ProgressDialog(requireContext());
         dialogg.setMessage("Please wait...");
         dialogg.setCancelable(false);
      //  dialog.show();


        // txt_top_header = view.findViewById(R.id.txt_top_header);
        // appSession=new AppSession( context );
        sendBtn = view.findViewById(R.id.attach);

        sendmsg = view.findViewById(R.id.ed_msg);
        //    pbar = findViewById(R.id.pbar);
        // user_img = findViewById(R.id.user_img);
        chats_rv = view.findViewById(R.id.rv_msgs);

        // txt_top_header.setText(getIntent().getStringExtra("name"));

        if (!isNetworkConnected()) {
            showToast("Please check internet connection!");
            return null;
        }
        if (timer != null) {
            timer.cancel();
        }

/*        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .centerCrop()
                .apply(new RequestOptions().override( getScreenWidth() / 3, getScreenWidth() / 3))
                .apply( RequestOptions.circleCropTransform())
                .into(user_img);*/


        chats_rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        sendmsg.setOnEditorActionListener(new DoneOnEditorActionListener());

        chatDetailAdapter = new ChatDetailAdapter(chatUserList, requireContext());
        chats_rv.setAdapter(chatDetailAdapter);
        chatDetailAdapter.notifyDataSetChanged();


        if (isNetworkConnected()) {
            adminDetails();
        } else {
            showInternetToast();
        }


        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 3000);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sendmsg.getText().toString().isEmpty()) {
                    Datum datum = new Datum();
                    datum.setId(0l);
                    datum.setConversationId(/*Long.parseLong(getIntent().getStringExtra("chat_group_id"))*/(long) 0);
                    datum.setFromUserId(Long.valueOf(getSession().getAppData().getData().getId()));
                    datum.setCreated_date("");
                    datum.setMessage(sendmsg.getText().toString());
                   Date d = new Date();
                    CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                 datum.setCreated_date(String.valueOf(s));

                    //  datum.setFromName(getSession().getAppData().getData().getName() );
                    chatUserList.add(datum);
                    chatDetailAdapter.notifyDataSetChanged();
                    if (chatUserList.size() > 0) {
                        chats_rv.scrollToPosition(chatUserList.size() - 1);
                    }
                    if (!isNetworkConnected()) {
                        showInternetToast();
                        return;
                    }
                    sendMsg(sendmsg.getText().toString());
                    sendmsg.setText("");
                    hideKeyboard(requireActivity());


                }
            }
        });


        return view;
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onResume() {

        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 3000);
        super.onResume();
    }

    public void sendMsg(String msg) {
      /*  from_user_id:9
        to_user_id:1
        message:hieeee
*/
        HashMap<String, String> map = new HashMap<>();
        // map.put("conversation_id", /*getIntent().getStringExtra("chat_group_id")*/ "0");
        map.put("from_user_id", String.valueOf(getSession().getAppData().getData().getId()));
        map.put("to_user_id", admin_id);
        map.put("message", msg);


        Observer<SendMessageRes> observer = apiService.sendMessage(getUserToken(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<SendMessageRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SendMessageRes res) {
                        if (!res.getStatus()) {

                            chatUserList.remove(chatUserList.size() - 1);
                            chatDetailAdapter.notifyDataSetChanged();

                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("err", "=" + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public void adminDetails() {
      /*  from_user_id:9
        to_user_id:1
        message:hieeee
*/

        dialogg.show();
        Observer<FetchAdminDetails> observer = apiService.fetchAdminDetails(getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<FetchAdminDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FetchAdminDetails res) {
                        if (res.getStatus()) {
                            dialogg.dismiss();
                            admin_id = String.valueOf(res.getData().getId());


                            if (isNetworkConnected()) {
                                //fetch messages
                                fetchMessages();


                                getConversationId();
                            } else {
                                showInternetToast();
                            }

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogg.dismiss();
                        Log.e("err", "=" + e);
                    }

                    @Override
                    public void onComplete() {
                        dialogg.dismiss();
                    }
                });


    }

    public void getConversationId() {
        dialogg.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("from_user_id", String.valueOf(getSession().getAppData().getData().getId()));
        map.put("to_user_id", admin_id);
        Observer<GetConverstaionIdRes> observer = apiService.getConversationId(getUserToken(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<GetConverstaionIdRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(GetConverstaionIdRes res) {
                        if (res.getStatus()) {
                            if (res.getConversationId() == 0) {
                                conversation_id = "0";
                            } else {
                                conversation_id = String.valueOf(res.getConversationId());
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        dialogg.dismiss();
                        Log.e("err", "=" + e);
                    }

                    @Override
                    public void onComplete() {
                        dialogg.dismiss();
                        //  pbar.setVisibility(View.GONE);
                    }
                });

    }


    public void fetchMessages() {

       dialogg.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("from_user_id", String.valueOf(getSession().getAppData().getData().getId()));
        map.put("to_user_id", admin_id);
        Observer<GetAllChatsRes> observer = apiService.getAllChatMessages(getUserToken(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<GetAllChatsRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetAllChatsRes res) {
                        if (res.getStatus()) {

                            dialogg.dismiss();
                            chatUserList.addAll(res.getData());
                            chatDetailAdapter.notifyDataSetChanged();
                            if (chatUserList.size() > 0) {
                                chats_rv.scrollToPosition(chatUserList.size() - 1);

/*
                                if (res.getData().get(0).getConversationId() != null) {
                                    conversation_id = res.getData().get(0).getConversationId().toString();
                                } else {
                                    conversation_id = "0";
                                }*/
                            }


                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialogg.dismiss();
                        Log.e("err", "=" + e);
                    }

                    @Override
                    public void onComplete() {
                        dialogg.dismiss();
                        //  pbar.setVisibility(View.GONE);
                    }
                });

    }

    public void recieveMsg() {


        HashMap<String, String> map = new HashMap<>();
        map.put("conversation_id", conversation_id /*getIntent().getStringExtra("chat_group_id")*/);
        map.put("to_user_id", String.valueOf(getSession().getAppData().getData().getId()));
        Observer<GetAllChatsRes> observer = apiService.receiveMsg(getUserToken(), map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<GetAllChatsRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GetAllChatsRes res) {
                        if (res.getStatus()) {
                            chatUserList.addAll(res.getData());
                            chatDetailAdapter.notifyDataSetChanged();

                            if (chatUserList.size() > 0) {
                                chats_rv.scrollToPosition(chatUserList.size() - 1);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Log.e("err", "=" + e);
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });


    }

    @Override
    public void onStop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            requireActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    if (!isNetworkConnected()) {
                        showInternetToast();
                        return;
                    }

                    recieveMsg();
                }
            });
        }

    }

}