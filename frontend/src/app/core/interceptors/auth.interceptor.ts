import { HttpInterceptorFn } from '@angular/common/http';
import { inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import Swal from 'sweetalert2';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const platformId = inject(PLATFORM_ID);
  const router = inject(Router);
  const emBrowser = isPlatformBrowser(platformId);

  let requisicao = req;
  let enviouToken = false;

  if (emBrowser) {
    const token = localStorage.getItem('teachhub_token');
    if (token) {
      enviouToken = true;
      requisicao = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }
  }

  const rotaDeAutenticacao = req.url.includes('/auth');

  return next(requisicao).pipe(
    catchError((erro) => {

     if (erro.status === 401 && emBrowser && enviouToken && !rotaDeAutenticacao) {
        localStorage.removeItem('teachhub_token');
        localStorage.removeItem('teachhub_role');
        Swal.fire({
          title: 'Sessão expirada',
          text: 'Sua sessão expirou. Faça login novamente.',
          icon: 'warning',
          confirmButtonText: 'OK'
        }).then(() => {
          router.navigate(['/login']);
        });
      }
      return throwError(() => erro);
    })
  );
};