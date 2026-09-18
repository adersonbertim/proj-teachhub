import { HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);
  const emBrowser = isPlatformBrowser(platformId);

  let requisicao = req;


  if (emBrowser) {
    const token = localStorage.getItem('teachhub_token');
    if (token) {
      requisicao = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
  }

  return next(requisicao).pipe(
    catchError((erro) => {

      if (erro.status === 401 && emBrowser) {
        localStorage.removeItem('teachhub_token');
        alert('Sua sessão expirou. Faça login novamente.');
        router.navigate(['/login']);
      }
      return throwError(() => erro);
    })
  );
};