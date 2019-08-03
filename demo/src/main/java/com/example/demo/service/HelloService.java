package com.example.demo.service;

import com.example.demo.domain.EmployeeResult;
import com.example.demo.repository.HelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HelloService {
    @Autowired
    private HelloRepository helloRepository;

    public EmployeeResult findOne(int id) {
        //1件検索実行
        Map<String, Object> map = helloRepository.findOne(id);

        //Mapから値を取得
        int employeeId = (Integer)map.get("employee_id");
        String employeeName = (String)map.get("employee_name");
        int age = (Integer) map.get("age");

        //Employeeクラスに値をセット
        EmployeeResult employee = new EmployeeResult();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeName(employeeName);
        employee.setAge(age);

        return employee;
    }

}
