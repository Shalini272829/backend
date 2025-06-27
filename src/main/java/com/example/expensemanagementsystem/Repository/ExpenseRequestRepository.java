package com.example.expensemanagementsystem.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import com.example.expensemanagementsystem.Model.ExpenseReportReq;

@Repository
public interface ExpenseRequestRepository extends JpaRepository<ExpenseReportReq,Integer>{

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.manager_emp_id) like %:empId% and (e.manager_status) like %:status%")
    List<ExpenseReportReq> findPendingRequests(@Param("empId") Long empId, @Param("status") String status);

     @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.admin_emp_id) like %:empId% and (e.admin_status) like %:status% and (e.manager_status) like %:managerStatus%")
    List<ExpenseReportReq> findAdminPendingRequests(@Param("empId") Long empId, @Param("status") String status, @Param("managerStatus") String managerStatus);

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.manager_emp_id) like %:empId% and (e.manager_status) like %:status%")
    List<ExpenseReportReq> findApprovedRequests(@Param("empId") Long empId, @Param("status") String status);

     @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.admin_emp_id) like %:empId% and (e.status) like %:status% and (e.status) like %:autoApp%")
    List<ExpenseReportReq> findAdminApprovedRequests(@Param("empId") Long empId, @Param("status") String status,@Param("autoApp") String autoApp);

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.manager_emp_id) like %:empId% and (e.manager_status) like %:status%")
    List<ExpenseReportReq> findRejectedRequests(@Param("empId") Long empId, @Param("status") String status);

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.admin_emp_id) like %:empId% and (e.status) like %:status%")
    List<ExpenseReportReq> findAdminRejectedRequests(@Param("empId") Long empId, @Param("status") String status);

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.emp_Id) like %:empId%")
    List<ExpenseReportReq> findAllByEmpId(@Param("empId") Long empId);

     @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.exp_request_id) like %:expRequestId%")
    Optional<ExpenseReportReq> findByExpId(@Param("expRequestId") Integer expRequestId);

     @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.manager_emp_id) like %:empId%")
    List<ExpenseReportReq> findAllExpenses(@Param("empId") Long empId);

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.admin_emp_id) like %:empId%")
    List<ExpenseReportReq> findAllExpensesAdmin(@Param("empId") Long empId);

    @Query(nativeQuery=true, value="SELECT * FROM expense_report_req e where (e.cutoff_date) like %:today%")
    List<ExpenseReportReq> cutOffList(@Param("today") LocalDate today);
   
    @Query(nativeQuery=true, value="SELECT COUNT(status) FROM expense_report_req e where (e.emp_id) like %:empId% AND (e.status) like %:status% ")
    int Count(@Param("empId") Long empId, @Param("status") String status);

     @Query(nativeQuery=true, value="SELECT COUNT(type) FROM expense_report_req e where (e.emp_id) like %:empId% AND (e.type) like %:type% ")
    int Category(@Param("empId") Long empId, @Param("type") String type);

    @Query(nativeQuery=true, value="SELECT SUM(amount) FROM expense_report_req e where (year(e.exp_date)) like %:year% AND (e.emp_id) like %:empId%  AND (month(e.exp_date)) like %:month% ")
    Long monthly(@Param("empId") Long empId, @Param("year") Long year, @Param("month") int month);

    @Query(nativeQuery=true, value=" SELECT * FROM expense_report_req e where (e.emp_id) like %:empId% ORDER BY amount DESC LIMIT 5 ")
    List<ExpenseReportReq> topExpenses(@Param("empId") Long empId);


    
}
