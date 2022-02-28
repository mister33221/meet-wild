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

  form: any = {
    username: null,
    password: null,
  };

  isLoggedIn = false;
  isLoginFailed = false;

  isLoggedInTest = false;
  isLoginFailedTest = false;

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



  onSubmit(): void {
    console.log(this.form);
    if(this.form.username == null){
        alert("不能空白啦!")
        return
    }
    const { username, password } = this.form;

    this.loginAuthService.login(username, password).subscribe(
      (data) => {
        console.log(data);

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
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;

        this.tokenStorage.isLoginFailedTest.next(true);
      }
    );
  }

  reloadPage(): void {
    window.location.reload();
  }
}
