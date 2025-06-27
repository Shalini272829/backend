package com.example.expensemanagementsystem.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.expensemanagementsystem.Model.Employee;
import com.example.expensemanagementsystem.Model.ExpenseReportReq;
import com.example.expensemanagementsystem.Model.FileData;
import com.example.expensemanagementsystem.Repository.EmployeeRepository;
import com.example.expensemanagementsystem.Repository.ExpenseRequestRepository;
import com.example.expensemanagementsystem.Repository.FileRepository;

import jakarta.persistence.EntityNotFoundException;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository emprepo;

    @Autowired
    ExpenseRequestRepository expreqrepo;

    @Autowired
    FileRepository filerepo;

    // private String FolderPath="C:\Users\VAIO\Desktop\Shalini";

    public Employee getDetails(Long empId){
        Optional<Employee> optionalEmployee=emprepo.findById(empId);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else{
            throw new EntityNotFoundException("Employee record is not found with id "+empId);
        }
    }

    public ExpenseReportReq postExpenseRequest(ExpenseReportReq ereq){
      
        ereq.setSubmissionDate(LocalDate.now());
        ereq.setStatus("pending");
        ereq.setAdmin_status("pending");
        ereq.setManager_status("pending");
        ereq.setCutoffDate(ereq.getSubmissionDate().with(TemporalAdjusters.lastDayOfMonth()));
        return expreqrepo.save(ereq);
    }

    public ExpenseReportReq editExpenseRequest(Integer expRequestId, ExpenseReportReq ereq){
        Optional<ExpenseReportReq> optionalExpenseRequest=expreqrepo.findById(expRequestId);
        if(optionalExpenseRequest.isPresent()){
            return expreqrepo.save(ereq);
        }
        else{
            throw new EntityNotFoundException("Such request with "+ expRequestId +" is not present. Please check!!");
        }
    }

    public String deleteExpenseRequest(Integer expRequestId){
        Optional<ExpenseReportReq> optionalExpenseRequest=expreqrepo.findById(expRequestId);
        if(optionalExpenseRequest.isPresent()){
            expreqrepo.deleteById(expRequestId);
            return "Mentioned Expense report deleted";
        }
        else{
            throw new EntityNotFoundException("Such request with \"+ expRequestId +\" is not present. Please check!!");
        }
    }

    public ExpenseReportReq getExpenseRequest(Integer expRequestId) {
        Optional<ExpenseReportReq> optionalExpenseRequest=expreqrepo.findByExpId(expRequestId);
        if(optionalExpenseRequest.isPresent())
        return optionalExpenseRequest.get();
        else{
            throw new EntityNotFoundException("Such request with \"+ expRequestId +\" is not present. Please check!!");
        }
    }

    public List<ExpenseReportReq> getAllExpense(Long empId){
         return expreqrepo.findAllByEmpId(empId);
    }

     public ByteArrayOutputStream getAllExpenseInExcel(Long empId) throws IOException{
         List<ExpenseReportReq> list=expreqrepo.findAllByEmpId(empId);
         XSSFWorkbook workbook=new XSSFWorkbook();
         Sheet sheet= (Sheet) workbook.createSheet("ExpenseRecords");

         Row headerRow=((XSSFSheet) sheet).createRow(0);
         headerRow.createCell(0).setCellValue("employeeId");
         headerRow.createCell(1).setCellValue("employeeName");
         headerRow.createCell(2).setCellValue("expenseType");
         headerRow.createCell(3).setCellValue("Description");
         headerRow.createCell(4).setCellValue("expenseDate");

         int rowNo=1;
         for(ExpenseReportReq lst:list){
            Row row=((XSSFSheet) sheet).createRow(rowNo++);
            row.createCell(0).setCellValue(lst.getEmpId());
            row.createCell(1).setCellValue(lst.getEmpName());
            row.createCell(2).setCellValue(lst.getType());
            row.createCell(3).setCellValue(lst.getDescription());
            row.createCell(4).setCellValue(lst.getExp_date());

         }

         ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
         workbook.write(outputStream);
         workbook.close();
         return outputStream;
    }

    public String approvalAnalytics(Long empId){
        final JSONObject jsonobj=new JSONObject();
        final JSONObject jsonobj1=new JSONObject();
        final JSONObject jsonobj2=new JSONObject();
        final JSONObject jsonobj3=new JSONObject();

        int appCount=expreqrepo.Count(empId,"Approved");
        int rejCount=expreqrepo.Count(empId,"Rejected");
        int pendCount=expreqrepo.Count(empId,"pending");
        int autoApproveCount=expreqrepo.Count(empId,"AutoApproved");

        // System.out.println(appCount);
        // System.out.println(rejCount);
        // System.out.println(pendCount);
        // System.out.println(autoApproveCount);
        jsonobj.put("label","Approved");
        jsonobj.put("value", appCount);
        jsonobj1.put("label","Rejected");
        jsonobj1.put("value", rejCount);
        jsonobj2.put("label","Pending");
        jsonobj2.put("value", pendCount);
        jsonobj3.put("label","AutoApproved");
        jsonobj3.put("value", autoApproveCount);

        // System.out.println(jsonobj);
        // System.out.println(jsonobj1);
        // System.out.println(jsonobj2);
        // System.out.println(jsonobj3);

        final JSONArray jsonArray=new JSONArray();
        jsonArray.put(jsonobj);
        jsonArray.put(jsonobj1);
        jsonArray.put(jsonobj2);
        jsonArray.put(jsonobj3);

        // System.out.println(jsonArray);
        return jsonArray.toString();
    }

    public String categoryAnalytics(Long empId){
        final JSONObject jsonobj=new JSONObject();
        final JSONObject jsonobj1=new JSONObject();
        final JSONObject jsonobj2=new JSONObject();
        final JSONObject jsonobj3=new JSONObject();

        int foodCount=expreqrepo.Category(empId,"food");
        int travelCount=expreqrepo.Category(empId,"travel");
        int othersCount=expreqrepo.Category(empId,"Others");
        int stayCount=expreqrepo.Category(empId,"Hotel_Stay");

        jsonobj.put("label","Business_Food");
        jsonobj.put("value", foodCount);
        jsonobj1.put("label","Business_Travel");
        jsonobj1.put("value", travelCount);
        jsonobj2.put("label","Hotel_Stay");
        jsonobj2.put("value", stayCount);
        jsonobj3.put("label","Others");
        jsonobj3.put("value", othersCount);

        final JSONArray jsonArray=new JSONArray();
        jsonArray.put(jsonobj);
        jsonArray.put(jsonobj1);
        jsonArray.put(jsonobj2);
        jsonArray.put(jsonobj3);
        return jsonArray.toString();
    }

    public String monthlyAnalytics(Long empId, Long year){
        final JSONObject[] jsonobj=new JSONObject[12];  
        final JSONArray jsonArray=new JSONArray();
        
        for(int i=0;i<12;i++){
            Long valueAmount=expreqrepo.monthly(empId,year,i+1);
            // if(valueAmount==null){
            //     valueAmount=0;
            // }
            jsonobj[i]=new JSONObject();
            String monthName=new DateFormatSymbols().getMonths()[i];
            jsonobj[i].put("label",monthName);
            jsonobj[i].put("value",valueAmount);
            jsonArray.put(jsonobj[i]);
        }
        System.out.println(jsonArray);
        return jsonArray.toString();
    }

    public List<ExpenseReportReq> getTopExpenses(Long empId){
        return expreqrepo.topExpenses(empId);
    }

    // public String uploadFile(Integer expenseId,MultipartFile file) throws IOException{
    //     // String filePath =FolderPath+file.getOriginalFilename();
    //     file.transferTo(new File(filePath));
    //     Optional<ExpenseReportReq> ereq=expreqrepo.findById(expenseId);
    //     if(ereq.isPresent()){
    //     ereq.get().setFileName(file.getOriginalFilename());
    //     ereq.get().setFileType(file.getContentType());
    //     ereq.get().setFilePath(filePath);
    //         expreqrepo.save(ereq.get());
    //     }

      
    //     if(ereq!=null){
    //         return "File uploaded successfully"+filePath;
    //     }
    //     return null;
    // }

    public byte[] filedownload(Integer expRequestId)throws IOException{
        Optional<ExpenseReportReq> fileData=expreqrepo.findById(expRequestId);
        String filePath=fileData.get().getFilePath();
        byte[] file=Files.readAllBytes(new File(filePath).toPath());
        return file;
    }

}
