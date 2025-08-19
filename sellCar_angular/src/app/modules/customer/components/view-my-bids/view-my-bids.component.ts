import { Component } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {NzTableComponent} from 'ng-zorro-antd/table';
import {NgClass, NgForOf, NgStyle, DecimalPipe} from '@angular/common';

@Component({
  selector: 'app-view-my-bids',
  imports: [
    NzSpinComponent,
    NzTableComponent,
    NgForOf,
    NgClass
  ],
  templateUrl: './view-my-bids.component.html',
  styleUrl: './view-my-bids.component.scss'
})
export class ViewMyBidsComponent {
  bids: any = [];
  issSpinning: boolean = false;
  constructor(private service: CustomerService,
              private message: NzMessageService) {
  }

  ngOnInit(){
    this.getMyBids();
  }

  getMyBids(){
    this.issSpinning = true;
    this.service.getMyBids().subscribe((res)=>{
      this.issSpinning = false;
      console.log(res);
      this.bids = res;
    })
  }

  trackByFn(index: number, item: any): any {
    return item.id || index;
  }
}
