import { Component } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {DatePipe, NgForOf} from '@angular/common';

@Component({
  selector: 'app-customer-dashboard',
  imports: [
    NzRowDirective,
    NzColDirective,
    DatePipe,
    NgForOf
  ],
  templateUrl: './customer-dashboard.component.html',
  styleUrl: './customer-dashboard.component.scss'
})
export class CustomerDashboardComponent {

  cars: any = [];
  constructor(private service: CustomerService) {
  }

  ngOnInit(){
    this.getCars();
  }

  getCars(){
    this.service.getAllCars().subscribe((res)=>{
      console.log(res);
      this.cars = res;
    })
  }
}
