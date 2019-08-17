package com.example.demo.login.controller;

import com.example.demo.login.domain.model.SignupForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SignupController {

    //ポイント１ :ラジオボタンの実装
    private Map<String, String> radioMarriage;

    //ラジオボタンの初期化メソッド
    private Map<String, String> initRadioMarrige(){

        Map<String, String> radio = new LinkedHashMap<>();

        //既婚、未婚をMapに格納
        radio.put("既婚", "true");
        radio.put("未婚", "false");

        return radio;
    }

    //ユーザー登録画面のGET用コントローラー
    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute SignupForm form,Model model){

        //ラジオボタンの初期化メソッド呼び出し
        radioMarriage = initRadioMarrige();

        //ラヂオボタン用のMapをModelに登録
        model.addAttribute("radioMarriage", radioMarriage);

        //signup.htmlに画面遷移
        return "login/signup";
    }

    //ユーザー登録画面のPOST用コントローラー
    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult,
                             Model model){

        // ポイント３ : データバインド失敗の場合
        // 入力チェックに引っかかった場合、ユーザー登録画面に戻る
        if( bindingResult.hasErrors() ) {

            // GETリクエスト用のメソッドを呼び出して、ユーザー登録画面に戻ります
            return getSignUp(form, model);
        }

        // formの中身をコンソールに出して確認します
        System.out.println(form);

        //ポイント２ :リダイレクト
        //login.htmlにリダイレクト
        return "redirect:/login";
    }
}
