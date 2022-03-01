import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { LoginAuthService } from "./service/login-auth.service";
import { TokenStorageService } from "./service/token-storage.service";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.scss"],
})
export class LoginComponent implements OnInit {
  data: Date = new Date();
  focus;
  focus1;

  loginForm: any = {
    username: null,
    password: null,
  };
  registerForm: any = {
    username: null,
    mail: null,
    password: null,
  };

  register: boolean = false;

  isLoggedIn = false;
  isLoginFailed = false;

  isLoggedInTest = false;
  isLoginFailedTest = false;

  isSuccessful = false;
  isSignUpFailed = false;

  errorMessage = "";
  roles: string[] = [];

  constructor(
    private loginAuthService: LoginAuthService,
    private tokenStorage: TokenStorageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    var body = document.getElementsByTagName("body")[0];
    body.classList.add("login-page");

    var navbar = document.getElementsByTagName("nav")[0];
    navbar.classList.add("navbar-transparent");

    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.tokenStorage.isLoggedInTest.next(true);
      this.roles = this.tokenStorage.getUser().roles;
    }
  }
  ngOnDestroy() {
    var body = document.getElementsByTagName("body")[0];
    body.classList.remove("login-page");

    var navbar = document.getElementsByTagName("nav")[0];
    navbar.classList.remove("navbar-transparent");
  }

  //-------------------------------------------------------------

  onSubmitLoginForm(): void {
    console.log(this.loginForm);
    if (this.loginForm.username == null) {
      alert("不能空白啦!");
      return;
    }
    const { username, password } = this.loginForm;

    this.loginAuthService.login(username, password).subscribe(
      (data) => {
        console.log(data);
        alert(data)

        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;

        this.tokenStorage.isLoggedInTest.next(true);
        this.tokenStorage.isLoginFailedTest.next(false);

        this.roles = this.tokenStorage.getUser().roles;
        // this.reloadPage();
        // this.router.navigate(['/product'], { relativeTo: this.route });
      },
      (err) => {
        console.log(err.error);
        alert(err.message)
        console.log(err.message)
        this.errorMessage = err.message;
        this.isLoginFailed = true;

        this.tokenStorage.isLoginFailedTest.next(true);
      }
    );
  }

  onSubmitRegisterForm() {
    const { username, email, password } = this.registerForm;

    this.loginAuthService.register(username, email, password).subscribe(
      (data) => {
        console.log(data);
        alert(data.message)
        this.isSuccessful = true;
        this.isSignUpFailed = false;
      },
      (err) => {
        alert(err.error)
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }

  /**
   * @summary 展開註冊表格，隱藏登入表格
   */
  goRegister() {
    this.register = true;
  }

  goLogin() {
    this.register = false;
  }
}
