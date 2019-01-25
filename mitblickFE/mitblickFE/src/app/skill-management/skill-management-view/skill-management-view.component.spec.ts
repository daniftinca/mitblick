import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SkillManagementViewComponent} from './skill-management-view.component';

describe('SkillManagementViewComponent', () => {
  let component: SkillManagementViewComponent;
  let fixture: ComponentFixture<SkillManagementViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SkillManagementViewComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillManagementViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
