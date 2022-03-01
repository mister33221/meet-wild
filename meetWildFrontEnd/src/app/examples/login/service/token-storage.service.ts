import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';


const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

/**
 * Service 的裝飾器叫 @Injectable 。
 * Angular 會到注射器裡去找看看之前有沒有創建過這個 Service 的實體
 * 如果沒有的話，注射器會自動創建一個該 Service 的實體
 * 然後 Angular 就會把這個 Service 實體注入到該 Component裡。
 * 而如果注射到不同的component中，就會是各自不同的實體service
 * 而如下的設定 就會讓整個app都共用同一個service。
 * 這種方式是在 Service 自己的 Metadata 裡加上 providedIn: 'root' 的屬性，
 * 來告訴 Angular 說：「請把我註冊在整個系統都是使用同一個實體的注射器裡」。
 */
@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {
  //取這邊的資料都會取到最後的狀態 進入頁面時 可以用來判斷 是否已登入來導向目標頁面
  loggedInNameTest: Subject<string> = new BehaviorSubject<string>(null);
  isLoggedInTest: Subject<boolean> = new BehaviorSubject<boolean>(false);
  isLoginFailedTest: Subject<boolean> = new BehaviorSubject<boolean>(false);
  authUser: Subject<any> = new BehaviorSubject<boolean>(null);

  constructor() { }

  /**
   * @summary 登出
   */
  signOut(): void {
    window.sessionStorage.clear();
    this.isLoggedInTest.next(false);
  }

  /**
   * @summary 儲存token 在sessionStorage中
   * @param token 
   */
  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  /**
   * @summary 取的token
   * @returns token
   */
  public getToken(): string | null {
    console.log(window.sessionStorage.getItem(TOKEN_KEY))
    console.log(!!window.sessionStorage.getItem(TOKEN_KEY))
    this.isLoggedInTest.next(!!window.sessionStorage.getItem(TOKEN_KEY))
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

    /**
   * @summary 儲存user資料 在sessionStorage中
   * @param token 
   */
  public saveUser(user: any): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  /**
   * @summary 從sessionStorage中 取得user資料，如果沒有資料就返回空陣列
   * @param token 
   */
  public getUser(): any {
    const user = window.sessionStorage.getItem(USER_KEY);
    console.log('123',JSON.parse(user))
    console.log('456',window.sessionStorage)
    if (user) {
      this.loggedInNameTest.next(JSON.parse(user).username);
      this.authUser.next(JSON.parse(user));
      return JSON.parse(user);
    }

    return {};
  }
}