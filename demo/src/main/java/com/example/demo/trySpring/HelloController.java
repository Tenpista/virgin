package com.example.demo.trySpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public String getHello() {
        return "hello";
    }

    @PostMapping("/hello")
    public String postRequest(@RequestParam("text1") String str, Model model) {
        model.addAttribute("sample", str);

        return "helloResponse";
    }

    @PostMapping("/hello/db")
    public String postDbRequest(@RequestParam("text2") String str, Model model) {
        //String からint型に変換
        int id = Integer.parseInt(str);

        //１件検索
        EmployeeResult employeeResult = helloService.findOne(id);
        //検索結果をModelに登録
        model.addAttribute("id", employeeResult.getEmployeeId());
        model.addAttribute("name", employeeResult.getEmployeeName());
        model.addAttribute("age", employeeResult.getAge());
        //helloResponseDB.htmlに画面遷移
        return "helloResponseDb";
    }
}
