import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostagemDetalheComponent } from './postagem-detalhe.component';

describe('PostagemDetalheComponent', () => {
  let component: PostagemDetalheComponent;
  let fixture: ComponentFixture<PostagemDetalheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostagemDetalheComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostagemDetalheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
