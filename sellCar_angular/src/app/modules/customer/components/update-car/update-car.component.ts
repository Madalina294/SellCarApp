import { Component } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-update-car',
  imports: [],
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {
  id:number = -1;
  car: any = null;
  constructor(private service: CustomerService,
              private activatedRoute: ActivatedRoute) {
    this.id = this.activatedRoute.snapshot.params["id"];
  }


  ngOnInit(){
    this.getCars();
  }

  getCars(){
    this.service.getCarById(this.id).subscribe((res)=>{
      console.log(res);
      this.car = res;
    })
  }
}
