import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CustomerDashboardComponent} from '../customer/components/customer-dashboard/customer-dashboard.component';
import {AdminDashboardComponent} from './components/admin-dashboard/admin-dashboard.component';
import {GetBookingsComponent} from './components/get-bookings/get-bookings.component';
import {SearchCarsComponent} from './components/search-cars/search-cars.component';

const routes: Routes = [
  {path:'dashboard', component:AdminDashboardComponent},
  {path:'bookings', component:GetBookingsComponent},
  {path:'search-cars', component:SearchCarsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
