import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HouseHomeRegisterComponent } from './house-home-register.component';

describe('HouseHomeRegisterComponent', () => {
  let component: HouseHomeRegisterComponent;
  let fixture: ComponentFixture<HouseHomeRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HouseHomeRegisterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HouseHomeRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
