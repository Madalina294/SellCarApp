import { Component } from '@angular/core';
import {DatePipe, NgForOf} from "@angular/common";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {CustomerService} from '../../services/customer.service';

@Component({
  selector: 'app-my-cars',
    imports: [
        DatePipe,
        NgForOf,
        NzColDirective,
        NzRowDirective
    ],
  templateUrl: './my-cars.component.html',
  styleUrl: './my-cars.component.scss'
})
export class MyCarsComponent {
  cars: any = [];
  constructor(private service: CustomerService) {
  }

  ngOnInit(){
    this.getCars();
  }

  getCars(){
    this.service.getMyCars().subscribe((res)=>{
      console.log(res);
      this.cars = res;
    })
  }
}
