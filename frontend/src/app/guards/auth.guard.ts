import { isPlatformBrowser } from '@angular/common';
import { inject,PLATFORM_ID  } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
   const platformId = inject(PLATFORM_ID);

  const router = inject(Router);
    if (!isPlatformBrowser(platformId)) {
    return true;
  }
 
  const token = localStorage.getItem('teachhub_token');

  if (token) {
    return true;
  }

  router.navigate(['/login']);
  return false;
};