package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

// セキュリティ設定用クラス
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // パスワードエンコーダーのBean定義
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private DataSource dataSource;

    // ユーザーIDとパスワードを取得するSQL文
    private static final String USER_SQL = "SELECT"
            + "   user_id,"
            + "   password,"
            + "   true"
            + " FROM"
            + "   m_user"
            + " WHERE"
            + "   user_id = ?";

    // ユーザーのロールを取得するSQL
    private static final String ROLE_SQL = "SELECT"
            +"   user_id,"
            +"   role"
            +" FROM"
            +"   m_user"
            +" WHERE"
            +"   user_id = ?";

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静的リソースへのアクセスには、セキュリティを適用しない
        web.ignoring().antMatchers("/webjars/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 直リンクの禁止 + URLでの認可設定 + ログイン不要ページの設定
        http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll() // webjarsへアクセス許可
                .antMatchers("/css/**").permitAll() // cssへのアクセス許可
                .antMatchers("/login").permitAll() // ログインは直リンクOK
                .antMatchers("/signUp").permitAll() //ユーザー登録画面は直リンクOK
                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated(); // これらのリクエストは認証させる

        // ログイン処理 ログイン失敗した際にいい感じのエラー画面を出すため
        http.formLogin()
                .loginProcessingUrl("/login") //ログイン処理のパス
                .loginPage("/login") //ログインページの設定
                .failureUrl("/login") //ログイン失敗時の遷移先
                .usernameParameter("userId") //入力したユーザーID
                .passwordParameter("password") //入力したパスワード
                .defaultSuccessUrl("/home", true); // ログイン成功時の遷移先

        // ログアウト
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

        // CSRF対策を無効に設定(一時的）
        // http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // ユーザーデータの取得
        // 入力値でDBに検索をかけて取得できた情報から勝手に認証処理をしてくれる
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_SQL)
                .authoritiesByUsernameQuery(ROLE_SQL)
                .passwordEncoder(passwordEncoder());
    }
}
