import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ManageAllUsersComponent} from './manage-all-users.component';

describe('ManageAllUsersComponent', () => {
  let component: ManageAllUsersComponent;
  let fixture: ComponentFixture<ManageAllUsersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageAllUsersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageAllUsersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
