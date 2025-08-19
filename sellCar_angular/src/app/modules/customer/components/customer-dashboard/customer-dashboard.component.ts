import { Component } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {DatePipe, NgForOf, NgIf, NgStyle} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {RouterLink} from '@angular/router';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzCardComponent, NzCardGridDirective} from 'ng-zorro-antd/card';

@Component({
  selector: 'app-customer-dashboard',
  imports: [
    NzRowDirective,
    NzColDirective,
    DatePipe,
    NgForOf,
    NzButtonComponent,
    NgIf,
    RouterLink,
    NzIconDirective,
    NzCardComponent,
    NzCardGridDirective,
    NgStyle
  ],
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.scss'
})
export class CustomerDashboardComponent {

  cars: any = [];
  analytics: any;
  constructor(private service: CustomerService) {
  }

  ngOnInit(){
    this.getCars();
    this.getAnalytics();
  }

  getCars(){
    this.service.getAllCars().subscribe((res)=>{
      console.log(res);
      this.cars = res;
    })
  }

  getAnalytics(){
    this.service.getAnalytics().subscribe((res)=>{
      console.log(res);
      this.analytics = res;
    })
  }

  gridStyle={
    width: '25%',
    textAlign: 'center'
  }
}
