import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { FooterComponent } from '../footer/footer.component';
// Importe seus componentes de layout aqui
// import { SidebarComponent } from './sidebar/sidebar.component';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [
    CommonModule, 
    RouterModule,
    HeaderComponent,
    SidebarComponent,
    FooterComponent,
  ], 
  templateUrl: './main-layout.component.html',
  styleUrl: './main-layout.component.scss'
})
export class MainLayoutComponent {

  isDarkMode = false;
  isSidebarCollapsed = false;

  toggleDarkMode(){
    this.isDarkMode = !this.isDarkMode;

    if(this.isDarkMode){
      document.body.classList.add('dark-theme');
    } else {
      document.body.classList.remove('dark-theme');
    }

  }

  handleSideBarToggle() {
    console.log('O Pai recebeu o aviso!');
    this.isSidebarCollapsed = !this.isSidebarCollapsed;
  }

}