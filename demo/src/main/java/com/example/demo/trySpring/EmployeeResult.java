package com.example.demo.trySpring;

import lombok.Data;

@Data
public class EmployeeResult {
    private int employeeId; //従業員ID
    private String employeeName; //従業員名
    private int age; //年齢
}
