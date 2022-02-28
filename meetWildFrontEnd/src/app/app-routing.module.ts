import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { ComponentsComponent } from './components/components.component';
import { NucleoiconsComponent } from './components/nucleoicons/nucleoicons.component';
import { LoginComponent } from './examples/login/login.component';
import { ProfileComponent } from './examples/profile/profile.component';
import { LandingComponent } from './examples/landing/landing.component';
import { BrowserModule } from '@angular/platform-browser';



const routes: Routes =[
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  { path: 'index',                component: ComponentsComponent },
  { path: 'nucleoicons',          component: NucleoiconsComponent },
  { path: 'examples/landing',     component: LandingComponent },
  { path: 'examples/login',       component: LoginComponent },
  { path: 'examples/profile',     component: ProfileComponent }
];

@NgModule({
  imports: [
      CommonModule,
      BrowserModule,
      RouterModule.forRoot(routes,
        { enableTracing: true } ) //dubug用
  ],
  exports: [
    RouterModule
  ],
})
//複雜的route 就把route  module化 然後放進 app.module.ts 中去管理，其他module可以自己產生自己的route 再從這邊import統一管理
export class AppRoutingModule { }