import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

/**
 * @summary 預先設定要連線的網址 但好像可以統一設定?
 */
const AUTH_API = 'http://localhost:8080/api/auth/';

/**
 * @summary 這要做甚麼?  要先設定? 為何?
 */
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class LoginAuthService {

  constructor(private http: HttpClient) { }

  /**
   * @summary 登入
   * @param username 帳號 
   * @param password 密碼
   * @returns 
   */
  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  /**
   * @summary 註冊
   * @param username 
   * @param email 
   * @param password 
   * @returns 
   */
  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      password
    }, httpOptions);
  }


}
