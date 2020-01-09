package com.engineering.shop.warehouse.controllers;

import com.engineering.shop.warehouse.models.Report;
import com.engineering.shop.warehouse.models.StockAmount;
import com.engineering.shop.warehouse.repositories.ReportRepository;
import com.engineering.shop.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/all")
    public Iterable<Report> getReports() {
        return reportRepository.findAll();
    }

    @PostMapping("/create")
    public Map<String, String> create(@RequestParam String startDateTime, @RequestParam String endDateTime) {
//        String result = "";
        Map<String, String> result = new HashMap<>();

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

            DateTimeFormatter f = DateTimeFormatter.ISO_DATE_TIME;

            result.put("status", "created");
            result.put("reportId", Integer.toString(report.getReportId()));
            result.put("creationDateTime", report.getCreationDateTime().format(f));
            result.put("startDateTime", report.getStartDateTime().format(f));
            result.put("endDateTime", report.getEndDateTime().format(f));
            result.put("info", report.getInfo());
        } else {
//            result = new ResponseEntity(HttpStatus.BAD_REQUEST);
            result.put("status", "failed");
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