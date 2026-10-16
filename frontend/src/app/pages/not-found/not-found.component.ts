import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-not-found',
  imports: [CommonModule, MaterialModule, RouterLink],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.scss'
})
export class NotFoundComponent {
  caminho: string;

  constructor(private location: Location, router: Router) {
    this.caminho = router.url;
  }

  voltar() {
    this.location.back();
  }
}