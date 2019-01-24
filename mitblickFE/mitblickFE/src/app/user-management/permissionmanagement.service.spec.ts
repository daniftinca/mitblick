import {TestBed} from '@angular/core/testing';

import {PermissionmanagementService} from './permissionmanagement.service';

describe('PermissionmanagementService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: PermissionmanagementService = TestBed.get(PermissionmanagementService);
    expect(service).toBeTruthy();
  });
});
