import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterFormPetHouseComponent } from './register-form-pet-house.component';

describe('RegisterFormPetHouseComponent', () => {
  let component: RegisterFormPetHouseComponent;
  let fixture: ComponentFixture<RegisterFormPetHouseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RegisterFormPetHouseComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterFormPetHouseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
