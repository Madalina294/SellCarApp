import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {CustomerService} from '../../services/customer.service';
import {Router} from '@angular/router';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzSpinComponent} from 'ng-zorro-antd/spin';
import {NzFormControlComponent, NzFormItemComponent, NzFormLabelComponent} from 'ng-zorro-antd/form';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';
import {DatePipe, NgForOf} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzColDirective, NzRowDirective} from 'ng-zorro-antd/grid';

@Component({
  selector: 'app-search-car',
  imports: [
    NzSpinComponent,
    ReactiveFormsModule,
    NzFormItemComponent,
    NzFormLabelComponent,
    NzFormControlComponent,
    NzSelectComponent,
    NzOptionComponent,
    NgForOf,
    NzButtonComponent,
    DatePipe,
    NzColDirective,
    NzRowDirective
  ],
  templateUrl: './search-car.component.html',
  styleUrl: './search-car.component.scss'
})
export class SearchCarComponent {
  listOfBrands = ["BMW", "AUDI", "MERCEDES", "FERRARI", "TESLA", "VOLVO", "TOYOTA", "HONDA", "FORD", "NISSAN", "HYUNDAI", "LEXUS", "KIA", "HAVAL"];
  listOfTypes = ["Petrol", "Hybrid", "Diesel","Electric", "CNG"];
  listOfColor = ["Red", "White", "Silver", "Blue", "Black", "Orange", "Grey"];
  listOfTransmission = ["Manual", "Automatic" ];
  searchCarForm!: FormGroup;
  isSpinning: boolean = false;
  cars:any[]=[];

  constructor(private service: CustomerService,
              private fb: FormBuilder,
              private router: Router,
              private message: NzMessageService) {}

  ngOnInit(): void {
    this.searchCarForm = this.fb.group({
      brand: [null],
      type: [null],
      transmission: [null],
      color: [null]
    });
  }

  searchCar(){
    this.isSpinning = true;
    this.cars=[];
    
    const searchCriteria = this.searchCarForm.value;
    console.log('Search Criteria sent to backend:', searchCriteria);
    
    // Check if all criteria are null/empty (search all cars)
    const isEmptySearch = !searchCriteria.brand && !searchCriteria.type && 
                         !searchCriteria.color && !searchCriteria.transmission;
    
    if (isEmptySearch) {
      console.log('Empty search - getting all cars');
      this.service.getAllCars().subscribe((res) => {
        this.isSpinning = false;
        this.cars = res;
        console.log('All cars received:', res);
        this.message.success(`Found ${res.length} car(s) total`, { nzDuration: 3000 });
      }, error => {
        this.isSpinning = false;
        console.error('Get all cars error:', error);
        this.message.error('Error getting cars', { nzDuration: 5000 });
      });
    } else {
      this.service.searchCars(searchCriteria).subscribe((res) => {
        this.isSpinning = false;
        this.cars=res;
        console.log('Search Results received:', res);
        console.log('Number of results:', res.length);
        
        if (res.length === 0) {
          this.message.info('No cars found matching your criteria', { nzDuration: 5000 });
        } else {
          this.message.success(`Found ${res.length} car(s) matching your criteria`, { nzDuration: 3000 });
        }
      }, error => {
        this.isSpinning = false;
        console.error('Search error:', error);
        this.message.error('Error searching cars: ' + (error.error?.message || error.message), { nzDuration: 5000 });
      });
    }
  }

}
