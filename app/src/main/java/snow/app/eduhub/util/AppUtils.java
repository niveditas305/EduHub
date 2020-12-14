package snow.app.eduhub.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import snow.app.eduhub.R;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class AppUtils {


    public static void roundImageWithGlide(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                /*  .transform(new GlideCircleWithBorder(view.getContext(), 2, Color.parseColor("#005194")))*/
                .placeholder(R
                        .drawable.user)
                .into(view);
    }

    public static void ImageWithGlide(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                /*  .transform(new GlideCircleWithBorder(view.getContext(), 2, Color.parseColor("#005194")))*/
                .into(view);
    }


    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;
    }


    public static long getDifferenceBetweenDates(String current_date, String created_date) {
        long diff = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date_current = sdf.parse(current_date);
            Date date_created = sdf.parse(created_date);


            diff = date_current.getTime() - date_created.getTime();
            System.out.println("Days: " + TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static String getDiffInddmmyyBetweenDates(String current_date, String created_date) {
        long diff = 0;
        long left_hours = 0;
        long left_min = 0;
        long left_sec = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date_current = sdf.parse(current_date);
            Date date_created = sdf.parse(created_date);
            diff = date_current.getTime() - date_created.getTime();
            left_hours = 24 - TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            left_min = 1440 - TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            left_sec = 86400 - TimeUnit.SECONDS.convert(diff, TimeUnit.MILLISECONDS);

            System.out.println("Days: " + TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return left_hours + ":" +
                left_min + ":" + left_sec;
    }

    public static String getMonthYear(String time) {
        int day = 0;

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "EEEE | dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            ///  str = outputFormat.format(date);


            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            //2nd of march 2015
            day = cal.get(Calendar.DATE);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (day % 10) {
            case 1:
                return new SimpleDateFormat("EEEE | d'st', MMMM yyyy").format(date);
            case 2:
                return new SimpleDateFormat("EEEE | d'nd', MMMM yyyy").format(date);
            case 3:
                return new SimpleDateFormat("EEEE | d'rd', MMMM yyyy").format(date);
            default:
                return new SimpleDateFormat("EEEE | d'th', MMMM yyyy").format(date);


        }
    }

    //
//    public static void showCustomDialog(Context context) {
//        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
//
//
//        //then we will inflate the custom alert dialog xml that we created
//        View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null, false);
//
//
//       TextView ok= dialogView.findViewById(R.id.ok);
//        TextView cancel= dialogView.findViewById(R.id.cancel);
//
//        //Now we need an AlertDialog.Builder object
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        //setting the view of the builder to our custom view that we already inflated
//        builder.setView(dialogView);
//
//        //finally creating the alert dialog and displaying it
//        AlertDialog alertDialog = builder.create();
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, LoginActivity.class);
//                context.startActivity(intent);
//                alertDialog.dismiss();
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog.dismiss();
//            }
//        });
//        alertDialog.show();
//    }
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShifting(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void highlightView(Context context, TextView textView, ArrayList<TextView> textViews) {
        for (int i = 0; i < 4; i++) {
            if (textView == textViews.get(i)) {
                textView.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                textViews.get(i).setTextColor(context.getResources().getColor(R.color.black));

            }
        }
    }

    public static String formatDate(String time) {

        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }

    public static String getFormatDate(String time) {

        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String outputPattern = "dd-MM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;
        Log.e("input", "--" + inputPattern);
        Log.e("time", "--" + time);
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;

    }


    public static long getMiliseconds(String datetime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        formatter.setLenient(false);

        Date curDate = new Date();
        long curMillis = curDate.getTime();
        String curTime = formatter.format(curDate);

        String oldTime = "05.01.2011, 12:45";
        Date oldDate = null;
        try {
            oldDate = formatter.parse(oldTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long oldMillis = oldDate.getTime();


        return oldMillis;
    }

    public static void setImageBg(View view, String url) {
        Glide.with(view.getContext()).load(url).fitCenter().centerCrop().into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    view.setBackground(resource);

                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {

                super.onLoadFailed(errorDrawable);
                Glide.with(view.getContext()).load(url).fitCenter().centerCrop().into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(resource);
                        }
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });

            }
        });
    }
}