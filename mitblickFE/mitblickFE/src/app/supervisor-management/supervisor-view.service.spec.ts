import {TestBed} from '@angular/core/testing';

import {SupervisorViewService} from './supervisor-view.service';

describe('SupervisorViewService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SupervisorViewService = TestBed.get(SupervisorViewService);
    expect(service).toBeTruthy();
  });
});
