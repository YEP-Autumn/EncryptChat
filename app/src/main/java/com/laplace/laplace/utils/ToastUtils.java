package com.laplace.laplace.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ToastUtils {

    /** 之前显示的内容 */
    private static String oldMsg ;
    /** Toast对象 */
    private static Toast toast = null ;
    /** 第一次时间 */
    private static long oneTime = 0 ;
    /** 第二次时间 */
    private static long twoTime = 0 ;

    /**
     * 显示Toast
     * @param context
     * @param message
     */
    public synchronized static void showToast(Context context, String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.show() ;
            oneTime = System.currentTimeMillis() ;
        }else{
            twoTime = System.currentTimeMillis() ;
            if(message.equals(oldMsg)){
                if(twoTime - oneTime > Toast.LENGTH_SHORT){
                    toast.show() ;
                }else {
                    Log.e("YEP", "showToast: ");
                }
            }else{
                oldMsg = message ;
                toast.setText(message);
                toast.show() ;
            }
        }
        oneTime = twoTime ;
    }

//    void warnHint(String str, int showTime) {
//
//        Toast toast = Toast.makeText(getLayoutInflater().getContext(), str, showTime);
//
//        //设置显示位置
////        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL , 0, 0);
//        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
//
//        // 设置 TextView 字体颜色
////        v.setTextColor(Color.RED);
//
//        toast.show();
//    }
}