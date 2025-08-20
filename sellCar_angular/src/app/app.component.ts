import { Component } from '@angular/core';
import {Router, RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NzHeaderComponent} from 'ng-zorro-antd/layout';
import {NzRowDirective} from 'ng-zorro-antd/grid';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {StorageService} from './auth/services/storage/storage.service';
import {NgIf} from '@angular/common';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NzHeaderComponent, NzRowDirective, RouterLink, NzButtonComponent, RouterLinkActive, NzButtonComponent, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'sellCar_angular';

  constructor(private router: Router) {
  }

  isAdminLoggedIn: boolean = false;
  isCustomerLoggedIn: boolean = false;
  userName: string | null = null;

  ngOnInit(){
    this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
    this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
    this.userName = StorageService.getUserName();
    this.router.events.subscribe(event => {
      if(event.constructor.name === "NavigationEnd"){
        this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
        this.isCustomerLoggedIn = StorageService.isCustomerLoggedIn();
        this.userName = StorageService.getUserName();
      }
    })
  }


  logout() {
    StorageService.signout();
    this.router.navigateByUrl("/login");
  }
}
