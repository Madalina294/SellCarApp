import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NzMessageService} from 'ng-zorro-antd/message';
import {ActivatedRoute, Router} from '@angular/router';
import {CustomerService} from '../../services/customer.service';
import {DatePipe, NgForOf, NgIf} from '@angular/common';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {NzInputNumberComponent} from 'ng-zorro-antd/input-number';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {StorageService} from '../../../../auth/services/storage/storage.service';

@Component({
  selector: 'app-book-a-car',
  imports: [
    DatePipe,
    NzColDirective,
    NzRowDirective,
    NgIf,
    NzSpinComponent,
    ReactiveFormsModule,
    NzInputNumberComponent,
    NzButtonComponent
  ],
  templateUrl: './book-a-car.component.html',
  styleUrl: './book-a-car.component.scss'
})
export class BookACarComponent {

  id:number = -1;
  car: any;
  bidForm: FormGroup;
  isSpinning: boolean = false;

  constructor(private service: CustomerService,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder,
              private message: NzMessageService,
              private router: Router) {
    this.id = this.activatedRoute.snapshot.params["id"];
  }

  ngOnInit(){
    this.bidForm = this.fb.group({
      price: [null, [Validators.required]]
    })
    this.getCar();
  }

  getCar(){
    this.id = this.activatedRoute.snapshot.params["id"];
    this.service.getCarById(this.id).subscribe((res)=>{
      console.log(res);
      this.car = res;

    })
  }

  bidACar(formData){
    this.isSpinning = true;
    const obj ={
      price:formData.price,
      userId:StorageService.getUserId(),
      carId:this.id
    };
    this.service.bidACar(obj).subscribe((res)=>{
      this.isSpinning = false;
      this.message.success("The bid was done!", {nzDuration: 5000});
      this.router.navigateByUrl("/customer/dashboard");
    }, error=>{
      this.message.error("Something went wrong", {nzDuration: 5000});
    });
  }
}
