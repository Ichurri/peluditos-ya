import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EnAdopcionComponent } from './en-adopcion.component';

describe('EnAdopcionComponent', () => {
  let component: EnAdopcionComponent;
  let fixture: ComponentFixture<EnAdopcionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnAdopcionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EnAdopcionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
