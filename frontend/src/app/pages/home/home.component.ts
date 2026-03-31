import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import path from 'path';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-home',
  imports: [CommonModule, MaterialModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  title= 'Pagina Inicial';
  constructor(private router : Router){}


  navigateTo(route: string){
    this.router.navigate([route]);
  }
}
