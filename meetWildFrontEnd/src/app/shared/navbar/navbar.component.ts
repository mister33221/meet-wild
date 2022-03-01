import { Component, OnInit, ElementRef } from '@angular/core';
import { Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { TokenStorageService } from 'app/examples/login/service/token-storage.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
    private toggleButton: any;
    private sidebarVisible: boolean;

    private roles: string[] = [];

    isLoggedIn = false;
    showAdminBoard = false;

    loggedInNameTest = '';
    isLoggedInTest = false;
    isLoginFailedTest = false;

    showModeratorBoard = false;
    username?: string;

    constructor(
        public location: Location, 
        private element : ElementRef,
        private tokenStorageService: TokenStorageService, 
        private router: Router,
        private activatedRoute: ActivatedRoute,) {
        this.sidebarVisible = false;
    }

    ngOnInit() {
        const navbar: HTMLElement = this.element.nativeElement;
        this.toggleButton = navbar.getElementsByClassName('navbar-toggler')[0];

        this.updateLoggedInStatus();

        if (this.isLoggedIn) {
            const user = this.tokenStorageService.getUser();
            this.roles = user.roles;
    
            this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
            this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');
    
            this.username = user.username;
            this.loggedInNameTest = user.username;
          }
    }
    sidebarOpen() {
        const toggleButton = this.toggleButton;
        const html = document.getElementsByTagName('html')[0];
        setTimeout(function(){
            toggleButton.classList.add('toggled');
        }, 500);
        html.classList.add('nav-open');

        this.sidebarVisible = true;
    };
    sidebarClose() {
        const html = document.getElementsByTagName('html')[0];
        // console.log('????',html);
        // TODO 不知道出錯原因暫時註解掉
        // this.toggleButton.classList.remove('toggled');
        this.sidebarVisible = false;
        html.classList.remove('nav-open');
    };
    sidebarToggle() {
        // const toggleButton = this.toggleButton;
        // const body = document.getElementsByTagName('body')[0];
        if (this.sidebarVisible === false) {
            this.sidebarOpen();
        } else {
            this.sidebarClose();
        }
    };

    logout(): void {
        this.tokenStorageService.signOut();
        this.router.navigate(['examples/login'],{relativeTo: this.activatedRoute})
    
        // window.location.reload();
      }
  
    isDocumentation() {
        var titlee = this.location.prepareExternalUrl(this.location.path());
        if( titlee === '/documentation' ) {
            return true;
        }
        else {
            return false;
        }
    }


    updateLoggedInStatus() {

        //訂閱登入狀態
        this.tokenStorageService.isLoggedInTest.subscribe(
          data => this.isLoggedInTest = data
        )
        //訂閱登入狀態
        this.tokenStorageService.isLoginFailedTest.subscribe(
          data => this.isLoginFailedTest = data
        )
    
        //訂閱登入者名稱
        this.tokenStorageService.loggedInNameTest.subscribe(
          data => this.loggedInNameTest = data
        )
      }
}
