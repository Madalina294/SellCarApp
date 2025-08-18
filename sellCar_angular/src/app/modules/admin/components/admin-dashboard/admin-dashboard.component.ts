import { Component } from '@angular/core';
import {CustomerService} from '../../../customer/services/customer.service';
import {DatePipe, NgForOf} from '@angular/common';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {AdminService} from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [
    DatePipe,
    NgForOf,
    NzColDirective,
    NzRowDirective
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent {

  cars: any = [];
  constructor(private service: AdminService) {
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
