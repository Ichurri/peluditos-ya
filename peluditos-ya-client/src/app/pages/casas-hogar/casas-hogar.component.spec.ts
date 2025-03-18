import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CasasHogarComponent } from './casas-hogar.component';

describe('CasasHogarComponent', () => {
  let component: CasasHogarComponent;
  let fixture: ComponentFixture<CasasHogarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CasasHogarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CasasHogarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
