import { Component } from '@angular/core';
import {CustomerService} from '../../services/customer.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';
import {NzDatePickerComponent} from 'ng-zorro-antd/date-picker';
import {NzFormControlComponent, NzFormDirective} from 'ng-zorro-antd/form';
import {NzInputDirective} from 'ng-zorro-antd/input';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {StorageService} from '../../../../auth/services/storage/storage.service';
import {NzMessageService} from 'ng-zorro-antd/message';

@Component({
  selector: 'app-update-car',
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    NzButtonComponent,
    NzColDirective,
    NzDatePickerComponent,
    NzFormControlComponent,
    NzFormDirective,
    NzInputDirective,
    NzOptionComponent,
    NzRowDirective,
    NzSelectComponent,
    NzSpinComponent,
    ReactiveFormsModule
  ],
  templateUrl: './update-car.component.html',
  styleUrl: './update-car.component.scss'
})
export class UpdateCarComponent {
  id:number = -1;
  car: any = null;
  existingImage : string| null = null;
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  imageChanged: boolean = false;

  listOfBrands = ["BMW", "AUDI", "MERCEDES", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA", "HAVAL"];
  listOfTypes = ["Petrol", "Hybrid", "Diesel","Electric", "CNG"];
  listOfColor = ["Red", "White", "Silver", "Blue", "Black", "Orange", "Grey"];
  listOfTransmission = ["Manual", "Automatic" ];
  updateCarForm!: FormGroup;
  isSpinning: boolean = false;

  constructor(private service: CustomerService,
              private activatedRoute: ActivatedRoute,
              private fb: FormBuilder,
              private message: NzMessageService,
              private router: Router) {
    this.id = this.activatedRoute.snapshot.params["id"];
  }


  ngOnInit(){
    this.updateCarForm = this.fb.group({
      brand: [null, [Validators.required]],
      name: [null, [Validators.required]],
      type: [null, [Validators.required]],
      transmission: [null, [Validators.required]],
      color: [null, [Validators.required]],
      year: [null, [Validators.required]],
      description: [null, [Validators.required]],
      price: [null, [Validators.required]]
    });
    this.getCars();
  }

  getCars(){
    this.service.getCarById(this.id).subscribe((res)=>{
      console.log(res);
      this.car = res;
      this.existingImage = 'data:image/jpeg;base64,'+ res.returnedImage;
      this.updateCarForm.patchValue(res);
    })
  }

  updateCar(){
    // Validate form
    if (this.updateCarForm.invalid) {
      this.message.error("Please fill in all required fields", {nzDuration: 3000});
      return;
    }

    // Validate image
    if (!this.selectedFile) {
      this.message.error("Please select an image", {nzDuration: 3000});
      return;
    }

    this.isSpinning = true;
    console.log(this.updateCarForm.value);
    console.log(this.selectedFile);

    const formData: FormData = new FormData();

    // Add image (required)
    formData.append("image", this.selectedFile);

    // Add all form fields
    formData.append("brand", this.updateCarForm.get('brand')?.value);
    formData.append("name", this.updateCarForm.get('name')?.value);
    formData.append("type", this.updateCarForm.get('type')?.value);
    formData.append("color", this.updateCarForm.get('color')?.value);
    formData.append("transmission", this.updateCarForm.get('transmission')?.value);
    formData.append("description", this.updateCarForm.get('description')?.value);
    formData.append("price", this.updateCarForm.get('price')?.value);
    formData.append("userId", StorageService.getUserId());

    // Format year as proper date (backend expects Date object)
    const year = this.updateCarForm.get('year')?.value;
    if (year) {
      // Create a proper date string: YYYY-MM-DD format
      const yearValue = new Date(year).getFullYear();
      const dateString = `${yearValue}-01-01`;
      formData.append("year", dateString);
    }

    this.service.updateCar(this.id, formData).subscribe((res) => {
      this.isSpinning = false;
      this.message.success("Car updated successfully!", {nzDuration: 5000});
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
    this.imageChanged = true;
    this.existingImage = null;
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
