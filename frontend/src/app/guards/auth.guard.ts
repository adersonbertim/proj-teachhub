import { isPlatformBrowser } from '@angular/common';
import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { tokenValido } from '../core/services/auth/token';

export const authGuard: CanActivateFn = () => {
  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);

  if (!isPlatformBrowser(platformId)) {
    return true;
  }

  if (tokenValido()) {
    return true;
  }

  return router.createUrlTree(['/login']);
};