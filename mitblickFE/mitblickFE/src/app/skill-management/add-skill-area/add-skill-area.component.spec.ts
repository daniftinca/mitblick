import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddSkillAreaComponent} from './add-skill-area.component';

describe('AddSkillAreaComponent', () => {
  let component: AddSkillAreaComponent;
  let fixture: ComponentFixture<AddSkillAreaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddSkillAreaComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSkillAreaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
