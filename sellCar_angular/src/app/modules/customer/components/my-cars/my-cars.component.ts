import { Component } from '@angular/core';
import {DatePipe, NgForOf} from "@angular/common";
import {NzColDirective, NzRowDirective} from "ng-zorro-antd/grid";
import {CustomerService} from '../../services/customer.service';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzMessageService} from 'ng-zorro-antd/message';

@Component({
  selector: 'app-my-cars',
  imports: [
    DatePipe,
    NgForOf,
    NzColDirective,
    NzRowDirective,
    NzButtonComponent
  ],
  templateUrl: './my-cars.component.html',
  styleUrl: './my-cars.component.scss'
})
export class MyCarsComponent {
  cars: any = [];
  constructor(private service: CustomerService,
              private message: NzMessageService) {
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

  deleteCar(id: number) {
    this.service.deleteCar(id).subscribe((res)=>{
      this.message.success("Car  deleted successfully", {nzDuration: 5000});
      this.getCars();
    });
  }
}
