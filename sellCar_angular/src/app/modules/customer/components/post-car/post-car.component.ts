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
import {StorageService} from '../../../../auth/services/storage/storage.service';


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
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;


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
    // Validate form
    if (this.postCarForm.invalid) {
      this.message.error("Please fill in all required fields", {nzDuration: 3000});
      return;
    }
    
    // Validate image
    if (!this.selectedFile) {
      this.message.error("Please select an image", {nzDuration: 3000});
      return;
    }
    
    this.isSpinning = true;
    console.log(this.postCarForm.value);
    console.log(this.selectedFile);
    
    const formData: FormData = new FormData();
    
    // Add image (required)
    formData.append("image", this.selectedFile);
    
    // Add all form fields
    formData.append("brand", this.postCarForm.get('brand')?.value);
    formData.append("name", this.postCarForm.get('name')?.value);
    formData.append("type", this.postCarForm.get('type')?.value);
    formData.append("color", this.postCarForm.get('color')?.value);
    formData.append("transmission", this.postCarForm.get('transmission')?.value);
    formData.append("description", this.postCarForm.get('description')?.value);
    formData.append("price", this.postCarForm.get('price')?.value);
    formData.append("userId", StorageService.getUserId());

    // Format year as proper date (backend expects Date object)
    const year = this.postCarForm.get('year')?.value;
    if (year) {
      // Create a proper date string: YYYY-MM-DD format
      const yearValue = new Date(year).getFullYear();
      const dateString = `${yearValue}-01-01`;
      formData.append("year", dateString);
    }

    this.service.postCar(formData).subscribe((res) => {
      this.isSpinning = false;
      this.message.success("Car posted successfully!", {nzDuration: 5000});
      this.router.navigateByUrl("/customer/dashboard");
    }, error => {
      this.isSpinning = false;
      console.error('Error details:', error);
      this.message.error("Something went wrong. Please check all fields and try again.", {nzDuration: 5000});
    });
  }

  onFileSelected(event:any){
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage(): void {
    if (this.selectedFile) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }
}
