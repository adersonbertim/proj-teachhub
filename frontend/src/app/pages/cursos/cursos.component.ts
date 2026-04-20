import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material-module';

@Component({
  selector: 'app-cursos',
  imports: [CommonModule, FormsModule, MaterialModule],
  templateUrl: './cursos.component.html',
  styleUrl: './cursos.component.scss'
})
export class CursosComponent {
temas: any;

}
