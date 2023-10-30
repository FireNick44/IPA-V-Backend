package com.sleeping.branch.ipavbackend.transaction.charts;

import com.sleeping.branch.ipavbackend.shared.models.DashedLineChart;
import com.sleeping.branch.ipavbackend.shared.models.DataPoint;
import com.sleeping.branch.ipavbackend.transaction.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChartConverter {
    public DashedLineChart getDashedLineChart(List<Transaction> transactionList) {
        DashedLineChart dashedLineChart = new DashedLineChart();
        List<DataPoint> processingTimeData = new ArrayList<>();
        List<DataPoint> memoryUsageData = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            LocalDateTime timestamp = transaction.getTimestamp();
            long epochMilli = timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Double x = (double) epochMilli;
            Double processingTime = (double) transaction.getProcessingTime();
            Double memoryUsage = (double) transaction.getMemoryUsage();

            processingTimeData.add(new DataPoint(x, processingTime));
            memoryUsageData.add(new DataPoint(x, memoryUsage));
        }

        dashedLineChart.addSeries("Processing Time", processingTimeData);
        dashedLineChart.addSeries("Memory Usage", memoryUsageData);
        return dashedLineChart;
    }

    public DashedLineChart getDashedLineChartBySenderServerLocation(List<Transaction> transactionList, String timeZone) {
        DashedLineChart dashedLineChart = new DashedLineChart();
        List<DataPoint> processingTimeData = new ArrayList<>();
        List<DataPoint> memoryUsageData = new ArrayList<>();
        List<DataPoint> activityData = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            long epochMilli = transaction.getTimestamp()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli();

            Double x = (double) epochMilli;

            if (transaction.getSenderServerLocation().equals(timeZone)) {
                Double processingTime = (double) transaction.getProcessingTime();
                Double memoryUsage = (double) transaction.getMemoryUsage();
                Double activity = 1.0;

                processingTimeData.add(new DataPoint(x, processingTime));
                memoryUsageData.add(new DataPoint(x, memoryUsage));
                activityData.add(new DataPoint(x, activity));
            } else {
                processingTimeData.add(new DataPoint(x, 0.0));
                memoryUsageData.add(new DataPoint(x, 0.0));
                activityData.add(new DataPoint(x, 0.0));
            }
        }

        dashedLineChart.addSeries("Processing Time", processingTimeData);
        dashedLineChart.addSeries("Memory Usage", memoryUsageData);
        dashedLineChart.addSeries("Activity", activityData);

        return dashedLineChart;
    }

}
