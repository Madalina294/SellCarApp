import { Component } from '@angular/core';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {NzHeaderComponent} from 'ng-zorro-antd/layout';
import {NzRowDirective} from 'ng-zorro-antd/grid';
import {NzButtonComponent} from 'ng-zorro-antd/button';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NzHeaderComponent, NzRowDirective, RouterLink, NzButtonComponent, RouterLinkActive, NzButtonComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'sellCar_angular';
}
