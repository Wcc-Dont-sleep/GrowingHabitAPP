package com.example.habittest;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

public class DateUtils {
    public static List<String> getDateRange(String startDateStr, String endDateStr) {
        List<String> dateList = new ArrayList<>();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // 将起始日期和结束日期解析为Date对象
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);

            // 循环遍历日期范围，将每个日期格式化为字符串并添加到列表中
            while (!calendar.getTime().after(endDate)) {
                String formattedDate = dateFormat.format(calendar.getTime());
                dateList.add(formattedDate);

                // 递增日期
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateList;
    }

    public static PolynomialFunction getline(ArrayList<Integer> pre,int days){
        // 创建一个WeightedObservedPoints对象来保存观测点
        WeightedObservedPoints observedPoints = new WeightedObservedPoints();
        int count=0;
// 添加观测点
        for(int i =0;i<days;i++)
        {
            observedPoints.add(pre.get(count), pre.get(count+1));
            Log.d("count",String.valueOf(pre.get(count)));
            Log.d("count",String.valueOf(pre.get(count+1)));
            count+=2;
        }
//        observedPoints.add(pre.get(2), pre.get(0));
// 添加更多观测点...

// 创建一个多项式拟合器，这里使用二次多项式
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(2);

// 拟合曲线并得到拟合参数
        double[] coefficients = fitter.fit(observedPoints.toList());
// 打印拟合参数
        PolynomialFunction fittedFunction = new PolynomialFunction(coefficients);
// 计算预测点的值
//        double x = days+1; // 预测点的横坐标
//        double predictedValue = fittedFunction.value(x);
//        Log.d("result",String.valueOf(predictedValue));
//        System.out.println("拟合参数：");
//        for (int i = 0; i < coefficients.length; i++) {
//            System.out.println("系数 " + i + ": " + coefficients[i]);
//        }
        return fittedFunction;
    }

    public static double getpredict(PolynomialFunction fittedFunction,double x){
        double predictedValue = fittedFunction.value(x);
        Log.d("result",String.valueOf(predictedValue));
        return predictedValue;
    }
}
