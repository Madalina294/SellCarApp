import { Component } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {NzTableComponent} from 'ng-zorro-antd/table';
import {NgClass, NgForOf, NgIf} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzSwitchComponent} from 'ng-zorro-antd/switch';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-view-my-bids',
  imports: [
    NzSpinComponent,
    NzTableComponent,
    NgForOf,
    NgClass,
    NgIf,
    NzButtonComponent,
    NzSwitchComponent,
    FormsModule
  ],
  templateUrl: './view-my-bids.component.html',
  styleUrl: './view-my-bids.component.scss'
})
export class ViewMyBidsComponent {
  bids: any = [];
  isSpinning: boolean = false;
  isReceived: boolean = false;
  viewMode: string = 'my-bids';

  constructor(private service: CustomerService,
              private message: NzMessageService) {
  }

  ngOnInit(){
    this.loadBids();
  }

  toggleView() {
    this.viewMode = this.isReceived ? 'received' : 'my-bids';
    this.loadBids();
  }

  loadBids() {
    this.isSpinning = true;
    if (this.viewMode === 'my-bids') {
      this.getMyBids();
    } else {
      this.getBidsOnMyCars();
    }
  }

  getMyBids(){
    this.service.getMyBids().subscribe((res)=>{
      this.isSpinning = false;
      console.log('My Bids:', res);
      this.bids = res;
    })
  }

  getBidsOnMyCars(){
    this.service.getBidsOnMyCars().subscribe((res)=>{
      this.isSpinning = false;
      console.log('Bids on My Cars:', res);
      this.bids = res;
    })
  }

  trackByFn(index: number, item: any): any {
    return item.id || index;
  }

  changeBookingStatus(id: number, status: string) {
    this.isSpinning = true;
    this.service.updateBidStatus(id, status).subscribe((res)=>{
      this.isSpinning = false;
      this.message.success("The bid status was updated successfully!!", {nzDuration: 5000});
      this.loadBids(); // Reload current view
    }, error=>{
      this.isSpinning = false;
      this.message.error("Something went wrong", {nzDuration: 5000});
    });
  }
}
