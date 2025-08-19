import { Component } from '@angular/core';
import {CustomerService} from '../../../customer/services/customer.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {AdminService} from '../../services/admin.service';
import {NgClass, NgForOf} from '@angular/common';
import {NzTableComponent} from 'ng-zorro-antd/table';
import {NzSpinComponent} from 'ng-zorro-antd/spin';

@Component({
  selector: 'app-get-bookings',
  imports: [
    NgClass,
    NgForOf,
    NzTableComponent,
    NzSpinComponent
  ],
  templateUrl: './get-bookings.component.html',
  styleUrl: './get-bookings.component.scss'
})
export class GetBookingsComponent {
  bids: any = [];
  issSpinning: boolean = false;
  constructor(private service: AdminService,
              private message: NzMessageService) {
  }

  ngOnInit(){
    this.getBids();
  }

  getBids(){
    this.issSpinning = true;
    this.service.getAllBids().subscribe((res)=>{
      this.issSpinning = false;
      console.log(res);
      this.bids = res;
    })
  }

  trackByFn(index: number, item: any): any {
    return item.id || index;
  }
}
