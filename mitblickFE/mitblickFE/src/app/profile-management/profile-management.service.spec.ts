import {TestBed} from '@angular/core/testing';

import {ProfileManagementService} from './profile-management.service';

describe('ProfileManagementService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ProfileManagementService = TestBed.get(ProfileManagementService);
    expect(service).toBeTruthy();
  });
});
