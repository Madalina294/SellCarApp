import { Component, OnInit } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {NzMessageComponent, NzMessageService} from 'ng-zorro-antd/message';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzFormControlComponent, NzFormDirective} from 'ng-zorro-antd/form';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {NzInputDirective} from 'ng-zorro-antd/input';
import {NzDatePickerComponent} from 'ng-zorro-antd/date-picker';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-post-car',
  imports: [
    CommonModule,
    NzSpinComponent,
    NzRowDirective,
    NzFormDirective,
    NzColDirective,
    NzFormControlComponent,
    NzSelectComponent,
    ReactiveFormsModule,
    NzOptionComponent,
    NzInputDirective,
    NzDatePickerComponent,
    NzButtonComponent
  ],
  templateUrl: './post-car.component.html',
  styleUrl: './post-car.component.scss'
})
export class PostCarComponent implements OnInit {

  listOfBrands = ["BMW", "AUDI", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA", "HAVAL"];
  listOfTypes = ["Petrol", "Hybrid", "Diesel","Electric", "CNG"];
  listOfColor = ["Red", "White", "Silver", "Blue", "Black", "Orange", "Grey"];
  listOfTransmission = ["Manual", "Automatic" ];
  postCarForm!: FormGroup;
  isSpinning: boolean = false;


  constructor(private service: CustomerService,
              private fb: FormBuilder,
              private router: Router,
              private message: NzMessageService) {}

  ngOnInit(): void {
    this.postCarForm = this.fb.group({
      brand: [null, [Validators.required]],
      name: [null, [Validators.required]],
      type: [null, [Validators.required]],
      transmission: [null, [Validators.required]],
      color: [null, [Validators.required]],
      year: [null, [Validators.required]],
      description: [null, [Validators.required]],
      price: [null, [Validators.required]]
    });
  }

  postCar(): void {
    console.log(this.postCarForm.value);
  }
}
