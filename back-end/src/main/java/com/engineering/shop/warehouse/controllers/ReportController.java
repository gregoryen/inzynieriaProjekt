package com.engineering.shop.warehouse.controllers;

import com.engineering.shop.warehouse.models.Report;
import com.engineering.shop.warehouse.models.StockAmount;
import com.engineering.shop.warehouse.repositories.ReportRepository;
import com.engineering.shop.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "/reports")
public class ReportController {

    ReportRepository reportRepository;
    StockAmountRepository stockAmountRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository,
                            StockAmountRepository stockAmountRepository) {
        this.reportRepository = reportRepository;
        this.stockAmountRepository = stockAmountRepository;
    }

    @PostMapping("/create")
    public String create(@RequestParam String startDateTime, @RequestParam String endDateTime) {
        String result = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
        Iterable<StockAmount> stockAmounts = stockAmountRepository.findAllByDateTimeBetween(start, end);
        String info = "";
        for (StockAmount s : stockAmounts) {
            info += s.getStockAmountId() + ";";
        }
        System.out.print(info);
        if (!info.contentEquals("")) {
            Report report = new Report();
            report.setCreationDateTime(LocalDateTime.now());
            report.setStartDateTime(start);
            report.setEndDateTime(end);
            report.setInfo(info);
            reportRepository.save(report);
            result = "created";
        } else {
            result = "failed";
        }
        return result;
    }

//    @PostMapping("/export")
//    public String exportToCSVFileFromDatabase(@RequestBody Map<String, String> filepathAndReportId) {
//        try {
//            String filepath = filepathAndReportId.get("filepath");
//            Integer reportId = Integer.parseInt(filepathAndReportId.get("reportId"));
//
//            Report report = reportRepository.findByReportId(reportId).
//                    orElseThrow(() -> new ResourceNotFoundException("Report not found with reportId"));
//            List<StockAmount> stockAmounts = report.getStockAmountChanges();
//
//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath), StandardCharsets.ISO_8859_1));
//            String separator = ",";
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append("\"Poprzednia ilosc\",\"Aktualna ilosc\",\"Data zmiany\",\"Info\",\"Produkt\",\"Jednostka\"");
//            bufferedWriter.write(stringBuffer.toString());
//            bufferedWriter.newLine();
//            for (StockAmount stockAmount : stockAmounts) {
//                stringBuffer = new StringBuffer();
//                stringBuffer.append("\""+stockAmountChange.getStockAmountChangeId()+"\"");
//                stringBuffer.append(separator);
//                stringBuffer.append("\""+stockAmountChange.getPreviousAmount()+"\"");
//                stringBuffer.append(separator);
//                stringBuffer.append("\""+stockAmountChange.getCurrentAmount()+"\"");
//                stringBuffer.append(separator);
//                stringBuffer.append("\""+stockAmountChange.getChangeDateTime()+"\"");
//                stringBuffer.append(separator);
//                String changeInfo = stockAmountChange.getChangeInfo();
//                if (changeInfo.equals("SOLD_OUT")) {
//                    changeInfo = "wyprzedano";
//                } else if (changeInfo.equals("PROVIDED")) {
//                    changeInfo = "dostarczono";
//                } else if (changeInfo.equals("SOLD")) {
//                    changeInfo = "sprzedano";
//                }
//                stringBuffer.append("\""+changeInfo+"\"");
//                stringBuffer.append(separator);
//                stringBuffer.append("\""+stockAmountChange.getStockAmount().getProductId()+"\"");
//                bufferedWriter.write(stringBuffer.toString());
//                bufferedWriter.newLine();
//            }
//            bufferedWriter.flush();
//            bufferedWriter.close();
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
//        return "Exported";
//    }
}