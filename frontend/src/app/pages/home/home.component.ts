import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import path from 'path';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  private router = inject(Router)

irPara(arg0: string) {
throw new Error('Method not implemented.');
}

  constructor(){}


  navigateTo(rota: string){
    this.router.navigate([`/${rota}`]);
  }
}
