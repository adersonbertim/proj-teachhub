import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { MaterialModule } from '../../material-module';




@Component({
  selector: 'app-header',
  imports: [MaterialModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})


export class HeaderComponent {
  constructor(private router: Router) {}

  fontSize= 100; //(16px)
  @Input() isDarkMode: boolean = false;
  @Output() toggleSideBar = new EventEmitter <void>();
  @Output() toggleDark = new EventEmitter<void> ();

  onToggleSidebar(){
    this.toggleSideBar.emit();
  }

  onToggleDark(){
    this.toggleDark.emit();
  }

  changeFontSize(action: 'diminuir' | 'resetar' | 'aumentar'){
    if(action === 'diminuir' && this.fontSize > 50 ) this.fontSize -= 10;
    if(action === 'resetar') this.fontSize = 100;
    if(action === 'aumentar' && this.fontSize < 150 ) this.fontSize += 10;

    document.documentElement.style.fontSize = `${this.fontSize}%`
  }

  goToIA(){
    this.router.navigate(['/ia']);
  }

}


