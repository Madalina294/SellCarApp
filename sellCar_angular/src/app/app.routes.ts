import { Routes } from '@angular/router';
import {LoginComponent} from './auth/auth-components/login/login.component';
import {SingupComponent} from './auth/auth-components/singup/singup.component';

export const routes: Routes = [
  {path:"login", component:LoginComponent},
  {path:"signup", component: SingupComponent},
  {path:"admin", loadChildren:() =>
      import("./modules/admin/admin.module").then(e => e.AdminModule)},
  {path:"customer", loadChildren:() =>
      import("./modules/customer/customer.module").then(e => e.CustomerModule)}
];
